package com.apigateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.HttpHeaders;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class JwtAuthGlobalFilter implements GlobalFilter, Ordered {

    private final JwtUtils jwtUtils;
    // map path prefix -> required role
    private final Map<String, String> protectedRoutes = new LinkedHashMap<>();

    public JwtAuthGlobalFilter(@Value("${jwt.secret}") String secret) {
        this.jwtUtils = new JwtUtils(secret);

        // configure protected routes (prefix matching)
        // adjust prefixes to match your gateway routing paths exactly
        protectedRoutes.put("/flights/add", "ADMIN");
        protectedRoutes.put("/airlines", "ADMIN");
        protectedRoutes.put("/booking/book", "CUSTOMER");
        protectedRoutes.put("/booking/cancel", "CUSTOMER");
        // add more rules as needed
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().value();

        Optional<Map.Entry<String, String>> rule = protectedRoutes.entrySet()
                .stream()
                .filter(e -> path.startsWith(e.getKey()))
                .findFirst();

        if (rule.isEmpty()) {
            return chain.filter(exchange);
        }

        List<String> auth = exchange.getRequest().getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION);
        if (auth.isEmpty() || !auth.get(0).startsWith("Bearer ")) {
            return unauthorized(exchange, "Missing or invalid Authorization header");
        }

        String token = auth.get(0).substring(7).trim();
        try {
            Jws<io.jsonwebtoken.Claims> jws = jwtUtils.parse(token);
            Claims claims = jws.getBody();
            String rolesCsv = JwtUtils.getRolesFromClaims(claims);
            String requiredRole = rule.get().getValue();

            boolean hasRole = Arrays.stream(rolesCsv.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .anyMatch(r -> r.equalsIgnoreCase(requiredRole));

            if (!hasRole) {
                return forbidden(exchange, "Insufficient role");
            }

            ServerHttpRequest mutatedRequest = exchange.getRequest()
                    .mutate()
                    .header("X-User", claims.getSubject())
                    .header("X-Roles", rolesCsv)
                    .build();

            ServerWebExchange mutatedExchange = exchange.mutate()
                    .request(mutatedRequest)
                    .build();

            return chain.filter(mutatedExchange);


        } catch (Exception ex) {
            return unauthorized(exchange, "Invalid token: " + ex.getMessage());
        }
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String body) {
        ServerHttpResponse resp = exchange.getResponse();
        resp.setStatusCode(HttpStatus.UNAUTHORIZED);
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        return resp.writeWith(Mono.just(resp.bufferFactory().wrap(bytes)));
    }

    private Mono<Void> forbidden(ServerWebExchange exchange, String body) {
        ServerHttpResponse resp = exchange.getResponse();
        resp.setStatusCode(HttpStatus.FORBIDDEN);
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        return resp.writeWith(Mono.just(resp.bufferFactory().wrap(bytes)));
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
