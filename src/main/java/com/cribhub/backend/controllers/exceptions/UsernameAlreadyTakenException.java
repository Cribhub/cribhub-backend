package com.cribhub.backend.controllers.exceptions;

public class UsernameAlreadyTakenException extends Throwable {
    public UsernameAlreadyTakenException(String userName) {
        super(userName + " is already taken. Please choose another username.");
    }

}
