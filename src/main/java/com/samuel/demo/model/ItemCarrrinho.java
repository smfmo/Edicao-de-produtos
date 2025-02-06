package com.samuel.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemCarrrinho {
    //métodos getters e setters
    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    private int quantidade;

    public ItemCarrrinho(Object o, Produto produto, int quantidade, Carrinho carrinho) {

    }
}
