package com.example.laza.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtService {

    @Value("${security.jwt.key}")
    private String key;

    public String generateToken(String username, Map<String, Object> claims) {

        Date data = new Date();
        Date expiration = Date.from(
                data.toInstant().plus(7, ChronoUnit.DAYS));

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(data)
                .setExpiration(expiration)
                .addClaims(claims)
                .signWith(signKey())
                .compact();

    }

    private Key signKey() {
        return Keys.hmacShaKeyFor(key.getBytes());
    }

    public Claims claims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


}
