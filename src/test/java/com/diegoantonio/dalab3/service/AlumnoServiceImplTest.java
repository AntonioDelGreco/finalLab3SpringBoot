package com.diegoantonio.dalab3.service;

import com.diegoantonio.dalab3.model.Alumno;
import com.diegoantonio.dalab3.model.dto.AlumnoDTO;
import com.diegoantonio.dalab3.persistence.AlumnoDAO;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.NotFoundException;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.SaveException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AlumnoServiceImplTest {

    @Mock
    private AlumnoDAO alumnoDAOMock;

    @InjectMocks
    private AlumnoServiceImpl alumnoServiceImplMock;

    private AlumnoDTO aDTO;
    private Alumno a;

    @BeforeEach
    void setUp() {
        aDTO = new AlumnoDTO();
        aDTO.setNombre("Julio");
        aDTO.setApellido("Gonzales");
        aDTO.setDni(12345678L);

        a = new Alumno();
        a.setNombre(aDTO.getNombre());
        a.setApellido(aDTO.getApellido());
        a.setDni(aDTO.getDni());
    }

    @Test
    public void addAlumnoServiceSuccess() throws SaveException {
        Mockito.when(alumnoDAOMock.save(Mockito.any(Alumno.class))).thenReturn(a);

        Alumno alumnoEsperado = alumnoServiceImplMock.addAlumno(aDTO);

        Assertions.assertNotNull(alumnoEsperado);
        Assertions.assertEquals(a, alumnoEsperado);
        Mockito.verify(alumnoDAOMock, Mockito.times(1))
                .save(Mockito.any(Alumno.class));
    }
    @Test
    public void addAlumnoServiceFailSaveException() throws SaveException {
        Mockito.when(alumnoDAOMock.save(Mockito.any(Alumno.class))).thenThrow(SaveException.class);

        Assertions.assertThrows(SaveException.class, () -> alumnoServiceImplMock.addAlumno(aDTO));
        Mockito.verify(alumnoDAOMock, Mockito.times(1))
                .save(Mockito.any(Alumno.class));
    }

    @Test
    public void updateAlumnoServiceSuccess() throws NotFoundException {
        Integer idAlumno = 1;

        Mockito.when(alumnoDAOMock.findAndUpdateAlumno(Mockito.eq(idAlumno), Mockito.any(Alumno.class)))
                .thenReturn(a);

        Alumno alumnoActualizadoEsperado = alumnoServiceImplMock.updateAlumno(idAlumno, aDTO);

        Assertions.assertNotNull(alumnoActualizadoEsperado);
        Assertions.assertEquals(a, alumnoActualizadoEsperado);
        Mockito.verify(alumnoDAOMock, Mockito.times(1))
                .findAndUpdateAlumno(Mockito.eq(idAlumno), Mockito.any(Alumno.class));
    }
    @Test
    public void updateAlumnoServiceFailNotFoundException() throws NotFoundException {
        Integer idAlumno = 1;

        Mockito.when(alumnoDAOMock.findAndUpdateAlumno(Mockito.eq(idAlumno), Mockito.any(Alumno.class)))
                .thenThrow(NotFoundException.class);

        Assertions.assertThrows(NotFoundException.class,
                () -> alumnoServiceImplMock.updateAlumno(idAlumno, aDTO));
        Mockito.verify(alumnoDAOMock, Mockito.times(1))
                .findAndUpdateAlumno(Mockito.eq(idAlumno), Mockito.any(Alumno.class));
    }

    @Test
    public void deleteAlumnoServiceSuccess() throws NotFoundException {
        Integer idAlumno = 1;

        alumnoServiceImplMock.deleteAlumno(idAlumno);

        Mockito.verify(alumnoDAOMock, Mockito.times(1))
                .delete(Mockito.eq(idAlumno));
    }
    @Test
    public void deleteAlumnoServiceFailNotFoundException() throws NotFoundException {
        Integer idAlumno = 1;

        Mockito.doThrow(NotFoundException.class).when(alumnoDAOMock).delete(Mockito.eq(idAlumno));

        Assertions.assertThrows(NotFoundException.class,
                () -> alumnoServiceImplMock.deleteAlumno(idAlumno));
        Mockito.verify(alumnoDAOMock, Mockito.times(1))
                .delete(Mockito.eq(idAlumno));
    }

    @Test
    public void updateAsignaturaServiceSuccess()throws NotFoundException {
        Integer idAlumno = 1;
        Integer idAsignatura = 2;
        String estado = "APROBADA";
        Integer nota = 8;

        Mockito.when(alumnoDAOMock.findAndUpdateAsignatura(
                Mockito.eq(idAlumno), Mockito.eq(idAsignatura),
                Mockito.eq(estado), Mockito.eq(nota)
        )).thenReturn(a);

        Alumno alumnoCambioAsignaEsperado = alumnoServiceImplMock.updateAsignatura(
                idAlumno, idAsignatura, estado, nota
        );

        Assertions.assertNotNull(alumnoCambioAsignaEsperado);
        Assertions.assertEquals(a, alumnoCambioAsignaEsperado);
        Mockito.verify(alumnoDAOMock, Mockito.times(1))
                .findAndUpdateAsignatura(
                Mockito.eq(idAlumno), Mockito.eq(idAsignatura),
                Mockito.eq(estado), Mockito.eq(nota)
        );
    }
    @Test
    public void updateAsignaturaServiceFailNotFoundException()throws NotFoundException {
        Integer idAlumno = 1;
        Integer idAsignatura = 2;
        String estado = "APROBADA";
        Integer nota = 8;

        Mockito.when(alumnoDAOMock.findAndUpdateAsignatura(
                Mockito.eq(idAlumno), Mockito.eq(idAsignatura),
                Mockito.eq(estado), Mockito.eq(nota)
        )).thenThrow(NotFoundException.class);

        Assertions.assertThrows(NotFoundException.class,
                () -> alumnoServiceImplMock.updateAsignatura(idAlumno, idAsignatura, estado, nota));
        Mockito.verify(alumnoDAOMock, Mockito.times(1))
                .findAndUpdateAsignatura(
                        Mockito.eq(idAlumno), Mockito.eq(idAsignatura),
                        Mockito.eq(estado), Mockito.eq(nota)
                );
    }
}
