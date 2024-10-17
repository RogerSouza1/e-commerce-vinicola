package com.orange.vinicola.service;

import com.orange.vinicola.model.Cliente;
import com.orange.vinicola.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void save(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    public Cliente findByEmail(String email){
        return clienteRepository.findByEmail(email);
    }

    public Cliente findByCPF(String cpf){
        return clienteRepository.findByCpf(cpf);
    }

    public Optional<Cliente> findById(Long id){
        return clienteRepository.findById(id);
    }
}
