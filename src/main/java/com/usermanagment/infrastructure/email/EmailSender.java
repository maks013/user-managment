package com.usermanagment.infrastructure.email;

public interface EmailSender {
    void send(String to, String email);
}
