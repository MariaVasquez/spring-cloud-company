package com.debugideas.gateway.beans;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class GatewayBeans {

    @Bean
    @Profile(value = "eureka-off")
    public RouteLocator routeLocatorEurekaOff(RouteLocatorBuilder routeLocatorBuilder){
        return routeLocatorBuilder.routes()
                .route(route->
                    route.path("/companies/api/company/**")
                            .uri("http://localhost:8081")
                )
                .route(route->
                        route.path("/report-ms/api/report/**")
                                .uri("http://localhost:7070")
                )
                .build();
    }

    @Bean
    @Profile(value = "eureka-on")
    public RouteLocator routeLocatorEurekaOn(RouteLocatorBuilder routeLocatorBuilder){
        return routeLocatorBuilder.routes()
                .route(route -> route
                        .path("/companies/api/company/**")
                        .uri("lb://companies")
                )
                .route(route -> route
                        .path("/report-ms/api/report/**")
                        .uri("lb://report-ms")
                )
                .build();
    }
}
