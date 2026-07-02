package br.edu.ifpb.ads.foodjava.view;

import br.edu.ifpb.ads.foodjava.model.ItemCardapio;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.File;

public class TelaEditarItem {

    private Stage window;
    private TextField txtNome;
    private TextArea txtDescricao;
    private TextField txtPreco;
    // Substitua 'CategoriaComida' pelo pacote correto se necessário
    private ComboBox<String> comboCategoria;
    private CheckBox chkDisponivel;
    private TextField txtImagemPath;

    private ItemCardapio itemEditado;
    private boolean salvo = false;

    // O construtor recebe o item que o gerente clicou para editar
    public TelaEditarItem(ItemCardapio itemAlvo) {
        this.itemEditado = itemAlvo;
    }

    public boolean exibe() {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("FoodJava - Editar Item do Cardápio");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(12);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);

        // --- COMPONENTES ---
        Label lblNome = new Label("Nome:");
        txtNome = new TextField(itemEditado.getNome()); // Já nasce preenchido

        Label lblDescricao = new Label("Descrição:");
        txtDescricao = new TextArea(itemEditado.getDescricao()); // Já nasce preenchido
        txtDescricao.setPrefRowCount(3);
        txtDescricao.setWrapText(true);

        Label lblPreco = new Label("Preço (R$):");
        txtPreco = new TextField(String.valueOf(itemEditado.getPreco())); // Já nasce preenchido

        Label lblCategoria = new Label("Categoria:");
        comboCategoria = new ComboBox<>();
        // OBS: Ajuste para o seu Enum ou lista se CategoriaComida for um tipo objeto
        comboCategoria.getItems().addAll("LANCHE", "BEBIDA", "SOBREMESA", "REFEICAO");
        comboCategoria.setValue(itemEditado.getCategoriaComida().toString());

        Label lblDisponivel = new Label("Disponível:");
        chkDisponivel = new CheckBox("Item ativo no cardápio");
        chkDisponivel.setSelected(itemEditado.isDisponivel()); // Já nasce marcado/desmarcado

        Label lblImagem = new Label("Imagem:");
        txtImagemPath = new TextField(itemEditado.getImagemPath()); // Já nasce preenchido
        txtImagemPath.setEditable(false);

        Button btnProcurarImg = new Button("Buscar...");
        btnProcurarImg.setOnAction(e -> selecionarImagem());
        HBox boxImagem = new HBox(5, txtImagemPath, btnProcurarImg);

        // --- BOTÕES ---
        Button btnSalvar = new Button("Salvar Alterações");
        Button btnCancelar = new Button("Cancelar");

        btnSalvar.setStyle("-fx-base: #3498db;"); // Azul para diferenciar do Cadastro
        btnCancelar.setStyle("-fx-base: #e74c3c;");

        btnCancelar.setOnAction(e -> window.close());

        btnSalvar.setOnAction(e -> {
            if (validarCampos()) {
                // Atualiza o objeto que recebemos por referência
                itemEditado.setNome(txtNome.getText().trim());
                itemEditado.setDescricao(txtDescricao.getText().trim());
                itemEditado.setPreco(Double.parseDouble(txtPreco.getText().replace(",", ".")));
                // itemEditado.setCategoriaComida(... converter string para seu enum aqui se necessário)
                itemEditado.setDisponivel(chkDisponivel.isSelected());
                itemEditado.setImagemPath(txtImagemPath.getText());

                salvo = true; // Define que a alteração foi confirmada
                window.close();
            } else {
                mostrarAlerta("Erro de Validação", "Preencha todos os campos corretamente.");
            }
        });

        // --- MONTAGEM DO GRID ---
        grid.add(lblNome, 0, 0);
        grid.add(txtNome, 1, 0);
        grid.add(lblDescricao, 0, 1);
        grid.add(txtDescricao, 1, 1);
        grid.add(lblPreco, 0, 2);
        grid.add(txtPreco, 1, 2);
        grid.add(lblCategoria, 0, 3);
        grid.add(comboCategoria, 1, 3);
        grid.add(lblDisponivel, 0, 4);
        grid.add(chkDisponivel, 1, 4);
        grid.add(lblImagem, 0, 5);
        grid.add(boxImagem, 1, 5);

        HBox boxBotoes = new HBox(10, btnSalvar, btnCancelar);
        boxBotoes.setAlignment(Pos.CENTER_RIGHT);
        grid.add(boxBotoes, 1, 6);

        Scene scene = new Scene(grid, 450, 400);
        window.setScene(scene);
        window.showAndWait();

        return salvo; // Retorna true se salvou, false se cancelou
    }

    private void selecionarImagem() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar Imagem do Item");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg"));
        File arquivo = fileChooser.showOpenDialog(window);
        if (arquivo != null) {
            txtImagemPath.setText(arquivo.getAbsolutePath());
        }
    }

    private boolean validarCampos() {
        if (txtNome.getText().trim().isEmpty() || txtDescricao.getText().trim().isEmpty() || txtPreco.getText().trim().isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(txtPreco.getText().replace(",", "."));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}