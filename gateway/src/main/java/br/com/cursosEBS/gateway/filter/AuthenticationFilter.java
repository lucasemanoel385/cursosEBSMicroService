package br.com.cursosEBS.gateway.filter;

import br.com.cursosEBS.gateway.UtilJWT.JwtUtil;
import br.com.cursosEBS.gateway.enums.Role;
import br.com.cursosEBS.gateway.infra.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class AuthenticationFilter implements GatewayFilter {

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
        //Pega o token do autorization
        String authHeader = request.getHeaders().getFirst("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Mono.error(new ValidationException("Token ausente ou inválido"));
        }

        // Obtenha o papel (role) que foi passado para o filtro
        Role role = Enum.valueOf(Role.class, exchange.getAttribute("ROLE"));

        Boolean verifySpecific = exchange.getAttribute("RoleSpecific");

        String token = authHeader.substring(7);

        var user = jwtUtil.validateToken(token).get();


        if (Boolean.TRUE.equals(verifySpecific)) {
            if (!user.token().equals(token) || !user.role().equals(role.name())) {
                return Mono.error(new ValidationException("JWT invalido ou sem autoridade!!"));
            }
        } else {
            if (!user.token().equals(token) || Enum.valueOf(Role.class, user.role()).getValueOfRole() < role.getValueOfRole()) {
                return Mono.error(new ValidationException("JWT invalido ou sem autoridade!!"));
            }
        }


        return Mono.just(true);
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }

}