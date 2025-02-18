package com.samuel.demo.Controller;

import com.samuel.demo.model.Endereco;
import com.samuel.demo.service.CepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cep")
public class CepController {

    @Autowired
    private CepService cepService;

    @GetMapping("/{cep}")
    public Endereco getEndereco(@PathVariable String cep) {
        return cepService.buscarEnderecoPeloCep(cep);
    }
}
