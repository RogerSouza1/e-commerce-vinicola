package com.orange.vinicola.controller;

import com.orange.vinicola.model.Carrinho;
import com.orange.vinicola.model.Produto;
import com.orange.vinicola.service.CarrinhoService;
import com.orange.vinicola.service.ProdutoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;
    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/abrirCarrinho")
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
        Carrinho carrinho = (Carrinho) request.getSession().getAttribute("carrinho");

        if(carrinho.getValorTotal() == 0){
            carrinho.setFrete(0);
            carrinho.setValorComFrete(0);
        }

        mv.addObject("carrinho", request.getSession().getAttribute("carrinho"));
        return mv;
    }

    @RequestMapping("/adicionarProduto")
    public ModelAndView adicionarProduto(@RequestParam("produtoId") Long produtoId, @RequestParam("quantidade") int quantidade, HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("redirect:/carrinho/abrirCarrinho");
        Optional<Produto> produto =  produtoService.findById(produtoId);

        // Verifica se a quantidade de produtos é maior que a quantidade em estoque
        if (quantidade > produto.get().getQtdEstoque()) {
            mv.setViewName("redirect:/detalhes-produto-cliente");
            return mv;
        }
        if (quantidade < 1) {
            mv.setViewName("redirect:/detalhes-produto-cliente");
            return mv;
        }

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

        if (carrinhoAtualizado.getValorTotal() <= 0) {
            carrinhoAtualizado.setValorTotal(0);
            carrinhoAtualizado.setFrete(0);
            carrinhoAtualizado.setValorComFrete(0);
        }

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

        if (carrinhoAtualizado.getValorTotal() <= 0) {
            carrinhoAtualizado.setValorTotal(0);
            carrinhoAtualizado.setFrete(0);
            carrinhoAtualizado.setValorComFrete(0);
        }

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
    public ModelAndView selecionarFrete(@RequestParam("freteValue") double frete, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("redirect:/carrinho/abrirCarrinho");

        Carrinho carrinho = (Carrinho) request.getSession().getAttribute("carrinho");

        Carrinho carrinhoAtualizado = carrinhoService.atualizarFrete(carrinho, frete);

        request.getSession().setAttribute("carrinho", carrinhoAtualizado);

        return mv;
    }
}
