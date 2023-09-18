package com.diegoantonio.dalab3.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;

@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
public class Asignatura {
    private Integer materiaId;
    private EstadoAsignatura estado;
    private Integer nota;

    public Asignatura() {}

    public Asignatura(Integer materiaId) {
        this.materiaId = materiaId;
        this.estado = EstadoAsignatura.NO_CURSADA;
    }

    public Integer getMateriaId() {
        return materiaId;
    }

    public void setMateriaId(Integer materiaId) {
        this.materiaId = materiaId;
    }

    public EstadoAsignatura getEstado() {
        return estado;
    }

    public void setEstado(EstadoAsignatura estado) {
        this.estado = estado;
    }

    public Integer getNota() {
        if (this.estado == EstadoAsignatura.NO_CURSADA || this.estado == EstadoAsignatura.CURSADA){
            return null;
        }
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }
}
