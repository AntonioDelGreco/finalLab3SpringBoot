package com.diegoantonio.dalab3.service;

import com.diegoantonio.dalab3.model.Carrera;
import com.diegoantonio.dalab3.model.dto.CarreraDTO;
import com.diegoantonio.dalab3.persistence.CarreraDAO;
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
class CarreraServiceImplTest {

    @Mock
    private CarreraDAO carreraDAOMock;

    @InjectMocks
    private CarreraServiceImpl carreraServiceImplMock;

    private CarreraDTO cDTO;
    private Carrera c;

    @BeforeEach
    public void setUp() {
        cDTO = new CarreraDTO();
        cDTO.setNombre("Agronomia");
        cDTO.setDepartamento(1);
        cDTO.setCuatrimestres(10);

        c = new Carrera();
        c.setNombre(cDTO.getNombre());
        c.setDepartamento(cDTO.getDepartamento());
        c.setCuatrimestres(cDTO.getCuatrimestres());
    }

    @Test
    public void addCarreraServiceSuccess() throws SaveException {
        Mockito.when(carreraDAOMock.save(Mockito.any(Carrera.class))).thenReturn(c);

        Carrera carreraEsperada = carreraServiceImplMock.addCarrera(cDTO);

        Assertions.assertNotNull(carreraEsperada);
        Assertions.assertEquals(c, carreraEsperada);
        Mockito.verify(carreraDAOMock, Mockito.times(1)).save(Mockito.any(Carrera.class));
    }
    @Test
    public void addCarreraServiceFailSaveException() throws SaveException {
        Mockito.when(carreraDAOMock.save(Mockito.any(Carrera.class))).thenThrow(SaveException.class);

        Assertions.assertThrows(SaveException.class, () -> carreraServiceImplMock.addCarrera(cDTO));
        Mockito.verify(carreraDAOMock, Mockito.times(1)).save(Mockito.any(Carrera.class));
    }

    @Test
    public void updateCarreraServiceSuccess() throws NotFoundException {
        Integer idCarrera = 1;

        Mockito.when(carreraDAOMock.findAndUpdate(Mockito.eq(idCarrera), Mockito.any(Carrera.class)))
                .thenReturn(c);

        Carrera carreraActualizadaEsperada = carreraServiceImplMock.updateCarrera(idCarrera, cDTO);

        Assertions.assertNotNull(carreraActualizadaEsperada);
        Assertions.assertEquals(c, carreraActualizadaEsperada);
        Mockito.verify(carreraDAOMock, Mockito.times(1))
                .findAndUpdate(Mockito.eq(idCarrera), Mockito.any(Carrera.class));
    }
    @Test
    public void updateCarreraServiceFailNotFoundException() throws NotFoundException {
        Integer idCarrera = 1;

        Mockito.when(carreraDAOMock.findAndUpdate(Mockito.eq(idCarrera), Mockito.any(Carrera.class)))
                .thenThrow(NotFoundException.class);

        Assertions.assertThrows(NotFoundException.class,
                () -> carreraServiceImplMock.updateCarrera(idCarrera, cDTO));
        Mockito.verify(carreraDAOMock, Mockito.times(1))
                .findAndUpdate(Mockito.eq(idCarrera), Mockito.any(Carrera.class));
    }

    @Test
    public void deleteCarreraServiceSuccess() throws NotFoundException {
        Integer idCarrera = 1;

        carreraServiceImplMock.deleteCarrera(idCarrera);

        Mockito.verify(carreraDAOMock, Mockito.times(1))
                .delete(Mockito.eq(idCarrera));
    }
    @Test
    public void deleteCarreraServiceFailNotFoundException() throws NotFoundException {
        Integer idCarrera = 1;

        Mockito.doThrow(NotFoundException.class).when(carreraDAOMock).delete(Mockito.eq(idCarrera));

        Assertions.assertThrows(NotFoundException.class,
                () -> carreraServiceImplMock.deleteCarrera(idCarrera));
        Mockito.verify(carreraDAOMock, Mockito.times(1))
                .delete(Mockito.eq(idCarrera));
    }
}