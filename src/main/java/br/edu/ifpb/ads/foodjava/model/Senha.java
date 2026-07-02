package br.edu.ifpb.ads.foodjava.model;

import br.edu.ifpb.ads.foodjava.exception.FormatoSenhaInvalidoException;
import br.edu.ifpb.ads.foodjava.exception.SenhaInvalidaException;

public class Senha {

    private String senha;

    /**
     * @param senha;
     * @throws FormatoSenhaInvalidoException;
     */
    public Senha(String senha) throws FormatoSenhaInvalidoException {
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

    private void validarCampoSenha(String senha) throws FormatoSenhaInvalidoException {
        if(senha==null || senha.isBlank()){
            throw new FormatoSenhaInvalidoException("Campo de validação NULO");
        }
    }
    private void validarTamanhoSenha(String senha) throws FormatoSenhaInvalidoException{
        if(senha.length()<8){
            throw new FormatoSenhaInvalidoException("Tamanho inválido");
        }
    }
    private void validarMaiusculaSenha(String senha) throws FormatoSenhaInvalidoException{
        boolean maiuscula = false;
        for(int i = 0; i<senha.length() ; i++){
            if(Character.isUpperCase(senha.charAt(i))){
                maiuscula = true;

            }
        }
        if(!(maiuscula)){
            throw new FormatoSenhaInvalidoException("Sem letras maiúsculas");
        }
    }
    private void validarMinsculaSenha(String senha) throws FormatoSenhaInvalidoException{
        boolean minuscula = false;
        for(int i = 0; i<senha.length() ; i++){
            if(Character.isLowerCase(senha.charAt(i))){
                minuscula = true;
            }
        }
        if(!(minuscula)){
            throw new FormatoSenhaInvalidoException("Sem letras minúsculas");
        }
    }
    private void validarNumeroSenha(String senha) throws FormatoSenhaInvalidoException{
        boolean numero = false;
        for(int i = 0; i<senha.length() ; i++){
            if(Character.isDigit(senha.charAt(i))){
                numero = true;
            }
        }
        if(!(numero)){
            throw new FormatoSenhaInvalidoException("Sem números");
        }
    }
    private void validarCaracterEspecialSenha(String senha) throws FormatoSenhaInvalidoException{
        boolean especial = false;
        for(int i = 0; i<senha.length() ; i++){
            if(!(Character.isLetterOrDigit(senha.charAt(i))) && !(Character.isWhitespace(senha.charAt(i)))){
                especial = true;
            }
        }
        if(!(especial)){
            throw new FormatoSenhaInvalidoException("Sem caracteres especiais");
        }
    }

}
