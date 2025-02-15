package com.samuel.demo.repository;

import com.samuel.demo.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto,  Long> {
    List<Produto> findByEmEstoqueTrue();
    List<Produto> findByEmEstoqueFalse();
}
