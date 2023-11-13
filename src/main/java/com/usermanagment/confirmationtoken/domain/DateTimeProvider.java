package com.usermanagment.confirmationtoken.domain;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DateTimeProvider {
    public LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }
}
