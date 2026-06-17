package br.edu.ifpb.ads.foodjava.model;

import br.edu.ifpb.ads.foodjava.exception.EmailInvalidoException;
import br.edu.ifpb.ads.foodjava.exception.SenhaInvalidaException;

public class Email{

    private String endereco;
    private Senha senha;

    protected Email(){}
    public Email(String email, Senha senha) throws EmailInvalidoException {
        validarCampoDoEmail(email);
        validarArroba(email);
        validarDominio(email);
        validarPontos(email);

        this.endereco = email;
        this.senha = senha;
    }

    // Setters ->
    public void setSenha(Senha senha){
        this.senha = senha;
    }

    // Getters ->
    public String getEmail(){
        return endereco;
    }
    public Senha getSenha(){
        return senha;
    }

    @Override
    public String toString(){
        return String.format("E-mail: %s%n"+
                "Senha: *******%n",endereco);
    }

    // tratando Exceções do email ->

    private void  validarCampoDoEmail(String email) throws EmailInvalidoException{
        if(email==null || email.isBlank()){
            throw new EmailInvalidoException("Campo VAZIO");
        }
        if(email.contains(" ")){
            throw new EmailInvalidoException("E-mail possui espaço -> inválido!");
        }
    }

    private void validarArroba(String email) throws EmailInvalidoException{
        byte totalArroba = 0;
        if(!(email.contains("@"))){
            throw new EmailInvalidoException(("Email não possui \"@\"!"));
        }
        if(email.substring(0,email.indexOf("@")).isEmpty()){
            throw new EmailInvalidoException("E-mail deve possuir caracteres antes do \"@\" !");
        }
        for(int i = 0; i<email.length() ; i++){
            if(email.charAt(i)=='@'){
                totalArroba++;
            }
            if(totalArroba>1){
                throw new EmailInvalidoException("E-mail com excesso de \"@\", número não pode ultrapassar de um (1)!");
            }
        }

    }

    private void validarDominio(String email) throws EmailInvalidoException{
        if(email.contains("@.")){
            throw new EmailInvalidoException("E-mail possui ponto após arroba, inválido!");
        }
        if(email.charAt(email.length()-1)=='.'){
            throw new EmailInvalidoException("Domínio inválido");
        }

        String dominio = email.substring(email.indexOf("@")+1);
        if(dominio.length()<=2){
            throw new EmailInvalidoException("Domínio muito curto ou inválido");
        }
        if(!(dominio.contains("."))){
            throw new EmailInvalidoException("E-mail não possui \".\" de domínio!");
        }
    }

    private void validarPontos(String email) throws EmailInvalidoException{
        if(email.charAt(0)=='.'){
            throw new EmailInvalidoException("E-mail não pode inicializar com ponto!");
        }
        if(email.contains("..")){
            throw new EmailInvalidoException("E-mail possui sequência de pontos -> \"..\" seguidos!");
        }
    }



}

