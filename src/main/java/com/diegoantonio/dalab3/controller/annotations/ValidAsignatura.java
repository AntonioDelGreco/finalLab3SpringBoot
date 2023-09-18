package com.diegoantonio.dalab3.controller.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {})
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Pattern(regexp = "^(NO_CURSADA|CURSADA|APROBADA)$", message = "En el estado solo puede colocar NO_CURSADA | CURSADA | APROBADA")
public @interface ValidAsignatura {
    String message() default "Parametro invalido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
