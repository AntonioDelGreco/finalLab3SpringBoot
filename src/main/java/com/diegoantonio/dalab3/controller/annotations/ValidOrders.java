package com.diegoantonio.dalab3.controller.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {})
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@NotBlank(message = "No puede dejar el campo vacio.")
@Pattern(regexp = "^(nombre_asc|nombre_desc|codigo_asc|codigo_desc)$", message = "Los unicos valores permitidos son: nombre_asc | nombre_desc | codigo_asc | codigo_desc")
public @interface ValidOrders {
    String message() default "Parametro invalido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
