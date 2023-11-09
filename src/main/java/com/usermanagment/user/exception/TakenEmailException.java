package com.usermanagment.user.exception;

public class TakenEmailException extends RuntimeException{
    public TakenEmailException(){
        super("This email is already taken");
    }
}
