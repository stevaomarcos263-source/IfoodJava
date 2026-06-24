package br.edu.ifpb.ads.foodjava.repository;

import br.edu.ifpb.ads.foodjava.exception.FormatoTelefoneException;
import br.edu.ifpb.ads.foodjava.model.User;
import br.edu.ifpb.ads.foodjava.model.Cliente;
import br.edu.ifpb.ads.foodjava.model.Gerente;

import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;

public class LoginRepository implements Repository<User,String > {

    RuntimeTypeAdapterFactory<User> adaptacaoDeUsusrio = RuntimeTypeAdapterFactory.of(User.class,"type").
            registerSubtype(Cliente.class,"Cliente").
            registerSubtype(Gerente.class,"Gerente");

    public static final String FILE_PATH = "src/main/resources/data/login.json";
    private final Gson gson = new GsonBuilder().registerTypeAdapterFactory(adaptacaoDeUsusrio).setPrettyPrinting().create();

    private List<User> listaDeLogins;

    public LoginRepository(){
        inicializarArquivoLogin();
        listaDeLogins = buscarTodos();
    }
    private void inicializarArquivoLogin(){
        try{
            File arquivo = new File(FILE_PATH);
            if(arquivo.getParentFile() != null){
                arquivo.getParentFile().mkdirs();
            }
            if(!arquivo.exists()){
                arquivo.createNewFile();
                salvarUsuarioNoLogin( new ArrayList<>());
            }
        }catch(IOException e){
            System.err.println("Erro ao inicializar arquivo -> login.json <- : "+e.getMessage());
        }
    }

    private void salvarUsuarioNoLogin(List<User> usuarios) {
        try(FileWriter writer = new FileWriter(FILE_PATH)){
            Type tipoDoObjeto = TypeToken.getParameterized(List.class, User.class).getType();
            gson.toJson(usuarios,tipoDoObjeto,writer);
        }catch(IOException e){
        System.err.println("Erro ao tentar salvar lista no ->login.json<- : "+e.getMessage());
        }
    }

    @Override
    public void salvar(User usuario){
        listaDeLogins.add(usuario);
        salvarUsuarioNoLogin(listaDeLogins);
    }

    @Override
    public User buscarPorId(String id){
        return listaDeLogins.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<User> buscarTodos(){
        File arquivo = new File(FILE_PATH);
        if(!arquivo.exists()){
            return new ArrayList<>();
        }
        try(FileReader reader = new FileReader(FILE_PATH)){

            Type tipoDoObjeto = new TypeToken<List<User>>(){}.getType();
            List<User> lista = gson.fromJson(reader,tipoDoObjeto);
            return (lista != null)? lista : new ArrayList<>();
        }catch(IOException e){
            System.err.println("Erro ao trabalhar com arquivo: "+e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public void atualizar(User usuarioAtualizado){
        for(int i = 0; i<listaDeLogins.size() ; i++){
            if(listaDeLogins.get(i).getId().equals(usuarioAtualizado.getId())){
                listaDeLogins.set(i,usuarioAtualizado);
                salvarUsuarioNoLogin(listaDeLogins);
                return;
            }
        }
    }

    @Override
    public void deletar(String id){
        boolean removido = listaDeLogins.removeIf(x -> x.getId().equals(id));
        if(removido){
            salvarUsuarioNoLogin(listaDeLogins);
        }
    }


}
