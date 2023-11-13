package com.usermanagment.user.domain;

import com.usermanagment.user.dto.UserDto;
import com.usermanagment.user.dto.UserDtoWithPassword;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Setter
@Getter
@Builder
@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    public enum Role {
        ADMIN, USER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Boolean enabled;

    UserDto toDto() {
        return UserDto.builder()
                .id(id)
                .username(username)
                .email(email)
                .build();
    }

    UserDtoWithPassword toDtoWithPassword() {
        return UserDtoWithPassword.builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .enabled(enabled)
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
