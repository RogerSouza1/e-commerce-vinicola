package com.orange.vinicola.service;

import com.orange.vinicola.model.Usuario;
import com.orange.vinicola.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

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

    public Long findIdByNome(String nome) {
        return usuarioRepository.findIdByNome(nome);
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            System.out.println("Usuário não encontrado: " + email);
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        if (!usuario.isAtivado()) {
            System.out.println("Usuário desativado: " + email);
            throw new UsernameNotFoundException("Usuário desativado");
        }

        return new User(usuario.getEmail(), usuario.getSenha(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + usuario.getGrupo())));
    }


}
