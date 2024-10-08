package com.orange.vinicola.controller;

import com.orange.vinicola.model.Produto;
import com.orange.vinicola.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/dashboard")
    public String index() {
        return "dashboard";
    }

    @PostMapping("/dashboard")
    public String indexPost() {
        return "dashboard";
    }

    @PostMapping("/index")
    public String paginaInicial() {
        return "redirect:/index";
    }

    @GetMapping("/")
    public String paginaInicial(Model model) {
        List<Produto> produtos = produtoService.findAllAtivado();
        model.addAttribute("produtos", produtos);
        return "index";
    }

}
