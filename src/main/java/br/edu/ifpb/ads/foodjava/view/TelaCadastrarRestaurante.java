package br.edu.ifpb.ads.foodjava.view;

import br.edu.ifpb.ads.foodjava.controller.LoginController;
import br.edu.ifpb.ads.foodjava.controller.RestauranteController;
import br.edu.ifpb.ads.foodjava.exception.DocumentoInvalidoException;
import br.edu.ifpb.ads.foodjava.exception.FormatoEmailInvalidoException;
import br.edu.ifpb.ads.foodjava.exception.FormatoSenhaInvalidoException;
import br.edu.ifpb.ads.foodjava.exception.FormatoTelefoneException;
import br.edu.ifpb.ads.foodjava.model.Restaurante;

import br.edu.ifpb.ads.foodjava.model.Endereco;
import br.edu.ifpb.ads.foodjava.model.Email;
import br.edu.ifpb.ads.foodjava.model.Senha;
import br.edu.ifpb.ads.foodjava.model.Gerente;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class TelaCadastrarRestaurante {

    //region atributos como pane, button,label, textField e outro...
    // Dependências e Atributos
    private LoginController loginController;
    private RestauranteController restauranteController;


    // Atributos para a Logo do Restaurante
    private Button btnSelecionarLogo = new Button("Selecionar Logo (Imagem)");
    private javafx.scene.image.ImageView imgPreviewLogo = new javafx.scene.image.ImageView();
    private String caminhoLogoSelecionada = "";

    private Label tituloTela = new Label("Cadastro do Restaurante");

    private Label nomeDoRestaurante = new Label("Nome do Restaurante");
    private TextField txtNomeDoRestaurante = new TextField();

    private Label cnpj = new Label("CNPJ");
    private TextField txtCnpj = new TextField();

    private Label categoriaCulinaria = new Label("Categoria Culinária");
    private TextField txtCategoriaCulinaria = new TextField();

    private Label contatoDoRestaurante = new Label("Contato");
    private TextField txtContatoDoRestaurante = new TextField();

    // Endereço Completo para o Layout (Igual ao do Cliente)
    private Label rua = new Label("Rua");
    private TextField txtRua = new TextField();

    private Label numeroDaResidencia = new Label("Número");
    private TextField txtNumeroDaResidencia = new TextField();

    private Label bairro = new Label("Bairro");
    private TextField txtBairro = new TextField();

    private Label cidade = new Label("Cidade");
    private TextField txtCidade = new TextField();

    private Label cep = new Label("CEP");
    private TextField txtCep = new TextField();

    // Dados do restaurante
    private Label emailDoRestaurante = new Label("E-mail");
    private TextField txtEmailDoRestaurante = new TextField();

    private Label senha = new Label("Senha");
    private PasswordField passSenha = new PasswordField();

    // Dados do Gerente Vinculado
    private Label nomeGerente = new Label("Nome do Gerente");
    private TextField txtNomeGerente = new TextField();
    private Label cpfGerente = new Label("CPF do Gerente");
    private TextField txtCpfGerente = new TextField();

    private Button btnSalvarCadastro = new Button("Cadastrar Restaurante");
    private Button btnVoltar = new Button("Voltar");
    private HBox blocoBotoes = new HBox(20);

    //endregion
    private VBox telaCadastroPrincipal = new VBox(12);

    public TelaCadastrarRestaurante() {

    //region configurante estilo da tela ->
        // Configura o tamanho máximo da pré-visualização da logo
        imgPreviewLogo.setFitWidth(80);
        imgPreviewLogo.setFitHeight(80);
        imgPreviewLogo.setPreserveRatio(true);

        btnSelecionarLogo.setStyle("-fx-background-color: #747d8c; -fx-text-fill: white;");

        // Coloca o botão e a fotinha lado a lado
        HBox blocoLogo = new HBox(15, btnSelecionarLogo, imgPreviewLogo);
        blocoLogo.setAlignment(Pos.CENTER_LEFT);

        // Personalização visual da tela
        tituloTela.setStyle("-fx-text-fill: #ff4757; -fx-font-size: 24px; -fx-font-weight: bold;");
        telaCadastroPrincipal.setAlignment(Pos.TOP_CENTER);
        telaCadastroPrincipal.setPadding(new Insets(25));
        telaCadastroPrincipal.setMaxWidth(500);

        // Configurando placeholders
        txtRua.setPromptText("Nome da rua ou avenida");
        txtNumeroDaResidencia.setPromptText("Nº");
        txtBairro.setPromptText("Bairro");
        txtCidade.setPromptText("Cidade");
        txtCep.setPromptText("00000-000");
        txtNomeDoRestaurante.setPromptText("Nome fantasia");
        txtCnpj.setPromptText("00.000.000/0001-00");
        txtEmailDoRestaurante.setPromptText("contato@restaurante.com");

        // Agrupando campos lado a lado de forma responsiva com HBox
        HBox linhaCnpjCategoria = new HBox(15, new VBox(5, cnpj, txtCnpj), new VBox(5, categoriaCulinaria, txtCategoriaCulinaria));
        HBox linhaContatoEmail = new HBox(15, new VBox(5, contatoDoRestaurante, txtContatoDoRestaurante), new VBox(5, emailDoRestaurante, txtEmailDoRestaurante));

        // Linha: Rua e Número estruturado
        VBox boxRua = new VBox(5, rua, txtRua);
        VBox boxNumero = new VBox(5, numeroDaResidencia, txtNumeroDaResidencia);
        HBox linhaRuaNumero = new HBox(15, boxRua, boxNumero);
        boxRua.prefWidthProperty().bind(linhaRuaNumero.widthProperty().multiply(0.75));
        boxNumero.prefWidthProperty().bind(linhaRuaNumero.widthProperty().multiply(0.25));

        // Linha: Bairro, Cidade e CEP estruturado
        VBox boxBairro = new VBox(5, bairro, txtBairro);
        VBox boxCidade = new VBox(5, cidade, txtCidade);
        VBox boxCep = new VBox(5, cep, txtCep);
        HBox linhaEnderecoCompleto = new HBox(15, boxBairro, boxCidade, boxCep);
        boxBairro.prefWidthProperty().bind(linhaEnderecoCompleto.widthProperty().multiply(0.33));
        boxCidade.prefWidthProperty().bind(linhaEnderecoCompleto.widthProperty().multiply(0.33));
        boxCep.prefWidthProperty().bind(linhaEnderecoCompleto.widthProperty().multiply(0.33));

        HBox linhaGerente = new HBox(15, new VBox(5, nomeGerente, txtNomeGerente), new VBox(5, cpfGerente, txtCpfGerente));

        // Estilo botões
        btnSalvarCadastro.setStyle("-fx-background-color: #ff4757; -fx-text-fill: white; -fx-font-weight: bold;");
        btnVoltar.setStyle("-fx-background-color: #747d8c; -fx-text-fill: white; -fx-font-weight: bold;");
        blocoBotoes.getChildren().addAll(btnVoltar, btnSalvarCadastro);
        blocoBotoes.setAlignment(Pos.CENTER);

        // Montando a árvore de nós principal
        telaCadastroPrincipal.getChildren().addAll(
                tituloTela,
                new VBox(5, nomeDoRestaurante, txtNomeDoRestaurante),
                linhaCnpjCategoria,
                linhaRuaNumero,
                linhaEnderecoCompleto,
                linhaContatoEmail,
                new VBox(5, senha, passSenha),
                blocoLogo,
                new Label("--- Dados do Gerente Administrador ---"),
                linhaGerente,
                blocoBotoes
        );
//endregion

        // Lógica para selecionar a Logo do Notebook (Inserido corretamente no Construtor)
        btnSelecionarLogo.setOnAction(event -> {
            Stage stageAtual = (Stage) btnSelecionarLogo.getScene().getWindow();

            FileChooser navegadorImagens = new FileChooser();
            navegadorImagens.setTitle("Selecione a Logo do Restaurante");

            FileChooser.ExtensionFilter filtroImagens = new FileChooser.ExtensionFilter(
                    "Imagens (*.png, *.jpg, *.jpeg)", "*.png", "*.jpg", "*.jpeg"
            );
            navegadorImagens.getExtensionFilters().add(filtroImagens);

            File arquivoImagem = navegadorImagens.showOpenDialog(stageAtual);

            if (arquivoImagem != null) {
                caminhoLogoSelecionada = arquivoImagem.getAbsolutePath();
                javafx.scene.image.Image imagem = new javafx.scene.image.Image(arquivoImagem.toURI().toString());
                imgPreviewLogo.setImage(imagem);
            }
        });

        // Lógica do Botão Salvar Cadastro
        btnSalvarCadastro.setOnAction(event -> {
            try {
                // Captura dos dados inseridos pelo usuário
                String nomeRestauranteDigitado = txtNomeDoRestaurante.getText();
                String cnpjDigitado = txtCnpj.getText();
                String categoriaDigitada = txtCategoriaCulinaria.getText();
                String contatoDigitado = txtContatoDoRestaurante.getText();
                String emailDigitado = txtEmailDoRestaurante.getText();
                String senhaDigitada = passSenha.getText();
                String nomeGerenteDigitado = txtNomeGerente.getText();
                String ruaDigitada = txtRua.getText();
                String bairroDigitado = txtBairro.getText();
                String cidadeDigitada = txtCidade.getText();
                String cepDigitado = txtCep.getText();

                int numeroDigitado = 0;
                if (!txtNumeroDaResidencia.getText().isEmpty()) {
                    numeroDigitado = Integer.parseInt(txtNumeroDaResidencia.getText());
                }

                // Instanciando os objetos de valor com as validações de runtime
                Email email = new Email(emailDigitado);
                Senha senhaObjeto = new Senha(senhaDigitada);
                Endereco endereco = new Endereco(numeroDigitado, ruaDigitada, bairroDigitado, cepDigitado, cidadeDigitada);
                Gerente gerente = new Gerente(nomeGerenteDigitado, email, senhaObjeto);

                loginController = new LoginController();
                restauranteController = new RestauranteController();

                loginController.salvarGerenteNoRepository(gerente);
                // Criando a entidade principal mapeando a String da logo
                Restaurante novoRestaurante = new Restaurante(nomeRestauranteDigitado, cnpjDigitado, categoriaDigitada, contatoDigitado, gerente, endereco, email,senhaObjeto,caminhoLogoSelecionada);
                novoRestaurante.setLogoDoRestaurante(caminhoLogoSelecionada);


                restauranteController.salvarRestaurante(novoRestaurante);

                Stage stageAtual = (Stage) btnSalvarCadastro.getScene().getWindow();

                TelaPainelGerente painelGerente = new TelaPainelGerente(nomeRestauranteDigitado);
                Scene cenaPainel = new Scene(painelGerente.getLayout(), 950, 650);

                stageAtual.setScene(cenaPainel);

            } catch (IllegalArgumentException e){
                exibirAlertaErro("Campo vazio", "Verifique os dados informados", e.getMessage());
                System.err.println("Erro em preenchimento do campo de validação: " + e.getMessage());

            } catch(FormatoSenhaInvalidoException e){
                exibirAlertaErro("Erro ao criar senha: ", "Senha fraca!", e.getMessage());
                System.err.println("Erro ao criar senha: "+e.getMessage());

            } catch(FormatoTelefoneException e){
                exibirAlertaErro("Erro", "Telefone inválido!", e.getMessage());
                System.err.println("Erro: "+e.getMessage());

            } catch(FormatoEmailInvalidoException e){
                exibirAlertaErro("Erro", "E-mail inválido!", e.getMessage());
                System.err.println("Erro: "+e.getMessage());

            } catch(DocumentoInvalidoException e){
                exibirAlertaErro("Erro no Cadastro", "Problemas com dados", e.getMessage());
                System.err.println("Erro: "+e.getMessage());

            } catch (RuntimeException e) {
                // Captura as suas outras exceções customizadas de negócio
                exibirAlertaErro("Erro no Cadastro", "Falha de Validação", e.getMessage());
                System.err.println("Erro: " + e.getMessage());
            }
        });

        // Lógica do Botão Voltar
        btnVoltar.setOnAction(event -> {
            Stage stageAtual = (Stage) btnVoltar.getScene().getWindow();
            TelaLogin telaLogin = new TelaLogin();
            stageAtual.setScene(new Scene(telaLogin.getLayout(), 400, 500));
        });
    }

    private void exibirAlertaErro(String titulo, String cabecalho, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecalho);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    public VBox getLayout() {
        return this.telaCadastroPrincipal;
    }
}