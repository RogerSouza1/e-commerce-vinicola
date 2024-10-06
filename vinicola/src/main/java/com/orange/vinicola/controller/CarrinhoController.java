package com.orange.vinicola.controller;

import com.orange.vinicola.model.Carrinho;
import com.orange.vinicola.service.CarrinhoService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @RequestMapping("/abrirCarrinho")
    public ModelAndView abrirCarrinho(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("carrinho");

        if (request.getSession().getAttribute("carrinho") == null) {
            if (request.getSession().getAttribute("usuario") != null) {
                Carrinho carrinho = carrinhoService.buscarCarrinhoPorUsuarioId(((Long) request.getSession().getAttribute("usuario")));
                request.getSession().setAttribute("carrinho", carrinho);
            } else {
                request.getSession().setAttribute("carrinho", new Carrinho());
            }
        }

        mv.addObject("carrinho", request.getSession().getAttribute("carrinho"));
        return mv;
    }

    @RequestMapping("/adicionarProduto")
    public ModelAndView adicionarProduto(@RequestParam("produtoId") Long produtoId, @RequestParam("quantidade") int quantidade, HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("redirect:/carrinho/abrirCarrinho");

        if (request.getSession().getAttribute("carrinho") == null) {
            if (request.getSession().getAttribute("usuario") != null) {
                Carrinho carrinho = carrinhoService.buscarCarrinhoPorUsuarioId(((Long) request.getSession().getAttribute("usuario")));
                request.getSession().setAttribute("carrinho", carrinho);
            } else {
                request.getSession().setAttribute("carrinho", new Carrinho());
            }
        }

        Carrinho carrinho = (Carrinho) request.getSession().getAttribute("carrinho");

        Carrinho carrinhoAtualizado = carrinhoService.adicionarProduto(carrinho, produtoId, quantidade);
        if (request.getSession().getAttribute("usuario") != null) {
            carrinhoService.save(carrinhoAtualizado);
        }

        request.getSession().setAttribute("carrinho", carrinhoAtualizado);

        return mv;
    }

    @RequestMapping("/removerProduto")
    public ModelAndView removerProduto(@RequestParam("produtoId")Long produtoId, HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("redirect:/carrinho/abrirCarrinho");

        Carrinho carrinho = (Carrinho) request.getSession().getAttribute("carrinho");

        Carrinho carrinhoAtualizado = carrinhoService.removerProduto(carrinho, produtoId);

        if (request.getSession().getAttribute("usuario") != null) {
            carrinhoService.save(carrinhoAtualizado);
        }

        request.getSession().setAttribute("carrinho", carrinhoAtualizado);

        return mv;
    }

    @RequestMapping("/diminuirQuantidade")
    public ModelAndView diminuirQuantidade(@RequestParam("produtoId")Long produtoId, HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("redirect:/carrinho/abrirCarrinho");

        Carrinho carrinho = (Carrinho) request.getSession().getAttribute("carrinho");

        Carrinho carrinhoAtualizado = carrinhoService.decrementarQuantidade(carrinho, produtoId);

        if (request.getSession().getAttribute("usuario") != null) {
            carrinhoService.save(carrinhoAtualizado);
        }

        request.getSession().setAttribute("carrinho", carrinhoAtualizado);

        return mv;
    }

    @RequestMapping("/aumentarQuantidade")
    public ModelAndView aumentarQuantidade(@RequestParam("produtoId")Long produtoId, HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("redirect:/carrinho/abrirCarrinho");

        Carrinho carrinho = (Carrinho) request.getSession().getAttribute("carrinho");

        Carrinho carrinhoAtualizado = carrinhoService.incrementarQuantidade(carrinho, produtoId);

        if (request.getSession().getAttribute("usuario") != null) {
            carrinhoService.save(carrinhoAtualizado);
        }

        request.getSession().setAttribute("carrinho", carrinhoAtualizado);

        return mv;
    }

    @RequestMapping("/selecionarFrete")
    public ModelAndView selecionarFrete(@RequestParam("frete") double frete, HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("redirect:/carrinho/abrirCarrinho");

        Carrinho carrinho = (Carrinho) request.getSession().getAttribute("carrinho");

        Carrinho carrinhoAtualizado = carrinhoService.atualizarFrete(carrinho, frete);

        request.getSession().setAttribute("carrinho", carrinhoAtualizado);

        return mv;
    }
}
