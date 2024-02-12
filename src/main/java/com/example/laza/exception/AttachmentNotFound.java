package com.example.laza.exception;

public class AttachmentNotFound extends RuntimeException {
    public AttachmentNotFound(String message) {
        super(message);
    }
}
