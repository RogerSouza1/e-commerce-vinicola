package com.orange.vinicola.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Pattern(regexp = "\\d{8}", message = "O cep deve conter somente número com máximo de 8 dígitos.")
    private String cep;

    @Column(nullable = false)
    @NotNull(message = "O logradouro não pode ser nulo")
    private String logradouro;

    @Column(nullable = false)
    @NotNull(message = "O numero não pode ser nulo")
    private String numero;

    private String complemento;

    @Column(nullable = false)
    @NotNull(message = "O bairro não pode ser nulo")
    private String bairro;

    @Column(nullable = false)
    @NotNull(message = "A cidade não pode ser nulo")
    private String cidade;

    @Column(nullable = false)
    @NotNull(message = "O estado não pode ser nulo")
    private String estado;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}
