package br.edu.ifpb.ads.foodjava.repository;

import br.edu.ifpb.ads.foodjava.repository.LoginRepository;
import br.edu.ifpb.ads.foodjava.model.Restaurante;
import br.edu.ifpb.ads.foodjava.model.Gerente;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class RestauranteRepository {

    private LoginRepository loginRepository = new LoginRepository();
    private static final String FILE_PATH = "src/main/resources/data/restaurante.json";
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private Restaurante restaurante;

    public RestauranteRepository(){
        inicializarArquivoRestaurante();

    }
    public Restaurante getRestaurante(){
        return restaurante;
    }

    public void salvarRestauranteNaRepository(Restaurante restaurante){
        salvarRestaurante(restaurante);
    }

//region Inicializadores privados
    private void inicializarArquivoRestaurante(){
        try{
            File arquivo = new File(FILE_PATH);
            if(arquivo.getParentFile() != null){
                arquivo.getParentFile().mkdirs();
            }
            if(!arquivo.exists()){
                arquivo.createNewFile();
                restaurante = Restaurante.retornarRestauranteVazio();
                salvarRestaurante(restaurante);
            }else{
                try( FileReader reader = new FileReader(FILE_PATH)){
                    restaurante = gson.fromJson(reader,Restaurante.class);

                    // garante que o restaurante exista ->
                    if(restaurante==null){
                        restaurante = Restaurante.retornarRestauranteVazio();
                    }
                }catch(IOException x){
                    // em caso de erro ao ler restaurante,  ele gera um do mesmo jeito ->
                    System.err.println("erro ao ler Restaurante: "+x.getMessage());
                    restaurante = Restaurante.retornarRestauranteVazio();
                }
            }
        }catch(IOException e){
            System.err.println("Erro ao inicializar repository do restaurante: "+e.getMessage());
        }
    }

    private void salvarRestaurante(Restaurante restaurante){
        try(FileWriter writer = new FileWriter(FILE_PATH)){
            gson.toJson( restaurante,writer);
        }catch(IOException e){
            System.err.println("Erro ao inicializar Restaurante: "+e.getMessage());
        }
    }

}


