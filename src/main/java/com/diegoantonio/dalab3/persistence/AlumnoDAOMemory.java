package com.diegoantonio.dalab3.persistence;

import com.diegoantonio.dalab3.model.Alumno;
import com.diegoantonio.dalab3.model.Asignatura;
import com.diegoantonio.dalab3.model.EstadoAsignatura;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.NotFoundException;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.SaveException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AlumnoDAOMemory implements AlumnoDAO{

    protected static final ArrayList<Alumno> repoAlumnos = new ArrayList<Alumno>();

    @Override
    public Alumno save(Alumno a) throws SaveException {
        for ( Alumno alumno : repoAlumnos ) {
            if (alumno.getDni().equals(a.getDni())){
                throw new SaveException("No fue posible guardar el alumno, por que este alumno ya existe.");
            }
        }
        repoAlumnos.add(a);
        return a;
    }

    @Override
    public Alumno findAndUpdateAlumno(Integer idAlumno, Alumno a) throws NotFoundException {
        if (repoAlumnos.size() == 0) throw new NotFoundException("No existen alumnos por lo que no sera posible actualizar a ninguno.");
        for ( Alumno alumno : repoAlumnos) {
            if (alumno.getId().equals(idAlumno)){
                alumno.setNombre(a.getNombre());
                alumno.setApellido(a.getApellido());
                alumno.setDni(a.getDni());
                return alumno;
            }
        }
        throw new NotFoundException("No fue posible actualizar a su alumno. El id de su alumno no fue encontrado.");
    }

    @Override
    public void delete(Integer idAlumno) throws NotFoundException {
        if (repoAlumnos.size() == 0) throw new NotFoundException("No existen alumnos por lo que no sera posible borrar a ninguno.");

        List<Alumno> alumnosFiltrados = repoAlumnos.stream()
                .filter(alumno -> !alumno.getId().equals(idAlumno))
                .collect(Collectors.toList());

        if (alumnosFiltrados.size() == repoAlumnos.size()) {
            throw new NotFoundException("No fue posible borrar a su alumno ya que no fue encontrada.");
        }

        repoAlumnos.clear();
        repoAlumnos.addAll(alumnosFiltrados);
    }

    @Override
    public Alumno findAndUpdateAsignatura(Integer idAlumno, Integer idAsignatura, String estado, Integer nota) throws NotFoundException {
        if (repoAlumnos.size() == 0) throw new NotFoundException("No existen alumnos por lo que no sera posible actualizar a ninguno.");
        ArrayList<Asignatura> asignaturasAlumno;
        for ( Alumno alumno : repoAlumnos ) {
            if (alumno.getId().equals(idAlumno)){
                asignaturasAlumno = alumno.getAsignaturas();
                for ( Asignatura asignatura: asignaturasAlumno ) {
                    if (asignatura.getMateriaId().equals(idAsignatura)){
                        asignatura.setEstado(EstadoAsignatura.valueOf(estado));
                        asignatura.setNota(nota);
                        return alumno;
                    }
                }
            }
        }
        throw new NotFoundException("No fue posible actualizar su estado por que no se encontro el alumno o a la asignatura asignada al alumno.");
    }
}
