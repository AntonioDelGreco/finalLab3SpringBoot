package com.diegoantonio.dalab3.persistence;

import com.diegoantonio.dalab3.model.Profesor;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProfesorDAOMemoryTest {

    @Mock
    private ProfesorDAOMemory profesorDAOMemoryMock;

    private Profesor p;

    @BeforeEach
    void setUp() {
        p = new Profesor("NombreA", "ApellidoA");
    }

    @Test
    public void getIdDAOSuccess() throws NotFoundException {
        Mockito.when(profesorDAOMemoryMock.getId()).thenReturn(p.getId());

        Integer idProfesorEsperado = profesorDAOMemoryMock.getId();

        Assertions.assertNotNull(idProfesorEsperado);
        Assertions.assertEquals(p.getId(), idProfesorEsperado);
    }
    @Test
    public void getIdDAOFailNotFoundException() throws NotFoundException {
        Mockito.when(profesorDAOMemoryMock.getId()).thenThrow(NotFoundException.class);

        Assertions.assertThrows(NotFoundException.class, () -> profesorDAOMemoryMock.getId());
    }
}

