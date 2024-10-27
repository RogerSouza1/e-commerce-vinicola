package com.orange.vinicola.controller;

import com.orange.vinicola.model.Cliente;
import com.orange.vinicola.model.Endereco;
import com.orange.vinicola.service.ClienteService;
import com.orange.vinicola.service.EnderecoService;
import com.orange.vinicola.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

import java.util.ArrayList;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping("/cadastro")
    public String showFormCadastro(Model model) {
        Cliente cliente = new Cliente();
        cliente.setEnderecos(new ArrayList<>());
        model.addAttribute("cliente", new Cliente());
        return "registrar-cliente";
    }

    @PostMapping("/cadastro")
    public ModelAndView registerCliente(@Valid @ModelAttribute("cliente") Cliente cliente,
                                        BindingResult result, Model model,
                                        RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return new ModelAndView("registrar-cliente", "cliente", cliente);
        }

        cliente.setEmail(cliente.getEmail().toLowerCase());

        try {
            if (clienteService.findByEmail(cliente.getEmail()) != null || usuarioService.findByEmail(cliente.getEmail()) != null) {
                model.addAttribute("mensagem", "Email já registrado!");
                redirectAttributes.addFlashAttribute("cliente", cliente);
                return new ModelAndView("registrar-cliente");
            }

            if (clienteService.findByCPF(cliente.getCpf()) != null || usuarioService.findByCpf(cliente.getCpf()) != null) {
                model.addAttribute("mensagem", "CPF já registrado!");
                redirectAttributes.addFlashAttribute("cliente", cliente);
                return new ModelAndView("registrar-cliente");
            }

            String encodedPassword = encoder.encode(cliente.getSenha());
            cliente.setSenha(encodedPassword);

            if (!cliente.getEnderecos().isEmpty()) {
                cliente.getEnderecos().get(0).setEnderecoFaturamento(true);
                cliente.getEnderecos().get(1).setEntregaPadrao(true);

               for(Endereco endereco : cliente.getEnderecos()) {
                   endereco.setCliente(cliente);
               }
            }

            clienteService.save(cliente);

            redirectAttributes.addFlashAttribute("cliente", cliente);

        } catch (DataIntegrityViolationException e) {
            model.addAttribute("mensagem", "Email já registrado!");
        }

        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/perfil")
    public String showPerfil(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Cliente cliente = clienteService.findByEmail(authentication.getName());
        model.addAttribute("carrinho", cliente.getCarrinho());
        model.addAttribute("cliente", cliente);
        return "editar-cliente";
    }

    @GetMapping("/editar-dados")
    public String showEditarDados(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Cliente cliente = clienteService.findByEmail(authentication.getName());
        model.addAttribute("carrinho", cliente.getCarrinho());
        model.addAttribute("cliente", cliente);
        return "editar-dados-cliente";
    }

    @PostMapping("/editar-dados")
    public ModelAndView editarCliente(@ModelAttribute("cliente") @Valid Cliente cliente, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("cliente", cliente);
            return new ModelAndView("editar-dados-cliente", "cliente", cliente);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Cliente clienteAtual = clienteService.findByEmail(authentication.getName());

        cliente.setId(clienteAtual.getId());
        cliente.setEmail(clienteAtual.getEmail());
        cliente.setCpf(clienteAtual.getCpf());
        cliente.setEnderecos(clienteAtual.getEnderecos());
        cliente.setCarrinho(clienteAtual.getCarrinho());

        if (!cliente.getSenha().isEmpty()) {
            String encodedPassword = encoder.encode(cliente.getSenha());
            clienteAtual.setSenha(encodedPassword);
        }

        clienteAtual.setNome(cliente.getNome());
        clienteAtual.setDataNascimento(cliente.getDataNascimento());
        clienteAtual.setGenero(cliente.getGenero());

        clienteService.save(clienteAtual);

        redirectAttributes.addFlashAttribute("mensagem", "Dados atualizados com sucesso!");
        redirectAttributes.addFlashAttribute("cliente", clienteAtual);

        return new ModelAndView("redirect:/cliente/perfil");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
        request.getSession().invalidate();
        request.getSession().setAttribute("cliente", null);
        request.getSession().setAttribute("carrinho", null);
        SecurityContextHolder.clearContext();
        return new ModelAndView("redirect:/login");
    }
}