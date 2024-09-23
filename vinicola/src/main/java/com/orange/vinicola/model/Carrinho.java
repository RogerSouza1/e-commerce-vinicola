package com.orange.vinicola.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Carrinho")
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> itens;

    @Column(nullable = false)
    private double frete;

    @Column(nullable = false)
    private double valorTotal;

    @OneToOne(cascade = CascadeType.ALL)
    private Endereco enderecoEntrega;

    @OneToOne(cascade = CascadeType.ALL)
    private Endereco enderecoCobranca;

    private String formaPagamento;

    @OneToOne(mappedBy = "carrinho", cascade = CascadeType.ALL)
    private Usuario usuario;

    @Column(nullable = false)
    private boolean finalizado;
}
