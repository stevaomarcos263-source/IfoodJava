package br.edu.ifpb.ads.foodjava.model;

import br.edu.ifpb.ads.foodjava.exception.DocumentoInvalidoException;
import br.edu.ifpb.ads.foodjava.exception.FormatoEmailInvalidoException;
import br.edu.ifpb.ads.foodjava.exception.FormatoSenhaInvalidoException;
import br.edu.ifpb.ads.foodjava.util.ValidadorCNPJ;


import java.util.List;
import java.util.ArrayList;
import java.util.stream.*;

public class Restaurante {

    private String nomeDoRestaurante;
    private String cnpj;
    private Endereco enderecoDoRestaurante;
    private Email emailDoRestaurante;
    private Senha senha;
    private Gerente gerente;
    private String categoriaCulinaria;
    private String contatoDoRestaurante;
    private String logoDoRestaurante;
    private List<ItemCardapio> cardapio;

    protected Restaurante(){}
    public Restaurante(String nomeDoRestaurante,
                       String cnpj,
                       String categoriaCulinaria, String contatoDoRestaurante,
                       Gerente gerente, Endereco enderecoDoRestaurante,Email emailDoRestaurante,Senha senha,
                       String logoDoRestaurante) throws DocumentoInvalidoException, FormatoSenhaInvalidoException, FormatoEmailInvalidoException {
        if(ValidadorCNPJ.isCNPJ(cnpj)){
            throw new DocumentoInvalidoException("Documento (CNPJ) inválido!");
        }
        cardapio = new ArrayList<>();
        this.nomeDoRestaurante = nomeDoRestaurante;
        this.cnpj = cnpj;
        this.categoriaCulinaria = categoriaCulinaria;
        this.contatoDoRestaurante = contatoDoRestaurante;
        this.enderecoDoRestaurante = enderecoDoRestaurante;
        this.emailDoRestaurante = emailDoRestaurante;
        this.senha = senha;
        this.gerente = gerente;
        this.logoDoRestaurante = logoDoRestaurante;
    }

    // Getters ->
    public String getNomeDoRestaurante(){
        return nomeDoRestaurante;
    }
    public String getCnpj(){
        return cnpj;
    }
    public String getCategoriaCulinaria(){
        return categoriaCulinaria;
    }
    public String getContatoDoRestaurante(){
        return contatoDoRestaurante;
    }
    public Endereco getEnderecoDoRestaurante(){
        return enderecoDoRestaurante;
    }
    public Email getEmailDoRestaurante(){
        return emailDoRestaurante;
    }
    public Senha getSenha(){
        return senha;
    }
    public Gerente getGerente(){
        return gerente;
    }
    public String getLogoDoRestaurante(){
        return logoDoRestaurante;
    }
    public List<ItemCardapio> getCardapio(){
        return cardapio;
    }



    // Stters ->
    public void setContatoDoRestaurante(String contatoDoRestaurante){
        this.contatoDoRestaurante = contatoDoRestaurante;
    }
    public void setLogoDoRestaurante(String logoDoRestaurante){
        this.logoDoRestaurante = logoDoRestaurante;
    }
    public void setNomeDoRestaurante(String nomeDoRestaurante){
        this.nomeDoRestaurante = nomeDoRestaurante;
    }
    public void setEnderecoDoRestaurante(Endereco enderecoDoRestaurante){
        this.enderecoDoRestaurante = enderecoDoRestaurante;
    }
    public void setCategoriaCulinaria(String categoriaCulinaria){
        this.categoriaCulinaria = categoriaCulinaria;
    }
    public void setNomeDoGerente(String nomeDoGerente){
        gerente.setNome(nomeDoGerente);
    }
    public void setCardapio(List<ItemCardapio> cardapio){
        this.cardapio = cardapio;
    }
    public void setCnpj(String cnpj)throws DocumentoInvalidoException{
        if(!ValidadorCNPJ.isCNPJ(cnpj)){
            throw new DocumentoInvalidoException("Documento inválido!");
        }
        this.cnpj = cnpj;
    }


    public static Restaurante retornarRestauranteVazio(){
        return new Restaurante();
    }

}
