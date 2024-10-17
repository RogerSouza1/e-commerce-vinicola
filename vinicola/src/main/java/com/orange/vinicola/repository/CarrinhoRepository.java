package com.orange.vinicola.repository;

import com.orange.vinicola.model.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {

    Carrinho findCarrinhoByClienteId(Long id);

}
