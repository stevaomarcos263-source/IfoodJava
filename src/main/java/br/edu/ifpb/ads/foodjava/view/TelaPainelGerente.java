package br.edu.ifpb.ads.foodjava.view;

import br.edu.ifpb.ads.foodjava.controller.CardapioController;

import br.edu.ifpb.ads.foodjava.controller.PedidoController;
import br.edu.ifpb.ads.foodjava.exception.*;
import br.edu.ifpb.ads.foodjava.model.ItemCardapio;
import br.edu.ifpb.ads.foodjava.model.Pedido;
import br.edu.ifpb.ads.foodjava.model.PedidoPreMoldado;
import br.edu.ifpb.ads.foodjava.model.enums.StatusPedido;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import java.io.File;
import java.io.FileReader;
import java.util.List;

public class TelaPainelGerente {

    private BorderPane layoutPrincipal = new BorderPane();

    // TOPO
    private Label tituloPainel = new Label();

    private Button btnImportarCardapio =
            new Button("Importar JSON");

    private Button btnLogout =
            new Button("Sair");

    // RESUMO
    private Label lblTotalPedidos =
            new Label("Total de Pedidos: ");

    private Label lblFaturamento =
            new Label("Faturamento: ");

    private Label lblAguardando =
            new Label("Aguardando: %d");

    private Label lblPreparo =
            new Label("Em preparo");

    private Label lblEntregues =
            new Label("Entregue");

    private ComboBox<String> comboFiltroStatus =
            new ComboBox<>();

    private void atualizarLabelsResumo() {
        // 1. Pega a lista de pedidos que está atualmente na ListView
        java.util.List<Pedido> pedidosAtuais = listaPedidos.getItems();

        int totalRealizados = pedidosAtuais.size();
        int qtdAguardando = 0;
        int qtdEmPreparo = 0;
        int qtdEntregues = 0;
        double faturamentoTotal = 0.0;

        for (Pedido p : pedidosAtuais) {

            if (p.getStatusPedido() == StatusPedido.AGUARDANDO_CONFIRMACAO) {
                qtdAguardando++;
            } else if (p.getStatusPedido() == StatusPedido.EM_PREPARO) {
                qtdEmPreparo++;
            } else if (p.getStatusPedido() == StatusPedido.ENTREGUE) {
                qtdEntregues++;
                faturamentoTotal += p.getValorTotal();
            }
        }

        // 3. Atualiza os textos na tela (Substitua pelo nome das suas variáveis de Label)
        lblTotalPedidos.setText("Total de Pedidos realizados: " + totalRealizados);
        lblFaturamento.setText(String.format("Faturamento: R$ %.2f", faturamentoTotal));
        lblAguardando.setText("Aguardando: " + qtdAguardando);
        lblPreparo.setText("Em preparo: " + qtdEmPreparo);
        lblEntregues.setText("Entregues: " + qtdEntregues);
    }

    // PEDIDOS

    private ListView<Pedido> listaPedidos =
            new ListView<>();

    private Button btnAvancarStatus =
            new Button("Avançar Status");

    private Button btnCancelarPedido =
            new Button("Cancelar Pedido");

    private Button btnDetalhesPedido =
            new Button("Detalhes");

    // CARDÁPIO

    private ListView<String> listaCardapio =
            new ListView<>();

    private Button btnNovoItem =
            new Button("Novo Item");

    private Button btnEditarItem =
            new Button("Editar Item");

    private Button btnExcluirItem =
            new Button("Excluir Item");

    private Button btnAtivarDesativar =
            new Button("Ativar/Desativar");





    public TelaPainelGerente(String nomeRestaurante){

        layoutPrincipal.setPadding(new Insets(20));

        configurarTopo(nomeRestaurante);

        configurarPainelLateral();

        configurarCentro();

        configurarEventos();

        carregarItensDoCardapio();

        carregarPedidosDoDia();




        listaPedidos.setCellFactory(cell -> new ListCell<>() {
            @Override
            protected void updateItem(Pedido pedido, boolean empty) {
                super.updateItem(pedido, empty);

                // CRÍTICO: Se estiver vazio ou nulo, limpe OBRIGATORIAMENTE para não duplicar textualmente
                if (empty || pedido == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Formata a linha normalmente
                    String idResumido = pedido.getId().substring(0, 5);
                    String linhaFormatada = String.format("Pedido #%s | CPF: %s | Total: R$ %.2f | [%s]",
                            idResumido,
                            pedido.getCpfCliente(),
                            pedido.getValorTotal(),
                            pedido.getStatusPedido()
                    );
                    setText(linhaFormatada);
                }
            }
        });


    }

