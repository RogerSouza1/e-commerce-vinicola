package com.orange.vinicola.repository;

import com.orange.vinicola.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);

    @Query("SELECT u FROM Usuario u WHERE LOWER(u.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Usuario> findByNome(@Param("nome") String nome);

    @Query("SELECT u.id FROM Usuario u WHERE u.nome = :nome")
    Long findIdByNome(@Param("nome") String nome);

    @Query("SELECT u.id FROM Usuario u WHERE u.email = :email")
    long findIdByEmail(String email);

    @Query("SELECT u FROM Usuario u WHERE u.cpf = :cpf")
    Usuario findByCpf(String cpf);
}
