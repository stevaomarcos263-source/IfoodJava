package br.edu.ifpb.ads.foodjava.view;

import br.edu.ifpb.ads.foodjava.controller.LoginController;
import br.edu.ifpb.ads.foodjava.exception.*;
import br.edu.ifpb.ads.foodjava.model.Email;
import br.edu.ifpb.ads.foodjava.model.Endereco;
import br.edu.ifpb.ads.foodjava.model.Senha;
import br.edu.ifpb.ads.foodjava.util.UsuarioLogadoNoSistema;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;

public class TelaCadastrarCliente {

    // Controlador
    private LoginController loginController = new LoginController();

    // Título da Tela
    private Label tituloTela = new Label("Cadastro de Cliente");

    // Componentes de Entrada (Dados Pessoais)
    private Label nome = new Label("Nome");
    private TextField txtNome = new TextField();

    private Label emailCadastro = new Label("E-mail");
    private TextField txtEmailCadastro = new TextField();

    private Label senhaCadastro = new Label("Senha");
    private PasswordField txtSenhaCadastro = new PasswordField(); // Alterado para PasswordField

    private Label contato = new Label("Contato");
    private TextField txtContato = new TextField();

    private Label cpf = new Label("CPF");
    private TextField txtCpf = new TextField();

    // Componentes de Entrada (Endereço)
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

    // Layouts e Botões
    private VBox telaCadastroPrincipal = new VBox(12);
    private Button btnSalvarLogin = new Button("Salvar Cadastro");
    private Button btnVoltarLogin = new Button("Voltar para login");
    private HBox blocoBotoes = new HBox(20);

