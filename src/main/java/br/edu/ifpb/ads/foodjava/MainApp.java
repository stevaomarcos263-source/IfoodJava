package br.edu.ifpb.ads.foodjava;

import br.edu.ifpb.ads.foodjava.view.TelaLogin; // Único import adicionado para enxergar sua tela
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;



/**
 * Ponto de entrada da aplicação FoodJava.
 *
 * Esta classe deve ser mantida mínima — ela apenas inicializa o JavaFX
 * e carrega a primeira tela. Toda a lógica de negócio deve ficar nos
 * pacotes model, controller e repository.
 *
 * DICA: para carregar uma tela FXML, substitua o conteúdo de start() por:
 *
 * FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
 * Parent root = loader.load();
 * stage.setScene(new Scene(root, 900, 600));
 * stage.show();
 */
public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        // 1. Instancia a sua classe de login customizada
        TelaLogin loginView = new TelaLogin();

        // 2. Cria a Scene passando o layout encapsulado do seu VBox (telaLogin)
        Scene cenaInicial = new Scene(loginView.getLayout(), 400, 500);

        // 3. Inicializa o seu Stage principal com as configurações necessárias
        stage.setTitle("FoodJava - Login");
        stage.setScene(cenaInicial);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}