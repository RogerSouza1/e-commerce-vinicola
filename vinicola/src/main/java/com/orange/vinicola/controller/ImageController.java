package com.orange.vinicola.controller;

import com.orange.vinicola.model.Imagem;
import com.orange.vinicola.service.ImagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ImageController {

    @Autowired
    private ImagemService imagemService;

    @GetMapping("/imagem/{id}")
    public ResponseEntity<byte[]> getImagem(@PathVariable Long id) {
        Imagem imagem = imagemService.findById(id).orElseThrow(() -> new RuntimeException("Imagem não encontrada"));
        return ResponseEntity.ok()
                .header("Content-Type", "image/jpeg") // ou image/png, conforme o tipo da imagem
                .body(imagem.getDados());
    }

}
