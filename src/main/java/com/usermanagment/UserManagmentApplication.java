package com.usermanagment;

import com.usermanagment.infrastructure.jwt.configuration.properties.JwtConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(JwtConfigurationProperties.class)
@SpringBootApplication
public class UserManagmentApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserManagmentApplication.class, args);
    }
}
