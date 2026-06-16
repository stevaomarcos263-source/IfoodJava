package br.edu.ifpb.ads.foodjava.model;

import java.time.LocalDateTime;
import java.util.UUID;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import br.edu.ifpb.ads.foodjava.model.enums.StatusPedido;

public class Pedido {

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private String id;
    private String cpfCliente;
    private String dataHora;
    private List<PedidoPreMoldado> itens;
    private double valorTotal;
    private StatusPedido statusPedido;

    protected Pedido(){}
    public Pedido(String cpfCliente, List<PedidoPreMoldado> itensPedido){
        this.cpfCliente = cpfCliente;
        itens = new ArrayList<>(itensPedido);
        id = UUID.randomUUID().toString();
        dataHora = LocalDateTime.now().format(formatter);
        statusPedido = StatusPedido.AGUARDANDO_CONFIRMACAO;
        valorTotal = calcularTotal();
    }

    // Getters ->
    public double calcularTotal(){
        return this.itens.stream().mapToDouble(PedidoPreMoldado::getSubTotal).sum();
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
