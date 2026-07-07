package br.edu.ifpb.ads.foodjava.view;

import br.edu.ifpb.ads.foodjava.controller.CardapioController;
import br.edu.ifpb.ads.foodjava.controller.PedidoController;
import br.edu.ifpb.ads.foodjava.exception.CarrinhoVazioException;
import br.edu.ifpb.ads.foodjava.model.ItemCardapio;
import br.edu.ifpb.ads.foodjava.util.UsuarioLogadoNoSistema;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class TelaPrincipalCliente {

    private BorderPane layoutPrincipal = new BorderPane();

    // ATRIBUTOS ADICIONADOS PARA GERENCIAR O CARRINHO LOGICAMENTE:
    private javafx.collections.ObservableList<ItemCardapio> itensNoCarrinho = javafx.collections.FXCollections.observableArrayList();
    private double valorTotalCarrinho = 0.0;

    private void carregarEntradas() {
        renderizarProdutosPorCategoria("ENTRADA");
    }

    private void carregarPratos() {
        renderizarProdutosPorCategoria("PRATO_PRINCIPAL");
    }

    private void carregarSobremesas() {
        renderizarProdutosPorCategoria("SOBREMESA");
    }

    private void carregarBebidas() {
        renderizarProdutosPorCategoria("BEBIDA");
    }

    private void removerItemCarrinho() {

        // 1. Pega o índice da linha que o cliente selecionou na ListView do Carrinho
        int indiceSelecionado = listaCarrinho.getSelectionModel().getSelectedIndex();

        // 2. Valida se o cliente realmente selecionou algo
        if (indiceSelecionado == -1) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Aviso");
            alerta.setHeaderText(null);
            alerta.setContentText("Por favor, selecione um item no carrinho para remover.");
            alerta.showAndWait();
            return;
        }

        ItemCardapio itemParaRemover = itensNoCarrinho.get(indiceSelecionado);

        valorTotalCarrinho -= itemParaRemover.getPreco();

        // Garante que o total não fique negativo por dízimas periódicas de ponto flutuante
        if (valorTotalCarrinho < 0) {
            valorTotalCarrinho = 0.0;
        }

        // 5. Remove o item das duas listas locais (da lógica e da tela)
        itensNoCarrinho.remove(indiceSelecionado);
        listaCarrinho.getItems().remove(indiceSelecionado);

        // 6. Atualiza o texto do preço total na tela
        lblTotal.setText("Total: R$ " + String.format("%.2f", valorTotalCarrinho));

        System.out.println("Item removido do carrinho visual.");
    }

    private void confirmarPedido() {


        // Janela de confirmação para o cliente
        Alert alertaConfirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        alertaConfirmacao.setTitle("Confirmar Pedido");
        alertaConfirmacao.setHeaderText("Finalizar sua compra");
        alertaConfirmacao.setContentText("Deseja enviar o seu pedido no valor total de R$ " + String.format("%.2f", valorTotalCarrinho) + "?");

        java.util.Optional<ButtonType> resultado = alertaConfirmacao.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            try {

               PedidoController pedidoController = new PedidoController();

                pedidoController.cadastrarNovoPedido(new java.util.ArrayList<>(itensNoCarrinho));

                itensNoCarrinho.clear();
                listaCarrinho.getItems().clear();
                valorTotalCarrinho = 0.0;
                lblTotal.setText("Total: R$ 0,00");

                Alert alertaSucesso = new Alert(Alert.AlertType.INFORMATION);
                alertaSucesso.setTitle("Sucesso!");
                alertaSucesso.setHeaderText(null);
                alertaSucesso.setContentText("Pedido enviado com sucesso! Acompanhe o status no painel.");
                alertaSucesso.showAndWait();

                System.out.println("Pedido registrado com sucesso no sistema.");

            } catch (CarrinhoVazioException erro) {
                System.err.println("Erro ao salvar pedido: " + erro.getMessage());
                Alert alertaErro = new Alert(Alert.AlertType.ERROR);
                alertaErro.setTitle("Erro");
                alertaErro.setHeaderText("Falha ao processar pedido");
                alertaErro.setContentText("Não foi possível salvar o seu pedido. Tente novamente.");
                alertaErro.showAndWait();
            } catch(Exception e){
                System.err.println("Erro geral: "+e.getMessage());
            }
        }
    }

    private void abrirHistorico() {
        TelaHistoricoCliente historicoTela = new TelaHistoricoCliente(UsuarioLogadoNoSistema.getUsuarioLogado().getCpf());
        historicoTela.exibir();
    }

    private void logout() {
        // 1. Cria uma janela de confirmação para o cliente não deslogar sem querer
        Alert alertaConfirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        alertaConfirmacao.setTitle("Confirmação");
        alertaConfirmacao.setHeaderText("Fazer Logout");
        alertaConfirmacao.setContentText("Deseja realmente sair e voltar para a tela de login?");

        java.util.Optional<ButtonType> resultado = alertaConfirmacao.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            javafx.stage.Stage stagePrincipal = (javafx.stage.Stage) btnLogout.getScene().getWindow();

            TelaLogin telaLogin = new TelaLogin();

            // 4. Cria a nova cena com o layout da tela de login
            // Estamos usando 800x600, mas você pode ajustar conforme o tamanho ideal da sua aplicação
            javafx.scene.Scene novaCena = new javafx.scene.Scene(telaLogin.getLayout(), 500, 650);

            // 5. Aplica a nova cena na janela e centralizar
            stagePrincipal.setScene(novaCena);
            stagePrincipal.setTitle("FoodJava - Login");
            stagePrincipal.centerOnScreen();

            System.out.println("Cliente deslogado com sucesso.");
        }
    }
    private void renderizarProdutosPorCategoria(String categoriaNome) {
        // Limpa o painel central para carregar a nova categoria
        painelProdutos.getChildren().clear();

        CardapioController cardapioController = new CardapioController();
        java.util.List<ItemCardapio> todosOsItens = cardapioController.obterCardapio();

        if (todosOsItens != null) {
            for (ItemCardapio item : todosOsItens) {

                // Só mostra se pertencer à categoria clicada E se estiver Ativo/Disponível pelo gerente
                if (item.getCategoriaComida().toString().equalsIgnoreCase(categoriaNome) && item.isDisponivel()) {

                    // Container horizontal para cada prato
                    HBox cardProduto = new HBox(20);
                    cardProduto.setPadding(new Insets(10));
                    cardProduto.setStyle("-fx-border-color: #f1f2f6; -fx-border-width: 0 0 1 0; -fx-alignment: center-left;");

                    // Informações do Texto
                    VBox infoProduto = new VBox(5);
                    Label lblNome = new Label(item.getNome());
                    lblNome.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
                    Label lblDesc = new Label(item.getDescricao());
                    lblDesc.setStyle("-fx-text-fill: #747d8c; -fx-font-size: 12;");
                    Label lblPreco = new Label("R$ " + String.format("%.2f", item.getPreco()));
                    lblPreco.setStyle("-fx-text-fill: #ff4757; -fx-font-weight: bold;");

                    infoProduto.getChildren().addAll(lblNome, lblDesc, lblPreco);
                    HBox.setHgrow(infoProduto, Priority.ALWAYS);

                    // Botão de Adicionar ao Carrinho
                    Button btnAdicionar = new Button("Adicionar +");
                    btnAdicionar.setStyle("-fx-background-color: #ff4757; -fx-text-fill: white; -fx-font-weight: bold;");

                    // Evento para quando clicar em Adicionar
                    btnAdicionar.setOnAction(e -> adicionarItemAoCarrinho(item));

                    cardProduto.getChildren().addAll(infoProduto, btnAdicionar);

                    // Adiciona o card estruturado dentro do VBox da Tela
                    painelProdutos.getChildren().add(cardProduto);
                }
            }
        }

        if (painelProdutos.getChildren().isEmpty()) {
            Label lblVazio = new Label("Nenhum item disponível nesta categoria no momento.");
            lblVazio.setStyle("-fx-text-fill: #a4b0be; -fx-font-style: italic;");
            painelProdutos.getChildren().add(lblVazio);
        }
    }

    // Método auxiliar para alimentar a lista lateral do carrinho
    private void adicionarItemAoCarrinho(ItemCardapio item) {
        itensNoCarrinho.add(item);
        listaCarrinho.getItems().add(item.getNome() + " - R$ " + String.format("%.2f", item.getPreco()));

        valorTotalCarrinho += item.getPreco();
        lblTotal.setText("Total: R$ " + String.format("%.2f", valorTotalCarrinho));
    }








    // topo
    private Label titulo = new Label("FoodJava");
    private Label lblUsuario = new Label("Bem-vindo, "+UsuarioLogadoNoSistema.getUsuarioLogado().getNome());

    // categorias
    private Button btnEntradas = new Button("Entradas");
    private Button btnPratos = new Button("Pratos principais");
    private Button btnSobremesas = new Button("Sobremesas");
    private Button btnBebidas = new Button("Bebidas");

    // cardápio
    private VBox painelProdutos = new VBox(10);

    // carrinho
    private ListView<String> listaCarrinho = new ListView<>();

    private Label lblTotal = new Label("Total: R$ 0,00");

    private Button btnRemoverItem =
            new Button("Remover Item");

    private Button btnConfirmarPedido =
            new Button("Confirmar Pedido");

    // rodapé
    private Button btnHistorico =
            new Button("Histórico");


    private Button btnLogout =
            new Button("Sair");

    public TelaPrincipalCliente() {

        configurarTopo();
        configurarCategorias();
        configurarCardapio();
        configurarCarrinho();
        configurarRodape();

        configurarEventos();
        carregarEntradas();
    }

    private void configurarTopo() {

        VBox topo = new VBox(10);

        titulo.setStyle(
                "-fx-font-size: 28;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #ff4757;"
        );

        topo.getChildren().addAll(
                titulo,
                lblUsuario
        );

        topo.setPadding(new Insets(15));

        layoutPrincipal.setTop(topo);
    }

    private void configurarCategorias() {

        HBox categorias = new HBox(10);

        categorias.getChildren().addAll(
                btnEntradas,
                btnPratos,
                btnSobremesas,
                btnBebidas
        );

        categorias.setAlignment(Pos.CENTER);

        VBox centro = new VBox(20);

        centro.getChildren().add(categorias);
        centro.getChildren().add(painelProdutos);

        centro.setPadding(new Insets(20));

        layoutPrincipal.setCenter(centro);
    }

    private void configurarCardapio() {
        painelProdutos.setPrefWidth(700);
    }

    private void configurarCarrinho() {

        VBox carrinho = new VBox(10);

        Label tituloCarrinho =
                new Label("Carrinho");

        carrinho.getChildren().addAll(
                tituloCarrinho,
                listaCarrinho,
                lblTotal,
                btnRemoverItem,
                btnConfirmarPedido
        );

        carrinho.setPadding(new Insets(15));

        carrinho.setPrefWidth(300);

        layoutPrincipal.setRight(carrinho);
    }

    private void configurarRodape() {

        HBox rodape = new HBox(15);

        rodape.getChildren().addAll(
                btnHistorico,
                btnLogout
        );

        rodape.setAlignment(Pos.CENTER);

        rodape.setPadding(new Insets(15));

        layoutPrincipal.setBottom(rodape);
    }

    private void configurarEventos() {

        btnEntradas.setOnAction(
                e -> carregarEntradas()
        );

        btnPratos.setOnAction(
                e -> carregarPratos()
        );

        btnSobremesas.setOnAction(
                e -> carregarSobremesas()
        );

        btnBebidas.setOnAction(
                e -> carregarBebidas()
        );

        btnRemoverItem.setOnAction(
                e -> removerItemCarrinho()
        );

        btnConfirmarPedido.setOnAction(
                e -> confirmarPedido()
        );

        btnHistorico.setOnAction(
                e -> abrirHistorico()
        );

        btnLogout.setOnAction(
                e -> logout()
        );
    }

    public BorderPane getLayout() {
        return layoutPrincipal;
    }
}