package com.diegoantonio.dalab3.controller.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {})
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@NotNull(message = "El nombre no puede ser un campo vacio.")
@Pattern(regexp = ".*\\p{L}.*", message = "Debe contener al menos una letra.")
public @interface ValidNombre {
    String message() default "Parametro invalido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
