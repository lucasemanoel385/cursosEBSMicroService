/*package br.com.cursosEBS.gateway.filter;

import br.com.cursosEBS.gateway.UtilJWT.JwtUtil;
import br.com.cursosEBS.gateway.infra.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class AuthenticationRoleFilter implements GatewayFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        return validateToken(exchange)
                .flatMap(valid -> chain.filter(exchange))
                .onErrorResume(ValidationException.class, ex -> unauthorizedResponse(exchange, ex.getMessage()));
    }

    private Mono<Boolean> validateToken(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String authHeader = request.getHeaders().getFirst("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Mono.error(new ValidationException("Token ausente ou inválido"));
        }

        String token = authHeader.substring(7);
        try {
            if (!jwtUtil.validateToken(token).get().token().equals(token)) {
                return Mono.error(new ValidationException("JWT inválido"));
            }
        } catch (Exception e) {
            return Mono.error(new ValidationException("JWT inválido"));
        }

        return Mono.just(true);
    }

    //private boolean isPublicEndpoint(String path) {
    // return PUBLIC_ENDPOINTS.stream().anyMatch(path::startsWith);
    //}

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }

}

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class JwtAuthenticationFilter implements GatewayFilter {

    private final JwtTokenService jwtTokenService;

    public JwtAuthenticationFilter(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Optional<JwtUserDetails> userDetails = jwtTokenService.validateToken(token);

            if (userDetails.isPresent()) {
                exchange.getRequest().mutate()
                        .header("X-User-Id", userDetails.get().getUsername())
                        .header("X-Roles", String.join(",", userDetails.get().getRoles()))
                        .build();
            } else {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        } else {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }
}
 */