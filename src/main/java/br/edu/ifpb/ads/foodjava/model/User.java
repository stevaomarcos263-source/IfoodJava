package br.edu.ifpb.ads.foodjava.model;

import java.util.UUID;

public abstract class User{

    private String nome;
    private Email email;
    private Senha senha;
    private String id;

    protected User(){}

    /**
     * @param nome;
     * @param email;
     * @param senha;
     * @throws IllegalArgumentException;
     */
    public User(String nome,Email email,Senha senha) {
        validacaoNome(nome);
        validacaoSenha(senha);
        validacaoEmail(email);
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        id = UUID.randomUUID().toString();
    }

    // Getters ->
    public String getNome(){
        return nome;
    }
    public Email getEmail(){
        return email;
    }
    public Senha getSenhaUser(){
        return senha;
    }
    public String getId(){
        return id;
    }

    public void setNome(String nome){
        this.nome = nome;
    }


    private void validacaoNome(String nome){
        if(nome==null || nome.isEmpty()){
            throw new IllegalArgumentException("Nome com campo vazio!");
        }
    }
    private void validacaoEmail(Email email){
        if(email.getEndereco()==null || email.getEndereco().isEmpty()){
            throw new IllegalArgumentException("E-mail com campo vazio!");
        }
    }
    public void validacaoSenha(Senha senha){
        if(senha.getSenha()==null || senha.getSenha().isEmpty()){
            throw new IllegalArgumentException("Senha com campo vazio!");
        }
    }


}
