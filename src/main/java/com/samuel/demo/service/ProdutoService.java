package com.samuel.demo.service;

import com.samuel.demo.model.Produto;
import com.samuel.demo.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    //listar os produtos
    public List<Produto> getAllProdutos(){
        return repository.findAll();
    }

    //adicionar os produtos
    public void addProduto(Produto produto){
        repository.save(produto);
    }

    //buscar por id
    public Optional<Produto> buscarpeloId(Long id){
        return repository.findById(id);
    }

    //atualizar produto
    public void atualizarProduto(Long id, Produto produtoAtualizado){
        repository.findById(id).ifPresent(produto -> {
            produto.setNome(produtoAtualizado.getNome());
            produto.setUrlImagem(produtoAtualizado.getUrlImagem());
            produto.setPreco(produtoAtualizado.getPreco());
            produto.setDescricao(produtoAtualizado.getDescricao());
            repository.save(produto);
        });
    }

    //deletar produto
    public void deletarProduto(Long id){
        repository.deleteById(id);
    }
}
