package br.com.cursosEBS.enrollments.config;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignClientConfig {

    // Insere o token no header authorization junto com a requisition
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            String token = getTokenFromRequest();
            if (token != null) {
                requestTemplate.header(HttpHeaders.AUTHORIZATION, token);
            }
        };
    }

    private String getTokenFromRequest() {
        // Obtém a requisição HTTP atual
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return null;
        }

        HttpServletRequest request = attributes.getRequest();
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        return (authHeader != null && authHeader.startsWith("Bearer ")) ? authHeader : null;
    }
}
