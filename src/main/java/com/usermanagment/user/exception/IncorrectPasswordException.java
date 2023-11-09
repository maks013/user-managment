package com.usermanagment.user.exception;

public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException(){
        super("Password is incorrect");
    }
}
