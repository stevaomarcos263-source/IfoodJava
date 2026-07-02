package br.edu.ifpb.ads.foodjava.repository;

import br.edu.ifpb.ads.foodjava.exception.ArquivoImportacaoException;
import br.edu.ifpb.ads.foodjava.exception.CancelamentoNaoPermitidoException;
import br.edu.ifpb.ads.foodjava.exception.IdInvalidoException;
import br.edu.ifpb.ads.foodjava.exception.ItemVinculadoException;
import br.edu.ifpb.ads.foodjava.model.ItemCardapio;
import br.edu.ifpb.ads.foodjava.model.Cardapio;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CardapioRepository {

    PedidoRepository pedidoRepository = new PedidoRepository();

    private static final String FILE_PATH = "src/main/resources/data/cardapio.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    public CardapioRepository() {
        inicializarArquivo();
    }

    public List<ItemCardapio> buscarTodosOsItensDoCardapio() {
        File arquivo = new File(FILE_PATH);
        if (!arquivo.exists() || arquivo.length() == 0) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(FILE_PATH)) {

            Cardapio objetoCardapio = gson.fromJson(reader, Cardapio.class);
            if (objetoCardapio != null && objetoCardapio.getCardapio() != null) {
                return objetoCardapio.getCardapio();
            }
            return new ArrayList<>();
        }catch(com.google.gson.JsonSyntaxException e){
            throw new ArquivoImportacaoException("Arquivo importado com erro na escrita ou objetos inválidos...");
        }catch (IOException e) {
            System.err.println("Erro ao ler o cardápio do arquivo: " + e.getMessage());
            return new ArrayList<>();
        }
    }


    public void adicionarMaisUmItemNoCardapio(ItemCardapio novoItem) {
        List<ItemCardapio> cardapio = buscarTodosOsItensDoCardapio();
        if(novoItem != null && cardapio.stream().noneMatch(pedido -> pedido.getNome().equalsIgnoreCase(novoItem.getNome()))) {
            cardapio.add(novoItem);
            salvarListaNoArquivo(cardapio);
        }
    }


    public void removerItemDoCardapio(String nome) {
        List<ItemCardapio> lista = buscarTodosOsItensDoCardapio();
        boolean existe = lista.stream().anyMatch(item -> item.getNome().equalsIgnoreCase(nome));
        if (!existe) {
            throw new IllegalArgumentException("Item com o nome '" + nome + "' não foi encontrado no cardápio.");
        }
        boolean estaEmUso = pedidoRepository.liberarItemParaDesativarOuExcluir(nome);
        if (estaEmUso) {
            throw new ItemVinculadoException("Não é possível excluir! O item '" + nome + "' está em processo de preparação.");
        }
        lista.removeIf(x -> x.getNome().equalsIgnoreCase(nome));
        salvarListaNoArquivo(lista);
    }

    public void activarOuDesativarItem(Boolean acao, String nomeItem) {
        boolean validacaoDeNomeNoCardapio = buscarTodosOsItensDoCardapio().stream().anyMatch(itemCardapio -> itemCardapio.getNome().equalsIgnoreCase(nomeItem));
        if (validacaoDeNomeNoCardapio && !pedidoRepository.liberarItemParaDesativarOuExcluir(nomeItem)) {
            List<ItemCardapio> lista = buscarTodosOsItensDoCardapio();
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getNome().equalsIgnoreCase(nomeItem)) {
                    lista.get(i).setDisponivel(acao);
                    break;
                }
            }
            salvarListaNoArquivo(lista);
        }else{
            throw new CancelamentoNaoPermitidoException("Item está em processo de desenvolvimento, tente novamente mais tarde!");
        }
    }

    public ItemCardapio editarItemDoCardapio(int indice, ItemCardapio itemEditado){

        if(itemEditado==null){
            throw new IdInvalidoException("Item não existe no cardápio!");
        }
        List<ItemCardapio> lista = buscarTodosOsItensDoCardapio();
        if(indice<0 || indice>=lista.size()){
            throw new IllegalArgumentException("Posição inválida no cardápio");
        }
        lista.set(indice,itemEditado);
        salvarListaNoArquivo(lista);

        return itemEditado;
    }

    //region verificar depois ->
    public List<ItemCardapio> importarCardapio(File arquivo)throws ArquivoImportacaoException, IOException{

        List<ItemCardapio> itensValidos = new ArrayList<>();
        try(FileReader reader = new FileReader(arquivo)){
            Cardapio cardapio = gson.fromJson(reader,Cardapio.class);
            if(cardapio==null || cardapio.getCardapio()==null || cardapio.getCardapio().isEmpty()){
                throw new ArquivoImportacaoException("Arquivo corrompido!");
            }
            for(ItemCardapio item : cardapio.getCardapio()){
                if(item.getNome()!=null && !item.getNome().isEmpty() &&
                item.getCategoriaComida()!=null &&
                item.getDisponivel()!=null &&
                item.getDescricao()!=null && !item.getDescricao().isEmpty() &&
                item.getImagemPath()!=null && !item.getImagemPath().isEmpty() &&
                item.getPreco()>0){
                    itensValidos.add(item);
                }
            }
        }
        salvarListaNoArquivo(itensValidos);
        return itensValidos;
    }
    //endregion

    //region inicializador de arquivo privado ->
    private void inicializarArquivo() {
        try {
            File arquivo = new File(FILE_PATH);
            if (arquivo.getParentFile() != null && !arquivo.getParentFile().exists()) {
                arquivo.getParentFile().mkdirs();
            }
            if (!arquivo.exists()) {
                arquivo.createNewFile();
                salvarListaNoArquivo(new ArrayList<>());
            }
        } catch (IOException e) {
            System.err.println("Erro ao inicializar o arquivo cardapio.json: " + e.getMessage());
        }
    }
    //endregion

    //region salvar novo cardapio "lista" no arquivoJson ->
    private void salvarListaNoArquivo(List<ItemCardapio> lista) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            Cardapio listaDoCardapio = new Cardapio();
            listaDoCardapio.setCardapio(lista);
            gson.toJson(listaDoCardapio, writer);
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados no cardapio.json: " + e.getMessage());
        }
    }
    //endregion

}