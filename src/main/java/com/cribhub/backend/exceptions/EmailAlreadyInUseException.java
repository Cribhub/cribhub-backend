package com.cribhub.backend.exceptions;

public class EmailAlreadyInUseException extends Throwable {
    public EmailAlreadyInUseException(String email) {
        super(email + " is already in use.");
    }

}
