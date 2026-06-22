package br.edu.ifpb.ads.foodjava.model;

import br.edu.ifpb.ads.foodjava.exception.FormatoEmailInvalidoException;

public class Email{

    private String endereco;

    protected Email(){}
    public Email(String email) throws FormatoEmailInvalidoException {
        validarCampoDoEmail(email);
        validarArroba(email);
        validarDominio(email);
        validarPontos(email);

        this.endereco = email;

    }

    // Getters ->
    public String getEndereco(){
        return endereco;
    }

    @Override
    public String toString(){
        return String.format("E-mail: %s%n"+
                "Senha: *******%n",endereco);
    }

    // tratando Exceções do email ->

    private void  validarCampoDoEmail(String email) throws FormatoEmailInvalidoException{
        if(email==null || email.isBlank()){
            throw new FormatoEmailInvalidoException("Campo VAZIO");
        }
        if(email.contains(" ")){
            throw new FormatoEmailInvalidoException("E-mail possui espaço -> inválido!");
        }
    }

    private void validarArroba(String email) throws FormatoEmailInvalidoException{
        byte totalArroba = 0;
        if(!(email.contains("@"))){
            throw new FormatoEmailInvalidoException(("Email não possui \"@\"!"));
        }
        if(email.substring(0,email.indexOf("@")).isEmpty()){
            throw new FormatoEmailInvalidoException("E-mail deve possuir caracteres antes do \"@\" !");
        }
        for(int i = 0; i<email.length() ; i++){
            if(email.charAt(i)=='@'){
                totalArroba++;
            }
            if(totalArroba>1){
                throw new FormatoEmailInvalidoException("E-mail com excesso de \"@\", número não pode ultrapassar de um (1)!");
            }
        }

    }

    private void validarDominio(String email) throws FormatoEmailInvalidoException{
        if(email.contains("@.")){
            throw new FormatoEmailInvalidoException("E-mail possui ponto após arroba, inválido!");
        }
        if(email.charAt(email.length()-1)=='.'){
            throw new FormatoEmailInvalidoException("Domínio inválido");
        }

        String dominio = email.substring(email.indexOf("@")+1);
        if(dominio.length()<=2){
            throw new FormatoEmailInvalidoException("Domínio muito curto ou inválido");
        }
        if(!(dominio.contains("."))){
            throw new FormatoEmailInvalidoException("E-mail não possui \".\" de domínio!");
        }
    }

    private void validarPontos(String email) throws FormatoEmailInvalidoException{
        if(email.charAt(0)=='.'){
            throw new FormatoEmailInvalidoException("E-mail não pode inicializar com ponto!");
        }
        if(email.contains("..")){
            throw new FormatoEmailInvalidoException("E-mail possui sequência de pontos -> \"..\" seguidos!");
        }
    }



}

