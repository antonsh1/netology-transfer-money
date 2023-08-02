package ru.smartjava.exceptions;

public class InvalidTransactionId extends RuntimeException {
    public InvalidTransactionId(String message) {
        super(message);
    }
}
