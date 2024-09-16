package com.orange.vinicola.repository;

import com.orange.vinicola.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produtos, Long> {
    Produtos findByNome(String nome);

    @Query("SELECT u FROM Produtos u WHERE LOWER(u.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Produtos> findByNome(@Param("nome") String nome);
}