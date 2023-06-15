package ru.practicum.exceptionHandler.exception;


public class ValidateFieldException extends RuntimeException {
    public ValidateFieldException(String message) {
        super(message);
    }
}
