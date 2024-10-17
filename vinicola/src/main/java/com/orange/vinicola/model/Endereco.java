package com.orange.vinicola.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Pattern(regexp = "\\d{8}", message = "O cep deve conter somente 8 dígitos númericos.")
    private String cep;

    @Column(nullable = false)
    @NotNull(message = "O logradouro não pode ser nulo")
    private String logradouro;

    @Column(nullable = false)
    @NotNull(message = "O numero não pode ser nulo")
    @Pattern(regexp = "\\d+", message = "O numero deve conter apenas números.")
    private String numero;

    @Column(columnDefinition = "default ''")
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

    @Column(name = "is_endereco_faturamento", nullable = false, columnDefinition = "boolean default false")
    private boolean isEnderecoFaturamento = false;

    @Column(name = "is_entrega_padrao", nullable = false, columnDefinition = "boolean default false")
    private boolean isEntregaPadrao = false;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}
