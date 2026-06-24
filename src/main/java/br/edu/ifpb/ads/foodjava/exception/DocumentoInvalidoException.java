package br.edu.ifpb.ads.foodjava.exception;

public class DocumentoInvalidoException extends RuntimeException {

    public DocumentoInvalidoException(String mensagem){
        super(mensagem);
    }
}
