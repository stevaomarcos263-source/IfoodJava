package br.edu.ifpb.ads.foodjava.exception;

public class EmailInvalidoException extends RuntimeException {

    public EmailInvalidoException(String mensagem){
        super(mensagem);
    }
}
