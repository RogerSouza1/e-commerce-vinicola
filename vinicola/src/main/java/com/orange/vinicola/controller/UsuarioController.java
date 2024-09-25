package com.orange.vinicola.controller;

import com.orange.vinicola.model.Usuario;
import com.orange.vinicola.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

        usuario.setEmail(usuario.getEmail().toLowerCase());

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
        return "redirect:/lista-usuarios";
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
        } else if (!usuario.get().isAtivado()) {
            model.addAttribute("mensagem", "Não é possível editar um usuário inativo!");
            return "redirect:/lista-usuarios";
        } else {
            model.addAttribute("usuario", usuario.get());
            boolean isEditingOwnProfile = usuarioAutenticado.getId().equals(usuario.get().getId());
            model.addAttribute("isEditingOwnProfile", isEditingOwnProfile);
            return "editar-usuario";
        }
    }

    @PostMapping("/editar-usuario")
    public String updateUser(Usuario usuario, Model model) {
        usuario.setAtivado(true);
        Optional<Usuario> usuarioExistente = UsuarioService.findById(usuario.getId());
        Usuario usuarioAutenticado = UsuarioService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if (usuarioExistente.isPresent()) {
            if (usuario.getId().equals(usuarioAutenticado.getId())) {
                usuario.setGrupo(usuarioAutenticado.getGrupo());
            }
        }

        usuario.setSenha(encoder.encode(usuario.getSenha()));
        UsuarioService.update(usuario);
        model.addAttribute("mensagem", "Usuário atualizado com sucesso!");
        return "redirect:/lista-usuarios";
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
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        SecurityContextHolder.clearContext();
        if (error != null) {
            model.addAttribute("error", error);
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email, @RequestParam("senha") String senha, Model model) {
        String userEmail = email.toLowerCase();
        Optional<Usuario> usuario = Optional.ofNullable(UsuarioService.findByEmail(userEmail));

        if (usuario.isEmpty()) {
            model.addAttribute("error", "Email inválido!");
            return "redirect:/login";
        } else if (!encoder.matches(senha, usuario.get().getSenha())) {
            model.addAttribute("error", "Senha incorreta!");
            return "redirect:/login";
        } else if (!usuario.get().isAtivado()) {
            model.addAttribute("error", "Usuário inativo!");
            return "redirect:/login";
        } else if (usuario.get().getGrupo().equals("CLIENTE")) {
            model.addAttribute("error", "Acesso negado!");
            return "redirect:/login";
        } else {
            Authentication auth = new UsernamePasswordAuthenticationToken(usuario.get().getEmail(), usuario.get().getSenha(), usuario.get().getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            return "redirect:/dashboard";
        }

    }
}