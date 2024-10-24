package com.orange.vinicola.repository;

import com.orange.vinicola.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    Pedido findPedidoByClienteId(Long clienteId);

    @Query("SELECT MAX(p.numeroPedido) FROM Pedido p")
    Integer findMaxNumeroPedido();
}
