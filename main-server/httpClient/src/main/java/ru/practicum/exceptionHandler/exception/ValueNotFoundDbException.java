package ru.practicum.exceptionHandler.exception;

public class ValueNotFoundDbException extends RuntimeException {
    public ValueNotFoundDbException(String message) {
        super(message);
    }
}
