package com.usermanagment.infrastructure.email.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = "email.confirmation")
public record EmailConfigurationProperties(
        String from,
        String subject
) {
}
