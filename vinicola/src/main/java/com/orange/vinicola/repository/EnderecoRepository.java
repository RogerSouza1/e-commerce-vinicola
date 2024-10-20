package com.orange.vinicola.repository;

import com.orange.vinicola.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    @Query("SELECT e FROM Endereco e WHERE e.cliente.id = :clienteId")
    ArrayList<Endereco> findByClienteId(Long clienteId);

    @Query("SELECT e FROM Endereco e WHERE e.cliente.id = :clienteId AND e.isEnderecoFaturamento = true")
    Endereco findEnderecoFaturamento(Long clienteId);

    @Query("SELECT e FROM Endereco e WHERE e.cliente.id = :clienteId AND e.isEnderecoFaturamento = true")
    Endereco findFirstByClienteIdAndIsEnderecoFaturamentoTrue(Long clienteId);

    @Query("SELECT e FROM Endereco e WHERE e.cliente.id = :clienteId AND e.isEntregaPadrao = true")
    Endereco findByClienteIdAndIsEntregaPadraoTrue(Long clienteId);

    @Query("SELECT e FROM Endereco e WHERE e.cliente.id = :clienteId AND e.isEnderecoFaturamento = false")
    ArrayList<Endereco> findByClienteIdAndIsEnderecoFaturamentoFalse(Long clienteId);
}
