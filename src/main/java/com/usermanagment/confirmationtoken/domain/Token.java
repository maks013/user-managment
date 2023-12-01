package com.usermanagment.confirmationtoken.domain;

import com.usermanagment.confirmationtoken.dto.TokenDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="confirmation_tokens")
class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;
    @JoinColumn(
            nullable = false,
            name = "user_id")
    private Integer userId;

    TokenDto toDto(){
        return TokenDto.builder()
                .id(id)
                .token(token)
                .createdAt(createdAt)
                .expiresAt(expiresAt)
                .confirmedAt(confirmedAt)
                .userId(userId)
                .build();
    }
}
