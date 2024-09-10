package com.orange.vinicola.controller;

import com.orange.vinicola.model.Produto;
import com.orange.vinicola.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/cadastro-produto")
    public String showFormProdutos(Model model) {
        model.addAttribute("produto", new Produto());
        return "registrar-produto";
    }

    @PostMapping("/cadastro-produto")
    public String registerProduct(Produto produto, Model model) {
        try {
            if (produtoService.findByNome(produto.getNome()) != null) {
                model.addAttribute("mensagem", "Produto já registrado!");
                return "registrar-produto";
            }

            produtoService.save(produto);
            model.addAttribute("mensagem", "Produto registrado com sucesso!");
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

}