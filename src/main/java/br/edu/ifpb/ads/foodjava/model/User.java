package br.edu.ifpb.ads.foodjava.model;

import java.util.UUID;

public abstract class User{

    private String nome;
    private Email email;
    private String id;

    protected User(){}
    public User(String nome,Email email){
        this.nome = nome;
        this.email = email;
        id = UUID.randomUUID().toString();
    }

    // Getters ->
    public String getNome(){
        return nome;
    }
    public Email getEmail(){
        return email;
    }
    public String getId(){
        return id;
    }

    public void setNome(String nome){
        this.nome = nome;
    }


}
