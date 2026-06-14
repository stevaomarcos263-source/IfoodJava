package br.edu.ifpb.ads.foodjava.model;

public class ItemPedido {

    private ItemCardapio item;
    private int qtda;
    private double precoUnitario;

    protected ItemPedido(){}
    public ItemPedido(ItemCardapio item, int qtda){
        this.item = item;
        this.qtda = qtda;
        precoUnitario = item.getPreco();
    }

    // Getters ->
    public double getSubTotal(){
        return precoUnitario*qtda;
    }
    public ItemCardapio getItem(){
        return item;
    }
    public int getQtda(){
        return qtda;
    }
    public double getPrecoUnitario(){
        return precoUnitario;
    }

    // Setters ->
    public void setQtda(int qtda){
        if(qtda>0) {
            this.qtda = qtda;
        }
    }
}
