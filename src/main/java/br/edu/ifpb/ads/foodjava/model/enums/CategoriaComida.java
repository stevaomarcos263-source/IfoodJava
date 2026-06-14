package br.edu.ifpb.ads.foodjava.model.enums;

public enum CategoriaComida {

    ENTRADA("Entrada"),
    PRATO_PRINCIPAL("Prato principal"),
    SOBREMESA("Sobremesa"),
    BEBIDAS("Bebidas");

    private final String categoria;
    CategoriaComida(String categoria){
        this.categoria = categoria;
    }

    // retorna a mensagem passada em específico do enum;
    public String toString(){
        return categoria;
    }
}
