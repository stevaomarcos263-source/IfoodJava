package br.edu.ifpb.ads.foodjava.model;

public class Endereco {

    private Integer numeroDaCasa;
    private String rua;
    private String bairro;
    private String cep;
    private String cidade;

    protected Endereco(){}
    public Endereco(int numeroDaCasa, String rua, String bairro, String cep,String cidade ){
        setNumeroDaCasa(numeroDaCasa);
        setRua(rua);
        setBairro(bairro);
        setCidade(cidade);
    }

    // Stters ->
    public void setNumeroDaCasa(int numeroDaCasa){
        if(numeroDaCasa<0){
            throw new IllegalArgumentException("Número da casa inválido!");
        }
        this.numeroDaCasa = numeroDaCasa;
    }
    public void setRua(String rua){
        if(rua==null || rua.isBlank()){
            throw new IllegalArgumentException("Rua com endereço vazio!");
        }
        this.rua = rua.trim();
    }
    public void setBairro(String bairro){
        if(bairro==null || bairro.isBlank()){
            throw new IllegalArgumentException("Bairro com endereço vazio!");
        }
        this.bairro = bairro.trim();

    }
    public void setCep(String cep){
       if(cep==null || cep.isBlank()){
           throw new IllegalArgumentException("CEP com endereço vazio!");
       }
        this.cep = cep.trim();
    }
    public void setCidade(String cidade){
        if(cidade==null || cidade.isBlank()){
            throw new IllegalArgumentException("Cidade com endereço vazio!");
        }
        this.cidade = cidade.trim();
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

    public void atualizarEndereco(int numeroDaCasa, String rua, String bairro, String cep, String cidade){
        setNumeroDaCasa(numeroDaCasa);
        setRua(rua);
        setBairro(bairro);
        setCidade(cidade);
        setCep(cep);
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
