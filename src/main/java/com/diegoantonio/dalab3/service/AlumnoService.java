package com.diegoantonio.dalab3.service;

import com.diegoantonio.dalab3.model.Alumno;
import com.diegoantonio.dalab3.model.dto.AlumnoDTO;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.NotFoundException;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.SaveException;

public interface AlumnoService {
    Alumno addAlumno(AlumnoDTO alumnoDTO) throws SaveException;

    Alumno updateAlumno(Integer idAlumno, AlumnoDTO alumnoDTO) throws NotFoundException;

    void deleteAlumno(Integer idAlumno) throws NotFoundException;

    Alumno updateAsignatura(Integer idAlumno, Integer idAsignatura, String estado, Integer nota) throws NotFoundException;
}
