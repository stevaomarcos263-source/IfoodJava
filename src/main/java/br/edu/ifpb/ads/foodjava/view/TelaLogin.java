package br.edu.ifpb.ads.foodjava.view;

import br.edu.ifpb.ads.foodjava.controller.LoginController;
import br.edu.ifpb.ads.foodjava.exception.EmailInvalidoException;
import br.edu.ifpb.ads.foodjava.exception.SenhaInvalidaException;
import br.edu.ifpb.ads.foodjava.model.User;
import br.edu.ifpb.ads.foodjava.model.Gerente;
import br.edu.ifpb.ads.foodjava.model.Cliente;

import br.edu.ifpb.ads.foodjava.util.UsuarioLogadoNoSistema;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import javafx.stage.Stage;

public class TelaLogin {
    LoginController loginController = new LoginController();

    private Label textoFoodJava = new Label("FoodJava");

    private Label email = new Label("E-mail");
    private TextField txtEmail = new TextField();
    private VBox blocoEmail = new VBox(5); // Reduzi o espaçamento interno para os labels não ficarem longe dos inputs

    private Label senha = new Label("Senha");
    private PasswordField passSenha = new PasswordField();
    private VBox blocoSenha = new VBox(5);

    private Button btnCadastrar = new Button("Cadastrar"); // Adicionado o texto do botão
    private Button btnLogin = new Button("Entrar");        // Adicionado o texto do botão
    private HBox blocoBotoes = new HBox(30);

    private VBox telaLogin = new VBox(20); // Aumentado levemente para distanciar os blocos na tela

    public TelaLogin(){
        // personalização dos nodes ->
        textoFoodJava.setStyle("-fx-text-fill: #ff4757; -fx-font-size: 36px; -fx-font-weight: bold;");
        txtEmail.setPromptText("Digite seu e-mail aqui:");
        passSenha.setPromptText("Digite sua senha aqui:");

        // Estilização básica dos botões para combinar com o FoodJava
        btnLogin.setStyle("-fx-background-color: #ff4757; -fx-text-fill: white; -fx-font-weight: bold;");
        btnCadastrar.setStyle("-fx-background-color: #747d8c; -fx-text-fill: white; -fx-font-weight: bold;");
        btnLogin.setPrefWidth(100);
        btnCadastrar.setPrefWidth(100);

        // organizando os layouts ->
        blocoEmail.getChildren().addAll(email, txtEmail);
        blocoEmail.setAlignment(Pos.TOP_LEFT);

        blocoSenha.getChildren().addAll(senha, passSenha);
        blocoSenha.setAlignment(Pos.TOP_LEFT);

        blocoBotoes.getChildren().addAll(btnCadastrar, btnLogin);
        blocoBotoes.setAlignment(Pos.CENTER);

        // configurando espaçamentos e limites da tela principal ->
        telaLogin.setAlignment(Pos.CENTER);
        telaLogin.setPadding(new Insets(40));
        telaLogin.setMaxWidth(350);

        // criando a tela de login principal após configurar tudo acima ->
        telaLogin.getChildren().addAll(
                textoFoodJava,
                blocoEmail,
                blocoSenha,
                blocoBotoes);

        // botões e seus métodos ->
        btnLogin.setOnAction(event -> {

            String emailDigitado = txtEmail.getText();
            String senhaDigitada = passSenha.getText();
            try {

                User usuario = loginController.autenticarEntradaDeUsuario(
                        emailDigitado,
                        senhaDigitada
                );

                Stage stageAtual = (Stage) btnLogin.getScene().getWindow();

                if(usuario instanceof Gerente){

                    TelaPainelGerente telaGerente = new TelaPainelGerente("FoodJava");
                    Scene cena = new Scene(telaGerente.getLayout(), 1200, 700);

                    stageAtual.setScene(cena);
                }else if(usuario instanceof Cliente){


                    UsuarioLogadoNoSistema.setUsuarioLogado((Cliente)usuario);
                    TelaPrincipalCliente telaGerente = new TelaPrincipalCliente();
                    Scene cena = new Scene(telaGerente.getLayout(), 1200, 700);
                    Stage stageAtua = (Stage) btnCadastrar.getScene().getWindow();
                    stageAtua.setScene(cena);

                }

            } catch(EmailInvalidoException e) {
                Alert alertaEmail = new Alert(Alert.AlertType.ERROR);
                alertaEmail.setTitle("Erro no Login");
                alertaEmail.setHeaderText("E-mail Inválido");
                alertaEmail.setContentText(e.getMessage()); // Mensagem que veio da sua Exception
                alertaEmail.showAndWait();
                System.err.println("Email inválido: " + e.getMessage());
            } catch(SenhaInvalidaException e) {
                Alert alertaSenha = new Alert(Alert.AlertType.ERROR);
                alertaSenha.setTitle("Erro no Login");
                alertaSenha.setHeaderText("Senha Incorreta");
                alertaSenha.setContentText(e.getMessage());
                alertaSenha.showAndWait();
                System.err.println("Senha inválida: " + e.getMessage());
            }
        });

        // Método para abrir a tela de cadastro ao clicar em Cadastrar ->
        btnCadastrar.setOnAction(event -> {
            // Captura o Stage atual a partir do próprio botão clicado
            Stage stageAtual = (Stage) btnCadastrar.getScene().getWindow();

            // Instancia a classe de cadastro que estruturamos
            TelaCadastrarCliente telaCadastro = new TelaCadastrarCliente();

            // Cria a nova cena contendo o layout da tela de cadastro
            Scene cenaCadastro = new Scene(telaCadastro.getLayout(), 500, 650);

            // Substitui a cena mantendo o mesmo Stage (janela)
            stageAtual.setScene(cenaCadastro);
        });
    }

    // Método essencial para expor o seu VBox principal para a classe MainApp do JavaFX
    public VBox getLayout() {
        return this.telaLogin;
    }
}