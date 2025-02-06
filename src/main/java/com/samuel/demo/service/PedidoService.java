package com.samuel.demo.service;

import com.samuel.demo.model.Carrinho;
import com.samuel.demo.model.Pedido;
import com.samuel.demo.repository.CarrinhoRepository;
import com.samuel.demo.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PedidoService {
    //atributos
    private final PedidoRepository pedidoRepository;
    private final CarrinhoRepository carrinhoRepository;

    //construtor
    public PedidoService(PedidoRepository pedidoRepository, CarrinhoRepository carrinhoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.carrinhoRepository = carrinhoRepository;
    }

    public Pedido criarPedido(Long carrinhoId){
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId).orElseThrow(()->
                new RuntimeException("Carrinho não encontrado"));

        if(carrinho.getItens().isEmpty()){
            throw new RuntimeException("O carrinho está vázio");
        }

        double totalDoPedido = carrinho.getItens().stream()
                .mapToDouble(item -> item.getProduto().getPreco() * item.getQuantidade()).sum();

        Pedido pedido = new Pedido();
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setItens(carrinho.getItens());
        pedido.setTotalDoPedido(totalDoPedido);

        //salva o pedido no banco
        Pedido salvarPedido = pedidoRepository.save(pedido);

        //limpar o carrinho após a compra
        carrinho.getItens().clear();
        carrinhoRepository.save(carrinho);

        return salvarPedido;
    }
}
