package com.usermanagment.user.exception;

public class UserNotEnabledException extends RuntimeException {
    public UserNotEnabledException(){
        super("User not enabled");
    }
}