    public TelaCadastrarCliente() {
        // 1. Configuração do Layout Principal (VBox)
        telaCadastroPrincipal.setAlignment(Pos.TOP_CENTER); // Começa alinhado no topo
        telaCadastroPrincipal.setPadding(new Insets(30));
        telaCadastroPrincipal.setMaxWidth(500); // Define uma largura confortável para os formulários

        // 2. Estilização do Título
        tituloTela.setStyle("-fx-text-fill: #ff4757; -fx-font-size: 26px; -fx-font-weight: bold;");
        tituloTela.setPadding(new Insets(0, 0, 15, 0)); // Margem inferior para afastar do formulário

        // 3. Configurando Placeholders (Dicas de preenchimento)
        txtNome.setPromptText("Digite seu nome completo");
        txtEmailCadastro.setPromptText("exemplo@email.com");
        txtSenhaCadastro.setPromptText("Crie uma senha segura");
        txtContato.setPromptText("(83) 99999-9999");
        txtCpf.setPromptText("000.000.000-00");
        txtRua.setPromptText("Nome da rua ou avenida");
        txtNumeroDaResidencia.setPromptText("Nº");
        txtBairro.setPromptText("Bairro");
        txtCidade.setPromptText("Cidade");
        txtCep.setPromptText("00000-000");

        // 4. Organizando sub-layouts (HBox) para os campos ficarem lado a lado

        // Linha: Contato e CPF
        VBox boxContato = new VBox(5, contato, txtContato);
        VBox boxCpf = new VBox(5, cpf, txtCpf);
        HBox linhaDadosPessoais = new HBox(15, boxContato, boxCpf);
        boxContato.prefWidthProperty().bind(linhaDadosPessoais.widthProperty().multiply(0.5));
        boxCpf.prefWidthProperty().bind(linhaDadosPessoais.widthProperty().multiply(0.5));

        // Linha: Rua e Número
        VBox boxRua = new VBox(5, rua, txtRua);
        VBox boxNumero = new VBox(5, numeroDaResidencia, txtNumeroDaResidencia);
        HBox linhaRuaNumero = new HBox(15, boxRua, boxNumero);
        // Faz a Rua ocupar 75% da linha e o Número ocupar 25%
        boxRua.prefWidthProperty().bind(linhaRuaNumero.widthProperty().multiply(0.75));
        boxNumero.prefWidthProperty().bind(linhaRuaNumero.widthProperty().multiply(0.25));

        // Linha: Bairro, Cidade e CEP
        VBox boxBairro = new VBox(5, bairro, txtBairro);
        VBox boxCidade = new VBox(5, cidade, txtCidade);
        VBox boxCep = new VBox(5, cep, txtCep);
        HBox linhaEnderecoCompleto = new HBox(15, boxBairro, boxCidade, boxCep);
        boxBairro.prefWidthProperty().bind(linhaEnderecoCompleto.widthProperty().multiply(0.33));
        boxCidade.prefWidthProperty().bind(linhaEnderecoCompleto.widthProperty().multiply(0.33));
        boxCep.prefWidthProperty().bind(linhaEnderecoCompleto.widthProperty().multiply(0.33));

        // 5. Configuração dos Botões
        blocoBotoes.setAlignment(Pos.CENTER);
        blocoBotoes.setPadding(new Insets(15, 0, 0, 0));

        btnSalvarLogin.setStyle("-fx-background-color: #ff4757; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");
        btnVoltarLogin.setStyle("-fx-background-color: #747d8c; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");

        btnSalvarLogin.setPrefWidth(150);
        btnVoltarLogin.setPrefWidth(150);
        blocoBotoes.getChildren().addAll(btnVoltarLogin, btnSalvarLogin);

        // 6. Juntando tudo na Tela Principal na ordem correta
        telaCadastroPrincipal.getChildren().addAll(
                tituloTela,
                new VBox(5, nome, txtNome),
                new VBox(5, emailCadastro, txtEmailCadastro),
                new VBox(5, senhaCadastro, txtSenhaCadastro),
                linhaDadosPessoais,
                linhaRuaNumero,
                linhaEnderecoCompleto,
                blocoBotoes
        );

        // 7. Lógica do Botão Salvar (Onde os dados são de fato capturados)
        btnSalvarLogin.setOnAction(event -> {
            try {

                String nomeDigitado = txtNome.getText();
                String emailDigitadoCadastro = txtEmailCadastro.getText();
                String senhaDigitadaCadastro = txtSenhaCadastro.getText();
                String ruaDigitada = txtRua.getText();
                String bairroDigitado = txtBairro.getText();
                String cidadeDigitada = txtCidade.getText();
                String cepDigitado = txtCep.getText();
                String contatoDigitado = txtContato.getText();
                String cpfDigitado = txtCpf.getText();

                int numeroDigitado = 0;
                if (!txtNumeroDaResidencia.getText().isEmpty()) {
                    numeroDigitado = Integer.parseInt(txtNumeroDaResidencia.getText());
                }

                br.edu.ifpb.ads.foodjava.model.Cliente clienteCadastrado = new br.edu.ifpb.ads.foodjava.model.Cliente(nomeDigitado,new Email(emailDigitadoCadastro),
                        new Senha(senhaDigitadaCadastro), contatoDigitado,
                        new Endereco(numeroDigitado,ruaDigitada,bairroDigitado,cepDigitado,cidadeDigitada),cpfDigitado);

                    loginController.salvarNovoUsuarioNoArquivoLoginJson(nomeDigitado, new Email(emailDigitadoCadastro),
                            new Senha(senhaDigitadaCadastro), contatoDigitado,
                            new Endereco(numeroDigitado, ruaDigitada, bairroDigitado, cepDigitado, cidadeDigitada), cpfDigitado);

                UsuarioLogadoNoSistema.setUsuarioLogado(clienteCadastrado);

                Stage stageAtual =
                        (Stage) btnSalvarLogin.getScene().getWindow();
                TelaPrincipalCliente telaCliente =
                        new TelaPrincipalCliente();
                Scene cenaCliente =
                        new Scene(telaCliente.getLayout(), 1200, 700);
                stageAtual.setScene(cenaCliente);

                System.out.println("Cadastro realizado para o cliente: " + nomeDigitado);

            } catch (NumberFormatException e) {
                exibirAlertaInformativo("Número da residência","Deve conter apenas dígitos!",e.getMessage());
                System.err.println("Erro: O número da residência deve conter apenas dígitos numéricos.");
            } catch(CepInvalidoException e){
                exibirAlertaInformativo("CEP","Cep inválido",e.getMessage());
                System.err.println("Erro: "+e.getMessage());
            } catch (FormatoSenhaInvalidoException e){
                exibirAlertaInformativo("Senha","Senha fraca",e.getMessage());
                System.err.println("Formato de senha inválido: "+e.getMessage());
            } catch(FormatoEmailInvalidoException e){
                exibirAlertaInformativo("E-mail inválido","Verifique o formato de seu e-mail!",e.getMessage());
                System.err.println("Formato de e-mail inválido: "+e.getMessage());
            }catch(FormatoTelefoneException e){
                exibirAlertaInformativo("Formato de Telefone error","Formato de telefone inválido...",e.getMessage());
                System.err.println("Formato de telefone inválido: "+e.getMessage());
            }catch(DocumentoInvalidoException e){
                exibirAlertaInformativo("CPF","Verifique se seu CPF está correto",e.getMessage());
                System.err.println("Erro no documento CPF: "+e.getMessage());
            }catch(UsuarioDuplicadoException e){
                exibirAlertaInformativo("Erro","Dados já existentes no cadastro",e.getMessage());
                System.err.println("Erro: "+e.getMessage());
            }catch(IllegalArgumentException e){
                Alert usuarioDuplicado = new Alert(Alert.AlertType.ERROR);
                exibirAlertaInformativo("Campo vazio","Verifique se todos os campos foram preenchidos!",e.getMessage());
                System.err.println("Erro em campo vazio: "+e.getMessage());
            }
        });

        // 8. Lógica do Botão Voltar
        btnVoltarLogin.setOnAction(event -> {
            Stage stageAtual = (Stage) btnVoltarLogin.getScene().getWindow();

            // Instancia a sua tela de Login e troca a cena de volta
            TelaLogin telaLogin = new TelaLogin();
            Scene cenaLogin = new Scene(telaLogin.getLayout(), 400, 500);

            stageAtual.setScene(cenaLogin);
        });
    }
    public VBox getLayout() {
        return this.telaCadastroPrincipal;
    }


    private void exibirAlertaInformativo(
            String titulo,
            String cabecalho,
            String mensagem
    ){
        Alert alerta =
                new Alert(
                        Alert.AlertType.INFORMATION
                );

        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecalho);
        alerta.setContentText(mensagem);

        alerta.showAndWait();
    }
}