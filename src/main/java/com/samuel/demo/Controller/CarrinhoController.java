package com.samuel.demo.Controller;

import com.samuel.demo.model.Carrinho;
import com.samuel.demo.service.CarrinhoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    //construtor
    public CarrinhoController(CarrinhoService carrinhoService) {
        this.carrinhoService = carrinhoService;
    }


    @PostMapping("/{carrinhoId}/add")
    public Carrinho addProdutoCarrinho(@PathVariable Long carrinhoId,
                                       @RequestParam Long produtoId,
                                       @RequestParam int quantidade){
        return carrinhoService.addProdutoCarrinho(carrinhoId, produtoId, quantidade);
    }

    @GetMapping("/{carrinhoId}")
    public Carrinho buscarCarrinhoPeloId(@PathVariable Long carrinhoId){
        return carrinhoService.buscarCarrinho(carrinhoId);
    }

    @DeleteMapping("/{carrinhoId}/clear")
    public void limparCarrinho(@PathVariable Long carrinhoId){
        carrinhoService.limparCarrinho(carrinhoId);
    }
}
