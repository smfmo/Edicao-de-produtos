package com.samuel.demo.Controller;


import com.samuel.demo.model.*;
import com.samuel.demo.repository.ItemCarrinhoRepository;
import com.samuel.demo.service.CarrinhoService;
import com.samuel.demo.service.CepService;
import com.samuel.demo.service.ProdutoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/carrinho")
public class CarrinhoController {
    @Autowired
    private CarrinhoService carrinhoService;
    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private ItemCarrinhoRepository itemCarrinhoRepository;
    @Autowired
    private CepService cepService;


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

    @GetMapping("/finalizar/{carrinhoId}")
    public String mostrarFormCliente(@PathVariable Long carrinhoId, Model model){
        model.addAttribute("carrinhoId", carrinhoId);
        model.addAttribute("cliente", new Cliente());
        List<ItemCarrinho> itens = carrinhoService.listarItensDoCarrinho(carrinhoId);
        model.addAttribute("itens", itens);

        double totalGeral = itens.stream()
                .mapToDouble(item -> item.getQuantidade() * item.getProduto().getPreco())
                .sum();
        model.addAttribute("totalGeral", totalGeral);
        return "formulario-cliente";
    }

    @PostMapping("/finalizar/{carrinhoId}")
    public String finalizarCompra(@PathVariable Long carrinhoId,
                                  @RequestParam String nome,
                                  @RequestParam String telefone,
                                  @RequestParam String cep,
                                  @RequestParam String logradouro,
                                  @RequestParam String bairro,
                                  @RequestParam String localidade,
                                  @RequestParam String uf,
                                  @RequestParam String numero,
                                  @RequestParam String complemento,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes){

        //válidar o cep
        if (!cepService.cepAtendido(cep)) {
            redirectAttributes.addFlashAttribute("error", "Desculpe, não atendemos sua região!");
            return "redirect:/carrinho/" + carrinhoId;
        }
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setTelefone(telefone);

        Endereco endereco = new Endereco();
        endereco.setCep(cep);
        endereco.setLogradouro(logradouro);
        endereco.setBairro(bairro);
        endereco.setLocalidade(localidade);
        endereco.setUf(uf);
        endereco.setNumero(numero);
        endereco.setComplemento(complemento);

        cliente.setEndereco(endereco);
        carrinhoService.finalizarCompra(carrinhoId, cliente);
        session.removeAttribute("carrinhoId"); //aqui remove o carrinho da sessão após finalizar a compra
        carrinhoService.limparCarrinhosVazios();
        return "redirect:/";
    }

    //metodo para adicionar itens ao carrinho existente ou criar um novo
    @GetMapping("/adicionarItem")
    public String adicionarItem(@RequestParam Long produtoId,
                                @RequestParam int quantidade,
                                HttpSession session){
        //limpar carrinhos vazios
        carrinhoService.limparCarrinhosVazios();

        Long carrinhoId = (Long) session.getAttribute("carrinhoId");
        Carrinho carrinho = null;

        if (carrinhoId == null) {
            //se não houver carrinho na sessão, cria um novo
            carrinho = carrinhoService.criarCarrinho();
            carrinhoId = carrinho.getId();
            session.setAttribute("carrinhoId", carrinhoId);
        } else {
            //verifica se o carrinho já existe no banco de dados
            carrinho = carrinhoService.buscarCarrinhoAtual(carrinhoId);
            if (carrinho == null) {
                //se o carrinho não existir, cria um novo
                carrinho = carrinhoService.criarCarrinho();
                carrinhoId = carrinho.getId();
                session.setAttribute("carrinhoId", carrinhoId);
            }
        }

        //adiciona o item ao carrinho
        Produto produto = produtoService.buscarpeloId(produtoId).orElseThrow();
        carrinhoService.adicionarItemAoCarrinho(carrinhoId, produtoId, quantidade, produto.getNome(),
                produto.getPreco() * quantidade);

        return "redirect:/carrinho/" + carrinhoId;
    }

}
