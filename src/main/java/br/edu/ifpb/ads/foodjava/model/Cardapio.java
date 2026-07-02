package br.edu.ifpb.ads.foodjava.model;

import java.util.ArrayList;
import java.util.List;

public class Cardapio {

    List<ItemCardapio> cardapio = new ArrayList<>();


    public List<ItemCardapio> getCardapio(){
        return cardapio;
    }
    public void adicionarItemEmCardapio(ItemCardapio itemCardapio){
        cardapio.add(itemCardapio);
    }
    public void removerItemEmCardapio(ItemCardapio itemCardapio){
        cardapio.remove(itemCardapio);
    }
    public void setCardapio(List<ItemCardapio> novoCardapio){
        cardapio = novoCardapio;
    }

}
