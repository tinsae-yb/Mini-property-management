package com.example.minipropertymanagement.filter;

import com.example.minipropertymanagement.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    public JWTFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {

        var token = tokenFromRequest(request);
        if (token != null ) {
            jwtUtil.validateToken(token);
            SecurityContextHolder.getContext().setAuthentication(jwtUtil.getAuthentication(token));
        }

        filterChain.doFilter(request, response);
    }


    public String tokenFromRequest(HttpServletRequest request) {
        final String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return  token.substring(7);
        }
        return null;
    }
}
