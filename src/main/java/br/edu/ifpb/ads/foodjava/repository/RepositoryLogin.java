package br.edu.ifpb.ads.foodjava.repository;

import java.util.List;

public interface RepositoryLogin<T,ID> {

    void salvarNovoUsuarioNoArquivoLoginJson(T entidade);
    T buscarUsuarioPorId(ID id);
    List<T> buscarTodosOsUsuarios();
}


