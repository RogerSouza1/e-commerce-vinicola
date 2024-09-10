package com.orange.vinicola.repository;

import com.orange.vinicola.model.Imagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagemRepository extends JpaRepository<Imagem, Long> {

    @Query("SELECT i FROM Imagem i WHERE i.produto.id = :produtoId")
    List<Imagem> findAllByProduto(Long produtoId);

    @Query("SELECT i FROM Imagem i WHERE i.principal = true AND i.produto.id = :produtoId")
    Imagem findPrincipalByProdutoId(Long produtoId);
}
