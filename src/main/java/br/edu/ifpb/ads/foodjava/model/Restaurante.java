package br.edu.ifpb.ads.foodjava.model;


public class Restaurante {

    private String nomeDoRestaurante;
    private String cnpj;
    private Endereco enderecoDoRestaurante;
    private Email emailDoRestaurante;
    private Gerente gerente;
    private String categoriaCulinaria;
    private String contatoDoRestaurante;
    private String logoDoRestaurante;

    protected Restaurante(){}
    public Restaurante(String nomeDoRestaurante,
                       String cnpj,
                       String categoriaCulinaria, String contatoDoRestaurante,
                       Gerente gerente, Endereco enderecoDoRestaurante,Email emailDoRestaurante,
                       String logoDoRestaurante){
        this.nomeDoRestaurante = nomeDoRestaurante;
        this.cnpj = cnpj;
        this.categoriaCulinaria = categoriaCulinaria;
        this.contatoDoRestaurante = contatoDoRestaurante;
        this.enderecoDoRestaurante = enderecoDoRestaurante;
        this.gerente = gerente;
        this.emailDoRestaurante = emailDoRestaurante;
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
    public Gerente getGerente(){
        return gerente;
    }
    public String getLogoDoRestaurante(){
        return logoDoRestaurante;
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

    public static Restaurante retornarRestauranteVazio(){
        return new Restaurante();
    }

}
