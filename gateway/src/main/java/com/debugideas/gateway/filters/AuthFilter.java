package com.debugideas.gateway.filters;

import com.debugideas.gateway.dtos.TokenDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthFilter implements GatewayFilter {

    private final WebClient webClient;

    private static final String AUTH_VALIDATE_URI = "http://ms-auth:3030/auth-server/api/auth/jwt";
    private static final String ACCESS_TOKEN_HEADER_NAME = "accessToken";

    public AuthFilter() {
        webClient = WebClient.builder().build();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("init validate filter auth");
        if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            log.error("Authorization header missing");
            return onError(exchange);
        }

        final String tokenHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        final String[] chunks = tokenHeader.split(" ");

        if (chunks.length != 2 || !chunks[0].equals("Bearer")) {
            log.error("Authorization header format is invalid");
            return onError(exchange);
        }

        final String token = chunks[1];

        return webClient.post()
                .uri(AUTH_VALIDATE_URI)
                .header(ACCESS_TOKEN_HEADER_NAME, token) // ← ✅ agregamos el token al header
                .retrieve()
                .bodyToMono(TokenDto.class)
                .flatMap(res -> chain.filter(exchange))
                .onErrorResume(e -> {
                    log.error("Token validation failed: {}", e.getMessage());
                    return onError(exchange);
                });
    }

    private Mono<Void> onError(ServerWebExchange exchange) {
        final ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.BAD_REQUEST);
        return response.setComplete();

    }
}
