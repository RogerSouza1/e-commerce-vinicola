package com.orange.vinicola.repository;


import com.orange.vinicola.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("SELECT p FROM Produto p WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Produto> findByNome(@Param("nome") String nome);

    @Query("SELECT p.id FROM Produto p WHERE p.nome = :nome")
    Long findByIdNome(@Param("nome") String nome);

    @Query("SELECT p FROM Produto p WHERE p.ativado = true")
    List<Produto> findAllAtivado();

    @Query("SELECT p.qtdEstoque FROM Produto p WHERE p.id = :id")
    int quantidadeEstoquePorProduto(@Param("id") Long id);

    List<Produto> findByNomeContainingIgnoreCase(String nome);
}
