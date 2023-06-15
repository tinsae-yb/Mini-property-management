package com.example.minipropertymanagement.util;

import com.example.minipropertymanagement.domain.User;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${jwtSecret}")
    private  String secret ;

    @Value("${accessTokenExpirationTime}")
    private  Integer accessTokenExpiration;

    @Value("${refreshTokenExpirationTime}")
    private  Integer refreshExpiration;
    private final UserDetailsService userDetailsService;


    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException |
                 IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject()); // LEFT THIS HERE ON PURPOSE
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }


    public String generateToken(User user, boolean isRefreshToken) {
        Map<String, Object> claims = new HashMap<>();
        if (!isRefreshToken) {
            claims.put("roles", user.getRoles());
        }
        return Jwts.builder().setClaims(claims).setSubject(user.getEmail()).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + (isRefreshToken ? refreshExpiration : accessTokenExpiration))).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

}
