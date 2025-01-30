package com.samuel.demo.repository;

import com.samuel.demo.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto,  Long> {
}
