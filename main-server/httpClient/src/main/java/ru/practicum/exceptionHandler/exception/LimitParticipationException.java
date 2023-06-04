package ru.practicum.exceptionHandler.exception;

public class LimitParticipationException extends RuntimeException {
    public LimitParticipationException(String message) {
        super(message);
    }
}
