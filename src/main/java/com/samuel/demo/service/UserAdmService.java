package com.samuel.demo.service;

import com.samuel.demo.model.UserAdm;
import com.samuel.demo.repository.UserAdmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAdmService {

    private final UserAdmRepository userAdmRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserAdmService(UserAdmRepository userAdmRepository, PasswordEncoder passwordEncoder) {
        this.userAdmRepository = userAdmRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void criarUsuarioAdmin(String username, String password){
        if (userAdmRepository.findByUsername(username).isPresent()) {
            System.out.println("usuário" + username + "já existe. nada foi feito.");
            return;
        }

        UserAdm userAdm = new UserAdm();
        userAdm.setUsername(username);
        userAdm.setPassword(passwordEncoder.encode(password)); //criptografa a senha
        userAdm.setRole("ADMIN"); //define o papel como ADMIN

        userAdmRepository.save(userAdm);
    }
}
