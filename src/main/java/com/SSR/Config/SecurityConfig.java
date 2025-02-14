package com.SSR.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class SecurityConfig {
    private JWTFilter jwtFilter;

    public SecurityConfig(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity
    ) throws Exception {
        httpSecurity.csrf().disable().cors().disable();
        httpSecurity.addFilterBefore(jwtFilter, AuthorizationFilter.class);
        //httpSecurity.authorizeHttpRequests().anyRequest().permitAll();
        httpSecurity.authorizeHttpRequests()
                .requestMatchers("/api/a1/appController/up","/api/a1/appController/in")
                .permitAll()
                .anyRequest()
                .authenticated();
        return httpSecurity.build();
    }
}
