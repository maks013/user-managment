package com.usermanagment.confirmationtoken.domain;

import com.usermanagment.confirmationtoken.dto.TokenDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class TokenServiceImpl implements TokenService {

    private final TokenRepository repository;

    public void saveConfirmationToken(Token token) {
        repository.save(token);
    }

    public Optional<Token> getToken(String token) {
        return repository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return repository.updateConfirmedAt(
                token, LocalDateTime.now());
    }

    public List<TokenDto> findAll() {
        return repository.findAll().stream()
                .map(Token::toDto)
                .collect(Collectors.toList());
    }

    public void deleteToken(Integer id){
        repository.deleteById(id);
    }
}
