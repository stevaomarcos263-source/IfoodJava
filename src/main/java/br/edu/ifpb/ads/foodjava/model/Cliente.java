package br.edu.ifpb.ads.foodjava.model;

import br.edu.ifpb.ads.foodjava.exception.DocumentoInvalidoException;
import br.edu.ifpb.ads.foodjava.exception.FormatoTelefoneException;
import br.edu.ifpb.ads.foodjava.util.ValidadorCPF;
import br.edu.ifpb.ads.foodjava.util.ValidadorTelefone;

public class Cliente extends User {

    private String contato;
    private Endereco endereco;
    private String cpf;

    protected Cliente(){}
    public Cliente(String nome,Email email,Senha senha,String contato, Endereco endereco,String cpf){
        super(nome,email,senha);
        if(!ValidadorTelefone.isTelefone(contato)){
            throw new FormatoTelefoneException("Formato de telefone inválido!");
        }
        if(!ValidadorCPF.isCPF(cpf)){
            throw new DocumentoInvalidoException("CPF inválido");
        }
        setContato(contato);
        this.endereco = endereco;
        this.cpf = cpf;
    }

    // Setters ->
    public void setContato(String contato) throws FormatoTelefoneException{
        if(!ValidadorTelefone.isTelefone(contato)){
            throw new FormatoTelefoneException("Número de contato é inválido!");
        }
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
