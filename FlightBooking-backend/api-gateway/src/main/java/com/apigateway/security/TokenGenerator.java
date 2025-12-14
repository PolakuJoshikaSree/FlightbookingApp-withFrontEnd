package com.apigateway.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Instant;
import java.util.*;

public class TokenGenerator {
    public static String generate(String secret, String subject, List<String> roles, long ttlSeconds) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes());
        Instant now = Instant.now();
        Map<String,Object> claims = new HashMap<>();
        claims.put("roles", roles);
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(ttlSeconds)))
                .addClaims(claims)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public static void main(String[] args) {
        String secret = "replace-with-32+chars-secret-for-dev-only-012345";
        String adminToken = generate(secret, "admin@example.com", List.of("ADMIN"), 3600);
        System.out.println("ADMIN token:\n" + adminToken);
        String custToken = generate(secret, "cust@example.com", List.of("CUSTOMER"), 3600);
        System.out.println("CUSTOMER token:\n" + custToken);
    }
}
