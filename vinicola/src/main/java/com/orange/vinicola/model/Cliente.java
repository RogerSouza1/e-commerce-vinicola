package com.orange.vinicola.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "O nome não pode ser nulo")
    @Pattern(regexp = "^[a-zA-Z]{3,}\\s[a-zA-Z]{3,}$", message = "O nome deve conter duas palavras, cada uma com no mínimo 3 letras")
    private String name;

    @Column(nullable = false, unique = true, updatable = false)
    @Email(message = "O email deve ser válido")
    @NotNull(message = "O email é obrigatório")
    private String email;

    @Column(nullable = false)
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter 11 dígitos.")
    private String cpf;

    @Column(nullable = false)
    @NotNull(message = "A senha não pode ser nula")
    private String senha;

    @Column(nullable = false)
    @NotNull(message = "O gênero não pode ser nulo")
    private String genero;

    @Column(nullable = false)
    @FutureOrPresent(message = "A data de nascimento não pode ser anterior a hoje")
    private Date dataNascimento;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "endereco_faturamento_id", referencedColumnName = "id")
    private Endereco enderecoFaturamento;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", orphanRemoval = true)
    private List<Endereco> enderecosEntrega = new ArrayList<>();

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private Carrinho carrinho;
}
