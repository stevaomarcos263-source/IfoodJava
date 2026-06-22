package br.edu.ifpb.ads.foodjava.model;

import java.util.UUID;

public abstract class User{

    private String nome;
    private Email email;
    private Senha senha;
    private String id;

    protected User(){}
    public User(String nome,Email email,Senha senha){
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


}
