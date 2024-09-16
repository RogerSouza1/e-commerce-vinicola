package com.orange.vinicola.service;

import com.orange.vinicola.repository.ProdutosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutosService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Optional<Produtos> findById(Long id) {
        return produtoRepository.findById(id);
    }

    public void update(Produtos produtos) {
        produtoRepository.save(produtos);
    }

    public void alterar_estado_produtos(Produtos produtos) {

        produtos.setAtivado(!produtos.isAtivado());

        produtoRepository.save(produtos);
    }

    public List<Produtos> findAll() {
        return produtoRepository.findAll();
    }

    public List<Produtoss> findByNome(String nome) {
        return produtoRepository.findByNome(nome);
    }
}