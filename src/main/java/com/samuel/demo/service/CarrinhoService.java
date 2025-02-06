package com.samuel.demo.service;

import com.samuel.demo.model.Carrinho;
import com.samuel.demo.model.ItemCarrrinho;
import com.samuel.demo.model.Produto;
import com.samuel.demo.repository.CarrinhoRepository;
import com.samuel.demo.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

@Service
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;
    private final ProdutoService produtoService;
    private final ProdutoRepository produtoRepository;

    public CarrinhoService(CarrinhoRepository carrinhoRepositoryrepository, ProdutoService produtoService, ProdutoRepository produtoRepository) {
        this.carrinhoRepository = carrinhoRepositoryrepository;
        this.produtoService = produtoService;
        this.produtoRepository = produtoRepository;
    }

    //adicionar produto no carrinho
    public Carrinho addProdutoCarrinho(Long produtoId, Long carrinhoId, int quantidade){
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId).orElse(new Carrinho());
        Produto produto = produtoRepository.findById(produtoId).orElseThrow(() ->
                new RuntimeException("produto não encontrado"));

        ItemCarrrinho item = new ItemCarrrinho();
        item.setProduto(produto);
        item.setQuantidade(quantidade);

        carrinho.getItens().add(item);
        return carrinhoRepository.save(carrinho);
    }

    //buscar carrinho
    public Carrinho buscarCarrinho(Long carrinhoId){
        return carrinhoRepository.findById(carrinhoId).orElseThrow(() ->
                new RuntimeException("carrinho não encontrado"));
    }

    //limpar o carrinho
    public void limparCarrinho(Long carrinhoId){
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId).orElseThrow(()->
                new RuntimeException("Carrinho não encontrado"));
        carrinho.getItens().clear();
        carrinhoRepository.save(carrinho);
    }


}
