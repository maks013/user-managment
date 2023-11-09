package com.usermanagment.confirmationtoken.exception;

public class TokenExpiredException extends RuntimeException{
    public TokenExpiredException(){
        super("Token expired");
    }
}
