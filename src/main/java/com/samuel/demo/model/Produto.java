package com.samuel.demo.model;

import jakarta.persistence.*;

@Entity(name = "produtos")
@Table(name = "produtos")
public class Produto {

    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String urlImagem;
    private double preco;
    private String descricao;


    //construtor obrigatorio para JPA
    public Produto(){

    }

    //construtor padrão
    public Produto(String nome, String urlImagem, double preco, String descricao) {
        this.nome = nome;
        this.urlImagem = urlImagem;
        this.preco = preco;
        this.descricao = descricao;
    }


    public Produto(Long id, String nome, String urlImagem, double preco, String descricao) {
        this.id = id;
        this.nome = nome;
        this.urlImagem = urlImagem;
        this.preco = preco;
        this.descricao = descricao;
    }

    //métodos getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
