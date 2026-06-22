package br.edu.ifpb.ads.foodjava.model;

public class Gerente extends User {

    protected Gerente(){}
    public Gerente(String nome, Email email, Senha senha){
        super(nome,email,senha);
    }
}
