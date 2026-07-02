package br.edu.ifpb.ads.foodjava.controller;

import br.edu.ifpb.ads.foodjava.model.ItemCardapio;
import br.edu.ifpb.ads.foodjava.repository.CardapioRepository;

import java.util.List;

public class CardapioController {

    private final CardapioRepository cardapioRepository;


    public CardapioController() {
        cardapioRepository = new CardapioRepository();
    }

    public List<ItemCardapio> obterCardapio() {
        return cardapioRepository.buscarTodosOsItensDoCardapio();
    }

    public void adicionarItemAoCardapio(ItemCardapio novoItem) {
        cardapioRepository.adicionarMaisUmItemNoCardapio(novoItem);
    }

    public void removerItemDoCardapio(String nome) {
        cardapioRepository.removerItemDoCardapio(nome);
    }

    public void mudarDisponibilidadeDoItem(boolean acao, String nome){
        cardapioRepository.activarOuDesativarItem(acao,nome);
    }

    public ItemCardapio editarItem(int indice, ItemCardapio itemCardapio){
        return cardapioRepository.editarItemDoCardapio(indice,itemCardapio);
    }


}
