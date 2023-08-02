package ru.smartjava.exceptions;

public class InvalidOperationId extends RuntimeException {
    public InvalidOperationId(String message) {
        super(message);
    }
}
