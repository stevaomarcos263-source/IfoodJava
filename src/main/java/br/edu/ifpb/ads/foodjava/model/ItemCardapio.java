package br.edu.ifpb.ads.foodjava.model;

import br.edu.ifpb.ads.foodjava.exception.PrecoInvalidoException;
import br.edu.ifpb.ads.foodjava.model.enums.CategoriaComida;

public class ItemCardapio {

    private String nome;
    private String descricao;
    private double preco ;
    private CategoriaComida categoria;
    private Boolean disponivel;
    private String imagemPath;

    protected ItemCardapio(){}

    /**
     * @param nome;
     * @param descricao;
     * @param preco;
     * @param categoria;
     * @param disponivel;
     * @param imagemPath;
     * @throws PrecoInvalidoException;
     */
    public ItemCardapio(String nome, String descricao, double preco, CategoriaComida categoria,
                        boolean disponivel, String imagemPath) {
        setPreco(preco);
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
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
        return categoria;
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
    public void setPreco(double preco) throws PrecoInvalidoException{
        if(preco<=0){
            throw new PrecoInvalidoException("Preço inválido, igual ou menor que zero!");
        }
        this.preco = preco;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public void setDescricao(String descricao){
        this.descricao = descricao;
    }
    public boolean isDisponivel(){
        if(disponivel == true){
            return true;
        }
        return false;
    }

}
