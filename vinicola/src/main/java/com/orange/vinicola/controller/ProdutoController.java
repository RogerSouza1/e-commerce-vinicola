package com.orange.vinicola.controller;

import com.orange.vinicola.model.Imagem;
import com.orange.vinicola.model.Produto;
import com.orange.vinicola.service.ImagemService;
import com.orange.vinicola.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;


import java.io.IOException;
import java.util.List;

@Controller
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private ImagemService imagemService;

    @GetMapping("/cadastro-produto")
    public String showFormProdutos(Model model) {
        model.addAttribute("produto", new Produto());
        return "registrar-produto";
    }

    @PostMapping("/cadastro-produto")
    public String registerProduct(Produto produto, @RequestParam("imageUpload") MultipartFile[] files, @RequestParam("principalImage") Integer principalImageIndex,
                                  Model model) {
        try {
            if (produtoService.findByNome(produto.getNome()) != null) {
                model.addAttribute("mensagem", "Produto já registrado!");
                return "registrar-produto";
            }

            produto.setAtivado(true);
            Produto produtoSalvo = produtoService.save(produto);

            // Verifica se as imagens foram carregadas
            if (files == null || files.length == 0) {
                model.addAttribute("mensagem", "Por favor, anexe pelo menos uma imagem.");
                return "registrar-produto";
            }

            // Verifica se o índice da imagem principal foi enviado
            if (principalImageIndex == null || principalImageIndex < 0 || principalImageIndex >= files.length) {
                model.addAttribute("mensagem", "Selecione uma imagem principal válida.");
                return "registrar-produto";
            }

            // Salva as imagens no banco
            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];
                try {
                    Imagem imagem = new Imagem();
                    imagem.setProduto(produtoSalvo);
                    imagem.setDados(file.getBytes());
                    imagem.setUrl(file.getOriginalFilename());
                    imagem.setPrincipal(i == principalImageIndex); // Define a imagem principal com base no índice
                    imagemService.save(imagem);
                } catch (IOException e) {
                    e.printStackTrace();
                    // Adicione tratamento de erro conforme necessário
                }
            }

            model.addAttribute("mensagem", "Produto registrado com sucesso!");
            return "redirect:/lista-produtos";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("mensagem", "Erro: Produto já registrado!");
        }
        return "registrar-produto";
    }

    @GetMapping("/lista-produtos")
    public String listarProduto(Model model) {
        List<Produto> produtos = produtoService.findAll();
        model.addAttribute("produtos", produtos);
        return "lista-produtos";
    }

    @GetMapping("/buscar-produto")
    public String buscarProduto(@RequestParam("nome") String nome, Model model) {
        List<Produto> produtos = produtoService.findByNome(nome);
        model.addAttribute("produtos", produtos);
        return "fragments/tabela-produtos :: tabela-produtos";
    }


    @GetMapping("/alterar-estado-produto")
    public String alterarEstadoProduto(@RequestParam("id") Long id, Model model) {
        Optional<Produto> produtoOpt = produtoService.findById(id);
        if (produtoOpt.isPresent()) {
            Produto produto = produtoOpt.get();
            produtoService.alterar_estado_produto(produto);
            model.addAttribute("mensagem", "Estado do produto alterado com sucesso!");
        } else {
            model.addAttribute("mensagem", "Produto não encontrado!");
        }
        List<Produto> todosProdutos = produtoService.findAll();
        model.addAttribute("produtos", todosProdutos);
        return "lista-produtos";
    }

    @GetMapping("/detalhes-produto")
    public String detalhesProduto(@RequestParam("id") Long id, Model model) {
        Optional<Produto> produtoOpt = produtoService.findById(id);
        if (produtoOpt.isPresent()) {
            Produto produto = produtoOpt.get();
            model.addAttribute("produto", produto);
            return "detalhes-produto";
        } else {
            model.addAttribute("mensagem", "Produto não encontrado!");
            return "redirect:/lista-produtos";
        }
    }
}
