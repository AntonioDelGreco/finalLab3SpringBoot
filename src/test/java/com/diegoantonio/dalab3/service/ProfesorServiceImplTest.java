package com.diegoantonio.dalab3.service;

import com.diegoantonio.dalab3.model.Profesor;
import com.diegoantonio.dalab3.persistence.ProfesorDAO;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProfesorServiceImplTest {

    @Mock
    private ProfesorDAO profesorDAOMock;

    @InjectMocks
    private ProfesorServiceImpl profesorServiceImplMock;

    @Test
    public void getIdProfesorSuccess() throws NotFoundException {
        Profesor profesor = new Profesor("ProfesorNombreA", "ProfesorApellidoA");
        Mockito.when(profesorDAOMock.getId()).thenReturn(1);
        Integer idProfeEsperado = profesorServiceImplMock.getIdProfesor();
        Assertions.assertNotNull(idProfeEsperado);
        Assertions.assertEquals(profesor.getId(), idProfeEsperado);
        Mockito.verify(profesorDAOMock, Mockito.times(1)).getId();
    }

    @Test
    public void getIdProfesorFail() throws NotFoundException {
        Mockito.when(profesorDAOMock.getId()).thenThrow(NotFoundException.class);
        Assertions.assertThrows(NotFoundException.class, () -> profesorServiceImplMock.getIdProfesor());
        Mockito.verify(profesorDAOMock, Mockito.times(1)).getId();
    }
}
