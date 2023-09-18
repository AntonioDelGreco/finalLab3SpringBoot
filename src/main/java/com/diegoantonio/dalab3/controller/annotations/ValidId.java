package com.diegoantonio.dalab3.controller.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Min;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {})
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Min(value = 1, message = "El valor en la URL debe ser mayor a cero.")
public @interface ValidId {
    String message() default "Parametro invalido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
