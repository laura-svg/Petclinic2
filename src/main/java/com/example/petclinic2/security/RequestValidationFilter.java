package com.example.petclinic2.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestValidationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String path = httpRequest.getServletPath();

        // Дозволити HTML-запити (включаючи login, register)
        if (!path.startsWith("/api") && !path.startsWith("/auth")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // Перевірка Request-Id тільки для API-запитів
        String requestId = httpRequest.getHeader("Request-Id");
        if (requestId == null || requestId.isBlank()) {
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpResponse.getWriter().write("Missing Request-Id header");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
