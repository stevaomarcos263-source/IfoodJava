package br.edu.ifpb.ads.foodjava;

import br.edu.ifpb.ads.foodjava.controller.RestauranteController; // Trocado para o Controller!
import br.edu.ifpb.ads.foodjava.util.UsuarioLogadoNoSistema;
import br.edu.ifpb.ads.foodjava.view.TelaCadastrarRestaurante;
import br.edu.ifpb.ads.foodjava.view.TelaLogin;
import br.edu.ifpb.ads.foodjava.util.UsuarioLogadoNoSistema;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Ponto de entrada da aplicação FoodJava.
 */
public class MainApp extends Application {

    // quem dita as regras é a Controller do Restaurante
    private RestauranteController restauranteController = new RestauranteController();

    @Override
    public void start(Stage stage) {

        // 1. A decisão é tomada através do método da Controller
        if (restauranteController.obterRestaurante().getNomeDoRestaurante().equalsIgnoreCase("vazio")) {

            // Se NÃO EXISTIR restaurante, força a abertura da tela de cadastro de primeira
            TelaCadastrarRestaurante cadastrarRestauranteView = new TelaCadastrarRestaurante();
            Scene cenaCadastro = new Scene(cadastrarRestauranteView.getLayout(), 500, 650);

            stage.setTitle("FoodJava - Primeiro Acesso (Cadastrar Restaurante)");
            stage.setScene(cenaCadastro);

        } else {

            // Se JÁ EXISTIR um restaurante cadastrado, o sistema abre no Login
            TelaLogin loginView = new TelaLogin();
            Scene cenaLogin = new Scene(loginView.getLayout(), 400, 500);

            stage.setTitle("FoodJava - Login");
            stage.setScene(cenaLogin);
        }

        // ======================================================================
        // GATILHO ADICIONADO: Executa sempre que o usuário clicar no "X" para fechar
        // ======================================================================
        stage.setOnCloseRequest(event -> {
            System.out.println("Janela fechada pelo usuário. Limpando dados do UsuarioLogadoNoSistema...");
            UsuarioLogadoNoSistema.esquecerUsuario();
        });

        // Exibe a janela escolhida pelo fluxo lógico
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}