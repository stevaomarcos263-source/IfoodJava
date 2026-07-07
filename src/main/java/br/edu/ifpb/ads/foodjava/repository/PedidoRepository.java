package br.edu.ifpb.ads.foodjava.repository;

import br.edu.ifpb.ads.foodjava.exception.*;
import br.edu.ifpb.ads.foodjava.model.ItemCardapio;
import br.edu.ifpb.ads.foodjava.model.Pedido;
import br.edu.ifpb.ads.foodjava.model.PedidoPreMoldado;
import br.edu.ifpb.ads.foodjava.model.enums.StatusPedido;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.List;

public class PedidoRepository implements RepositoryPedido<Pedido,String> {

    private static final String FILE_PATH = "src/main/resources/data/pedidos.json";

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private List<Pedido> listaDePedidos;



    public PedidoRepository(){
        inicializarArquivo();
        listaDePedidos = buscarTodosOsPedidos();
    }


    /**
     * @param nome;
     * @return {@link PedidoRepository}
     */
    public boolean liberarItemParaDesativarOuExcluir(String nome){
        // esse buscarTodos() retorna uma lista de Pedidos estando pré-moldados com quantidade, valor total e etc...;
        return buscarTodosOsPedidos().stream().filter(itemDoPedido -> itemDoPedido.getStatusPedido() == StatusPedido.CONFIRMADO || itemDoPedido.getStatusPedido() == StatusPedido.EM_PREPARO)
                .anyMatch( listaDeCadaPedido -> listaDeCadaPedido.getListaPedidosPreMoldadosComItemEQuantidade().stream().anyMatch(pedidos -> pedidos.getItem().getNome().equalsIgnoreCase(nome)));
    }

    /**
     * @param cpfDoCliente;
     * @return {@link Pedido}
     */
    // Filtro de pedidos através do cpf do cliente ->
    @Override
    public List<Pedido> filtroDePedidoComCpf(String cpfDoCliente) {
        return buscarTodosOsPedidos().stream().filter(pedido -> pedido.getCpfCliente().equalsIgnoreCase(cpfDoCliente)).toList();
    }



    @Override
    public void atualizarStatusDoPedido(Pedido pedidoAtualizado) {
        List<Pedido> listaGeral = buscarTodosOsPedidos();

        if (listaGeral != null) {
            for (int i = 0; i < listaGeral.size(); i++) {
                if (listaGeral.get(i).getId().equalsIgnoreCase(pedidoAtualizado.getId())) {
                    listaGeral.set(i, pedidoAtualizado);
                    break;
                }
            }
            salvarListaDePedidosNoArquivo(listaGeral);
        }
    }


    /**
     @param cpfDoCliente;
     @param pedidosDoCarrinho;
     @return {@link Pedido};
     @throws IllegalArgumentException;
     @throws CarrinhoVazioException;
    **/
    public Pedido fecharPedido(String cpfDoCliente, List<PedidoPreMoldado> pedidosDoCarrinho){
        if(cpfDoCliente==null || cpfDoCliente.isEmpty()){
            throw new IllegalArgumentException("Erro ao buscar CPF do cliente para finalizar pedido do carrinho!");
        }
        if(pedidosDoCarrinho==null || pedidosDoCarrinho.isEmpty()){
            throw new CarrinhoVazioException("Não foi possível finalizar pedido, motivo: Carrinho vazio!");
        }
        Pedido novoPedido = new Pedido(cpfDoCliente,pedidosDoCarrinho);
        salvarNovoItem(novoPedido);

        System.out.printf("Pedido realizado com sucesso: %n"+novoPedido);
        return novoPedido;
    }