    private void configurarTopo(String nomeRestaurante){

        tituloPainel.setText(
                "Painel de Controle - " +
                        nomeRestaurante
        );

        tituloPainel.setStyle(
                "-fx-font-size:20;" +
                        "-fx-font-weight:bold;" +
                        "-fx-text-fill:#ff4757;"
        );

        HBox topo = new HBox(
                10,
                tituloPainel,
                btnImportarCardapio,
                btnLogout
        );

        topo.setAlignment(Pos.CENTER_LEFT);

        topo.setPadding(
                new Insets(0,0,20,0)
        );

        layoutPrincipal.setTop(topo);
    }

    private void configurarPainelLateral(){

        VBox painelEsquerdo = new VBox(15);

        painelEsquerdo.setPadding(
                new Insets(10)
        );

        painelEsquerdo.setPrefWidth(250);

        Label resumo =
                new Label("Resumo do Dia");

        resumo.setStyle(
                "-fx-font-weight:bold;"
        );

        comboFiltroStatus.getItems().addAll(
                "TODOS",
                "AGUARDANDO_CONFIRMACAO",
                "CONFIRMADO",
                "EM_PREPARO",
                "SAIU_PARA_ENTREGA",
                "ENTREGUE",
                "CANCELADO"
        );

        comboFiltroStatus.setValue("TODOS");

        painelEsquerdo.getChildren().addAll(
                resumo,
                lblTotalPedidos,
                lblFaturamento,
                lblAguardando,
                lblPreparo,
                lblEntregues,
                new Label("Filtrar por status"),
                comboFiltroStatus
        );

        layoutPrincipal.setLeft(
                painelEsquerdo
        );
    }

    private void configurarCentro(){

        VBox centro = new VBox(20);

        Label lblPedidos =
                new Label("Pedidos");

        lblPedidos.setStyle(
                "-fx-font-size:16;" +
                        "-fx-font-weight:bold;"
        );

        HBox acoesPedido = new HBox(
                10,
                btnAvancarStatus,
                btnCancelarPedido,
                btnDetalhesPedido
        );

        Label lblCardapio =
                new Label("Cardápio");

        lblCardapio.setStyle(
                "-fx-font-size:16;" +
                        "-fx-font-weight:bold;"
        );

        HBox acoesCardapio = new HBox(
                10,
                btnNovoItem,
                btnEditarItem,
                btnExcluirItem,
                btnAtivarDesativar
        );

        centro.getChildren().addAll(
                lblPedidos,
                listaPedidos,
                acoesPedido,

                new Separator(),

                lblCardapio,
                listaCardapio,
                acoesCardapio
        );

        layoutPrincipal.setCenter(centro);
    }

    private void configurarEventos(){


        btnImportarCardapio.setOnAction(
                e -> {
                    importarCardapio();
                }

                );

        btnLogout.setOnAction(
                e -> logout()
        );

        btnNovoItem.setOnAction(
                e -> {
                    TelaNovoItem telaCadastro = new TelaNovoItem();
                    ItemCardapio itemCadastrado = telaCadastro.exibe();

                    if (itemCadastrado != null) {
                        // 2. ATUALIZA A INTERFACE VISUAL (Adiciona o texto do item na ListView)
                        // Como sua ListView é de String, vamos formatar como ela deve exibir o item:
                        String linhaTexto = itemCadastrado.getNome() + " - R$ " + String.format("%.2f", itemCadastrado.getPreco());
                        listaCardapio.getItems().add(linhaTexto);

                        System.out.println("Item criado e adicionado à tela com sucesso!");
                    }
                }
        );

        btnEditarItem.setOnAction(
                e -> editarItem()
        );

        btnExcluirItem.setOnAction(
                e -> excluirItem()
        );

        btnAtivarDesativar.setOnAction(
                e -> ativarDesativarItem()
        );

        btnAvancarStatus.setOnAction(
                e -> alterarStatusDoPedidoSelecionado()
        );

        btnCancelarPedido.setOnAction(
                e -> cancelarPedido()
        );

        btnDetalhesPedido.setOnAction(
                e -> mostrarDetalhesPedido()
        );

        btnEditarItem.setOnAction(
                e -> editarItem()
        );

        comboFiltroStatus.setOnAction(
                e -> filtrarPedidos()
        );
    }

