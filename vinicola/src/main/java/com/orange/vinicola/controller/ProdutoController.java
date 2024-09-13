package com.orange.vinicola.controller;

import com.orange.vinicola.model.Imagem;
import com.orange.vinicola.model.Produto;
import com.orange.vinicola.service.ImagemService;
import com.orange.vinicola.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
    public String registerProduct(Produto produto, @RequestParam("imageUpload") MultipartFile[] files, @RequestParam("principalImage") int principalImageIndex, Model model) {
        try {
            if (produtoService.findByNome(produto.getNome()) != null) {
                model.addAttribute("mensagem", "Produto já registrado!");
                return "registrar-produto";
            }
            produto.setAtivado(true);
            Produto produtoSalvo = produtoService.save(produto);

            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];
                try {
                    Imagem imagem = new Imagem();
                    imagem.setProduto(produtoSalvo);
                    imagem.setDados(file.getBytes());
                    imagem.setUrl(file.getOriginalFilename());
                    imagem.setPrincipal(i == principalImageIndex); // Define a imagem principal
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
        List<Produto> produtos = produtoService.findByNomes(nome);
        model.addAttribute("produtos", produtos);
        return "fragments/tabela-produtos :: tabela-produtos";
    }

}
