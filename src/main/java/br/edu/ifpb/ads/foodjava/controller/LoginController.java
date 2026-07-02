package br.edu.ifpb.ads.foodjava.controller;

import br.edu.ifpb.ads.foodjava.model.*;

import br.edu.ifpb.ads.foodjava.repository.LoginRepository;

public class LoginController {

    private final LoginRepository loginRepository;

    public LoginController() {
        loginRepository = new LoginRepository();
    }

    public User autenticarEntradaDeUsuario(String email, String senha) {
        return loginRepository.autenticarUsuarioTentandoEntrarEmSistema(email,senha);
    }

    public void salvarNovoUsuarioNoArquivoLoginJson(String nome, Email email, Senha senha, String contato, Endereco endereco, String cpf){
        loginRepository.cadastrarCliente(nome,email,senha,contato,endereco,cpf);
    }
    public void salvarGerenteNoRepository(Gerente gerente){
        loginRepository.salvarGerente(gerente);
    }
}


