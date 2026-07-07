package br.edu.ifpb.ads.foodjava.repository;

import br.edu.ifpb.ads.foodjava.model.ItemCardapio;

import java.util.List;

public interface RepositoryLogin<T,ID> {

    void salvarNovoUsuarioNoArquivoLoginJson(T entidade);
    List<T> buscarTodosOsUsuarios();

}


