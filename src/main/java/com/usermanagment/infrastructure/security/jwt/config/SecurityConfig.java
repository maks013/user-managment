package com.usermanagment.infrastructure.security.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain applicationSecurity(HttpSecurity httpSecurity) throws Exception {
       httpSecurity
               .cors(AbstractHttpConfigurer::disable)
               .csrf(AbstractHttpConfigurer::disable)
               .securityMatcher("/**")
               .sessionManagement(sessionManagementConfigurer
                       -> sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .formLogin(AbstractHttpConfigurer::disable)
               .authorizeHttpRequests((requests) -> requests
                       .requestMatchers("/users/**").permitAll()
                       .anyRequest().authenticated()
               );
       return httpSecurity.build();
    }

}

