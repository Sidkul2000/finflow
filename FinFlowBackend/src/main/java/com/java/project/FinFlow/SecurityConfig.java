package com.java.project.FinFlow;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Disable CSRF for simplicity (not recommended for production)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/public/**").permitAll()  // Allow public access to certain APIs
                        .requestMatchers("/login", "/register/**").permitAll()
                        .requestMatchers("/users/budget", "/{userId}/budget").authenticated()
                        .requestMatchers("/category/**").permitAll() // NOTE: For dev purpose, should be changed to authenticated later
                        .requestMatchers(("/record/**")).permitAll() // NOTE: For dev purpose, should be changed to authenicated later
                        .requestMatchers("/users/public/register", "users/create").permitAll()
                        .anyRequest().authenticated()  // Require authentication for any other requests
                )
                .httpBasic(httpBasic -> {
                });  // Use basic HTTP authentication


        return http.build();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}

