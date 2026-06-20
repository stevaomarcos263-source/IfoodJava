package br.edu.ifpb.ads.foodjava.model;

public class PedidoPreMoldado {

    private ItemCardapio itemCardapio;
    private int qtda;
    private double precoUnitario;

    protected PedidoPreMoldado(){}
    public PedidoPreMoldado(ItemCardapio itemCardapio, int qtda){
        this.itemCardapio = itemCardapio;
        this.qtda = qtda;
        precoUnitario = itemCardapio.getPreco();
    }

    // Getters ->
    public double getSubTotal(){
        return precoUnitario*qtda;
    }
    public ItemCardapio getItem(){
        return itemCardapio;
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
    @Override
    public String toString(){
        String nomeItem = (getItem().getNome()!=null)? nomeItem = getItem().getNome() : "Item não identificado";
        return String.format("%s (Qtd: %d) -Preço Unit: %.2f - Subtotal: %.2f",nomeItem,getQtda(),getPrecoUnitario(),getSubTotal());
    }

}
