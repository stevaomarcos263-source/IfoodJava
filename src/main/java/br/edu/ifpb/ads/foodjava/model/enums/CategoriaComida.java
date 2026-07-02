package br.edu.ifpb.ads.foodjava.model.enums;

public enum CategoriaComida {

    ENTRADA("ENTRADA"),
    PRATO_PRINCIPAL("PRATO_PRINCIPAL"),
    SOBREMESA("SOBREMESA"),
    BEBIDA("BEBIDA");

    private final String categoria;
    CategoriaComida(String categoria){
        this.categoria = categoria;
    }

    // retorna a mensagem passada em específico do enum;
    public String toString(){
        return categoria;
    }
}
