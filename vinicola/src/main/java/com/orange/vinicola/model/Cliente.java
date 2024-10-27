package com.orange.vinicola.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Cliente implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "O nome não pode ser nulo")
    @Pattern(regexp = "^(?=.*\\b\\w{3,}\\b.*\\b\\w{3,}\\b).*$", message = "O nome deve conter ao menos duas palavras, cada uma com no mínimo 3 letras")
    private String nome;

    @Column(nullable = false, unique = true, updatable = false)
    @Email(message = "O email deve ser válido")
    @NotNull(message = "O email é obrigatório")
    private String email;

    @Column(nullable = false)
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter somente números com 11 dígitos.")
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", orphanRemoval = true)
    @Valid
    private List<Endereco> enderecos = new ArrayList<>();

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private Carrinho carrinho;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedidos;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_CLIENTE"));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
