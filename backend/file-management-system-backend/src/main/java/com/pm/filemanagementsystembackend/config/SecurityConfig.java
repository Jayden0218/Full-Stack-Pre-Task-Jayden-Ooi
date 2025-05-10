package com.pm.filemanagementsystembackend.config;

import com.pm.filemanagementsystembackend.filter.JwtReactiveAuthorizationManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http, WebClient.Builder webClientBuilder, @Value("${auth.service.url}") String authServiceUrl) {

        WebClient webClient = webClientBuilder.baseUrl(authServiceUrl).build();
        JwtReactiveAuthorizationManager jwtAuthManager = new JwtReactiveAuthorizationManager(webClient);
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/create","/validate","/login").permitAll()
                        .pathMatchers("/notes","/notes/**").permitAll()
                        .pathMatchers("/auth/notes/**").access(jwtAuthManager)
                )
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfig = new org.springframework.web.cors.CorsConfiguration();
                    corsConfig.addAllowedOrigin("http://localhost:5173");
                    corsConfig.addAllowedMethod("*");
                    corsConfig.addAllowedHeader("*");
                    return corsConfig;
                }))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
