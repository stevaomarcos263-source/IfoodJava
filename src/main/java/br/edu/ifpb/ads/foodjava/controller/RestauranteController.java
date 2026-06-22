package br.edu.ifpb.ads.foodjava.controller;

import br.edu.ifpb.ads.foodjava.exception.ItemVinculadoException;
import br.edu.ifpb.ads.foodjava.exception.PrecoInvalidoException;
import br.edu.ifpb.ads.foodjava.model.ItemCardapio;
import br.edu.ifpb.ads.foodjava.model.Restaurante;
import br.edu.ifpb.ads.foodjava.repository.CardapioRepository;
import br.edu.ifpb.ads.foodjava.repository.RestauranteRepository;
import java.util.List;

public class RestauranteController {

    private final RestauranteRepository restauranteRepository;
    private final CardapioRepository cardapioRepository; // Adicionado o novo repositório

    public RestauranteController() {
        restauranteRepository = new RestauranteRepository();
        cardapioRepository = new CardapioRepository(); // Inicializado aqui
    }

    public Restaurante obterRestaurante() {
        return restauranteRepository.getRestaurante();
    }

    public List<ItemCardapio> obterCardapio() {
        return cardapioRepository.buscarTodos();
    }


    public void adicionarItemAoCardapio(ItemCardapio novoItem) throws ItemVinculadoException, PrecoInvalidoException {
        if (novoItem == null) {
            throw new IllegalArgumentException("O item do cardápio não pode ser nulo.");
        }
        if (novoItem.getPreco() <= 0) {
            throw new PrecoInvalidoException("Preço de item do cardápio menor ou igual a zero.");
        }

        List<ItemCardapio> cardapioAtual = cardapioRepository.buscarTodos();
        boolean existenciaDeItem = cardapioAtual.stream().anyMatch(refeicao -> refeicao.getNome().equalsIgnoreCase(novoItem.getNome()));

        if (existenciaDeItem) {
            throw new ItemVinculadoException("Existência de item já cadastrado");
        }
        cardapioRepository.salvar(novoItem);
    }

    public void removerItemDoCardapio(String nome) {

        List<ItemCardapio> cardapioAtual = cardapioRepository.buscarTodos();
        boolean removido = cardapioAtual.removeIf(item -> item.getNome().equalsIgnoreCase(nome));

        if (removido) {
            cardapioRepository.salvarTodos(cardapioAtual);
        } else {
            throw new IllegalArgumentException("Item com o nome informado não foi encontrado.");
        }
    }
}