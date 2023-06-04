package ru.practicum.exceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.practicum.exceptionHandler.exception.LimitParticipationException;
import ru.practicum.exceptionHandler.exception.StatusParticipationException;
import ru.practicum.exceptionHandler.exception.ValueNotFoundDbException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handlerMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.error("Data input incorrect: {}", e.getMessage());
        return new ApiError(
                Arrays.toString(e.getStackTrace()),

                "Field: " + e.getFieldError().getField() + " Error: " + e.getMessage(),
                "Incorrectly made request.",
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                LocalDateTime.now().format(formatter));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handlerDataIntegrityViolationException(final DataIntegrityViolationException e) {
        log.error("Data input incorrect: {}", e.getMessage());
        return new ApiError(
                Arrays.toString(e.getStackTrace()),
                e.getMessage(),
                "Integrity constraint has been violated.",
                HttpStatus.CONFLICT.getReasonPhrase(),
                LocalDateTime.now().format(formatter));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handlerLimitParticipationException(final LimitParticipationException e) {
        log.error("Data input incorrect: {}", e.getMessage());
        return new ApiError(
                Arrays.toString(e.getStackTrace()),
                e.getMessage(),
                "For the requested operation the conditions are not met.",
                HttpStatus.CONFLICT.getReasonPhrase(),
                LocalDateTime.now().format(formatter));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handlerStatusParticipationException(final StatusParticipationException e) {
        log.error("Data input incorrect: {}", e.getMessage());
        return new ApiError(
                Arrays.toString(e.getStackTrace()),
                e.getMessage(),
                "For the requested operation the conditions are not met.",
                HttpStatus.CONFLICT.getReasonPhrase(),
                LocalDateTime.now().format(formatter));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handlerMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException e) {
        log.error("Data input incorrect: {}", e.getMessage());
        return new ApiError(
                Arrays.toString(e.getStackTrace()),
                e.getMessage(),
                "Incorrectly made request.",
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                LocalDateTime.now().format(formatter));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handlerConstraintViolationException(final ConstraintViolationException e) {
        log.error("Data input incorrect: {}", e.getMessage());
        return new ApiError(
                Arrays.toString(e.getStackTrace()),
                e.getMessage(),
                "Incorrectly made request.",
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                LocalDateTime.now().format(formatter));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handlerIValueNotFoundDbException(final ValueNotFoundDbException e) {
        log.error("Data input incorrect: {}", e.getMessage());
        return new ApiError(
                Arrays.toString(e.getStackTrace()),
                e.getMessage(),
                "The required object was not found.",
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                LocalDateTime.now().format(formatter));
    }

}
