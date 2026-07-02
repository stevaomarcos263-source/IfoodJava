package br.edu.ifpb.ads.foodjava.util;

import br.edu.ifpb.ads.foodjava.model.Cliente;
import br.edu.ifpb.ads.foodjava.model.User;

public class UsuarioLogadoNoSistema {

    private static Cliente usuarioLogado;

    public static void setUsuarioLogado(Cliente logadoNoMomento){
        usuarioLogado = logadoNoMomento;
    }
    public static Cliente getUsuarioLogado(){
        return usuarioLogado;
    }
    public static void esquecerUsuario(){
        usuarioLogado = null;
    }
}
