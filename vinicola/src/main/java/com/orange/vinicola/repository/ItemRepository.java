package com.orange.vinicola.repository;

import com.orange.vinicola.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    void deleteByProdutoId(Long produtoId);
}
