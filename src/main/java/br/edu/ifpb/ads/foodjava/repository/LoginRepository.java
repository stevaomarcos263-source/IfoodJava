package br.edu.ifpb.ads.foodjava.repository;

import br.edu.ifpb.ads.foodjava.exception.EmailInvalidoException;
import br.edu.ifpb.ads.foodjava.exception.SenhaInvalidaException;
import br.edu.ifpb.ads.foodjava.exception.UsuarioDuplicadoException;
import br.edu.ifpb.ads.foodjava.model.*;

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

public class LoginRepository implements RepositoryLogin<User,String > {

    RuntimeTypeAdapterFactory<User> adaptacaoDeUsusrio = RuntimeTypeAdapterFactory.of(User.class,"type").
            registerSubtype(Cliente.class,"Cliente").
            registerSubtype(Gerente.class,"Gerente");

    public static final String FILE_PATH = "src/main/resources/data/login.json";
    private final Gson gson = new GsonBuilder().registerTypeAdapterFactory(adaptacaoDeUsusrio).setPrettyPrinting().create();

    private List<User> listaDeLogins;



    public LoginRepository(){
        inicializarArquivoLogin();
        listaDeLogins = buscarTodosOsUsuarios();
    }

    // Inicializa o arquivo com segurança devido o getParentFile().mkdirs() e o exists() - que confere se o arquivo existe ou não
    private void inicializarArquivoLogin(){
        try{
            File arquivo = new File(FILE_PATH);
            if(arquivo.getParentFile() != null){
                arquivo.getParentFile().mkdirs();
            }
            if(!arquivo.exists()){
                arquivo.createNewFile();
                listaDeLogins = new ArrayList<>();
                try(FileReader arquivoRestaurante = new FileReader("src/main/resources/data/restaurante.json")){
                    Restaurante restauranteProvisorio = gson.fromJson(arquivoRestaurante, Restaurante.class);
                    if (restauranteProvisorio != null && restauranteProvisorio.getGerente() != null) {
                        listaDeLogins.add(restauranteProvisorio.getGerente());
                    }
                } catch(IOException e){
                    System.err.println("Erro ao ler gerente do restaurante no arquivo novo: " + e.getMessage());
                }
                // Salvando gerente no login em caso que o arquivo login.json seja apagado, evitando que o programa fique travado!
                salvarUsuarioNoLogin(listaDeLogins);
            }else{
                try (FileReader reader = new FileReader(FILE_PATH)) {
                    Type type = new TypeToken<ArrayList<User>>(){}.getType();
                    listaDeLogins = gson.fromJson(reader, type);
                    if(listaDeLogins==null || listaDeLogins.isEmpty()){
                        listaDeLogins = new ArrayList<>();
                        try(FileReader arquivoRestaurante = new FileReader("src/main/resources/data/restaurante.json")){
                            Restaurante restauranteProvisorio = gson.fromJson(arquivoRestaurante,Restaurante.class);
                            if(restauranteProvisorio != null && restauranteProvisorio.getGerente()!=null) {
                                listaDeLogins.add(restauranteProvisorio.getGerente());
                            }
                            salvarUsuarioNoLogin(listaDeLogins);
                        }catch(IOException e){
                            System.err.println("Erro ao ler gerente do restaurante:"+e.getMessage());
                        }
                    }
                } catch (IOException x) {
                    System.err.println("Erro ao ler arquivo de login: " + x.getMessage());
                }
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

    public void salvarGerente(Gerente gerente){

        listaDeLogins.add(gerente);
        salvarUsuarioNoLogin(listaDeLogins);
    }
    @Override
    public void salvarNovoUsuarioNoArquivoLoginJson(User usuario){
        listaDeLogins.add(usuario);
        salvarUsuarioNoLogin(listaDeLogins);
    }

    @Override
    public User buscarUsuarioPorId(String id){
        return listaDeLogins.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<User> buscarTodosOsUsuarios(){
        File arquivo = new File(FILE_PATH);
        if(!arquivo.exists()){
            return new ArrayList<>();
        }
        try(FileReader reader = new FileReader(FILE_PATH)){

            Type tipoDoObjeto = new TypeToken<List<User>>(){}.getType();
            List<User> lista = gson.fromJson(reader,tipoDoObjeto);
            return (lista != null)? lista : new ArrayList<>();
        }catch(IOException e){
            System.err.println("Erro ao tentar importar lista de usuários do json para o sistema como objeto: "+e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * @param email;
     * @param senha;
     * @return {@link User};
     */
    // serve para autentica quem está tentando entrar e retorna o usuário, com isso posso por em tela "Bem-vindo <usuárioLogado>"
    public User autenticarUsuarioTentandoEntrarEmSistema(String email, String senha) {

        User usuarioEncontrado = buscarTodosOsUsuarios().stream().filter(user -> user.getEmail().getEndereco().equalsIgnoreCase(email)).findFirst().orElseThrow(() -> new EmailInvalidoException("E-mail não encontrado"));
        if (!usuarioEncontrado.getSenhaUser().getSenha().equals(senha)) {
            throw new SenhaInvalidaException("Senha incorreta!");
        }
        return usuarioEncontrado;
    }

    /**
     * @param nome;
     * @param email;
     * @param senha;
     * @param contato;
     * @param endereco;
     * @param cpf;
     * @throws UsuarioDuplicadoException;
     */
    // cadastrar Cliente ->
    public void cadastrarCliente(String nome, Email email, Senha senha, String contato, Endereco endereco, String cpf){

        if(buscarTodosOsUsuarios().stream().filter(cliente -> cliente instanceof Cliente).anyMatch(cliente -> ((Cliente)cliente).getCpf().equals(cpf))){
            throw new UsuarioDuplicadoException("Usuário com \"cpf\" já cadastrado!");
        }
        if(buscarTodosOsUsuarios().stream().anyMatch(cliente -> cliente.getEmail().getEndereco().equalsIgnoreCase(email.getEndereco()))) {
            throw new UsuarioDuplicadoException("E-mail já cadastrado");
        }

        Cliente novoCliente = new Cliente(nome, email, senha,contato, endereco,cpf);
        salvarNovoUsuarioNoArquivoLoginJson(novoCliente);
    }


}
