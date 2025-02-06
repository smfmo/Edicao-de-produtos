package com.samuel.demo.Controller;

import com.samuel.demo.model.Pedido;
import com.samuel.demo.service.PedidoService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
    //atributo
    private final PedidoService pedidoService;

    //construtor
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/{carrinhoId}/chekout")
    public Pedido checkout(@PathVariable Long carrinhoId) {
        return pedidoService.criarPedido(carrinhoId);
    }
}