    private void importarCardapio() {
        Stage stageAtual = (Stage) btnImportarCardapio.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar JSON do Cardápio");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos JSON", "*.json")
        );

        File arquivo = fileChooser.showOpenDialog(stageAtual);

        if (arquivo != null) {
            CardapioController cardapioController = new CardapioController();

            List<ItemCardapio> itensValidosImportados = new ArrayList<>();
            List<String> itensCorrompidos = new ArrayList<>();

            try (FileReader reader = new FileReader(arquivo);

                 BufferedReader bufferedReader = new BufferedReader(reader)) {
                Gson gson = new Gson();
                // 1. LÊ O ARQUIVO COMO UM OBJETO JSON COMPLETO
                com.google.gson.JsonObject jsonCompleto = gson.fromJson(bufferedReader, com.google.gson.JsonObject.class);
                if(jsonCompleto == null ){
                    throw new ArquivoImportacaoException("Arquivo Corrompido");
                }
                com.google.gson.JsonArray arrayCardapio = jsonCompleto.getAsJsonArray("cardapio");
                if(arrayCardapio == null){
                    throw new ArquivoImportacaoException("O arquivo não possui a chave de valor \"cardapio\"...");
                }
                if (arrayCardapio != null) {
                    // Define o tipo para converter cada item individualmente ou a lista desmembrada
                    Type tipoLista = new TypeToken<ArrayList<ItemCardapio>>(){}.getType();
                    List<ItemCardapio> itensImportados = gson.fromJson(arrayCardapio, tipoLista);

                    if (itensImportados != null) {
                        for (ItemCardapio item : itensImportados) {
                            // Sua validação impecável continua aqui:
                            if (item.getNome() == null || item.getNome().trim().isEmpty() ||
                                    item.getDescricao() == null || item.getDescricao().trim().isEmpty() ||
                                    item.getPreco() <= 0 ||
                                    item.getCategoriaComida() == null) {

                                String identificador = (item.getNome() != null) ? item.getNome() : "Item sem nome";
                                itensCorrompidos.add(identificador + " (Dados ausentes ou inválidos)");
                            } else {
                                itensValidosImportados.add(item);
                            }
                        }
                    }
                }
                // passando item por item para o cardápio
                for (ItemCardapio novoItem : itensValidosImportados) {
                    cardapioController.adicionarItemAoCardapio(novoItem);
                }
                carregarItensDoCardapio();
                // Feedback visual dos Erros/Acertos
                exibirResultadoImportacao(itensValidosImportados.size(), itensCorrompidos);

            } catch (ArquivoImportacaoException e) {
                System.err.println("Erro: "+e.getMessage());
                exibirAlertaInformativo("Erro Grave", "Falha Crítica na Leitura", "O arquivo selecionado não está no formato JSON esperado.");

            }catch (IOException e) {
                System.err.println("Erro ao importar cardápio: " + e.getMessage());
                exibirAlertaInformativo("Erro Grave", "Falha Crítica na Leitura", "O arquivo selecionado não está no formato JSON esperado.");
            }
        }
    }


    private void logout() {

        Alert alertaConfirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        alertaConfirmacao.setTitle("Confirmação de Saída");
        alertaConfirmacao.setHeaderText("Fazer Logout");
        alertaConfirmacao.setContentText("Deseja realmente sair do Painel do Gerente e voltar para a tela de login?");

        java.util.Optional<ButtonType> resultado = alertaConfirmacao.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            // Recupera o Stage (a janela atual do sistema) através do botão de logout
            Stage stagePrincipal = (Stage) btnLogout.getScene().getWindow();

            TelaLogin telaLogin = new TelaLogin();
            Scene novaCena = new Scene(telaLogin.getLayout(), 500, 650);

            // Substitui a cena no palco principal e atualiza o título da janela
            stagePrincipal.setScene(novaCena);
            stagePrincipal.setTitle("FoodJava - Login");
            stagePrincipal.centerOnScreen(); // Centraliza a tela de login bonitinha

            System.out.println("Gerente deslogado com sucesso.");
        }
    }


    private void editarItem(){

        int indiceSelecionado = listaCardapio.getSelectionModel().getSelectedIndex();

        if (indiceSelecionado == -1) {
            exibirAlertaInformativo("Aviso", "Nenhum item selecionado", "Por favor, selecione um item da lista para editar.");
            return;
        }

        CardapioController cardapioController = new CardapioController();
        ItemCardapio itemParaEditar = cardapioController.obterCardapio().get(indiceSelecionado);

        // Abre a tela de edição passando o item selecionado
        TelaEditarItem telaEdicao = new TelaEditarItem(itemParaEditar);
        boolean alteradoComSucesso = telaEdicao.exibe();

        //Se o usuário confirmou as alterações na outra janela
        try{
        if (alteradoComSucesso) {
            cardapioController.editarItem(indiceSelecionado, itemParaEditar);

            // Atualiza a string na tela para refletir os novos dados editados
            String linhaTextoAtualizada = itemParaEditar.getNome() + " - R$ " + String.format("%.2f", itemParaEditar.getPreco());
            listaCardapio.getItems().set(indiceSelecionado, linhaTextoAtualizada);
            System.out.println("Item editado com sucesso na interface!");
            }
        }catch(IdInvalidoException e){
            System.err.println("Erro: "+e.getMessage());
        }catch(IllegalArgumentException e){
            System.err.println("Erro: "+e.getMessage());
        }
    }



    private void carregarPedidosDoDia() {

        List<Pedido> pedidosFiltrado = new ArrayList<>();
        PedidoController pedidoController = new PedidoController();
        java.util.List<Pedido> todosOsPedidos = pedidoController.obterListaDeTodosOsPedidos();
        listaPedidos.getItems().clear();
        int indice = comboFiltroStatus.getSelectionModel().getSelectedIndex();

        if(indice == 0){
            pedidosFiltrado = todosOsPedidos;
        }else if (indice == 1) {
            pedidosFiltrado = todosOsPedidos.stream().filter(p -> p.getStatusPedido() == StatusPedido.AGUARDANDO_CONFIRMACAO).toList();
        }else if(indice == 2) {
            pedidosFiltrado = todosOsPedidos.stream().filter(p -> p.getStatusPedido() == StatusPedido.CONFIRMADO).toList();
        }else if(indice == 3){
            pedidosFiltrado = todosOsPedidos.stream().filter(p -> p.getStatusPedido() == StatusPedido.EM_PREPARO).toList();
        }else if(indice == 4){
            pedidosFiltrado = todosOsPedidos.stream().filter(p -> p.getStatusPedido() == StatusPedido.SAIU_PARA_ENTREGA).toList();
        }else if(indice == 5){
            pedidosFiltrado = todosOsPedidos.stream().filter(p -> p.getStatusPedido() == StatusPedido.ENTREGUE).toList();
        }else if(indice == 6){
            pedidosFiltrado = todosOsPedidos.stream().filter(p -> p.getStatusPedido() == StatusPedido.CANCELADO).toList();
        }

        listaPedidos.setPlaceholder(new Label("Nenhum pedido encontrado"));
        listaPedidos.getItems().addAll(pedidosFiltrado);
        atualizarLabelsResumo();
    }

    private void excluirItem() {

        try {
            int indiceSelecionado = listaCardapio.getSelectionModel().getSelectedIndex();

            // Se o gerente não clicou em nenhum item da lista
            if (indiceSelecionado == -1) {
                exibirAlertaInformativo("Aviso", "Nenhum item selecionado", "Por favor, selecione um item do cardápio para excluir.");
                return;
            }

            // Busca o item real (Objeto) correspondente no seu Controller antes de apagar
            PedidoController pedidoController = new PedidoController();
            CardapioController cardapioController = new CardapioController();
            ItemCardapio itemParaExcluir = cardapioController.obterCardapio().get(indiceSelecionado);

            // 3. Cria uma janela de confirmação para evitar exclusões acidentais
            Alert alertaConfirmacao = new Alert(Alert.AlertType.CONFIRMATION);
            alertaConfirmacao.setTitle("Confirmar Exclusão");
            alertaConfirmacao.setHeaderText("Você está prestes a excluir um item.");
            alertaConfirmacao.setContentText("Deseja realmente excluir o item \"" + itemParaExcluir.getNome() + "\" do cardápio?");

            // 4. Captura a resposta do usuário (OK ou Cancelar)
            java.util.Optional<ButtonType> resultado = alertaConfirmacao.showAndWait();

            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {

                cardapioController.removerItemDoCardapio(itemParaExcluir.getNome());
                cardapioController.obterCardapio().remove(indiceSelecionado);

                // 5. Atualiza a interface gráfica (remove o texto da ListView na hora)
                listaCardapio.getItems().remove(indiceSelecionado);

                System.out.println("Item excluído com sucesso!");
                carregarItensDoCardapio();
            }
        }catch(ItemVinculadoException e){
            exibirAlertaInformativo("Ação bloqueada","Item não pode ser removido do cardapio: ",e.getMessage());
            System.err.println("Erro: "+e.getMessage());
        }
    }


    private void ativarDesativarItem() {

        try {
            int indiceSelecionado = listaCardapio.getSelectionModel().getSelectedIndex();
            // Se nada estiver selecionado, avisa o gerente
            if (indiceSelecionado == -1) {
                exibirAlertaInformativo("Aviso", "Nenhum item selecionado", "Por favor, selecione um item do cardápio para ativar ou desativar.");
                return;
            }
            // Busca o item real (Objeto) correspondente no seu Controller
            CardapioController cardapioController = new CardapioController();
            ItemCardapio item = cardapioController.obterCardapio().get(indiceSelecionado);
            // Inverte o status atual (Se era true vira false, se era false vira true)
            boolean novoStatus = !item.isDisponivel();
            cardapioController.mudarDisponibilidadeDoItem(novoStatus, item.getNome());

            // Atualiza o texto da linha na ListView para refletir a mudança
            // [INDISPONÍVEL] caso o item tenha sido desativado
            String statusTexto = novoStatus ? "" : " [INDISPONÍVEL]";
            String linhaTextoAtualizada = item.getNome() + " - R$ " + String.format("%.2f", item.getPreco()) + statusTexto;
            listaCardapio.getItems().set(indiceSelecionado, linhaTextoAtualizada);



            String mensagemFeedback = novoStatus ? "ativado" : "desativado";
            System.out.println("O item \"" + item.getNome() + "\" foi " + mensagemFeedback + " com sucesso!");

        }catch( CancelamentoNaoPermitidoException e){
            exibirAlertaInformativo("Ação bloqueada","Item não pode ser auterado",e.getMessage());
            System.err.println("Erro: "+e.getMessage());
        }
    }

    private void alterarStatusDoPedidoSelecionado() {

        Pedido pedidoSelecionado = listaPedidos.getSelectionModel().getSelectedItem();

        // 2. Valida se o gerente selecionou algo
        if (pedidoSelecionado == null) {
            javafx.scene.control.Alert alertaAviso = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING, "Selecione um pedido na lista para avançar o status!");
            alertaAviso.setTitle("Aviso");
            alertaAviso.setHeaderText(null);
            alertaAviso.showAndWait();
            return;
        }

        try {
            PedidoController pedidoController = new PedidoController();

            pedidoController.avancarStatusDoPedido(pedidoSelecionado.getId());
            // Feedback de sucesso
            carregarPedidosDoDia();
            String statusPedido = pedidoController.obterListaDeTodosOsPedidos().stream().filter(p -> p.getId().equalsIgnoreCase(pedidoSelecionado.getId())).map(p -> p.getStatusPedido().getStatus()).findFirst().orElse("Status não capturado | Erro ");
            System.out.printf("Status do pedido atualizado para: %s%n",statusPedido);
            Alert sucesso = new Alert(Alert.AlertType.INFORMATION, String.format("Status do pedido atualizado para: %s%n",statusPedido));
            sucesso.setTitle("Sucesso");
            sucesso.setHeaderText(null);
            sucesso.showAndWait();


        } catch (StatusInvalidoException erro) {
            // Captura a sua exceção personalizada caso quebre o peso do status
            Alert alertaErro = new Alert(Alert.AlertType.ERROR, erro.getMessage());
            alertaErro.setTitle("Erro de Sequência");
            alertaErro.setHeaderText("Transição Não Permitida");
            alertaErro.showAndWait();
        } catch (Exception e) {
            Alert erroGeral = new Alert(Alert.AlertType.ERROR, "Erro ao atualizar status: " + e.getMessage());
            erroGeral.showAndWait();
        }
    }

    private void cancelarPedido(){
        try {
            Pedido pedidoSelecionado = listaPedidos.getSelectionModel().getSelectedItem();
            if (pedidoSelecionado == null) {
                throw new IllegalArgumentException(String.format("Selecione algum pedido para poder cancelar%n" +
                        "Regras de cancelamento ->%n" +
                        "Itens que estiverem fora do status \"AGUARDANDO_CONFIRMACAO\", não poderão ser cancelados!"));
            }
            PedidoController pedidoController = new PedidoController();
            pedidoController.cancelarPedido(pedidoSelecionado);
            carregarPedidosDoDia();
        }catch(CancelamentoNaoPermitidoException e){
            System.err.println("Erro ao tantar cancelar pedido: "+e.getMessage());
            exibirAlertaInformativo("Erro","Cancelamento não permitido",e.getMessage());
        }catch(IllegalArgumentException e){
            System.err.println("Erro ao tentar cancelar objeto com valor null: "+e.getMessage());
            exibirAlertaInformativo("Erro","Selecione um item",e.getMessage());
        }catch(IdInvalidoException e){
            System.err.println("Erro: "+e.getMessage());
            exibirAlertaInformativo("Erro","Erro ao procurar ID",e.getMessage());

        }


    }



    private void mostrarDetalhesPedido(){
        Pedido pedidoSelecionado = listaPedidos.getSelectionModel().getSelectedItem();

        if (pedidoSelecionado == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING, "Selecione um pedido para ver os detalhes!");
            alerta.showAndWait();
            return;
        }

        StringBuilder detalhes = new StringBuilder();
        detalhes.append("ID Completo: ").append(pedidoSelecionado.getId()).append("\n");
        detalhes.append("Cliente (CPF): ").append(pedidoSelecionado.getCpfCliente()).append("\n");
        detalhes.append("Data/Hora: ").append(pedidoSelecionado.getDataHora()).append("\n");
        detalhes.append("Status Atual: ").append(pedidoSelecionado.getStatusPedido()).append("\n");
        detalhes.append("--------------------------------------------------\n");
        detalhes.append("ITENS DO PEDIDO:\n");

        for (PedidoPreMoldado item : pedidoSelecionado.getListaPedidosPreMoldadosComItemEQuantidade()) {
            detalhes.append(String.format("• %s x %d (Unid: R$ %.2f)\n",
                    item.getItem().getNome(),
                    item.getQtda(),
                    item.getPrecoUnitario()
            ));
        }detalhes.append("--------------------------------------------------\n");
        detalhes.append(String.format("VALOR TOTAL: R$ %.2f", pedidoSelecionado.getValorTotal()));

        Alert janelaDetalhes = new Alert(Alert.AlertType.INFORMATION);
        janelaDetalhes.setTitle("Detalhes do Pedido");
        janelaDetalhes.setHeaderText("Informações de Processamento");
        janelaDetalhes.setContentText(detalhes.toString());
        janelaDetalhes.getDialogPane().setMinHeight(javafx.scene.layout.Region.USE_PREF_SIZE);

        janelaDetalhes.showAndWait();
    }


    private void filtrarPedidos(){
        carregarPedidosDoDia();
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

    public BorderPane getLayout(){
        return layoutPrincipal;
    }

    private void exibirResultadoImportacao(int quantSucesso, List<String> erros) {

        if (erros.isEmpty()) {
            exibirAlertaInformativo("Sucesso!", "Importação Concluída",
                    quantSucesso + " novos itens foram adicionados ao cardápio com sucesso.");
        } else {
            // Constrói o texto com a lista de itens que falharam ->
            StringBuilder sb = new StringBuilder();
            sb.append("Itens importados com sucesso: ").append(quantSucesso).append("\n\n");
            sb.append("Os seguintes itens estavam CORROMPIDOS e foram ignorados:\n");

            for (String erro : erros) {
                sb.append("- ").append(erro).append("\n");
            }

            Alert alertaErro = new Alert(Alert.AlertType.WARNING);
            alertaErro.setTitle("Importação com Avisos");
            alertaErro.setHeaderText("Alguns itens não puderam ser carregados");

            // Cria uma caixa de texto rolável para o alerta se a lista de erros for gigante ->
            TextArea textArea = new TextArea(sb.toString());
            textArea.setEditable(false);
            textArea.setWrapText(true);
            textArea.setPrefHeight(200);

            alertaErro.getDialogPane().setContent(textArea);
            alertaErro.showAndWait();
        }
    }


    private void carregarItensDoCardapio() {
        listaCardapio.getItems().clear();

        CardapioController cardapioController = new CardapioController();
        List<ItemCardapio> itensDoJson = cardapioController.obterCardapio();

        if (itensDoJson != null) {
            for (ItemCardapio item : itensDoJson) {
                String statusAtualizado = item.isDisponivel()?"":"[INDISPONÍVEL]";
                String linhaTexto = item.getNome() + " - R$ " + String.format("%.2f", item.getPreco())+" "+statusAtualizado;
                listaCardapio.getItems().add(linhaTexto);
            }
        }
    }








}