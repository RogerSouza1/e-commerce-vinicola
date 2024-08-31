package com.orange.vinicola.controller;

import com.orange.vinicola.model.Usuario;
import com.orange.vinicola.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
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
    public String showEditForm(@RequestParam("id") Long id, Model model, Authentication authentication) {
        Optional<Usuario> usuario = UsuarioService.findById(id);
        Usuario usuarioAutenticado = UsuarioService.findByEmail(authentication.getName());

        //TODO: Revisar código e páginas de erro

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
}