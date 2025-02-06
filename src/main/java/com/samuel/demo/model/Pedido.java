package com.samuel.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    //métodos getters e setters
    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataPedido;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ItemCarrrinho> itens;

    private double totalDoPedido;

    public Pedido(Long id, Produto produto, int quantidade, Pedido pedido) {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public void setItens(List<ItemCarrrinho> itens) {
        this.itens = itens;
    }

    public void setTotalDoPedido(double totalDoPedido) {
        this.totalDoPedido = totalDoPedido;
    }
}
