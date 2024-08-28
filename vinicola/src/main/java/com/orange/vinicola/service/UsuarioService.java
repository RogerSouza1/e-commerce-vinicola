package com.orange.vinicola.service;

import com.orange.vinicola.model.Usuario;
import com.orange.vinicola.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario registrarUsuario(Usuario usuario){
        Optional<Usuario> usuarioExistente = userRepository.findByEmail(usuario.getEmail());
        if(usuarioExistente.isPresent()){
            throw new IllegalArgumentException("Email já cadastrado");
        }
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return userRepository.save(usuario);
    }
}
