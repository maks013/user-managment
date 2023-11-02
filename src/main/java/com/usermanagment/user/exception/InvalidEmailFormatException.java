package com.usermanagment.user.exception;

public class InvalidEmailFormatException extends RuntimeException {
    public InvalidEmailFormatException(){
        super("Format of email address is invalid");
    }
}
