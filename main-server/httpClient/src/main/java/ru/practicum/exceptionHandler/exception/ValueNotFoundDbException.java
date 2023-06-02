package ru.practicum.exceptionHandler.exception;

import org.springframework.dao.EmptyResultDataAccessException;

public class ValueNotFoundDbException extends EmptyResultDataAccessException {
    public ValueNotFoundDbException(String message) {
        super(message, 0);
    }
}
