package br.edu.ifpb.ads.foodjava.exception;

public class CancelamentoNaoPermitidoException extends RuntimeException {

    public CancelamentoNaoPermitidoException(String mensagem){
        super(mensagem);
    }
}
