package com.pm.filemanagementsystembackend.exception;

public class NoteAlreadyExistsException extends RuntimeException {
    public NoteAlreadyExistsException(String message) {
        super(message);
    }
}
