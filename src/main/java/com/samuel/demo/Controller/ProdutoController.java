package com.samuel.demo.Controller;

import com.samuel.demo.model.Produto;
import com.samuel.demo.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    //página de compras
    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("produtos", produtoService.getAllProdutos());
        return "index";
    }

    //página do administrador
    @GetMapping("/admin")
    public String admin(Model model){
        model.addAttribute("produtos", produtoService.getAllProdutos());
        return "admin";
    }

    //adicionar produtos
    @PostMapping("/addProduto")
    public String addProduto(@RequestParam String nome,
                             @RequestParam String urlImagem,
                             @RequestParam double preco,
                             @RequestParam String descricao){
        Produto produto = new Produto(nome, urlImagem, preco, descricao);
        produtoService.addProduto(produto);
        return "redirect:/admin";
    }

    //editar produtos
    @GetMapping("/editar/{id}")
    public String editarProduto(@PathVariable Long id, Model model){
        return produtoService.buscarpeloId(id).map(produto -> {
            model.addAttribute("produto", produto);
            return "editProduto";
        })
                .orElse("redirect:/admin");
    }

    //atualizar produto
    @PostMapping("/atualizarProduto/{id}")
    public String atualizarProduto(@PathVariable Long id, @ModelAttribute Produto produtoAtualizado){
        produtoService.atualizarProduto(id, produtoAtualizado);
        return "redirect:/admin";
    }

    //deletar produto
    @GetMapping("/deletarProduto/{id}")
    public String deletarProduto(@PathVariable Long id){
        produtoService.deletarProduto(id);
        return "redirect:/admin";
    }
}
