package com.orange.vinicola.service;

import com.orange.vinicola.model.Cliente;
import com.orange.vinicola.model.Usuario;
import com.orange.vinicola.repository.ClienteRepository;
import com.orange.vinicola.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {

        String userEmail = email.toLowerCase();

        Usuario usuario = usuarioRepository.findByEmail(userEmail);
        Cliente cliente = clienteRepository.findByEmail(userEmail);

        if (usuario == null && cliente == null) {
            System.out.println("Usuário não encontrado: " + userEmail);
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        assert usuario != null;


        if (usuario != null) {
            if (!usuario.isAtivado()) {
                System.out.println("Usuário desativado: " + userEmail);
                throw new UsernameNotFoundException("Usuário desativado");
            }
            return new User(usuario.getEmail(), usuario.getSenha(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + usuario.getGrupo())));
        }

        if (cliente != null) {
            return new User(cliente.getEmail(), cliente.getSenha(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_CLIENTE")));
        }

        return null;

    }
}
