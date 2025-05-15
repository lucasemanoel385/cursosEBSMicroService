package br.com.cursosEBS.users.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable).sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
                authorizeHttpRequests(authorize -> {
                    authorize.
                            //requestMatchers(HttpMethod.POST, "/auth/**").permitAll().
                            //requestMatchers(HttpMethod.POST, "/user/register").hasRole("ADMIN").
                            anyRequest().permitAll();
                })
                .cors(Customizer.withDefaults())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class).build();
    }

    @Bean //Serve pra exportar uma classe para o spring, fazendo com que ele consiga carregá-la e realize a sua injeção de dependência em outras classes.
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        //Essa classe AuthenticationConfiguration tem esse metodo getAuthenticationManager que sabe criar um objeto AuthenticationManager
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder encoderBCrypt() {
        return new BCryptPasswordEncoder();

    }
}


