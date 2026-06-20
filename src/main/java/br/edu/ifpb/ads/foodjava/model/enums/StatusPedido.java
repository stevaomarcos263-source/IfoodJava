package br.edu.ifpb.ads.foodjava.model.enums;
/*
AGUARDANDO_CONFIRMACAO → CONFIRMADO → EM_PREPARO →
SAIU_PARA_ENTREGA → ENTREGUE
 */

public enum StatusPedido {

    AGUARDANDO_CONFIRMACAO("Aguardando confirmação...",1),
    CONFIRMADO("Pedido confirmado!",2),
    EM_PREPARO("Em preparo",3),
    SAIU_PARA_ENTREGA("Seu pedido está em rota de entrega",4),
    ENTREGUE("Pedido entregue",5);


    private final String status;
    private final int peso;
    StatusPedido(String status, int peso){
        this.status = status;
        this.peso = peso;
    }

    public int getPeso(){
        return peso;
    }
    public String toString(){
        return status;
    }

}
