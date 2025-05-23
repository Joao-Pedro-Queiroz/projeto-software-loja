package br.insper.loja.security;

import br.insper.loja.login.LoginService;
import br.insper.loja.usuario.Usuario;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private LoginService loginService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String method = request.getMethod();

        if (method.equals("POST")) {
            String token = request.getHeader("Authorization");

            Usuario usuario = loginService.validateToken(token);

            if (!usuario.getPapel().equals("ADMIN")) {
                response.sendError(HttpStatus.FORBIDDEN.value());
            } else {
                filterChain.doFilter(request, response);
            }
        } else {
            String token = request.getHeader("Authorization");

            loginService.validateToken(token);
            filterChain.doFilter(request, response);
        }
    }
}
