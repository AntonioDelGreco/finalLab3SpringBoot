package com.diegoantonio.dalab3.controller.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {})
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Max(value = 10, message = "La nota no puede ser mayor a 10.")
@Min(value = 0, message = "La nota no puede ser menor que 0.")
@NotNull(message = "No puede quedar como un campo vacio.")
public @interface ValidNota {
    String message() default "Parametro invalido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
