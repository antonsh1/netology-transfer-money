package ru.smartjava.exceptions;

public class InvalidCode extends RuntimeException {
    public InvalidCode(String message) {
        super(message);
    }
}
