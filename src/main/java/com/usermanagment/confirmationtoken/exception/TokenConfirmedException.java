package com.usermanagment.confirmationtoken.exception;

public class TokenConfirmedException extends RuntimeException {
    public TokenConfirmedException(){
        super("Token already confirmed");
    }
}
