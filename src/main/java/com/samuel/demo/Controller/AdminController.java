package com.samuel.demo.Controller;

import com.samuel.demo.model.Carrinho;
import com.samuel.demo.model.Produto;
import com.samuel.demo.repository.ProdutoRepository;
import com.samuel.demo.service.ArmazenamentoImagemService;
import com.samuel.demo.service.CarrinhoService;
import com.samuel.demo.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ArmazenamentoImagemService imagemService;

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CarrinhoService carrinhoService;

    //página de compras - lista de produtos
    @GetMapping("/")
    public String index(Model model){
        Carrinho carrinho = carrinhoService.criarCarrinho();
        model.addAttribute("produtos", produtoService.getAllProdutos());
        model.addAttribute("carrinhoId", carrinho.getId());
        /*List<Produto> produtos = produtoRepository.findAll();
        model.addAttribute("produtos", produtoService.getAllProdutos());*/
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

    public String addProduto(@ModelAttribute Produto produto,
                             @RequestParam("imagem") MultipartFile imagem){
        try{
            //salva a imagem e obtém o caminho
            String nomeArquivo = imagemService.armazenarImg(imagem);
            produto.setUrlImagem(nomeArquivo);

            //salvar o produto no banco de dados
            produtoService.addProduto(produto);

    } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/admin";
    /*public String addProduto(@RequestParam String nome,
                             @RequestParam String urlImagem,
                             @RequestParam double preco,
                             @RequestParam String descricao){
        Produto produto = new Produto(nome, urlImagem, preco, descricao);
        produtoService.addProduto(produto);
        return "redirect:/admin";*/
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
    public String atualizarProduto(@PathVariable Long id,
                                   @ModelAttribute Produto produtoAtualizado,
                                   @RequestParam("imagem") MultipartFile imagem){
        try{
            //Se uma nova Imagem for enviada, salva e atualiza o caminho
            if (!imagem.isEmpty()) {
                String caminhoImagem = imagemService.armazenarImg(imagem);
                produtoAtualizado.setUrlImagem(caminhoImagem);
            }

            //atualizar o Produto no banco de dados
            produtoService.atualizarProduto(id, produtoAtualizado);
    } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/admin";
    /*public String atualizarProduto(@PathVariable Long id, @ModelAttribute Produto produtoAtualizado){
        produtoService.atualizarProduto(id, produtoAtualizado);
        return "redirect:/admin";*/
    }

    //deletar produto
    @GetMapping("/deletarProduto/{id}")
    public String deletarProduto(@PathVariable Long id){
        produtoService.deletarProduto(id);
        return "redirect:/admin";
    }
}
