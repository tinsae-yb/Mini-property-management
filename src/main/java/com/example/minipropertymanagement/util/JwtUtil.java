package com.example.minipropertymanagement.util;

import com.example.minipropertymanagement.domain.User;
import com.example.minipropertymanagement.exception.InvalidCredential;
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

    private final UserDetailsService userDetailsService;
    @Value("${jwtSecret}")
    private String secret;
    @Value("${accessTokenExpirationTime}")
    private Integer accessTokenExpiration;
    @Value("${refreshTokenExpirationTime}")
    private Integer refreshExpiration;

    public Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }


    public void validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
//            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException |
                 IllegalArgumentException e) {
            System.out.println(e.getMessage());
            throw new InvalidCredential("Invalid Token. " + e.getMessage());
        }
//        return false;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject()); // LEFT THIS HERE ON PURPOSE
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }


    public String generateToken(User user, boolean isRefreshToken) {
        Map<String, Object> claims = new HashMap<>();
        if (!isRefreshToken) {
            claims.put("roles", user.getRole());
        }
        claims.put("accountStatus", user.getAccountStatus());
        return Jwts.builder().setClaims(claims).setSubject(user.getEmail()).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + (isRefreshToken ? refreshExpiration : accessTokenExpiration))).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

}
