package com.samuel.demo.service;

import com.samuel.demo.model.Produto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ArmazenamentoImagemService {

    private final Path rootLocation = Paths.get("uploads");

    private Produto produto;

    public String armazenarImg(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("Arquivo vazio, falha ao carregar imagem");
        }

        //nome único para o arquivo
        String nomeArquivo = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        Path destinoArquivo = rootLocation.resolve(nomeArquivo);

        //vai criar um diretorio se não existir
        if (!Files.exists(rootLocation)) {
            Files.createDirectories(rootLocation);
        }

        //Salvar os arquivos no sistema de arquivos
        Files.copy(file.getInputStream(), destinoArquivo);

        //vai retornar o caminho do arquivo
        return nomeArquivo;
    }
}
