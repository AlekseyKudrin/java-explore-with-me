package ru.practicum.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class DateTimeValidator implements ConstraintValidator<DateTime, LocalDateTime> {

    @Override
    public void initialize(DateTime constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        return value.isAfter(LocalDateTime.now().plusHours(2));
    }
}
