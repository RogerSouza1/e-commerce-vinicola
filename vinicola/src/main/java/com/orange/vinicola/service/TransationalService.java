package com.orange.vinicola.service;

import com.orange.vinicola.model.Carrinho;
import com.orange.vinicola.repository.CarrinhoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransationalService {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Transactional
    public void save(Carrinho carrinho) {
        carrinhoRepository.save(carrinho);
    }
}
