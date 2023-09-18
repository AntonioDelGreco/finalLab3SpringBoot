package com.diegoantonio.dalab3.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Carrera {
    private String nombre;
    private String codigo;
    private Integer departamento;
    private Integer cuatrimestres;
    private ArrayList<Integer> materiasId;
    private static Integer contador = 0;

    public Carrera() {
        setCodigo();
    }

    public void setCodigo() {
            ++contador;
            this.codigo = "000" + contador;
    }
}
