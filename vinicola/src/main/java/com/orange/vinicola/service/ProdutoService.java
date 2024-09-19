package com.orange.vinicola.service;

import com.orange.vinicola.model.Produto;
import com.orange.vinicola.model.Usuario;
import com.orange.vinicola.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto save (Produto produto) {
        return produtoRepository.save(produto);
    }

    public List<Produto> findAll(){
        return produtoRepository.findAll();
    }

    public Optional<Produto> findById(Long id) {
        return produtoRepository.findById(id);
    }


    public List<Produto> findByNome(String nome) {
        return produtoRepository.findByNome(nome);
    }

    public void alterar_estado_produto(Produto produto) {
        produto.setAtivado(!produto.isAtivado());
        produtoRepository.save(produto);
    }
}
