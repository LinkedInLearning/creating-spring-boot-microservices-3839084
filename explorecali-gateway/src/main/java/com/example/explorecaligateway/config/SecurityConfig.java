package com.example.explorecaligateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class    SecurityConfig {
   @Bean
   public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
       return http
               .authorizeExchange(exchanges -> exchanges
                       .pathMatchers(HttpMethod.POST, "/**").authenticated()
                       .pathMatchers(HttpMethod.PUT, "/**").authenticated()
                       .pathMatchers(HttpMethod.PATCH, "/**").authenticated()
                       .pathMatchers(HttpMethod.DELETE, "/**").authenticated()
                      .pathMatchers(HttpMethod.GET, "/**").permitAll()
               )
              .httpBasic(withDefaults())
               .formLogin(withDefaults())
               .csrf(csrf -> csrf.disable()).build();

   }
}
