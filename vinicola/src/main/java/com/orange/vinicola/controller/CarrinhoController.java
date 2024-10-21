package com.orange.vinicola.controller;

import com.orange.vinicola.model.Carrinho;
import com.orange.vinicola.model.Cliente;
import com.orange.vinicola.model.Endereco;
import com.orange.vinicola.repository.CarrinhoRepository;
import com.orange.vinicola.service.CarrinhoService;
import com.orange.vinicola.service.ClienteService;
import com.orange.vinicola.service.EnderecoService;
import com.orange.vinicola.service.ProdutoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @Autowired
    private EnderecoService enderecoService;
    @Autowired
    private ClienteService clienteService;

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
        if (cliente != null) {
            carrinho = carrinhoRepository.findCarrinhoByClienteId(cliente.getId());
        }
        carrinho = carrinhoService.adicionarProduto(carrinho, produtoId, quantidade, cliente);
        request.getSession().setAttribute("carrinho", carrinho);
        return new ModelAndView("redirect:/carrinho");
    }

    @RequestMapping("/removerProduto")
    public ModelAndView removerProduto(@RequestParam Long produtoId, @RequestParam(required = false) String redirect, @RequestParam(required = false) String etapaAtual, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        Carrinho carrinho = (Carrinho) request.getSession().getAttribute("carrinho");
        Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");
        if (cliente != null) {
            carrinho = carrinhoRepository.findCarrinhoByClienteId(cliente.getId());
        }
        carrinho = carrinhoService.removerProduto(carrinho, produtoId, cliente);
        request.getSession().setAttribute("carrinho", carrinho);
        if (etapaAtual != null) {
            redirectAttributes.addFlashAttribute("etapaAtual", etapaAtual);
        }
        if (redirect != null && !redirect.isEmpty()) {
            return new ModelAndView("redirect:" + redirect);
        }
        return new ModelAndView("redirect:/carrinho");
    }

    @RequestMapping("/diminuirQuantidade")
    public ModelAndView diminuirQuantidade(@RequestParam("produtoId") Long produtoId, @RequestParam(required = false) String redirect, @RequestParam(required = false) String etapaAtual, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/carrinho");
        Carrinho carrinho = (Carrinho) request.getSession().getAttribute("carrinho");
        Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");
        if (cliente != null) {
            carrinho = carrinhoRepository.findCarrinhoByClienteId(cliente.getId());
        }
        carrinho = carrinhoService.decrementarQuantidade(carrinho, produtoId, cliente);
        request.getSession().setAttribute("carrinho", carrinho);
        if (etapaAtual != null) {
            redirectAttributes.addFlashAttribute("etapaAtual", etapaAtual);
        }
        if (redirect != null && !redirect.isEmpty()) {
            return new ModelAndView("redirect:" + redirect);
        }
        return mv;
    }

    @RequestMapping("/aumentarQuantidade")
    public ModelAndView aumentarQuantidade(@RequestParam("produtoId") Long produtoId, @RequestParam(required = false) String redirect, @RequestParam(required = false) String etapaAtual, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/carrinho");
        Carrinho carrinho = (Carrinho) request.getSession().getAttribute("carrinho");
        Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");
        if (cliente != null) {
            carrinho = carrinhoRepository.findCarrinhoByClienteId(cliente.getId());
        }
        carrinho = carrinhoService.incrementarQuantidade(carrinho, produtoId, cliente);
        request.getSession().setAttribute("carrinho", carrinho);
        if (etapaAtual != null) {
            redirectAttributes.addFlashAttribute("etapaAtual", etapaAtual);
        }
        if (redirect != null && !redirect.isEmpty()) {
            return new ModelAndView("redirect:" + redirect);
        }
        return mv;
    }

    @RequestMapping("/selecionarFrete")
    public ModelAndView selecionarFrete(@RequestParam("freteValue") double frete, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("redirect:/carrinho");
        Carrinho carrinho = (Carrinho) request.getSession().getAttribute("carrinho");
        Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");
        if (cliente != null) {
            carrinho = carrinhoRepository.findCarrinhoByClienteId(cliente.getId());
        }
        carrinho = carrinhoService.atualizarFrete(carrinho, frete, cliente);
        request.getSession().setAttribute("carrinho", carrinho);
        return mv;
    }

    @GetMapping("/checkout")
    public ModelAndView checkout(Model model, HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("checkout");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Cliente cliente = clienteService.findByEmail(authentication.getName());

        Endereco enderecoFaturamento = enderecoService.findEnderecoFaturamento(cliente.getId());
        ArrayList<Endereco> enderecosEntrega = enderecoService.findALlEnderecoEntregaByClienteId(cliente.getId());
        Endereco enderecoEntregaPadrao = null;

        for (Endereco endereco : enderecosEntrega) {
            if (endereco.isEntregaPadrao()) {
                enderecoEntregaPadrao = endereco;
            }
        }

        Carrinho carrinho = carrinhoService.buscarCarrinhoPorClienteId(cliente.getId());

        model.addAttribute("carrinho", carrinho);
        model.addAttribute("enderecoPadrao", enderecoEntregaPadrao);
        model.addAttribute("enderecoFaturamento", enderecoFaturamento);
        model.addAttribute("enderecoEntrega", enderecosEntrega);
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
