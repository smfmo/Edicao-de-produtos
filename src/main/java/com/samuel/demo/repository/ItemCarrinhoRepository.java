package com.samuel.demo.repository;

import com.samuel.demo.model.ItemCarrinho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, Long> {
    List<ItemCarrinho> findByCarrinhoId(Long carrinhoId);
}
