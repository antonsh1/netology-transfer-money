package ru.smartjava.exceptions;

public class TransferClosed extends RuntimeException {
    public TransferClosed(String message) {
        super(message);
    }
}
