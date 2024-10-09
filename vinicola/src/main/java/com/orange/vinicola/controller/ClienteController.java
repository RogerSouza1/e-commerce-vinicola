package com.orange.vinicola.controller;

import com.orange.vinicola.model.Cliente;
import com.orange.vinicola.model.Endereco;
import com.orange.vinicola.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping("/cadastro-cliente")
    public String showForm(Model model) {
        Cliente cliente = new Cliente();
        cliente.setEnderecoFaturamento(new Endereco());
        model.addAttribute("cliente", new Cliente());
        return "registrar-cliente";
    }

    @PostMapping("/cadastro-cliente")
    public ModelAndView registerCliente(@ModelAttribute("cliente")@Valid Cliente cliente, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return new ModelAndView("registrar-cliente", "cliente", cliente);
        }
           cliente.setEmail(cliente.getEmail().toLowerCase());
        try {
            if (clienteService.findByEmail(cliente.getEmail()) != null) {
                System.out.println("Entrou no if de email");
                redirectAttributes.addFlashAttribute("mensagem", "Email já registrado!");
                redirectAttributes.addFlashAttribute("cliente", cliente);
                return new ModelAndView("registrar-cliente");
            }

            if(clienteService.findByCPF(cliente.getCpf()) != null){
                System.out.println("Entrou no if de cpf");
                redirectAttributes.addFlashAttribute("mensagem", "CPF já registrado!");
                redirectAttributes.addFlashAttribute("cliente", cliente);
                return new ModelAndView("registrar-cliente");
            }

            String encodedPassword = encoder.encode(cliente.getSenha());
            cliente.setSenha(encodedPassword);

            clienteService.save(cliente);
            redirectAttributes.addFlashAttribute("cliente", cliente);

        } catch (DataIntegrityViolationException e) {
            model.addAttribute("mensagem", "Email já registrado!");
        }
        return new ModelAndView("redirect:/login");
    }
}