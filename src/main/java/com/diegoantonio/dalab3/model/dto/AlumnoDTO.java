package com.diegoantonio.dalab3.model.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlumnoDTO {

    @NotNull(message = "El nombre del alumno no puede estar vacio.")
    @Pattern(regexp = ".*\\p{L}.*", message = "El nombre del alumno debe contener al menos una letra.")
    private String nombre;

    @NotNull(message = "El apellido del alumno no puede estar vacio.")
    @Pattern(regexp = ".*\\p{L}.*", message = "El apellido del alumno debe contener al menos una letra.")
    private String apellido;

    @NotNull(message = "El DNI del alumno no puede estar vacio.")
    @Min(value = 10000000, message = "El DNI debe contener 8 digitos solamente.")
    @Max(value = 99999999, message = "El DNI no puede tener mas de 8 digitos.")
    private Long dni;
}
