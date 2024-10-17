package com.orange.vinicola.controller;

import com.orange.vinicola.model.Cliente;
import com.orange.vinicola.model.Endereco;
import com.orange.vinicola.repository.EnderecoRepository;
import com.orange.vinicola.service.ClienteService;
import com.orange.vinicola.service.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/endereco")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @GetMapping("/lista-enderecos")
    public String listaEnderecos(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Cliente cliente = clienteService.findByEmail(authentication.getName());

        if (cliente == null) {
            model.addAttribute("mensagem", "Cliente não encontrado.");
            return "lista-enderecos";
        }

        List<Endereco> enderecos = enderecoService.findByClienteId(cliente.getId());
        Endereco enderecoFaturamento = enderecoService.findEnderecoFaturamento(cliente.getId());

        if (enderecoFaturamento != null) {
            enderecos.remove(enderecoFaturamento);
        }

        model.addAttribute("cliente", cliente);
        model.addAttribute("enderecoFaturamento", enderecoFaturamento);
        model.addAttribute("enderecos", enderecos);

        return "lista-enderecos";
    }

    @PostMapping("/definir-padrao")
    public String definirEnderecoPadrao(@RequestParam("id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Cliente cliente = clienteService.findByEmail(authentication.getName());
        Endereco endereco = enderecoService.findById(id);

        for (Endereco enderecos : cliente.getEnderecos()) {
            if (enderecos.isEntregaPadrao()) {
                enderecos.setEntregaPadrao(false);
            }
        }

        endereco.setEntregaPadrao(true);

        enderecoService.save(endereco);

        return "redirect:/endereco/lista-enderecos";
    }

    @GetMapping("/cadastrar")
    public String cadastrarEndereco(Model model) {
        model.addAttribute("endereco", new Endereco());
        return "registrar-endereco";
    }

    @PostMapping("/cadastrar")
    public ModelAndView cadastrarEndereco(@Valid @ModelAttribute("endereco") Endereco endereco,
                                          BindingResult result, Model model,
                                          RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return new ModelAndView("registrar-endereco", "endereco", endereco);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Cliente cliente = clienteService.findByEmail(authentication.getName());

        endereco.setCliente(cliente);
        enderecoRepository.save(endereco);

        redirectAttributes.addFlashAttribute("mensagem", "Endereço cadastrado com sucesso!");

        return new ModelAndView("redirect:/endereco/lista-enderecos");

    }
}
