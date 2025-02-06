package com.samuel.demo.Controller;

import com.samuel.demo.model.ItemCarrrinho;
import com.samuel.demo.model.Produto;
import com.samuel.demo.repository.ProdutoRepository;
import com.samuel.demo.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private final ProdutoRepository produtoRepository;

    private final List<ItemCarrrinho> itemCarrrinho = new ArrayList<>();

    public ProdutoController(ProdutoService produtoService, ProdutoRepository produtoRepository) {
        this.produtoService = produtoService;
        this.produtoRepository = produtoRepository;
    }

    //página de compras - lista de produtos
    @GetMapping("/")
    public String index(Model model){
        List<Produto> produtos = produtoRepository.findAll();
        model.addAttribute("produtos", produtoService.getAllProdutos());
        return "index";
    }

    //adicionar item ao carrinho de compras
    @PostMapping("/carrinho/add")
    public String addProdutoCarrinho(@RequestParam Long produtoId, @RequestParam int quantidade){
        Produto produto = produtoRepository.findById(produtoId).orElse(null);
        if (produto != null) {
            itemCarrrinho.add(new ItemCarrrinho(null, produto, quantidade));
        }
        return "redirect:/carrinho";
    }

    //exibir carrinho
    @GetMapping("/carrinho")
    public String exibirCarrinho(Model model){
        double total = itemCarrrinho.stream().mapToDouble(item -> item.getProduto().getPreco() * item.getQuantidade())
                .sum();
        model.addAttribute("itemCarrrinho", itemCarrrinho);
        model.addAttribute("total", total);

        return "carrinho";
    }

    //finalizar compra
    @PostMapping("/carrinho/checkout")
    public String checkout(){
        itemCarrrinho.clear(); // aqui vai limpar o carrinho após a compra
        return "redirect:/";
    }
}