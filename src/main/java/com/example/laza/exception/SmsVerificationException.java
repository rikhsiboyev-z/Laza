package com.example.laza.exception;

public class SmsVerificationException extends RuntimeException{
    public SmsVerificationException(String message) {
        super(message);
    }

    public SmsVerificationException(String message, Throwable cause) {
        super(message, cause);
    }
}
