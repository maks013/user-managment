package com.usermanagment.confirmationtoken.domain;

import com.usermanagment.confirmationtoken.dto.TokenDto;
import com.usermanagment.user.domain.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="confirmationToken")
class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "user_id")
    private User user;

    TokenDto toDto(){
        return TokenDto.builder()
                .id(id)
                .token(token)
                .createdAt(createdAt)
                .expiresAt(expiresAt)
                .confirmedAt(confirmedAt)
                .build();
    }
}
