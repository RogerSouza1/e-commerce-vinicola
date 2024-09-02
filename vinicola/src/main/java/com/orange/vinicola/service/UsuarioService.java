package com.orange.vinicola.service;

import com.orange.vinicola.model.Usuario;
import com.orange.vinicola.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    //TODO: Alterar para optional
    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public void update(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public void alterar_estado(Usuario usuario) {

        usuario.setAtivado(!usuario.isAtivado());
        
        usuarioRepository.save(usuario);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public List<Usuario> findByNome(String nome) {
        return usuarioRepository.findByNome(nome);
    }

    public boolean validar_login(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario != null) {
            if (usuario.getSenha().equals(senha)) {
                return true;
            }
        }
        return false;
    }

}
