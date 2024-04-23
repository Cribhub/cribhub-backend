package com.cribhub.backend.exceptions;

public class CribNameAlreadyTakenException extends Throwable {
    public CribNameAlreadyTakenException(String cribName) {
        super(cribName + " is already taken. Please choose another name.");
    }

}
