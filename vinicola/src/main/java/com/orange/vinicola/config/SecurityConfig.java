package com.orange.vinicola.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    @Lazy
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/", "/detalhes-produto-cliente", "/index", "/h2-console/**", "/css/**", "/images/**", "/js/**", "/imagem/**", "/carrinho/**", "/cliente/**","/pesquisa").permitAll()
                        .requestMatchers("/cliente/perfil", "/cliente/editar-dados", "/carrinho/finalizar", "/carrinho/checkout","/pedido","/pedido/finalizar").hasRole("CLIENTE")
                        .requestMatchers("/dashboard", "/lista-produtos", "/buscar-produto", "/editar-produto").hasAnyRole("ADMINISTRADOR","ESTOQUISTA")
                        .requestMatchers("/lista-usuarios", "/buscar-usuario", "/editar-usuario","/cadastro-produto", "/alterar-estado-produto", "/detalhes-produto").hasRole("ADMINISTRADOR")
                        .requestMatchers("/pedido/listar", "/pedido/editar").hasRole("ESTOQUISTA")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")
                        .passwordParameter("senha")
                        .permitAll()
                        .failureHandler(new CustomAuthenticationFailureHandler())
                        .successHandler(new CustomAuthenticationSuccessHandler())
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")
                )
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}