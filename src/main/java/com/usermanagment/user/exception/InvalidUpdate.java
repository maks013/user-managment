package com.usermanagment.user.exception;

public class InvalidUpdate extends RuntimeException {
    public InvalidUpdate(){
        super("Can not update user data");
    }
}
