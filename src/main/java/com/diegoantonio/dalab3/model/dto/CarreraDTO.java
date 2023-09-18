package com.diegoantonio.dalab3.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarreraDTO {

    @NotBlank(message = "El nombre de la carrera no puede ser un espacio vacio.")
    @Pattern(regexp = ".*\\p{L}.*", message = "El nombre de la carrera debe contener al menos una letra.")
    private String nombre;

    @NotNull(message = "El departamento no puede ser un valor vacio.")
    @Min(value = 1, message = "El departamento debe ser mayor o igual a 1.")
    private Integer departamento;

    @NotNull(message = "La cantidad de cuatrimestres que tiene la carrera no puede estar vacio.")
    @Min(value = 4, message = "La cantidad de cuatrimestres que tiene la carrera debe ser de por lo menos 4.")
    private Integer cuatrimestres;

}
