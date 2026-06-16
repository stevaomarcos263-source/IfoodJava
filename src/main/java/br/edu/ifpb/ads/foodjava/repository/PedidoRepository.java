package br.edu.ifpb.ads.foodjava.repository;

import br.edu.ifpb.ads.foodjava.model.Pedido;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PedidoRepository implements Repository<Pedido,String> {

    private static final String FILE_PATH = "src/main/resources/data/pedidos.json";


    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    private List<Pedido> pedidosCache;

    public PedidoRepository(){
        inicialzarArquivo();
        pedidosCache = buscarTodos();
        if(pedidosCache == null){
            pedidosCache = new ArrayList<>();
        }
    }

    // Privado 00
    private void inicialzarArquivo(){
        try{
            File arquivo = new File(FILE_PATH);
            if(!arquivo.exists()){
                arquivo.createNewFile();
                salvarListaNoArquivo(new ArrayList<>());
            }
        }catch(IOException e){
            System.err.println("Erro ao inicializar arquivo: "+e.getMessage());
        }
    }

    // Privado 01
    private void salvarListaNoArquivo(List<Pedido> lista){
        try(FileWriter writer = new FileWriter(FILE_PATH)){
            gson.toJson(lista,writer);
        }catch(IOException e){
            System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }

    @Override
    public void deletar(String id) {
        boolean removido = pedidosCache.removeIf(p -> p.getId().equals(id));
        if (removido) {
            salvarListaNoArquivo(this.pedidosCache);
        }
    }

    @Override
    public void atualizar(Pedido entidade) {
        for (int i = 0; i < pedidosCache.size(); i++) {
            if (pedidosCache.get(i).getId().equals(entidade.getId())) {
                pedidosCache.set(i, entidade);
                salvarListaNoArquivo(this.pedidosCache);
                return;
            }
        }
    }

    @Override
    public List<Pedido> buscarTodos() {
        File arquivo = new File(FILE_PATH);
        if (!arquivo.exists() || arquivo.length() == 0) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type tipoLista = new TypeToken<ArrayList<Pedido>>(){}.getType();
            List<Pedido> lista = gson.fromJson(reader, tipoLista);
            return lista != null ? lista : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Erro ao carregar pedidos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public Pedido buscarPorId(String id) {
        return pedidosCache.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void salvar(Pedido entidade) {
        pedidosCache.add(entidade);
        salvarListaNoArquivo(pedidosCache);
    }

}


