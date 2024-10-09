package com.orange.vinicola.repository;

import com.orange.vinicola.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findByEmail(String email);

    @Query("SELECT u.id FROM Cliente u WHERE u.email = :email")
    Long findIdByEmail(String email);
}
