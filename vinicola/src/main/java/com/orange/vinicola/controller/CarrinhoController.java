package com.orange.vinicola.controller;

import com.orange.vinicola.model.Carrinho;
import com.orange.vinicola.model.Cliente;
import com.orange.vinicola.repository.CarrinhoRepository;
import com.orange.vinicola.service.CarrinhoService;
import com.orange.vinicola.service.ProdutoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @RequestMapping
    public ModelAndView abrirCarrinho(HttpServletRequest request) {
        Carrinho carrinho = (Carrinho) request.getSession().getAttribute("carrinho");
        Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");

        if (cliente != null && cliente.getCarrinho() != null) {
            if (cliente.getCarrinho().getId() == null)
                carrinho = carrinhoRepository.findCarrinhoByClienteId(cliente.getId());
            else
                carrinho = (Carrinho) request.getSession().getAttribute("carrinho");
        }

        if (carrinho == null) {
            carrinho = new Carrinho();
            carrinho.setItens(new ArrayList<>());
        }
        request.getSession().setAttribute("carrinho", carrinho);
        return new ModelAndView("carrinho").addObject("carrinho", carrinho);
    }

    @RequestMapping("/adicionarProduto")
    public ModelAndView adicionarProduto(@RequestParam("produtoId") Long produtoId, @RequestParam("quantidade") int quantidade, HttpServletRequest request) {
        Carrinho carrinho = (Carrinho) request.getSession().getAttribute("carrinho");


        if (carrinho == null) {
            carrinho = new Carrinho();
            carrinho.setItens(new ArrayList<>());
            request.getSession().setAttribute("carrinho", carrinho);
        }

        Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");
        carrinho = carrinhoService.adicionarProduto(carrinho, produtoId, quantidade, cliente);
        request.getSession().setAttribute("carrinho", carrinho);
        return new ModelAndView("redirect:/carrinho");
    }

    @RequestMapping("/removerProduto")
    public ModelAndView removerProduto(@RequestParam Long produtoId, HttpServletRequest request) {
        Carrinho carrinho = (Carrinho) request.getSession().getAttribute("carrinho");
        Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");
        carrinho = carrinhoService.removerProduto(carrinho, produtoId, cliente);
        request.getSession().setAttribute("carrinho", carrinho);
        return new ModelAndView("redirect:/carrinho");
    }

    @RequestMapping("/diminuirQuantidade")
    public ModelAndView diminuirQuantidade(@RequestParam("produtoId")Long produtoId, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("redirect:/carrinho");
        Carrinho carrinho = (Carrinho) request.getSession().getAttribute("carrinho");
        Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");
        carrinho = carrinhoService.decrementarQuantidade(carrinho, produtoId, cliente);
        request.getSession().setAttribute("carrinho", carrinho);
        return mv;
    }

    @RequestMapping("/aumentarQuantidade")
    public ModelAndView aumentarQuantidade(@RequestParam("produtoId")Long produtoId, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("redirect:/carrinho");
        Carrinho carrinho = (Carrinho) request.getSession().getAttribute("carrinho");
        Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");
        carrinho = carrinhoService.incrementarQuantidade(carrinho, produtoId, cliente);
        request.getSession().setAttribute("carrinho", carrinho);
        return mv;
    }

    @RequestMapping("/selecionarFrete")
    public ModelAndView selecionarFrete(@RequestParam("freteValue") double frete, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("redirect:/carrinho");
        Carrinho carrinho = (Carrinho) request.getSession().getAttribute("carrinho");
        Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");
        carrinho = carrinhoService.atualizarFrete(carrinho, frete, cliente);
        request.getSession().setAttribute("carrinho", carrinho);
        return mv;
    }

    @GetMapping("/checkout")
    public ModelAndView checkout(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("checkout");
//        Carrinho carrinho = (Carrinho) request.getSession().getAttribute("carrinho");
//        Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");
//        carrinho = carrinhoService.calcularTotal(carrinho, cliente);
//        request.getSession().setAttribute("carrinho", carrinho);
        return mv;
    }

    @RequestMapping("/finalizar-pedido")
    public ModelAndView finalizarPedido(HttpServletRequest request) {
        if (request.getSession().getAttribute("cliente") == null) {
            return new ModelAndView("redirect:/login");
        }

        return new ModelAndView("redirect:/carrinho/checkout");

    }

}
