package com.orange.vinicola.service;

import com.orange.vinicola.model.Endereco;
import com.orange.vinicola.repository.EnderecoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Transactional
    public void save(Endereco endereco){
        enderecoRepository.save(endereco);
    }
}
