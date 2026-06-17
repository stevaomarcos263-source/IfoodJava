package br.edu.ifpb.ads.foodjava.model;

import br.edu.ifpb.ads.foodjava.exception.SenhaInvalidaException;

public class Senha {

    private String senha;

    public Senha(String senha) throws SenhaInvalidaException {
        validarCampoSenha(senha);
        validarTamanhoSenha(senha);
        validarMaiusculaSenha(senha);
        validarMinsculaSenha(senha);
        validarNumeroSenha(senha);
        validarCaracterEspecialSenha(senha);

        this.senha = senha;
    }

    // Getters ->
    public String getSenha(){
        return senha;
    }

    private void validarCampoSenha(String senha) throws SenhaInvalidaException{
        if(senha==null || senha.isBlank()){
            throw new SenhaInvalidaException("Campo de validação NULO");
        }
    }
    private void validarTamanhoSenha(String senha) throws SenhaInvalidaException{
        if(senha.length()<8){
            throw new SenhaInvalidaException("Tamanho inválido");
        }
    }
    private void validarMaiusculaSenha(String senha) throws SenhaInvalidaException{
        boolean maiuscula = false;
        for(int i = 0; i<senha.length() ; i++){
            if(Character.isUpperCase(senha.charAt(i))){
                maiuscula = true;

            }
        }
        if(!(maiuscula)){
            throw new SenhaInvalidaException("Sem letras maiúsculas");
        }
    }
    private void validarMinsculaSenha(String senha) throws SenhaInvalidaException{
        boolean minuscula = false;
        for(int i = 0; i<senha.length() ; i++){
            if(Character.isLowerCase(senha.charAt(i))){
                minuscula = true;
            }
        }
        if(!(minuscula)){
            throw new SenhaInvalidaException("Sem letras minúsculas");
        }
    }
    private void validarNumeroSenha(String senha) throws SenhaInvalidaException{
        boolean numero = false;
        for(int i = 0; i<senha.length() ; i++){
            if(Character.isDigit(senha.charAt(i))){
                numero = true;
            }
        }
        if(!(numero)){
            throw new SenhaInvalidaException("Sem números");
        }
    }
    private void validarCaracterEspecialSenha(String senha) throws SenhaInvalidaException{
        boolean especial = false;
        for(int i = 0; i<senha.length() ; i++){
            if(!(Character.isLetterOrDigit(senha.charAt(i))) && !(Character.isWhitespace(senha.charAt(i)))){
                especial = true;
            }
        }
        if(!(especial)){
            throw new SenhaInvalidaException("Sem caracters especiais");
        }
    }

}
