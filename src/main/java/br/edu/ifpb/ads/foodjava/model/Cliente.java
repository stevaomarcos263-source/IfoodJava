package br.edu.ifpb.ads.foodjava.model;

public class Cliente extends User {

    private String contato;
    private Endereco endereco;
    private String cpf;

    protected Cliente(){}
    public Cliente(String nome,Email email,String contato, Endereco endereco,String cpf){
        super(nome,email);
        this.contato = contato;
        this.endereco = endereco;
        this.cpf = cpf;
    }

    // Setters ->
    public void setContato(String contato){
        this.contato = contato;
    }

    //Getters ->
    public String getContato(){
        return contato;
    }
    public Endereco getEndereco(){
        return endereco;
    }
    public String getCpf(){
        return cpf;
    }

}
