package com.diegoantonio.dalab3.model.dto;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MateriaDTO {

    @NotBlank(message = "El nombre de la materia no puede ser un espacio vacio.")
    @Pattern(regexp = ".*\\p{L}.*", message = "El nombre de la materia debe contener al menos una letra.")
    private String nombre;

    @NotNull(message = "El anio no puede estar vacio.")
    @Digits(integer = 4, fraction = 0, message = "El anio debe ser un numero de 4 digitos.")
    @Min(value = 2020, message = "El anio debe ser mayor o igual a 2020.")
    private Integer anio;

    @NotNull(message = "El cuatrimestre no puede estar vacio.")
    @Min(value = 1, message = "El cuatrimestre debe ser 1 o 2.")
    @Max(value = 2, message = "El cuatrimestre debe ser 1 o 2.")
    private Integer cuatrimestre;
}
