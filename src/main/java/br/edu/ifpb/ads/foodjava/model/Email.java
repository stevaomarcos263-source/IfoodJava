package br.edu.ifpb.ads.foodjava.model;

public class Email {

    private String endereco;
    private String senha;

    protected Email(){}
    public Email(String email, String senha) throws EmailInvalidoException {
        validarCampoDoEmail(email);
        validarArroba(email);
        validarDominio(email);
        validarPontos(email);

        this.endereco = email;
        this.senha = senha;
    }

    // Setters ->
    public void setSenha(String senha){
        this.senha = senha;
    }

    // Getters ->
    public String getEmail(){
        return endereco;
    }
    public String getSenha(){
        return senha;
    }

    public String toString(){
        return String.format("E-mail: %s%n"+
                "Senha: *******%n",endereco);
    }

}

