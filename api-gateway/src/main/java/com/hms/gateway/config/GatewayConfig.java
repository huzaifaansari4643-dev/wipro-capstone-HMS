package com.hms.gateway.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hms.gateway.filter.JwtAuthenticationFilter;



@Configuration
public class GatewayConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                // Auth service route - publicly accessible (no filter)
                .route("auth-service-route", r -> r.path("/api/auth/**")
                        .uri("lb://AUTH-SERVICE"))
                
                // Patient service route - requires authentication, allows all roles (ADMIN, PATIENT, DOCTOR)
                .route("patient-service-route", r -> r.path("/api/patients/**")
                        .filters(f -> {
                            JwtAuthenticationFilter.Config config = new JwtAuthenticationFilter.Config();
                            // Optionally set specific roles; omit for any authenticated
                            config.setRequiredRoles(List.of("ADMIN", "PATIENT", "DOCTOR"));
                            return f.filter(jwtAuthenticationFilter.apply(config));
                        })
                        .uri("lb://PATIENT-SERVICE"))
                .route("doctor-service-route", r -> r.path("/api/doctors/**")
                	    .filters(f -> {
                	        JwtAuthenticationFilter.Config config = new JwtAuthenticationFilter.Config();
                	        // Enable the multi-role config (uncomment and use setRequiredRoles)
                	        // config.setRequiredRoles(List.of("ADMIN", "DOCTOR", "PATIENT"));
                	        return f.filter(jwtAuthenticationFilter.apply(config));
                	    })
                	    .uri("lb://DOCTOR-SERVICE"))
                .build();
    }
}