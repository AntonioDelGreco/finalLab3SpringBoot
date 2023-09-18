package com.diegoantonio.dalab3.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@EqualsAndHashCode
public class Alumno {
    private Integer id;
    private String nombre;
    private String apellido;
    private Long dni;
    private ArrayList<Asignatura> asignaturas;
    private static Integer contador = 0;

    public Alumno(){
        setId();
        asignaturas = new ArrayList<Asignatura>();
        asignaturas.add(new Asignatura(1));
        asignaturas.add(new Asignatura(2));
        asignaturas.add(new Asignatura(3));
        asignaturas.add(new Asignatura(4));
    }

    public void setId() {
        this.id = ++contador;
    }

}
