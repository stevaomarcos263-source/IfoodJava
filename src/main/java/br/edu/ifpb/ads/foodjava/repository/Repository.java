package br.edu.ifpb.ads.foodjava.repository;

import java.util.List;

public interface Repository<T,ID> {

    void salvar(T entidade);
    T buscarPorId(ID id);
    List<T> buscarTodos();
    void atualizar(T entidade);
    void deletar(ID id);
}
