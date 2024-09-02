package com.orange.vinicola.controller;

import com.orange.vinicola.model.Usuario;
import com.orange.vinicola.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;
import java.util.Optional;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService UsuarioService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping("/cadastro-funcionario")
    public String showForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registrar-funcionario";
    }

    @PostMapping("/cadastro-funcionario")
    public String registerUser(Usuario usuario, Model model) {
        try {
            if (UsuarioService.findByEmail(usuario.getEmail()) != null) {
                model.addAttribute("mensagem", "Email já registrado!");
                return "registrar-funcionario";
            }

            usuario.setAtivado(true);
            String encodedPassword = encoder.encode(usuario.getSenha());
            usuario.setSenha(encodedPassword);

            UsuarioService.save(usuario);
            model.addAttribute("mensagem", "Funcionário registrado com sucesso!");
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("mensagem", "Erro: Email já registrado!");
        }
        return "registrar-funcionario";
    }

    @GetMapping("/lista-usuarios")
    public String listarUsuario(Model model) {
        List<Usuario> usuarios = UsuarioService.findAll();
        model.addAttribute("usuarios", usuarios);
        return "lista-usuarios";
    }

    @GetMapping("/editar-usuario")
    public String showEditForm(@RequestParam("id") Long id, Model model) {
        Optional<Usuario> usuario = UsuarioService.findById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioAutenticado = UsuarioService.findByEmail(auth.getName());

        if (usuario.isEmpty()) {
            model.addAttribute("mensagem", "Usuário não encontrado!");
            return "erro";
        } else if (usuario.get().getEmail().equals(usuarioAutenticado.getEmail())) {
            model.addAttribute("mensagem", "Você não pode alterar seu próprio grupo!");
            return "erro";
        } else {
            model.addAttribute("usuario", usuario.get());
            return "editar-usuario";
        }
    }

    @PostMapping("/editar-usuario")
    public String updateUser(Usuario usuario, @RequestParam("confirmarSenha") String confirmarSenha, Model model) {
        if (!usuario.getSenha().equals(confirmarSenha)) {
            model.addAttribute("mensagem", "As senhas não conferem!");
            return "editar-usuario";
        }
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        UsuarioService.update(usuario);
        model.addAttribute("mensagem", "Usuário atualizado com sucesso!");
        return "sucesso";
    }

    @GetMapping("/alterar-estado")
    public String alterarEstado(@RequestParam("id") Long id, Model model) {
        Optional<Usuario> usuario = UsuarioService.findById(id);
        if (usuario.isPresent()) {
            UsuarioService.alterar_estado(usuario.get());
            model.addAttribute("mensagem", "Estado do usuário alterado com sucesso!");
        } else {
            model.addAttribute("mensagem", "Usuário não encontrado!");
        }
        return "redirect:/lista-usuarios";
    }

    @GetMapping("/buscar-usuario")
    public String buscarUsuario(@RequestParam("nome") String nome, Model model) {
        List<Usuario> usuarios = UsuarioService.findByNome(nome);
        model.addAttribute("usuarios", usuarios);
        return "fragments/tabela-usuarios :: tabela-usuarios";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "login";
    }
    @PostMapping("/login-acess")
    public String loginAcess(Usuario usuario, Model model) {
        if (UsuarioService.validar_login(usuario.getEmail(), usuario.getSenha())) {
            model.addAttribute("mensagem", "Login efetuado com sucesso!");
            return "redirect:/index";
        } else {
            model.addAttribute("mensagem", "Email ou senha incorretos!");
            return "login";
        }
    }
}