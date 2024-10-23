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
@Entity(name = "Pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private int numeroPedido;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private double frete;

    @Column(nullable = false)
    private double valorTotal;

    @Column(nullable = false)
    private double valorComFrete;

    @Column(nullable = false)
    private String formaPagamento;

    @Column(nullable = false)
    private boolean finalizado;

    @OneToMany
    @JoinColumn(name = "itens_id", referencedColumnName = "id")
    private List<Item> itens;

    @OneToOne
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;

    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private Cliente cliente;
}

