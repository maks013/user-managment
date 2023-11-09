package com.usermanagment.user.exception;

public class TakenUsernameException extends RuntimeException{
    public TakenUsernameException(){
        super("This username is already taken");
    }
}
