package com.orange.vinicola.service;

import com.orange.vinicola.model.Pedido;
import com.orange.vinicola.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido buscarPedidoPorClienteId(Long clienteId) {
        return pedidoRepository.findPedidoByClienteId(clienteId);
    }

}
