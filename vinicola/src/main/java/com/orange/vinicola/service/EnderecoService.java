package com.orange.vinicola.service;

import com.orange.vinicola.model.Endereco;
import com.orange.vinicola.repository.EnderecoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public ArrayList<Endereco> findAllByClienteId(Long clienteId) {
        return enderecoRepository.findByClienteId(clienteId);
    }

    public ArrayList<Endereco> findALlEnderecoEntregaByClienteId(Long clienteId) {
        return enderecoRepository.findByClienteIdAndIsEnderecoFaturamentoFalse(clienteId);
    }

    public Endereco findEnderecoFaturamento(Long clienteId) {
        return enderecoRepository.findFirstByClienteIdAndIsEnderecoFaturamentoTrue(clienteId);
    }

    public Endereco findById(Long id) {
        return enderecoRepository.findById(id).get();
    }

    public Endereco findEnderecoPadrao(Long clienteId) {
        return enderecoRepository.findByClienteIdAndIsEntregaPadraoTrue(clienteId);
    }


    @Transactional
    public void save(Endereco endereco){
        enderecoRepository.save(endereco);
    }
}
