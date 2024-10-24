package com.orange.vinicola.controller;

import com.orange.vinicola.model.Carrinho;
import com.orange.vinicola.model.Cliente;
import com.orange.vinicola.model.Pedido;
import com.orange.vinicola.service.CarrinhoService;
import com.orange.vinicola.service.ClienteService;
import com.orange.vinicola.service.PedidoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private CarrinhoService carrinhoService;

    @PostMapping("/finalizar")
    public ModelAndView finalizarPedido(@Valid @ModelAttribute("pedido")Pedido pedido, HttpServletRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Cliente cliente = clienteService.findByEmail(authentication.getName());

        if (cliente != null) {
           pedido.setCliente(cliente);
           pedido = pedidoService.finalizarPedido(pedido);
           Carrinho carrinho = carrinhoService.buscarCarrinhoPorClienteId(cliente.getId());
           carrinhoService.limparCarrinho(carrinho);
        }else{
            return new ModelAndView("redirect:/login");
        }
        return new ModelAndView("pedidos");
    }
}
