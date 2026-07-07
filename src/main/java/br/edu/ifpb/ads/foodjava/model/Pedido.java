package br.edu.ifpb.ads.foodjava.model;

import java.time.LocalDate;
import java.util.UUID;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

import br.edu.ifpb.ads.foodjava.model.enums.StatusPedido;

public class Pedido {

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private String id;  // ->
    private String cpfCliente;
    private String data;  // ->
    private List<PedidoPreMoldado> itensDoPedido;
    private double valorTotal; // ->
    private StatusPedido statusPedido; // ->

    protected Pedido(){}
    public Pedido(String cpfCliente, List<PedidoPreMoldado> itensPedido){
        this.cpfCliente = cpfCliente;
        itensDoPedido = new ArrayList<>(itensPedido);
        id = UUID.randomUUID().toString();
        data = LocalDate.now().format(formatter);
        statusPedido = StatusPedido.AGUARDANDO_CONFIRMACAO;
        valorTotal = calcularTotal();
    }

    @Override
    public String toString(){
        return String.format(
                "================== PEDIDO ==================%n"+
                "ID: %s%n"+
                "CPF: %s%n"+
                "Status: %s%n"+
                "Data: %s%n"+
                "Pedidos: %s%n"+
                "Valor total: %.2f%n",id,cpfCliente,statusPedido, data,itensDoPedido,getValorTotal());
    }
    // Getters ->
    private double calcularTotal(){
        return this.itensDoPedido.stream().mapToDouble(PedidoPreMoldado::getSubTotal).sum();
    }
    public double getValorTotal(){
        valorTotal=calcularTotal();
        return valorTotal;
    }
    public String getId(){
        return id;
    }
    public String getCpfCliente(){
        return cpfCliente;
    }
    public String getData(){
        return data;
    }
    public StatusPedido getStatusPedido(){
        return statusPedido;
    }

    public List<PedidoPreMoldado> getListaPedidosPreMoldadosComItemEQuantidade(){
        return itensDoPedido;
    }


    // Stters ->
    public void setStatusPedido(StatusPedido statusPedido){
        this.statusPedido = statusPedido;

    }


}
