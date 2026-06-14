package br.edu.ifpb.ads.foodjava.model;

import br.edu.ifpb.ads.foodjava.model.enums.CategoriaComida;

public class ItemCardapio {

    private String nome;
    private String descricao;
    private double preco ;
    private CategoriaComida categoriaComida;
    private Boolean disponivel;
    private String imagemPath;

    protected ItemCardapio(){}
    public ItemCardapio(String nome, String descricao, double preco, CategoriaComida categoriaComida, boolean disponivel, String imagemPath){
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoriaComida = categoriaComida;
        this.disponivel = disponivel;
        this.imagemPath = imagemPath;
    }

    // Getters ->
    public String getNome(){
        return nome;
    }
    public String getDescricao(){
        return descricao;
    }public double getPreco(){
        return preco;
    }
    public CategoriaComida getCategoriaComida(){
        return categoriaComida;
    }
    public Boolean getDisponivel(){
        return disponivel;
    }
    public String getImagemPath(){
        return imagemPath;
    }

    // Setters ->
    public void setDisponivel(boolean disponivel){
        this.disponivel = disponivel;
    }
    public void setImagemPath(String imagemPath){
        this.imagemPath = imagemPath;
    }
}
