package br.edu.ifpb.ads.foodjava.model;

public class Endereco {

    private Integer numeroDaCasa;
    private String rua;
    private String bairro;
    private String cep;
    private String cidade;

    protected Endereco(){}
    public Endereco(int numeroDaCasa, String rua, String bairro, String cep,String cidade ){
        this.numeroDaCasa = numeroDaCasa;
        this.rua = rua;
        this.bairro = bairro;
        this.cep = cep;
        this.cidade = cidade;
    }

    // Stters ->
    public void setNumeroDaCasa(int numeroDaCasa){
        this.numeroDaCasa = numeroDaCasa;
    }
    public void setRua(String rua){
        this.rua = rua;
    }
    public void setBairro(String bairro){
        this.bairro = bairro;
    }
    public void setCep(String cep){
        this.cep = cep;
    }
    public void setCidade(String cidade){
        this.cidade = cidade;
    }

    // Getters ->
    public Integer getNumeroDaCasa(){
        return numeroDaCasa;
    }
    public String getCep(){
        return cep;
    }
    public String getRua(){
        return rua;
    }
    public String getBairro(){
        return bairro;
    }
    public String getCidade(){
        return cidade;
    }

    @Override
    public String toString(){
        return String.format("N° %d,%n"+
                "Rua: %s,%n"+
                "Bairro: %s,%n"+
                "Cidade: %s,%n"+
                "CEP: %s.%n",numeroDaCasa,rua,bairro,cidade,cep);
    }
}
