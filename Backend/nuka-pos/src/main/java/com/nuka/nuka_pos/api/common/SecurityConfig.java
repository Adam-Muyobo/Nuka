package com.nuka.nuka_pos.api.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for testing
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/trusted/**").permitAll() // Allow public endpoints
                        .anyRequest().authenticated() // Secure all other endpoints
                )
                .httpBasic(httpBasic -> {}); // Enable Basic Authentication

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Create an in-memory user for testing
        UserDetails user = User.withUsername("admin")
                .password(passwordEncoder().encode("admin123")) // Encrypt password
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
