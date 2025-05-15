package br.com.cursosEBS.gateway.infra.rateLimiter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class InMemoryRateLimiter implements RateLimiter<Object> {

    //ConcurrentHashMap e um thread-safe que suporta multiplos acessos simultaneos de threads diferentes. Uasadas para ambientes multithreaded.
    private final Map<String, AtomicInteger> requestCounts = new ConcurrentHashMap<>();
    private final Map<String, Instant> blockedUntil = new ConcurrentHashMap<>();
    private final int limit = 5;  // Máximo de 5 requisições por segundo
    private final Duration blockDuration = Duration.ofSeconds(5);  // Bloqueio por 5 segundos

    @Override
    public Mono<Response> isAllowed(String routeId, String key) {
        // Verificar se o usuário está bloqueado
        Instant blockedTime = blockedUntil.get(key);
        if (blockedTime != null && Instant.now().isBefore(blockedTime)) {

            return Mono.just(new Response(false, Map.of("X-Rate-Limit", "Blocked until " + blockedTime)));
        }

        // Se o usuário não estiver bloqueado, permitir e verificar o limite de requisições
        requestCounts.putIfAbsent(key, new AtomicInteger(0));
        AtomicInteger requests = requestCounts.get(key);

        // Verificar se o limite foi atingido
        if (requests.incrementAndGet() > limit) {
            // Se o limite for atingido, bloquear o usuário por 5 segundos
            blockedUntil.put(key, Instant.now().plus(blockDuration));
            Mono.delay(Duration.ofSeconds(1)).doOnNext(aLong ->  requests.decrementAndGet()).subscribe(); System.out.println("teste");
            return Mono.just(new Response(false, Map.of("X-Rate-Limit", "Exceeded limit, blocked for 5 seconds")));
        }

        // Resetar o contador após 1 segundo
        Mono.delay(Duration.ofSeconds(1)).doOnNext(aLong ->  requests.decrementAndGet()).subscribe(); System.out.println("teste");

        return Mono.just(new Response(true, Map.of()));
    }

    @Override
    public Map<String, Object> getConfig() {
        return Map.of();
    }

    @Override
    public Class<Object> getConfigClass() {
        return null;
    }

    @Override
    public Object newConfig() {
        return null;
    }
}
