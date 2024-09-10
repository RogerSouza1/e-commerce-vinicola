package com.orange.vinicola.controller;

import com.orange.vinicola.model.Imagem;
import com.orange.vinicola.service.ImagemService;
import com.orange.vinicola.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class ImagemController {

    //TODO: Realizar a chamada dos endpoints (/adicionar-imagem...etc)
    //TODO: Realizar o front em html básico para testar a chamada dos endpoints

    @Autowired
    private ImagemService imagemService;

    @Autowired
    private ProdutoService produtoService;

    //TODO: Fazer método de adicionar imagem
    public Imagem adicionarImagem() {
        return null;
    }

    public Imagem atualizar(Imagem imagem) {
        return imagemService.save(imagem);
    }

    //TODO: Validar lógica de definir imagem principal
    public Imagem definirPrincipal(Long id) {
        Imagem antigaPrincipal = imagemService.findPrincipalByProdutoId(id);
        Optional<Imagem> novaPrincipal = imagemService.findById(id);

        if (novaPrincipal.isPresent()) {
            if (!antigaPrincipal.equals(novaPrincipal.get())) {
                antigaPrincipal.setPrincipal(false);
                imagemService.save(antigaPrincipal);
                novaPrincipal.get().setPrincipal(true);
                return imagemService.save(novaPrincipal.get());
            }
        }
        return null;
    }


    public List<Imagem> listarImagensPorProdutoId(Long produtoId) {
        return imagemService.findByProdutoId(produtoId);
    }

    public void deletar(Long id) {
        imagemService.deleteById(id);
    }


}
