package br.edu.ifpb.ads.foodjava.repository;

import java.util.List;

public interface RepositoryPedido<T,ID> {

    void salvarNovoItem(T entidade);
    List<T> buscarTodosOsPedidos();
    void atualizarStatusDoPedido(T pedido);
    List<T> filtroDePedidoComCpf(ID id);
}

