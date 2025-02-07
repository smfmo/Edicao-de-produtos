package com.samuel.demo.Controller;

import com.samuel.demo.model.Carrinho;
import com.samuel.demo.repository.CarrinhoRepository;
import com.samuel.demo.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

    private final CarrinhoService carrinhoService;
    @Autowired
    private CarrinhoRepository carrinhoRepository;

    //construtor
    public CarrinhoController(CarrinhoService carrinhoService) {
        this.carrinhoService = carrinhoService;
    }


    @PostMapping("/carrinho/add")
    public Carrinho addProdutoCarrinho(@PathVariable Long carrinhoId,
                                       @RequestParam Long produtoId,
                                       @RequestParam int quantidade){
        return carrinhoService.addProdutoCarrinho(carrinhoId, produtoId, quantidade);

    }

    @GetMapping("/carrinho")
    public Carrinho buscarCarrinhoPeloId(@PathVariable Long carrinhoId){
        return carrinhoService.buscarCarrinho(carrinhoId);
    }

    @DeleteMapping("/carrinho/clear")
    public void limparCarrinho(@PathVariable Long carrinhoId){
        carrinhoService.limparCarrinho(carrinhoId);
    }
}
