package com.pm.filemanagementsystembackend.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class JwtReactiveAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private static final Logger log = LoggerFactory.getLogger(JwtReactiveAuthorizationManager.class);
    private final WebClient webClient;

    public JwtReactiveAuthorizationManager(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<AuthorizationDecision> check(Mono<org.springframework.security.core.Authentication> authentication, AuthorizationContext context) {
        String token = context.getExchange().getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        log.info("1. token: {}", token);

        if (token == null || !token.startsWith("Bearer ")) {
            return Mono.just(new AuthorizationDecision(false));
        }

        return webClient.get()
                .uri("/validate")
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .toBodilessEntity()
                .map(response -> {
                    log.info("2. validation status: {}", response.getStatusCode());
                    return new AuthorizationDecision(response.getStatusCode().is2xxSuccessful());
                })
                .onErrorReturn(new AuthorizationDecision(false));
    }
}
