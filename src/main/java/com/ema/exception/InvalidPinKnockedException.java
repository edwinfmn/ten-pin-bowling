package com.ema.exception;

public class InvalidPinKnockedException extends Exception {

    public InvalidPinKnockedException(String errorMessage) {
        super(errorMessage);
    }
    public InvalidPinKnockedException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
