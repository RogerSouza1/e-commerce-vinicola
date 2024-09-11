package com.orange.vinicola.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(min = 1, max = 200)
    private String nome;

    @DecimalMin(value = "0", inclusive = true)
    @DecimalMax(value = "5.0", inclusive = true)
    @Column(nullable = false)
    private double avaliacao;

    @Column(nullable = false, length = 2000)
    private String descricao;

    @Column(nullable = false)
    private double preco;

    @Column(nullable = false)
    private int qtdEstoque;

    @Getter
    @Column(nullable = false)
    private boolean ativado;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Imagem> imagems;


}
