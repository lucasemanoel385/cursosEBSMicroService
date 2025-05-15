package br.com.cursosEBS.gateway.infra.config;

import br.com.cursosEBS.gateway.enums.Role;
import br.com.cursosEBS.gateway.filter.AuthenticationFilter;
import br.com.cursosEBS.gateway.infra.rateLimiter.InMemoryRateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

@Configuration
public class GatewayConfig {

    @Autowired
    private AuthenticationFilter authenticationFilter;

    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }

    @Bean
    public InMemoryRateLimiter inMemoryRateLimiter() {
        return new InMemoryRateLimiter();
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {

        return builder.routes()

                // As request que vierem com essas path vao cair nesse route
                .route(r -> r.path("/courses-ms/courses", "/courses-ms/category", "/courses-ms/lesson")
                        .and().method(HttpMethod.POST)
                        .filters(f -> f.stripPrefix(1)
                                .requestRateLimiter(config -> config.setRateLimiter(inMemoryRateLimiter()))
                                .filter(((exchange, chain) -> {
                                //Adicionamos um Attibute na exchange
                                exchange.getAttributes().put("ROLE", Role.ROLE_INSTRUCTOR.toString());
                                //Indicamos se e uma Role especifica no filtro
                                exchange.getAttributes().put("RoleSpecific", false);
                                return authenticationFilter.filter(exchange, chain);
                        })))
                        .uri("lb://courses-ms"))

                .route(r -> r.path("/courses-ms/lesson", "/courses-ms/lesson/{id}")
                        .and().method(HttpMethod.GET)
                        .filters(f -> f.stripPrefix(1)
                                .requestRateLimiter(config -> config.setRateLimiter(inMemoryRateLimiter()))
                                .filter(((exchange, chain) -> {
                                    //Adicionamos um Attibute na exchange
                                    exchange.getAttributes().put("ROLE", Role.ROLE_STUDENTS.toString());
                                    //Indicamos se e uma Role especifica no filtro
                                    exchange.getAttributes().put("RoleSpecific", false);
                                    return authenticationFilter.filter(exchange, chain);
                                })))
                        .uri("lb://courses-ms"))

                .route(r -> r.path("/courses-ms/courses/{id}", "/courses-ms/category/{id}", "/courses-ms/lesson/{id}")
                        .and().method(HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.PATCH)
                        .filters(f -> f.stripPrefix(1)
                                .requestRateLimiter(config -> config.setRateLimiter(inMemoryRateLimiter()))
                                .filter(((exchange, chain) -> {
                                exchange.getAttributes().put("ROLE", Role.ROLE_INSTRUCTOR.toString());
                                exchange.getAttributes().put("RoleSpecific", false);
                                return authenticationFilter.filter(exchange, chain);
                        })))
                        .uri("lb://COURSES-MS"))

                .route(r -> r.path("/payments-ms/api/payment/**")
                        .filters(f -> f.stripPrefix(1)
                                .requestRateLimiter(config -> config.setRateLimiter(inMemoryRateLimiter()))
                                .filter(((exchange, chain) -> {
                                exchange.getAttributes().put("ROLE", Role.ROLE_RENEW.toString());
                                exchange.getAttributes().put("RoleSpecific", true);
                                return authenticationFilter.filter(exchange, chain);
                        })))
                        .uri("lb://payments-ms"))

                .route(r -> r.path("/enrollments-ms/enrollment/**")
                        .and().method(HttpMethod.POST)
                        .filters(f -> f.stripPrefix(1)
                                .requestRateLimiter(config -> config.setRateLimiter(inMemoryRateLimiter()))
                                .filter(((exchange, chain) -> {
                                exchange.getAttributes().put("ROLE", Role.ROLE_STUDENTS.toString());
                                exchange.getAttributes().put("RoleSpecific", false);
                                return authenticationFilter.filter(exchange, chain);
                        })))
                        .uri("lb://enrollments-ms"))

                .route(r -> r.path("/enrollments-ms/enrollment/**")
                        .and().method(HttpMethod.DELETE)
                        .filters(f -> f.stripPrefix(1)
                                .requestRateLimiter(config -> config.setRateLimiter(inMemoryRateLimiter()))
                                .filter(((exchange, chain) -> {
                                exchange.getAttributes().put("ROLE", Role.ROLE_ADMIN.toString());
                                exchange.getAttributes().put("RoleSpecific", false);
                                return authenticationFilter.filter(exchange, chain);
                        })))
                        .uri("lb://enrollments-ms"))

                .route(r -> r.path("/enrollments-ms/subscription/**", "/enrollments-ms/subscription")
                        .and().method(HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PATCH)
                        .filters(f -> f.stripPrefix(1)
                                .requestRateLimiter(config -> config.setRateLimiter(inMemoryRateLimiter()))
                                .filter(((exchange, chain) -> {
                                exchange.getAttributes().put("ROLE", Role.ROLE_ADMIN.toString());
                                exchange.getAttributes().put("RoleSpecific", false);
                                return authenticationFilter.filter(exchange, chain);
                        })))
                        .uri("lb://enrollments-ms"))


                .route(r -> r.path("/users-ms/user/{id}", "/users-ms/user/update-profile")
                        .and().method(HttpMethod.DELETE, HttpMethod.PATCH, HttpMethod.PUT, HttpMethod.GET)
                        .filters(f -> f.stripPrefix(1)
                                .requestRateLimiter(config -> config.setRateLimiter(inMemoryRateLimiter()))
                                .filter(((exchange, chain) -> {
                                    exchange.getAttributes().put("ROLE", Role.ROLE_ADMIN.toString());
                                    exchange.getAttributes().put("RoleSpecific", false);
                                    return authenticationFilter.filter(exchange, chain);
                                })))
                        .uri("lb://users-ms"))

                .build();
    }

}
