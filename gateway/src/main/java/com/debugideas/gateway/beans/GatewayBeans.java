package com.debugideas.gateway.beans;

import com.debugideas.gateway.filters.AuthFilter;
import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Set;

@Configuration
@AllArgsConstructor
public class GatewayBeans {

    private final AuthFilter authFilter;

    @Bean
    @Profile(value = "eureka-off")
    public RouteLocator routeLocatorEurekaOff(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(route -> route
                        .path("/companies/api/company/**")
                        .uri("http://localhost:8081"))
                .route(route -> route
                        .path("/report-ms/api/report/**")
                        .uri("http://localhost:7070"))
                .route(route -> route
                        .path("/companies-fallback/api/company/**")
                        .uri("http://localhost:8787"))
                .build();
    }

    @Bean
    @Profile(value = "eureka-on")
    public RouteLocator routeLocatorEurekaOn(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(route -> route
                        .path("/companies/api/company/**")
                        .uri("lb://companies"))
                .route(route -> route
                        .path("/report-ms/api/report/**")
                        .uri("lb://report-ms"))
                .route(route -> route
                        .path("/companies-fallback/api/company/**")
                        .uri("lb://companies-fallback"))
                .route(route -> route
                        .path("/auth-server/api/auth/**")
                        .uri("lb://auth-server"))
                .build();
    }

    @Bean
    @Profile("eureka-on-cb")
    public RouteLocator routeLocatorEurekaOnCB(RouteLocatorBuilder builder) {
        return builder.routes()
                .route( route -> route
                        .path("/companies/api/company/**")
                        .filters(f -> f.circuitBreaker(cb -> cb
                                .setName("gateway-cb")
                                .setStatusCodes(Set.of("500", "400"))
                                .setFallbackUri("forward:/companies-fallback/api/company/**")
                        ))
                        .uri("lb://companies"))
                .route( route -> route
                        .path("/report-ms/api/report/**")
                        .uri("lb://report-ms"))
                .route(route -> route
                        .path("/companies-fallback/api/company/**")
                        .uri("lb://companies-fallback"))
                .build();
    }

    @Bean
    @Profile("oauth2")
    public RouteLocator routeLocatorEurekaOauth2(RouteLocatorBuilder builder) {
        return builder.routes()
                .route( route -> route
                        .path("/companies/api/company/**")
                        .filters(f -> {
                                f.circuitBreaker(cb -> cb
                                .setName("gateway-cb")
                                .setStatusCodes(Set.of("500", "502", "503", "504"))
                                .setFallbackUri("forward:/companies-fallback/api/company/**"));
                                f.filter(authFilter);
                                return f;
                        })
                        .uri("lb://companies"))
                .route( route -> route
                        .path("/report-ms/api/report/**")
                        .filters(filter-> filter.filter(authFilter))
                        .uri("lb://report-ms"))
                .route(route -> route
                        .path("/companies-fallback/api/company/**")
                        .uri("lb://companies-fallback"))
                .route(route -> route
                        .path("/auth-server/api/auth/**")
                        .uri("lb://auth-server"))
                .build();
    }
}