    // Retorna a lista de pedidos ->
    @Override
    public List<Pedido> buscarTodosOsPedidos() {
        File arquivo = new File(FILE_PATH);
        if (arquivo.getParentFile() != null ){
            arquivo.getParentFile().mkdirs();
        }
        if (!arquivo.exists() || arquivo.length() == 0) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type tipoLista = new TypeToken<ArrayList<Pedido>>(){}.getType();
            List<Pedido> lista = gson.fromJson(reader, tipoLista);
            return lista != null ? lista : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Erro ao carregar pedidos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * @param id;
     * @throws IdInvalidoException;
     * @throws CancelamentoNaoPermitidoException;
     */
    public void cancelarPedidoNoRepository(String id){
        if(id==null){
            throw new IdInvalidoException("Pedido não encontrado");
        }
        List<Pedido> lista =  buscarTodosOsPedidos();
        boolean alterou = false;

        for(int i = 0; i<lista.size() ; i++){
            Pedido pedidoAtual = lista.get(i);
            if(lista.get(i).getId().equalsIgnoreCase(id) && lista.get(i).getStatusPedido()==StatusPedido.AGUARDANDO_CONFIRMACAO){
                pedidoAtual.setStatusPedido(StatusPedido.CANCELADO);
                alterou = true;
                break;
            }
        }
        if(alterou){
            salvarListaDePedidosNoArquivo(lista);
            System.out.println("Pedido cancelado com sucesso");
        }else{
            throw new CancelamentoNaoPermitidoException("Pedido não pode ser cancelado...");
        }
    }


    /**
     * @param idPedido;
     * @throws IllegalArgumentException;
     * @throws CancelamentoNaoPermitidoException;
     * @throws StatusInvalidoException;
     * @throws ItemVinculadoException;
     */
    public void avancarStatusDoPedidoRepository(String idPedido){
        PedidoRepository pedidoRepository = new PedidoRepository();
        List<Pedido> pedidos = pedidoRepository.buscarTodosOsPedidos();

        CardapioRepository cardapioRepository = new CardapioRepository();
        List<ItemCardapio> listaCardapio = cardapioRepository.buscarTodosOsItensDoCardapio();

        // confere se o pedido não possui nenhum item que esteja desativado, caso exista ele lança a exceção
        // criei esse metodo porquê ao desativar um pedido e existir algum pendendo com aquele item em específico, ele iria atualizar o pedido mesmo o item do cardápio estando desatualizado.
        for(int i = 0; i<pedidos.size() ; i++){
            if(pedidos.get(i).getId().equalsIgnoreCase(idPedido)){
                List<PedidoPreMoldado> pedidoPreMoldado = pedidos.get(i).getListaPedidosPreMoldadosComItemEQuantidade();
                for(int indice = 0; indice<pedidoPreMoldado.size() ; indice++){
                    ItemCardapio item = pedidoPreMoldado.get(indice).getItem();
                    if(listaCardapio.stream().anyMatch(itemCardapio -> itemCardapio.getNome().equalsIgnoreCase(item.getNome()) && itemCardapio.getDisponivel()==false)){
                        throw new ItemVinculadoException(String.format("Pedido possui item do cardápio com status de desativado, item a seguir: %s%n",pedidoPreMoldado.get(indice).getItem().getNome()));
                    }
                }
                break;
            }
        }


        Pedido pedidoEncontrado = pedidos.stream().filter(p -> p.getId().equalsIgnoreCase(idPedido)).findFirst().orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado no banco de dados pedidos.json!"));

        StatusPedido statusAtual = pedidoEncontrado.getStatusPedido();
        StatusPedido proximoStatus = null;

        for (StatusPedido stPedido : StatusPedido.values()) {
            if (stPedido.getPeso() == statusAtual.getPeso() + 1) {
                proximoStatus = stPedido;
                break;
            }
        }
        if (proximoStatus == null) {
            throw new StatusInvalidoException("O pedido já atingiu o status final!");
        }
        pedidoEncontrado.setStatusPedido(proximoStatus);

        pedidoRepository.atualizarStatusDoPedido(pedidoEncontrado);
    }

    /**
     * @param novoPedido;
     */
    @Override
    public void salvarNovoItem(Pedido novoPedido) {
        listaDePedidos.add(novoPedido);
        salvarListaDePedidosNoArquivo(listaDePedidos);
    }

    private void inicializarArquivo(){
        try{
            File arquivo = new File(FILE_PATH);
            if(arquivo.getParentFile() != null){
                arquivo.getParentFile().mkdirs();
            }
            if(!arquivo.exists()){
                arquivo.createNewFile();
                salvarListaDePedidosNoArquivo(new ArrayList<>());
            }
        }catch(IOException e){
            System.err.println("Erro ao inicializar arquivo: "+e.getMessage());
        }
    }
    /**
     * @param lista;
     */
    private void salvarListaDePedidosNoArquivo(List<Pedido> lista){
        try(FileWriter writer = new FileWriter(FILE_PATH)){
            gson.toJson(lista,writer);
        }catch(IOException e){
            System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }

}


