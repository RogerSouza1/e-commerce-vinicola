package com.orange.vinicola.repository;

import com.orange.vinicola.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    Pedido findPedidoByClienteId(Long clienteId);
}
