package com.employeemanagement.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SalaryRangeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSalaryRange {

    String message() default "Salary must be between {min} and {max}";

    double min() default 0;

    double max() default 10_000_000;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
