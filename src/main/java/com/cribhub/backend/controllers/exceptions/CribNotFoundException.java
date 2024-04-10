package com.cribhub.backend.controllers.exceptions;

public class CribNotFoundException extends Throwable {
    public CribNotFoundException(long cribId) {
        super("Crib with id " + cribId + " not found");
    }
}
