package com.orange.vinicola.service;

import com.orange.vinicola.model.Cliente;
import com.orange.vinicola.model.Usuario;
import com.orange.vinicola.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Cliente save(Cliente cliente){

        if (cliente.getEnderecoFaturamento() != null) {
            cliente.getEnderecoFaturamento().setCliente(cliente); // Associa o cliente ao endereço de faturamento
        }
        Cliente clienteSalvo = clienteRepository.saveAndFlush(cliente);

        if (cliente.getEnderecoFaturamento() != null) {
            enderecoService.save(cliente.getEnderecoFaturamento()); // Salva o endereço após associar ao cliente
        }

        return clienteSalvo;
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
