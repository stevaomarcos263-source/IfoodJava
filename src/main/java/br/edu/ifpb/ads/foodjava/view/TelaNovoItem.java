package br.edu.ifpb.ads.foodjava.view;

import br.edu.ifpb.ads.foodjava.controller.CardapioController;
import br.edu.ifpb.ads.foodjava.exception.ItemJaRegistradoNoCardapio;
import br.edu.ifpb.ads.foodjava.exception.PrecoInvalidoException;
import br.edu.ifpb.ads.foodjava.model.ItemCardapio;
import br.edu.ifpb.ads.foodjava.model.enums.CategoriaComida;
import br.edu.ifpb.ads.foodjava.repository.CardapioRepository;
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

public class TelaNovoItem{

    private Stage window;
    private TextField txtNome;
    private TextArea txtDescricao;
    private TextField txtPreco;
    private ComboBox<CategoriaComida> comboCategoria;
    private CheckBox chkDisponivel;
    private TextField txtImagemPath;

    private ItemCardapio novoItem;


    public ItemCardapio exibe() {
        this.novoItem = null;
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("FoodJava - Adicionar Item ao Cardápio");

        // Layout Principal
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(12);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);

        // --- COMPONENTES DA TELA ---

        // 1. Nome
        Label lblNome = new Label("Nome:");
        txtNome = new TextField();
        txtNome.setPromptText("Ex: Hambúrguer Artesanal");

        // 2. Descrição
        Label lblDescricao = new Label("Descrição:");
        txtDescricao = new TextArea();
        txtDescricao.setPromptText("Ex: Pão brioche, blend de 150g, queijo cheddar...");
        txtDescricao.setPrefRowCount(3); // Limita o tamanho visível da caixa de texto
        txtDescricao.setWrapText(true);

        // 3. Preço
        Label lblPreco = new Label("Preço (R$):");
        txtPreco = new TextField();
        txtPreco.setPromptText("Ex: 29.90");

        // 4. Categoria (Preenche com os valores do seu Enum CategoriaComida)
        Label lblCategoria = new Label("Categoria:");
        comboCategoria = new ComboBox<>();
        comboCategoria.getItems().addAll(CategoriaComida.values());
        comboCategoria.setPromptText("Selecione...");

        // 5. Disponível
        Label lblDisponivel = new Label("Disponível:");
        chkDisponivel = new CheckBox("Item ativo no cardápio");
        chkDisponivel.setSelected(true); // Deixa marcado por padrão

        // 6. Caminho da Imagem (Texto + Botão para buscar arquivo)
        Label lblImagem = new Label("Imagem:");
        txtImagemPath = new TextField();
        txtImagemPath.setEditable(false); // Usuário não digita, apenas escolhe o arquivo
        txtImagemPath.setPromptText("Nenhum arquivo selecionado");

        Button btnProcurarImg = new Button("Buscar...");
        btnProcurarImg.setOnAction(e -> selecionarImagem());

        HBox boxImagem = new HBox(5, txtImagemPath, btnProcurarImg);

        // --- BOTÕES DE AÇÃO ---
        Button btnAdicionar = new Button("Adicionar");
        Button btnCancelar = new Button("Cancelar");

        // Estilização básica dos botões de ação
        btnAdicionar.setStyle("-fx-base: #2ecc71;"); // Verde
        btnCancelar.setStyle("-fx-base: #e74c3c;");  // Vermelho

        // Ações
        btnCancelar.setOnAction(e -> window.close());

        btnAdicionar.setOnAction(e -> {
            if (validarCampos()) {
                // Captura e converte todos os dados para o seu construtor
                String nome = txtNome.getText().trim();
                String descricao = txtDescricao.getText().trim();
                double preco = Double.parseDouble(txtPreco.getText().replace(",", "."));
                CategoriaComida categoria = comboCategoria.getValue();
                boolean disponivel = chkDisponivel.isSelected();
                String imagemPath = txtImagemPath.getText();
                try{
                    // Instancia usando exatamente o seu construtor:
                    novoItem = new ItemCardapio(nome, descricao, preco, categoria, disponivel, imagemPath);
                    CardapioController cardapioController = new CardapioController();
                    cardapioController.adicionarItemAoCardapio(novoItem);
                    System.out.println("Item adicionado com sucesso!");
                    window.close(); // Fecha e retorna à tela principal

                }catch(PrecoInvalidoException er) {
                    mostrarAlerta("Erro", er.getMessage());
                    System.err.println("Erro: " + er.getMessage());
                }catch(ItemJaRegistradoNoCardapio er){
                    mostrarAlerta("Erro",er.getMessage());
                    System.err.println("Erro: "+er.getMessage());
                }
            } else {
                mostrarAlerta("Erro de Validação", "Por favor, preencha todos os campos obrigatórios corretamente.");
                System.out.println("Por favor, preencha todos os campos obrigatórios corretamente.");
            }
        });

        // --- ADICIONANDO AO GRID ---
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

        HBox boxBotoes = new HBox(10, btnAdicionar, btnCancelar);
        boxBotoes.setAlignment(Pos.CENTER_RIGHT);
        grid.add(boxBotoes, 1, 6);

        // Renderização da Janela
        Scene scene = new Scene(grid, 650, 500);
        window.setScene(scene);
        window.showAndWait();

        return novoItem;
    }

    // Método para abrir a janela de escolha de arquivo de imagem
    private void selecionarImagem() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar Imagem do Item");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg")
        );

        File arquivoSelecionado = fileChooser.showOpenDialog(window);
        if (arquivoSelecionado != null) {
            // Guarda o caminho absoluto do arquivo no TextField
            txtImagemPath.setText(arquivoSelecionado.getAbsolutePath());
        }
    }

    // Validação dos dados digitados
    private boolean validarCampos() {
        if (txtNome.getText().trim().isEmpty() ||
                txtDescricao.getText().trim().isEmpty() ||
                txtPreco.getText().trim().isEmpty() ||
                comboCategoria.getValue() == null) {
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