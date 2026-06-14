package br.edu.ifpb.ads.foodjava.model;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.util.List;
import br.edu.ifpb.ads.foodjava.model.enums.StatusPedido;

public class Pedido {

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private String id;
    private String cpfCliente;
    private String dataHora;
    private List<ItemPedido> itens;
    private double valorTotal;
    private StatusPedido statusPedido;

    protected Pedido(){}
    public Pedido(String cpfCliente, List<ItemPedido> itensDoCarrinho){
        this.cpfCliente = cpfCliente;
        this.itens = new ArrayList<>();
        id = UUID.randomUUID().toString();
        dataHora = LocalDateTime.now().format(formatter);
        statusPedido = StatusPedido.AGUARDANDO_CONFIRMACAO;
        valorTotal = calcularTotal();
    }

    // Getters ->
    public double calcularTotal(){
        return this.itens.stream().mapToDouble(ItemPedido::getSubTotal).sum();
    }
    public String getId(){
        return id;
    }
    public String getCpfCliente(){
        return cpfCliente;
    }
    public String getDataHora(){
        return dataHora;
    }
    public StatusPedido getStatusPedido(){
        return statusPedido;
    }
    public double getValorTotal(){
        return valorTotal;
    }

    // Stters ->
    public void setStatusPedido(StatusPedido statusPedido){
        this.statusPedido = statusPedido;
    }




}
