package br.edu.ifpb.ads.foodjava.exception;

public class FormatoEmailInvalidoException extends RuntimeException{

    public FormatoEmailInvalidoException(String mensagem){
        super(mensagem);
    }
}
