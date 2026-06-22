package br.edu.ifpb.ads.foodjava.repository;

import br.edu.ifpb.ads.foodjava.model.ItemCardapio;
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

public class CardapioRepository {

    // Caminho exigido pelo PDF do projeto: src/main/resources/data/cardapio.json
    private static final String FILE_PATH = "src/main/resources/data/cardapio.json";
    // O serializeNulls garante que mesmo campos vazios (como imagemPath opcional) funcionem bem
    private final Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();;

    public CardapioRepository() {
        inicializarArquivo();
    }

    /**
     * Garante que o arquivo cardapio.json e as pastas existam na primeira execução.
     */
    private void inicializarArquivo() {
        try {
            File arquivo = new File(FILE_PATH);
            if (arquivo.getParentFile() != null && !arquivo.getParentFile().exists()) {
                arquivo.getParentFile().mkdirs();
            }
            if (!arquivo.exists()) {
                arquivo.createNewFile();
                salvarListaNoArquivo(new ArrayList<>());
            }
        } catch (IOException e) {
            System.err.println("Erro ao inicializar o arquivo cardapio.json: " + e.getMessage());
        }
    }

    /**
     * Remove ou atualiza reescrevendo a lista inteira de forma segura.
     */
    private void salvarListaNoArquivo(List<ItemCardapio> lista) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados no cardapio.json: " + e.getMessage());
        }
    }
    /**
     * Lê o arquivo JSON e retorna a lista completa de itens do cardápio.
     */
    public List<ItemCardapio> buscarTodos() {
        File arquivo = new File(FILE_PATH);
        if (!arquivo.exists() || arquivo.length() == 0) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<ArrayList<ItemCardapio>>() {}.getType();
            List<ItemCardapio> lista = gson.fromJson(reader, listType);
            return lista != null ? lista : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Erro ao ler o cardápio do arquivo: "+ e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Adiciona um novo item individual ao cardápio e salva no arquivo.
     */
    public void salvar(ItemCardapio novoItem) {
        List<ItemCardapio> cardapio = buscarTodos();
        cardapio.add(novoItem);
        salvarListaNoArquivo(cardapio);
    }

    /**
     * Salva uma lista completa de uma vez (Útil para remoções, edições e IMPORTAÇÃO via JSON).
     */
    public void salvarTodos(List<ItemCardapio> novaLista) {
        salvarListaNoArquivo(novaLista);
    }

    /**
     * Busca um item específico do cardápio pelo nome.
     */
    public ItemCardapio buscarPorNome(String nome) {
        return buscarTodos().stream()
                .filter(item -> item.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .orElse(null);
    }

}