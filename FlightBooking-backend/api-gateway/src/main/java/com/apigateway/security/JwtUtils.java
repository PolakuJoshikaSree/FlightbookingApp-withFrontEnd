package com.apigateway.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

public class JwtUtils {

    private final Key key;
    private final JwtParser parser;

    public JwtUtils(String secret) {
        if (secret == null || secret.length() < 32) {
            throw new IllegalArgumentException("JWT secret must be at least 32 chars");
        }
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.parser = Jwts.parserBuilder().setSigningKey(key).build();
    }

    public Jws<Claims> parse(String jwt) throws JwtException {
        return parser.parseClaimsJws(jwt);
    }

    public static String getRolesFromClaims(Claims c) {
        Object rolesObj = c.get("roles");
        if (rolesObj instanceof Collection) {
            Collection<?> col = (Collection<?>)rolesObj;
            return col.stream().map(Object::toString).collect(Collectors.joining(","));
        }
        return rolesObj == null ? "" : rolesObj.toString();
    }
}
