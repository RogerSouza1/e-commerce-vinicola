package com.orange.vinicola.service;

import com.orange.vinicola.model.Usuario;
import com.orange.vinicola.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;



}
