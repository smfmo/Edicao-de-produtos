package com.samuel.demo.service;

import com.samuel.demo.model.Endereco;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CepService {

    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/%s/json/";
    private static final List<String> CIDADES_ATENDIDAS = List.of("Novo Gama", "Valparaíso de Goiás");

    public Endereco buscarEnderecoPeloCep(String cep) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(VIA_CEP_URL, cep);
        return restTemplate.getForObject(url, Endereco.class);
    }

    //VÁLIDAÇÃO DA REGIÃO
    public boolean cepAtendido(String cep) {
        Endereco endereco = buscarEnderecoPeloCep(cep);
        if (endereco == null) {
            return false; //CEP inválido ou não encontrado
        }
        return CIDADES_ATENDIDAS.contains(endereco.getLocalidade());
    }

}
