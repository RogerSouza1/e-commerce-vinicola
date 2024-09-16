package com.orange.vinicola.controller;

import com.orange.vinicola.model.Produtos;
import com.orange.vinicola.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;
import java.util.Optional;

@Controller
public class ProdutoController {

    @Autowired
    private UsuarioService ProdutoService;

    @Autowired
    private BCryptPasswordEncoder encoder;


    @GetMapping("/lista-produtos")
    public String listarUsuario(Model model) {
        List<Produtos> produtos = ProdutoService.findAll();
        model.addAttribute("produtos", produtos);
        return "lista-produtos";
    }


    @GetMapping("/alterar-estado-produto")
    public String alterarEstadoProduto(@RequestParam("id") Long id, Model model) {
        Optional<Produto> produto = ProdutoService.findById(id);
        if (produto.isPresent()) {
            ProdutoService.alterar_estado_produto(produto.get());
            model.addAttribute("mensagem", "Estado do produto alterado com sucesso!");
        } else {
            model.addAttribute("mensagem", "Produto não encontrado!");
        }
        return "redirect:/lista-usuarios";
    }

}