package com.orange.vinicola.controller;

import com.orange.vinicola.model.Carrinho;
import com.orange.vinicola.model.Cliente;
import com.orange.vinicola.model.Endereco;
import com.orange.vinicola.model.Pedido;
import com.orange.vinicola.service.CarrinhoService;
import com.orange.vinicola.service.ClienteService;
import com.orange.vinicola.service.PedidoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    @ResponseBody
    public Map<String, Object> finalizarPedido(@RequestParam("forma-pagamento") String formaPagamento, @RequestParam("endereco") Endereco endereco, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Carrinho carrinho = (Carrinho) request.getSession().getAttribute("carrinho");
        Cliente cliente = clienteService.findByEmail(authentication.getName());

        if (cliente != null) {
            carrinho = carrinhoService.buscarCarrinhoPorClienteId(cliente.getId());
        } else {
            System.out.println("Cliente não encontrado");
        }

        Pedido pedido = pedidoService.finalizarCarrinho(carrinho, endereco, formaPagamento);

        Map<String, Object> response = new HashMap<>();
        response.put("numeroPedido", pedido.getNumeroPedido());
        response.put("valorTotal", pedido.getValorComFrete());

        return response;
    }

    @GetMapping
    public String listarPedidos(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Cliente cliente = clienteService.findByEmail(authentication.getName());

        List<Pedido> pedidos = pedidoService.buscarPedidosPorClienteId(cliente.getId());

        model.addAttribute("pedidos", pedidos);

        return "lista-pedidos";
    }

    @GetMapping("/detalhes/{id}")
    public String detalhesPedido(@PathVariable Long id, Model model) {
        Optional<Pedido> pedido = pedidoService.findById(id);

        if (pedido.isPresent()) {
            model.addAttribute("pedido", pedido.get());
            return "detalhe-pedido";
        }

        return null;
    }
}
