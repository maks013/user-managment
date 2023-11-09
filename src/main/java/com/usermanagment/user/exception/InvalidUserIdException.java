package com.usermanagment.user.exception;

public class InvalidUserIdException extends RuntimeException {
    public InvalidUserIdException(){
        super("Can not reach user with that id");
    }
}
