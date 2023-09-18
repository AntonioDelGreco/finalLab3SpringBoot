package com.diegoantonio.dalab3.service;

import com.diegoantonio.dalab3.model.Alumno;
import com.diegoantonio.dalab3.model.dto.AlumnoDTO;
import com.diegoantonio.dalab3.persistence.AlumnoDAO;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.NotFoundException;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.SaveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlumnoServiceImpl implements AlumnoService{

    @Autowired
    private AlumnoDAO dao;

    @Override
    public Alumno addAlumno(AlumnoDTO alumnoDTO) throws SaveException {
        Alumno a = alumnoDTOToAlumno(alumnoDTO);
        return dao.save(a);
    }

    @Override
    public Alumno updateAlumno(Integer idAlumno, AlumnoDTO alumnoDTO) throws NotFoundException {
        Alumno a = alumnoDTOToAlumno(alumnoDTO);
        return dao.findAndUpdateAlumno(idAlumno, a);
    }

    @Override
    public void deleteAlumno(Integer idAlumno) throws NotFoundException {
        dao.delete(idAlumno);
    }

    @Override
    public Alumno updateAsignatura(Integer idAlumno, Integer idAsignatura, String estado, Integer nota) throws NotFoundException {
        return dao.findAndUpdateAsignatura(idAlumno, idAsignatura, estado, nota);
    }

    private Alumno alumnoDTOToAlumno(AlumnoDTO alumnoDTO){
        Alumno a = new Alumno();
        a.setNombre(alumnoDTO.getNombre());
        a.setApellido(alumnoDTO.getApellido());
        a.setDni(alumnoDTO.getDni());
        return a;
    }
}
