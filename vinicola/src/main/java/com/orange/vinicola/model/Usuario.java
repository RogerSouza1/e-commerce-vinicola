package com.orange.vinicola.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private String nome;

    @Column (nullable = false, unique = true, updatable = false)
    @Email
    private String email;

    @Column (nullable = false, length = 11)
    @CPF
    private String cpf;

    @Column (nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column (nullable = false)
    private Grupo grupo;

    @Column (nullable = false)
    private boolean ativo;
}
