package com.samuel.demo.service;

import com.samuel.demo.model.Endereco;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CepService {

    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/%s/json/";

    public Endereco buscarEnderecoPeloCep(String cep) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(VIA_CEP_URL, cep);
        return restTemplate.getForObject(url, Endereco.class);
    }
}
