package com.samuel.demo.controller;

import com.samuel.demo.model.Carrinho;
import com.samuel.demo.service.CarrinhoService;
import com.samuel.demo.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class CompraController {
    //atributos
    @Autowired
    private CarrinhoService carrinhoService;
    @Autowired
    private ProdutoService produtoService;

    //página de compras - lista de produtos
    @GetMapping
    public String index(Model model){
        Carrinho carrinho = carrinhoService.criarCarrinho();
        model.addAttribute("produtos", produtoService.buscarProdutosAtivos());
        model.addAttribute("carrinhoId", carrinho.getId());
        carrinhoService.limparCarrinhosVazios();
        /*List<Produto> produtos = produtoRepository.findAll();
        model.addAttribute("produtos", produtoService.getAllProdutos());*/
        return "index";
    }
}
