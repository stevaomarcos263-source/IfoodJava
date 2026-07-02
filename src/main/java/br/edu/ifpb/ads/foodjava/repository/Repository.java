package br.edu.ifpb.ads.foodjava.repository;

import br.edu.ifpb.ads.foodjava.model.enums.StatusPedido;

import java.util.List;

public interface Repository<T,ID> {

    void salvarNovoItem(T entidade);
    T buscarPorId(ID id);
    List<T> buscarTodosOsPedidos();
    void atualizarStatusDoPedido(T pedido);
    List<T> filtroDePedidoComCpf(ID id);
}

