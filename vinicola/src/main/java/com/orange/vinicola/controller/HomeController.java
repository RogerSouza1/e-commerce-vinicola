package com.orange.vinicola.controller;

import com.orange.vinicola.model.Carrinho;
import com.orange.vinicola.model.Cliente;
import com.orange.vinicola.model.Produto;
import com.orange.vinicola.repository.CarrinhoRepository;
import com.orange.vinicola.service.CarrinhoService;
import com.orange.vinicola.service.ClienteService;
import com.orange.vinicola.service.ProdutoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private CarrinhoService carrinhoService;
    @Autowired
    private CarrinhoRepository carrinhoRepository;

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
    public String paginaInicial(Model model, HttpServletRequest request) {
        List<Produto> produtos = produtoService.findAllAtivado();
        model.addAttribute("produtos", produtos);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Cliente cliente = null;

        if (authentication.isAuthenticated()) {

            cliente = clienteService.findByEmail(authentication.getName());

            if (cliente != null) {

                request.getSession().setAttribute("cliente", cliente);

                Carrinho carrinhoSessao = (Carrinho) request.getSession().getAttribute("carrinho");

                if (carrinhoSessao != null) {
                    Carrinho carrinhoBanco = carrinhoRepository.findCarrinhoByClienteId(cliente.getId());
                    Carrinho carrinhoMesclado = carrinhoService.mesclarCarrinho(carrinhoSessao, carrinhoBanco, cliente);
                    if (carrinhoMesclado == null) {
                        request.getSession().setAttribute("carrinho", carrinhoBanco);
                    } else {
                        carrinhoService.save(carrinhoMesclado);
                        request.getSession().setAttribute("carrinho", carrinhoMesclado);
                    }

                }

                Carrinho carrinho = carrinhoRepository.findCarrinhoByClienteId(cliente.getId());
                if (carrinho != null) {
                    request.getSession().setAttribute("carrinho", carrinho);
                }

            }
        }

        Carrinho carrinho = (Carrinho) request.getSession().getAttribute("carrinho");

        if (carrinho == null) {
            carrinho = new Carrinho();
            carrinho.setCliente(cliente);
            carrinho.setItens(new ArrayList<>());
            request.getSession().setAttribute("carrinho", carrinho);
        }

        return "index";
    }
}
