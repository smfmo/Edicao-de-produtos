package com.samuel.demo.service;

import com.samuel.demo.model.Carrinho;
import com.samuel.demo.model.ItemCarrinho;
import com.samuel.demo.model.Produto;
import com.samuel.demo.repository.CarrinhoRepository;
import com.samuel.demo.repository.ItemCarrinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarrinhoService{
    @Autowired
    private CarrinhoRepository carrinhoRepository;
    @Autowired
    private ItemCarrinhoRepository itemCarrinhoRepository;
    @Autowired
    private ProdutoService produtoService;

    //criar um carrinho
    public Carrinho criarCarrinho(){
        Carrinho carrinho = new Carrinho();
        return carrinhoRepository.save(carrinho);
    }

    //adicionar itens ao carrinho
    public void adicionarItemAoCarrinho(Long carrinhoId, Long produtoId, int quantidade){
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId).orElseThrow();
        Produto produto = produtoService.buscarpeloId(produtoId).orElseThrow();

        ItemCarrinho item = new ItemCarrinho();
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setCarrinho(carrinho);
        itemCarrinhoRepository.save(item);

    }

    public List<ItemCarrinho> listarItensDoCarrinho(Long carrinhoId){
        return itemCarrinhoRepository.findByCarrinhoId(carrinhoId);
    }
    public void finalizarCompra(Long carrinhoId){
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId).orElseThrow();
        //lógica de finalizar a compra, salvar no banco de dados (vai aqui)


        // aqui vai limpar o carrinho após a compra
        itemCarrinhoRepository.deleteAll(carrinho.getItens());
    }
}
