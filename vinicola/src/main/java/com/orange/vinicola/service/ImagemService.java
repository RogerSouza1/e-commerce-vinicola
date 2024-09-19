package com.orange.vinicola.service;

import com.orange.vinicola.model.Imagem;
import com.orange.vinicola.repository.ImagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ImagemService {

    @Autowired
    private ImagemRepository imagemRepository;

    public Imagem save(Imagem imagem) {
        return imagemRepository.save(imagem);
    }

    public List<Imagem> findAll() {
        return imagemRepository.findAll();
    }

    public List<Imagem> findByProduto(Long produtoId) {
        return imagemRepository.findAllByProduto(produtoId);
    }

    public List<Imagem> findByProdutoId(Long produtoId) {
        return imagemRepository.findAllByProduto(produtoId); // Adapte conforme necessário
    }

    public Optional<Imagem> findById(Long id) {
        return imagemRepository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        imagemRepository.deleteById(id);
    }

    public Imagem findPrincipalByProdutoId(Long produtoId) {
        return imagemRepository.findPrincipalByProdutoId(produtoId);
    }
}
