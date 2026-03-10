package com.employeemanagement.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class SalaryRangeValidator implements ConstraintValidator<ValidSalaryRange, BigDecimal> {

    private BigDecimal min;
    private BigDecimal max;

    @Override
    public void initialize(ValidSalaryRange annotation) {
        this.min = BigDecimal.valueOf(annotation.min());
        this.max = BigDecimal.valueOf(annotation.max());
    }

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }

        boolean valid = value.compareTo(min) >= 0 && value.compareTo(max) <= 0;

        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Salary must be between " + min + " and " + max
            ).addConstraintViolation();
        }

        return valid;
    }
}