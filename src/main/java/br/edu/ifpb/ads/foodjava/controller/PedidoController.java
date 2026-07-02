package br.edu.ifpb.ads.foodjava.controller;


import br.edu.ifpb.ads.foodjava.exception.ItemVinculadoException;
import br.edu.ifpb.ads.foodjava.exception.StatusInvalidoException;
import br.edu.ifpb.ads.foodjava.model.ItemCardapio;
import br.edu.ifpb.ads.foodjava.model.PedidoPreMoldado;

import br.edu.ifpb.ads.foodjava.model.Carrinho;
import br.edu.ifpb.ads.foodjava.model.Pedido;

import br.edu.ifpb.ads.foodjava.model.enums.StatusPedido;
import br.edu.ifpb.ads.foodjava.repository.CardapioRepository;
import br.edu.ifpb.ads.foodjava.repository.PedidoRepository;
import br.edu.ifpb.ads.foodjava.util.UsuarioLogadoNoSistema;

import java.util.List;

public class PedidoController {

    private final PedidoRepository pedidoRepository;

    public PedidoController() {
        pedidoRepository = new PedidoRepository();
    }

    private Pedido fecharPedido(String cpfDoCliente, List<PedidoPreMoldado> itensCarrinho) {
        return pedidoRepository.fecharPedido(cpfDoCliente, itensCarrinho);
    }

    public void cadastrarNovoPedido(List<ItemCardapio> itensDoCarrinhoView) {
        Carrinho carrinho = new Carrinho();
        for (ItemCardapio item : itensDoCarrinhoView) {
            carrinho.adicionarItem(item, 1);
        }
        String cpfDoCliente = UsuarioLogadoNoSistema.getUsuarioLogado().getCpf();
        fecharPedido(cpfDoCliente, carrinho.getItensDoCarrinho());

    }

    public List<Pedido> obterListaDeTodosOsPedidos() {
        return pedidoRepository.buscarTodosOsPedidos();
    }

    public List<Pedido> buscarHistoricoDePedidosDoCliente(String cpfDoCliente) {
        return pedidoRepository.filtroDePedidoComCpf(cpfDoCliente);
    }


    public void avancarStatusDoPedido(String idPedido){
        pedidoRepository.avancarStatusDoPedidoRepository(idPedido);
    }

    public void cancelarPedido(Pedido pedido){
        pedidoRepository.cancelarPedidoNoRepository(pedido.getId());
    }



}