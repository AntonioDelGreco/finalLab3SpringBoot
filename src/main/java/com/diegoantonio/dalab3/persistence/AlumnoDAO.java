package com.diegoantonio.dalab3.persistence;

import com.diegoantonio.dalab3.model.Alumno;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.NotFoundException;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.SaveException;

public interface AlumnoDAO {
    Alumno save(Alumno a) throws SaveException;

    Alumno findAndUpdateAlumno(Integer idAlumno, Alumno a) throws NotFoundException;

    void delete(Integer idAlumno) throws NotFoundException;

    Alumno findAndUpdateAsignatura(Integer idAlumno, Integer idAsignatura, String estado, Integer nota) throws NotFoundException;
}
