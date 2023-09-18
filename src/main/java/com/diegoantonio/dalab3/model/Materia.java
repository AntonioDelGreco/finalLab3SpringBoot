package com.diegoantonio.dalab3.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Materia {

    private String nombre;
    private Integer anio;
    private Integer cuatrimestre;
    private Integer materiaId;
    private String codigo;
    private Integer profesorId;
    private Integer carreraId = null;
    private static Integer contador = 0;

    public Materia(){
        setMateriaId();
        setCodigo();
    }

    public void setMateriaId() {
        this.materiaId = ++contador;
    }

    public void setCodigo(){
        this.codigo = "00" + this.materiaId;
    }
}
