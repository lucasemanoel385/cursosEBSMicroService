package br.com.cursosEBS.email.infra.messageConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

@Configuration
public class MessageSourceConfig {

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("messages"); //
        source.setDefaultEncoding("UTF-8");
        source.setDefaultLocale(Locale.of("pt", "BR"));
        return source;
    }
}
