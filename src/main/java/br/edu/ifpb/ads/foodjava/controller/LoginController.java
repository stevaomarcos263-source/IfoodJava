package br.edu.ifpb.ads.foodjava.controller;

import br.edu.ifpb.ads.foodjava.exception.*;
import br.edu.ifpb.ads.foodjava.model.Email;
import br.edu.ifpb.ads.foodjava.model.Senha;
import br.edu.ifpb.ads.foodjava.model.User;
import br.edu.ifpb.ads.foodjava.model.Cliente;
import br.edu.ifpb.ads.foodjava.model.Endereco;

import br.edu.ifpb.ads.foodjava.repository.LoginRepository;

public class LoginController {

    private final LoginRepository loginRepository;

    public LoginController() {
        loginRepository = new LoginRepository();
    }

    public User autenticar(String email, String senha) {

        User usuarioEncontrado = loginRepository.buscarTodos().stream().filter(user -> user.getEmail().getEndereco().equalsIgnoreCase(email)).findFirst().orElseThrow(() -> new EmailInvalidoException("E-mail não encontrado"));

        if (!usuarioEncontrado.getSenhaUser().getSenha().equals(senha)) {
            throw new SenhaInvalidaException("Senha incorreta!");
        }
        return usuarioEncontrado;
    }

    public void cadastrarCliente(String nome, Email email, Senha senha, String contato, Endereco endereco, String cpf){

        if(loginRepository.buscarTodos().stream().filter(cliente -> cliente instanceof Cliente).anyMatch(cliente -> ((Cliente)cliente).getCpf().equals(cpf))){
            throw new UsuarioDuplicadoException("Usuário \"cpf\" já cadastrado!");
        }


        if(loginRepository.buscarTodos().stream().anyMatch(cliente -> cliente.getEmail().getEndereco().equalsIgnoreCase(email.getEndereco()))) {
            throw new UsuarioDuplicadoException("E-mail já cadastrado");
        }
            Cliente novoCliente = new Cliente(nome, email, senha,contato, endereco,cpf);
        loginRepository.salvar(novoCliente);
    }
}


