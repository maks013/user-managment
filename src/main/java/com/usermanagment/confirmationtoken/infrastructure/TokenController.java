package com.usermanagment.confirmationtoken.infrastructure;

import antlr.Token;
import com.usermanagment.confirmationtoken.domain.TokenFacade;
import com.usermanagment.confirmationtoken.dto.TokenDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/confirmation-token")
@AllArgsConstructor
public class TokenController {

    private final TokenFacade tokenFacade;

    @GetMapping( path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return tokenFacade.confirmToken(token);
    }

    @GetMapping
    public ResponseEntity<List<TokenDto>> readAll(){
        return ResponseEntity.ok(tokenFacade.readAllTokens());
    }

}
