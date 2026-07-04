package br.edu.ifpb.ads.foodjava.repository;

import br.edu.ifpb.ads.foodjava.exception.CancelamentoNaoPermitidoException;
import br.edu.ifpb.ads.foodjava.exception.CarrinhoVazioException;
import br.edu.ifpb.ads.foodjava.exception.IdInvalidoException;
import br.edu.ifpb.ads.foodjava.exception.StatusInvalidoException;
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

import static java.util.stream.Collectors.toList;

public class PedidoRepository implements Repository<Pedido,String> {

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



    // Filtra os pedidos pelo Status colocado no "status" como -> ( StatusPedido.EM_CONFIRMACAO );
    public List<Pedido> filtroDeListaViaPorStatus(StatusPedido status){
        return listaDePedidos.stream().filter(pedido -> pedido.getStatusPedido() == status).toList();
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
     * @param idPedido
     * @throws IllegalArgumentException;
     * @throws CancelamentoNaoPermitidoException;
     * @throws StatusInvalidoException;
     */
    public void avancarStatusDoPedidoRepository(String idPedido){
        PedidoRepository pedidoRepository = new PedidoRepository();

        List<Pedido> pedidos = pedidoRepository.buscarTodosOsPedidos();

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
     * @param id
     * @return {@link Pedido}
     */
    // Entrega o pedido localizado pelo ID ->
    @Override
    public Pedido buscarPorId(String id) {
        return listaDePedidos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
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


