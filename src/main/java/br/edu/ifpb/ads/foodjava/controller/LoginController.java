package br.edu.ifpb.ads.foodjava.controller;

import br.edu.ifpb.ads.foodjava.exception.*;
import br.edu.ifpb.ads.foodjava.model.Email;
import br.edu.ifpb.ads.foodjava.model.Senha;
import br.edu.ifpb.ads.foodjava.model.User;
import br.edu.ifpb.ads.foodjava.model.Cliente;
import br.edu.ifpb.ads.foodjava.model.Gerente;
import br.edu.ifpb.ads.foodjava.model.Endereco;

import br.edu.ifpb.ads.foodjava.repository.LoginRepository;

public class LoginController {

    String nomeSimulado = "Marcos";
    String cpfSimulado = "123.456.789-00";
    String senhaSimulada = "Marcos123_";
    String emailSimulado = "marcos@email.com";
    String contatoSimulado = "(87) 991602912";

    String ruaSimulada = "Av. Principal";
    int numeroSimulado = 100;
    String bairroSimulado = "Centro";

    private final LoginRepository loginRepository;

    public LoginController() {
        loginRepository = new LoginRepository();
    }

    public User autenticar(String email, String senha) throws EmailInvalidoException,SenhaInvalidaException {

        User usuarioEncontrado = loginRepository.buscarTodos().stream().filter(user -> user.getEmail().getEndereco().equalsIgnoreCase(email)).findFirst().orElseThrow(() -> new EmailInvalidoException("E-mail não encontrado"));

        if (!usuarioEncontrado.getSenhaUser().getSenha().equals(senha)) {
            throw new SenhaInvalidaException("Senha incorreta!");
        }
        return usuarioEncontrado;
    }

    public void cadastrarCliente(){

        try{
            User novoCliente = new Cliente("Sebastião", new Email(emailSimulado), new Senha(senhaSimulada),contatoSimulado, new Endereco(numeroSimulado,ruaSimulada,bairroSimulado,"Sertânia","56600-000"),cpfSimulado);
            loginRepository.salvar(novoCliente);
        }catch(FormatoEmailInvalidoException e){
            System.err.println("Erro ao gerar endereço e-mail: "+e.getMessage());
        }catch(FormatoSenhaInvalidoException e){
            System.err.println("Erro ao gerar senha: "+e.getMessage());
        }catch(FormatoTelefoneException e){
            System.err.println("Erro no formato do telefone! "+e.getMessage());
        }

    }




}


