package com.samuel.demo.Controller;


import com.samuel.demo.model.ItemCarrinho;
import com.samuel.demo.model.Produto;
import com.samuel.demo.service.CarrinhoService;
import com.samuel.demo.service.ProdutoService;
import jakarta.servlet.http.HttpSession;
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
    @Autowired
    private ProdutoService produtoService;

    //vizualizar o carrinho
    @GetMapping("/{carrinhoId}")
    public String verCarrinho(@PathVariable Long carrinhoId, Model model){
        List<ItemCarrinho> itens = carrinhoService.listarItensDoCarrinho(carrinhoId);
        //calcular o total geral do carrinho
        double totalGeral = itens.stream()
                .mapToDouble(item -> item.getQuantidade() * item.getProduto()
                        .getPreco()).sum();

        model.addAttribute("itens", itens);
        model.addAttribute("totalGeral", totalGeral); //aqui passa o total geral para a View
        model.addAttribute("carrinhoId", carrinhoId);
        return "carrinho";
    }

    //adicionar os produtos ao carrinho
    @PostMapping("/adicionar")
    public String adicionarItemAoCarrinho(@RequestParam Long carrinhoId,
                                          @RequestParam Long produtoId,
                                          @RequestParam int quantidade,
                                          @RequestParam String nomeProduto,
                                          @RequestParam double precoTotal,
                                          HttpSession session){
        carrinhoService.adicionarItemAoCarrinho(carrinhoId, produtoId, quantidade, nomeProduto, precoTotal);
        session.setAttribute("carrinhoId", carrinhoId); //aqui armazena o ID do carrinho na sessão
        return "redirect:/carrinho/" + carrinhoId;
    }

    @PostMapping("/finalizar/{carrinhoId}")
    public String finalizarCompra(@PathVariable Long carrinhoId, HttpSession session){
        carrinhoService.finalizarCompra(carrinhoId);
        session.removeAttribute("carrinhoId"); //aqui remove o carrinho da sessão após finalizar a compra
        return "redirect:/";
    }

    //metodo para adicionar itens ao carrinho existente ou criar um novo
    @GetMapping("/adicionarItem")
    public String adicionarItem(@RequestParam Long produtoId,
                                @RequestParam int quantidade,
                                HttpSession session){
        Long carrinhoId = (Long) session.getAttribute("carrinhoId");
        if (carrinhoId == null) {
            //se não houver carrinho na sessão, cria um novo
            carrinhoId = carrinhoService.criarCarrinho().getId();
            session.setAttribute("carrinhoId", carrinhoId);
        }

        //adiciona o item ao carrinho
        Produto produto = produtoService.buscarpeloId(produtoId).orElseThrow();
        carrinhoService.adicionarItemAoCarrinho(carrinhoId, produtoId, quantidade, produto.getNome(),
                produto.getPreco() * quantidade);

        return "redirect:/carrinho/" + carrinhoId;
    }
}
