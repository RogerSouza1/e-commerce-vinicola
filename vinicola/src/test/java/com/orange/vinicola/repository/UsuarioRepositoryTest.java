package com.orange.vinicola.repository;

import com.orange.vinicola.model.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario ususarioTest1;
    private Usuario ususarioTest2;
    private Usuario ususarioTest3;

    @BeforeEach
    void setUp() {
        ususarioTest1 = new Usuario(1L, "Usuario1", "usuario1@winery.com", "12345678901", "123", "ADMINISTRADOR", true);
    }

    @AfterEach
    void tearDown() {
        usuarioRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve salvar um usuário")
    void deveSalvarUsuario() {
        Usuario usuario1 = usuarioRepository.save(ususarioTest1);

        assertNotNull(usuario1.getId());
        assertEquals(ususarioTest1.getNome(), usuario1.getNome());
        assertEquals(ususarioTest1.getEmail(), usuario1.getEmail());
        assertEquals(ususarioTest1.getCpf(), usuario1.getCpf());
        assertEquals(ususarioTest1.getSenha(), usuario1.getSenha());
        assertEquals(ususarioTest1.getGrupo(), usuario1.getGrupo());
        assertTrue(usuario1.isAtivado());

    }

}