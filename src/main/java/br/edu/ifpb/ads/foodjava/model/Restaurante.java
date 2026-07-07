package br.edu.ifpb.ads.foodjava.model;

import br.edu.ifpb.ads.foodjava.exception.DocumentoInvalidoException;
import br.edu.ifpb.ads.foodjava.exception.FormatoTelefoneException;
import br.edu.ifpb.ads.foodjava.util.ValidadorCNPJ;
import br.edu.ifpb.ads.foodjava.util.ValidadorTelefone;

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
    //private List<ItemCardapio> cardapio;
    private Cardapio cardapio;

    protected Restaurante(){
        nomeDoRestaurante = "vazio";
    }

    /**
     * @param nomeDoRestaurante;
     * @param cnpj;
     * @param categoriaCulinaria;
     * @param contatoDoRestaurante;
     * @param gerente;
     * @param enderecoDoRestaurante;
     * @param emailDoRestaurante;
     * @param senha;
     * @param logoDoRestaurante;
     * @throws DocumentoInvalidoException;
     */
    public Restaurante(String nomeDoRestaurante,
                       String cnpj,
                       String categoriaCulinaria, String contatoDoRestaurante,
                       Gerente gerente, Endereco enderecoDoRestaurante,Email emailDoRestaurante,Senha senha,
                       String logoDoRestaurante){

        if(nomeDoRestaurante == null || nomeDoRestaurante.isEmpty()){
            throw new IllegalArgumentException("Nome do restaurante com campo vazio!");
        }
        //cardapio = new ArrayList<>();
        cardapio = new Cardapio();
        setNomeDoRestaurante(nomeDoRestaurante);
        setCnpj(cnpj);
        setContatoDoRestaurante(contatoDoRestaurante);
        this.categoriaCulinaria = categoriaCulinaria;
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
    public Cardapio getCardapio(){
        return cardapio;
    }



    // Stters ->
    public void setContatoDoRestaurante(String contatoDoRestaurante){
        if(!ValidadorTelefone.isTelefone(contatoDoRestaurante)){
            throw new FormatoTelefoneException("Formato de telefone inválido!");
        }
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
    public void setCardapio(Cardapio cardapio){
        this.cardapio = cardapio;
    }

    public void adicionarItemAoCardapio(ItemCardapio item) {
        this.cardapio.adicionarItemEmCardapio(item);
    }

    public void removerItemDoCardapio(ItemCardapio item) {
        this.cardapio.removerItemEmCardapio(item);
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
