package com.diegoantonio.dalab3.model;

import lombok.Data;

@Data
public class Profesor {
    private Integer id;
    private String nombre;
    private String apellido;
    private static Integer contador = 0;

    public Profesor(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
        setId();
    }

    public void setId() {
        this.id = ++contador;
    }
}
