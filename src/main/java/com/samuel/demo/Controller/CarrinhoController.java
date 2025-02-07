package com.samuel.demo.Controller;


import com.samuel.demo.model.ItemCarrinho;
import com.samuel.demo.model.Produto;
import com.samuel.demo.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/carrinho")
public class CarrinhoController {
    @Autowired
    private CarrinhoService carrinhoService;

    //vizualizar o carrinho
    @GetMapping("/{carrinhoId}")
    public String verCarrinho(@PathVariable Long carrinhoId, Model model){
        List<ItemCarrinho> itens = carrinhoService.listarItensDoCarrinho(carrinhoId);
        model.addAttribute("itens", itens);
        return "carrinho";
    }
    //adicionar os produtos ao carrinho
    @PostMapping("/adicionar")
    public String adicionarItemAoCarrinho(@RequestParam Long carrinhoId,
                                          @RequestParam Long produtoId,
                                          @RequestParam int quantidade){
        carrinhoService.adicionarItemAoCarrinho(carrinhoId, produtoId, quantidade);
        return "redirect:/carrinho/" + carrinhoId;
    }

    @PostMapping("/finalizar/{carrinhoId}")
    public String finalizarCompra(@PathVariable Long carrinhoId){
        carrinhoService.finalizarCompra(carrinhoId);
        return "redirect:/";
    }
}
