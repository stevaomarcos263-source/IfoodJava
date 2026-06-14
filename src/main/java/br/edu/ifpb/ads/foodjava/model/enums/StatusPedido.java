package br.edu.ifpb.ads.foodjava.model.enums;
/*
AGUARDANDO_CONFIRMACAO → CONFIRMADO → EM_PREPARO →
SAIU_PARA_ENTREGA → ENTREGUE
 */

public enum StatusPedido {

    AGUARDANDO_CONFIRMACAO("Aguardando confirmação..."),
    CONFIRMADO("Pedido confirmado!"),
    EM_PREPARO("Em preparo"),
    SAIU_PARA_ENTREGA("Seu pedido está em rota de entrega"),
    ENTREGUE("Pedido entregue");


    private final String status;
    StatusPedido(String status){
        this.status = status;
    }

    public String toString(){
        return status;
    }

}
