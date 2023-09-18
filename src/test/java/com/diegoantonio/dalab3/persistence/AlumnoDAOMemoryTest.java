package com.diegoantonio.dalab3.persistence;

import com.diegoantonio.dalab3.model.Alumno;
import com.diegoantonio.dalab3.model.Asignatura;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.NotFoundException;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.SaveException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
class AlumnoDAOMemoryTest {

    private Alumno a;
    @Spy
    private AlumnoDAOMemory alumnoDAOMemorySpy;

    @BeforeEach
    public void setUp() {
        a = new Alumno();
        a.setNombre("Diego");
        a.setApellido("Antonio Del Greco");
        a.setDni(12345678L);
    }

    @AfterEach
    public void tearDown(){
        alumnoDAOMemorySpy.repoAlumnos.clear();

        try {
            Field contadorField = Alumno.class.getDeclaredField("contador");
            contadorField.setAccessible(true);
            contadorField.set(null, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void saveDAOSuccess () throws SaveException {
        Integer repoAlumnosEmpty = alumnoDAOMemorySpy.repoAlumnos.size();
        Alumno alumnoEsperado = alumnoDAOMemorySpy.save(a);

        Assertions.assertNotNull(alumnoEsperado);
        Assertions.assertEquals(a, alumnoEsperado);
        Assertions.assertEquals(repoAlumnosEmpty + 1, alumnoDAOMemorySpy.repoAlumnos.size());
    }
    @Test
    public void saveDAOFailSaveException () throws SaveException {
        alumnoDAOMemorySpy.repoAlumnos.add(a);
        Integer alumnoYaAgregado = alumnoDAOMemorySpy.repoAlumnos.size();

        Assertions.assertThrows(SaveException.class, () -> alumnoDAOMemorySpy.save(a));
        Assertions.assertEquals(alumnoYaAgregado, alumnoDAOMemorySpy.repoAlumnos.size());
    }

    @Test
    public void findAndUpdateAlumnoDAOSuccess() throws NotFoundException, SaveException {
        Integer idAlumno = 1;
        Alumno alumnoAntesUpdate = alumnoDAOMemorySpy.save(a);
        String nombreAlumnoAntesUpdate = alumnoAntesUpdate.getNombre();
        String apellidoAlumnoAntesUpdate = alumnoAntesUpdate.getApellido();
        Long dniAlumnoAntesUpdate = alumnoAntesUpdate.getDni();
        Integer alumnoYaAgregado = alumnoDAOMemorySpy.repoAlumnos.size();
        Alumno alumnoUpdate = new Alumno();
        alumnoUpdate.setNombre("Jose");
        alumnoUpdate.setApellido("Enrique");
        alumnoUpdate.setDni(88888888L);
        Alumno alumnoDespuesUpdate = alumnoDAOMemorySpy.findAndUpdateAlumno(idAlumno, alumnoUpdate);

        Assertions.assertNotNull(alumnoDespuesUpdate);
        Assertions.assertFalse(alumnoDespuesUpdate.getNombre().contains(nombreAlumnoAntesUpdate));
        Assertions.assertFalse(alumnoDespuesUpdate.getApellido().contains(apellidoAlumnoAntesUpdate));
        Assertions.assertFalse(alumnoDespuesUpdate.getDni().equals(dniAlumnoAntesUpdate));
        Assertions.assertEquals(alumnoUpdate.getNombre(), alumnoDespuesUpdate.getNombre());
        Assertions.assertEquals(alumnoUpdate.getApellido(), alumnoDespuesUpdate.getApellido());
        Assertions.assertEquals(alumnoUpdate.getDni(), alumnoDespuesUpdate.getDni());
        Assertions.assertEquals(alumnoYaAgregado, alumnoDAOMemorySpy.repoAlumnos.size());
    }
    @Test
    public void findAndUpdateAlumnoDAOFailNotFoundExceptionRepoSize() throws NotFoundException {
        Integer repoAlumnoEmpty = alumnoDAOMemorySpy.repoAlumnos.size();
        Integer idAlumno = 1;

        Assertions.assertThrows(NotFoundException.class,
                () -> alumnoDAOMemorySpy.findAndUpdateAlumno(idAlumno, a));
        Assertions.assertEquals(repoAlumnoEmpty, alumnoDAOMemorySpy.repoAlumnos.size());
    }
    @Test
    public void findAndUpdateAlumnoDAOFailNotFoundExceptionNotId() throws NotFoundException {
        alumnoDAOMemorySpy.repoAlumnos.add(a);
        Integer idAlumno = 5700;
        Alumno alumnoUpdate = new Alumno();
        alumnoUpdate.setNombre("Jose");
        alumnoUpdate.setApellido("Enrique");
        alumnoUpdate.setDni(88888888L);

        Assertions.assertThrows(NotFoundException.class,
                () -> alumnoDAOMemorySpy.findAndUpdateAlumno(idAlumno, alumnoUpdate));
        Assertions.assertFalse(alumnoDAOMemorySpy.repoAlumnos.contains(alumnoUpdate));
    }

    @Test
    public void deleteDAOSuccess() throws NotFoundException {
        Integer idAlumno = 1;
        alumnoDAOMemorySpy.repoAlumnos.add(a);
        alumnoDAOMemorySpy.delete(idAlumno);

        Assertions.assertFalse(alumnoDAOMemorySpy.repoAlumnos.contains(a));
    }
    @Test
    public void deleteDAOFailNotFoundExceptionRepoSize() {
        Integer repoAlumnosEmpty = alumnoDAOMemorySpy.repoAlumnos.size();
        Integer idAlumno = 1;

        Assertions.assertThrows(NotFoundException.class,
                () -> alumnoDAOMemorySpy.delete(idAlumno));
        Assertions.assertEquals(repoAlumnosEmpty, alumnoDAOMemorySpy.repoAlumnos.size());
    }
    @Test
    public void deleteDAOFailNotFoundExceptionNotId() {
        alumnoDAOMemorySpy.repoAlumnos.add(a);
        Integer repoAlumnosUnAlumno = alumnoDAOMemorySpy.repoAlumnos.size();
        Integer idAlumno = 5700;

        Assertions.assertThrows(NotFoundException.class,
                () -> alumnoDAOMemorySpy.delete(idAlumno));
        Assertions.assertEquals(repoAlumnosUnAlumno, alumnoDAOMemorySpy.repoAlumnos.size());
    }
    @Test
    public void findAndUpdateAsignaturaDAOSuccess() throws NotFoundException {
        alumnoDAOMemorySpy.repoAlumnos.add(a);

        Integer idAlumno = 1;
        Integer idAsignatura = 1;
        String estado = "APROBADA";
        Integer nota = 8;

        Alumno alumnoEsperado = alumnoDAOMemorySpy
                .findAndUpdateAsignatura(
                        idAlumno, idAsignatura,
                        estado, nota);
        Asignatura asignaturaAlumnoEsperado = alumnoEsperado.getAsignaturas().get(idAsignatura);

        Assertions.assertNotNull(alumnoEsperado);
        Assertions.assertNotEquals("APROBADA", asignaturaAlumnoEsperado.getEstado());
        Assertions.assertNotEquals(8, asignaturaAlumnoEsperado.getNota());
    }
    @Test
    public void findAndUpdateAsignaturaDAOFailNotFoundExceptionRepoSize() throws NotFoundException {
        Integer repoAlumnosEmpty = alumnoDAOMemorySpy.repoAlumnos.size();
        Integer idAlumno = 1;
        Integer idAsignatura = 1;
        String estado = "APROBADA";
        Integer nota = 10;

        Assertions.assertThrows(NotFoundException.class,
                () -> alumnoDAOMemorySpy
                        .findAndUpdateAsignatura(
                                idAlumno, idAsignatura,
                                estado, nota));
        Assertions.assertEquals(repoAlumnosEmpty, alumnoDAOMemorySpy.repoAlumnos.size());
    }
    @Test
    public void findAndUpdateAsignaturaDAOFailNotIdAlumnoAsignatura() throws NotFoundException {
        alumnoDAOMemorySpy.repoAlumnos.add(a);

        Integer idAlumno = 5700;
        Integer idAsignatura = 5700;
        String estado = "APROBADA";
        Integer nota = 9;

        Assertions.assertThrows(NotFoundException.class,
                () -> alumnoDAOMemorySpy
                        .findAndUpdateAsignatura(
                                idAlumno, idAsignatura,
                                estado, nota));
        Assertions.assertTrue(alumnoDAOMemorySpy.repoAlumnos.contains(a));
    }
}