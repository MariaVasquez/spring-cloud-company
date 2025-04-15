package com.debugideas.gateway.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.factory.SpringCloudCircuitBreakerFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Set;

@Configuration
public class GatewayBeans {

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
}
