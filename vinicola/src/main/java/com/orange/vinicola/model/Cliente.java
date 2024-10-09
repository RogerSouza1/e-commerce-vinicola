package com.orange.vinicola.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
    private String nome;

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
    @NotNull(message = "A data de nascimento não pode ser nula")
    @PastOrPresent(message = "A data de nascimento não pode ser posterior a hoje")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataNascimento;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "endereco_faturamento_id", referencedColumnName = "id")
    private Endereco enderecoFaturamento;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", orphanRemoval = true)
    private List<Endereco> enderecosEntrega = new ArrayList<>();
}
