package com.samuel.demo.repository;

import com.samuel.demo.model.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
    @Query("SELECT DISTINCT c FROM Carrinho c " +
            "LEFT JOIN FETCH c.itens " +
            "LEFT JOIN FETCH c.cliente " +
            "WHERE SIZE(c.itens) > 0")
    List<Carrinho> findAllWithItens();
}
