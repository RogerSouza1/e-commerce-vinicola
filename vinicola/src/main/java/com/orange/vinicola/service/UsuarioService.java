package com.orange.vinicola.service;

import com.orange.vinicola.model.Usuario;
import com.orange.vinicola.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void save(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public long findIdByEmail(String email) {
        return usuarioRepository.findIdByEmail(email);
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
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

    public Long findIdByNome(String nome) {
        return usuarioRepository.findIdByNome(nome);
    }

    public Usuario findByCpf(String cpf) {
        return usuarioRepository.findByCpf(cpf);
    }

}
