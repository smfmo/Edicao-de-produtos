package com.samuel.demo.components;

import com.samuel.demo.service.UserAdmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InicializacaoAdm implements CommandLineRunner {

    private final UserAdmService userAdmService;

    @Autowired
    public InicializacaoAdm(UserAdmService userAdmService) {
        this.userAdmService = userAdmService;
    }

    @Override
    public void run (String... args) throws Exception{
        //aqui cria o usuário administrador
        userAdmService.criarUsuarioAdmin("admin", "senha123");
    }
}
