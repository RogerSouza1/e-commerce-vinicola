package com.orange.vinicola.repository;

import com.orange.vinicola.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    Optional<Pedido> findById(Long produtoId);

    List<Pedido> findPedidosByClienteId(Long clienteId);

    @Query("SELECT MAX(p.numeroPedido) FROM Pedido p")
    Integer findMaxNumeroPedido();
}
