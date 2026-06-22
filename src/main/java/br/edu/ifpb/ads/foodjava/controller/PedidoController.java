package br.edu.ifpb.ads.foodjava.controller;

import br.edu.ifpb.ads.foodjava.exception.CarrinhoVazioException;
import br.edu.ifpb.ads.foodjava.model.Cliente;
import br.edu.ifpb.ads.foodjava.model.ItemCardapio;
import br.edu.ifpb.ads.foodjava.model.PedidoPreMoldado;

import br.edu.ifpb.ads.foodjava.model.Pedido;
import br.edu.ifpb.ads.foodjava.model.enums.StatusPedido;
import br.edu.ifpb.ads.foodjava.repository.PedidoRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class PedidoController {

    private final PedidoRepository pedidoRepository;

    public PedidoController() {
        this.pedidoRepository = new PedidoRepository();
    }

    /**
     * Regra de negócio: Transforma a lista de itens selecionados na tela em um Pedido definitivo.
     */
    public Pedido fecharPedido(String cpfDoCliente, List<PedidoPreMoldado> itensCarrinho) throws CarrinhoVazioException {
        if (itensCarrinho == null || itensCarrinho.isEmpty()) {
            throw new CarrinhoVazioException("O carrinho não pode estar vazio para fechar um pedido!");
        }
        // Instancia o novo pedido (ajuste os parâmetros de acordo com o construtor da sua classe Pedido)
        Pedido novoPedido = new Pedido(cpfDoCliente, itensCarrinho);

        // Salva na persistência do arquivo JSON
        pedidoRepository.salvar(novoPedido);
        return novoPedido;
    }

    /**
     * Filtro com Stream/Lambda: Retorna o histórico de pedidos específicos de um Cliente.
     */
    public List<Pedido> buscarHistoricoDoCliente(String cpfDoCliente) {
        return pedidoRepository.buscarTodos().stream()
                .filter(pedido -> pedido.getCpfCliente().equals(cpfDoCliente))
                .collect(Collectors.toList());
    }

    /**
     * Filtro com Stream/Lambda: Retorna todos os pedidos que o Gerente precisa preparar ou enviar.
     */
    public List<Pedido> buscarPedidosAtivosParaOGerente() {
        return pedidoRepository.buscarTodos().stream()
                .filter(pedido -> pedido.getStatusPedido().toString().equalsIgnoreCase("PENDENTE")
                        || pedido.getStatusPedido().toString().equalsIgnoreCase("EM_PREPARO"))
                .collect(Collectors.toList());
    }

    /**
     * Regra de negócio: Altera o estado do delivery (ex: de PENDENTE -> EM_PREPARO -> SAIU_PARA_ENTREGA).
     */
    public void avancarStatusPedido(String cpfDoCliente, StatusPedido novoStatus) {
        Pedido pedido = pedidoRepository.buscarPorId(cpfDoCliente);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado no sistema.");
        }

        pedido.setStatusPedido(novoStatus);
        pedidoRepository.atualizar(pedido);
    }
}