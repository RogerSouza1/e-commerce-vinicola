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
    private BCryptPasswordEncoder passwordEncoder;

    public void save(Cliente cliente){
        clienteRepository.save(cliente);
    }

    public Cliente findByEmail(String email){
        return clienteRepository.findByEmail(email);
    }

    public Optional<Cliente> findById(Long id){
        return clienteRepository.findById(id);
    }
}
