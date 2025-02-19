package com.samuel.demo.service;

import com.samuel.demo.model.Carrinho;
import com.samuel.demo.model.Cliente;
import com.samuel.demo.model.ItemCarrinho;
import com.samuel.demo.model.Produto;
import com.samuel.demo.repository.CarrinhoRepository;
import com.samuel.demo.repository.ItemCarrinhoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public void adicionarItemAoCarrinho(Long carrinhoId, Long produtoId,
                                        int quantidade, String nomeProduto, double precoTotal){
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId).orElseThrow();
        Produto produto = produtoService.buscarpeloId(produtoId).orElseThrow();

        ItemCarrinho item = new ItemCarrinho();
        item.setPrecoTotal(produto.getPreco() * quantidade);
        item.setNomeProduto(produto.getNome());
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setCarrinho(carrinho);
        itemCarrinhoRepository.save(item);

    }

    public List<ItemCarrinho> listarItensDoCarrinho(Long carrinhoId){
        return itemCarrinhoRepository.findByCarrinhoId(carrinhoId);
    }

    public void finalizarCompra(Long carrinhoId, Cliente cliente){
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId).orElseThrow();
        carrinho.setCliente(cliente);
        carrinho.setDataHoraCompra(LocalDateTime.now());
        carrinhoRepository.save(carrinho);

        //lógica de finalizar a compra, salvar no banco de dados (vai aqui)

        // aqui vai limpar o carrinho após a compra
        itemCarrinhoRepository.deleteAll(carrinho.getItens());
    }

    //metodo para buscar o carrinho atual
    public Carrinho buscarCarrinhoAtual(Long carrinhoId){
        return carrinhoRepository.findById(carrinhoId).orElseThrow();
    }

    //metodo para exibir as compras no controle de vendas
    public List<Carrinho> exibirCarrinho(){
        return carrinhoRepository.findAllWithItens();

    }

    //limpar carrinhos  exceto o ativo
    public void limparCarrinhosVaziosExcetoAtivo(HttpSession session){
        Long carrinhoIdAtivo = (Long) session.getAttribute("carrinhoId");
        List<Carrinho> carrinhos = carrinhoRepository.findAll();

        for(Carrinho carrinho : carrinhos){
            if (!carrinho.getId().equals(carrinhoIdAtivo)) {
                carrinhoRepository.delete(carrinho);
            }
        }
    }

    //limpar carrinhos vazios
    public void limparCarrinhosVazios(){
        List<Carrinho> carrinhos = carrinhoRepository.findAll();
        for (Carrinho carrinho : carrinhos) {
            if (carrinho.getItens().isEmpty()) {
                carrinhoRepository.delete(carrinho);
            }
        }
    }
}
