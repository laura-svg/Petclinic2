package com.example.petclinic2.security;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    @Autowired
    public JwtAuthFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();
        if (path.startsWith("/auth/")|| path.equals("/auth")) {
            filterChain.doFilter(request, response); // пропускаємо
            return;
        }

        String token = getToken(request);// отримуємо токен з запиту , якщо він у нас є
        if (token != null && jwtTokenProvider.validateToken(token)) {// перевіряємо якщо токен не нульовий, і він проходить валідацію
            String username = jwtTokenProvider.getUsernameFromToken(token);// ми виймаємо  цього токена логін у вигляді username
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);// беремо користувача з бази
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());// створюємо токен автетнтифікації
            SecurityContextHolder.getContext().setAuthentication(auth);// і передаємо цей отриманий токен в Security Conext, тобто вручну автентифікуємо користувача врчуну
        }
        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        return (bearerToken !=null && bearerToken.startsWith("Bearer "))? bearerToken.substring(7) : null;
    }

}