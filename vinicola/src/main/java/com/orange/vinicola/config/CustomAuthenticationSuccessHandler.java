package com.orange.vinicola.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String redirectUrl = request.getContextPath();

        if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_CLIENTE"))) {
            redirectUrl += "/";
        } else if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().contains("ROLE_ADMIN") || role.getAuthority().contains("ROLE_ESTOQUISTA"))) {
            redirectUrl += "/dashboard";
        }

        response.sendRedirect(redirectUrl);
    }
}
