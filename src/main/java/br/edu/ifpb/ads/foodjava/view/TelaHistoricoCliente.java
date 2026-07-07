package br.edu.ifpb.ads.foodjava.view;

import br.edu.ifpb.ads.foodjava.exception.CancelamentoNaoPermitidoException;
import br.edu.ifpb.ads.foodjava.exception.StatusInvalidoException;
import br.edu.ifpb.ads.foodjava.util.UsuarioLogadoNoSistema;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import br.edu.ifpb.ads.foodjava.model.Pedido;
import br.edu.ifpb.ads.foodjava.controller.PedidoController;

public class TelaHistoricoCliente {

    private Stage stage;
    private ListView<Pedido> listaHistorico = new ListView<>();
    private Button btnCancelar = new Button("Cancelar Pedido");
    private Button btnFechar = new Button("Voltar");
    private String cpfClienteLogado;
    private PedidoController pedidoController = new PedidoController();

    public TelaHistoricoCliente(String cpfCliente) {
        this.cpfClienteLogado = cpfCliente;
        this.stage = new Stage();
        this.stage.setTitle("FoodJava - Meu Histórico de Pedidos");

        // Layout Principal
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Label titulo = new Label("Seu Histórico de Pedidos");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333;");

        // Configura a exibição bonita usando a CellFactory identica à do gerente
        configurarFabricaDeCelulas();

        // Barra de botões inferior
        HBox barraBotoes = new HBox(15, btnCancelar, btnFechar);
        barraBotoes.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(titulo, listaHistorico, barraBotoes);

        configurarEventos();
        carregarHistorico();

        Scene cena = new Scene(layout, 750, 600);
        stage.setScene(cena);
    }

    public void exibir() {
        stage.show();
    }

    private void configurarFabricaDeCelulas() {
        listaHistorico.setCellFactory(cell -> new ListCell<>() {
            @Override
            protected void updateItem(Pedido pedido, boolean empty) {
                super.updateItem(pedido, empty);
                if (empty || pedido == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    String idResumido = pedido.getId().substring(0, 5);
                    setText(String.format("Pedido #%s | Itens: %d | Total: R$ %.2f | [%s]",
                            idResumido,
                            pedido.getListaPedidosPreMoldadosComItemEQuantidade().size(),
                            pedido.getValorTotal(),
                            pedido.getStatusPedido()
                    ));
                }
            }
        });
    }

    private void carregarHistorico() {
        listaHistorico.getItems().clear();
        java.util.List<Pedido> historico = pedidoController.buscarHistoricoDePedidosDoCliente(UsuarioLogadoNoSistema.getUsuarioLogado().getCpf());

        if (historico != null && !historico.isEmpty()) {
            listaHistorico.getItems().addAll(historico);
        } else {
            listaHistorico.setPlaceholder(new Label("Você ainda não realizou nenhum pedido."));
        }
    }

    private void configurarEventos() {
        // Ação do Botão Cancelar
        btnCancelar.setOnAction(e -> {
            Pedido pedidoSelecionado = listaHistorico.getSelectionModel().getSelectedItem();

            if (pedidoSelecionado == null) {
                new Alert(Alert.AlertType.WARNING, "Selecione um pedido para cancelar!").showAndWait();
                return;
            }

            Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION, "Tem certeza que deseja cancelar este pedido?", ButtonType.YES, ButtonType.NO);
            confirmacao.setTitle("Confirmar Cancelamento");
            confirmacao.setHeaderText(null);

            if (confirmacao.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                try {
                    pedidoController.cancelarPedido(pedidoSelecionado);

                    new Alert(Alert.AlertType.INFORMATION, "Pedido cancelado com sucesso!").showAndWait();
                    carregarHistorico();

                } catch (StatusInvalidoException erro) {
                    Alert alertaErro = new Alert(Alert.AlertType.ERROR, erro.getMessage());
                    alertaErro.setTitle("Cancelamento Negado");
                    alertaErro.showAndWait();
                }catch(CancelamentoNaoPermitidoException er){
                    System.err.println("Erro: "+er.getMessage());
                    Alert alertaSucesso = new Alert(Alert.AlertType.INFORMATION);
                    alertaSucesso.setTitle("Erro!");
                    alertaSucesso.setHeaderText(null);
                    alertaSucesso.setContentText("Erro: "+er.getMessage());
                    alertaSucesso.showAndWait();
                }catch(IllegalArgumentException erO){
                    System.err.println("Erro: "+erO.getMessage());
                }catch(Exception erroGeral){
                    System.err.println("Erro geral: "+erroGeral.getMessage());
                }
            }
        });

        btnFechar.setOnAction(e -> stage.close());
    }
}