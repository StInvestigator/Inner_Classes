package com.project.exceptions;

public class ConsoleIsOffException extends RuntimeException {
    public ConsoleIsOffException(String message) {
        super(message);
    }
}
