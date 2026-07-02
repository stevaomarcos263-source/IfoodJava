package br.edu.ifpb.ads.foodjava.controller;

import br.edu.ifpb.ads.foodjava.model.Restaurante;
import br.edu.ifpb.ads.foodjava.repository.RestauranteRepository;

public class RestauranteController {

    private final RestauranteRepository restauranteRepository;

    public RestauranteController() {
        restauranteRepository = new RestauranteRepository();
    }

    public Restaurante obterRestaurante() {
        return restauranteRepository.getRestaurante();
    }

    public void salvarRestaurante(Restaurante restaurante){
        restauranteRepository.salvarRestauranteNaRepository(restaurante);
    }

}