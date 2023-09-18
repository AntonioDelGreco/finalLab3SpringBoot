package com.diegoantonio.dalab3.persistence;

import com.diegoantonio.dalab3.model.Carrera;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.NotFoundException;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.SaveException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

@ExtendWith(MockitoExtension.class)
class CarreraDAOMemoryTest {

    @Spy
    private CarreraDAOMemory carreraDAOMemorySpy;
    private Carrera c;

    @BeforeEach
    public void setUp() {
        c = new Carrera();
        c.setNombre("Tecnicatura Universitaria en Programacion");
        c.setDepartamento(1);
        c.setCuatrimestres(10);
    }

    @AfterEach
    public void tearDown(){
        carreraDAOMemorySpy.repoCarrera.clear();

        try {
            Field contadorField = Carrera.class.getDeclaredField("contador");
            contadorField.setAccessible(true);
            contadorField.set(null, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void saveDAOSuccess() throws SaveException {
        Integer repoCarreraEmpty = carreraDAOMemorySpy.repoCarrera.size();
        Carrera carreraEsperada = carreraDAOMemorySpy.save(c);

        Assertions.assertNotNull(carreraEsperada);
        Assertions.assertEquals(c, carreraEsperada);
        Assertions.assertEquals(repoCarreraEmpty + 1, carreraDAOMemorySpy.repoCarrera.size());
    }
    @Test
    public void saveDAOFailSaveException() {
        carreraDAOMemorySpy.repoCarrera.add(c);
        Integer carreraYaAgregada = carreraDAOMemorySpy.repoCarrera.size();

        Assertions.assertThrows(SaveException.class, () -> carreraDAOMemorySpy.save(c));
        Assertions.assertEquals(carreraYaAgregada, carreraDAOMemorySpy.repoCarrera.size());
    }

    @Test
    public void findAndUpdateDAOSuccess() throws SaveException, NotFoundException {
        Integer idCarrera = 1;
        Carrera carreraAntesUpdate = carreraDAOMemorySpy.save(c);
        String nombreCarreraAntesUpdate = carreraAntesUpdate.getNombre();
        Integer carreraYaAgregada = carreraDAOMemorySpy.repoCarrera.size();
        Carrera carreraUpdate = new Carrera();
        carreraUpdate.setNombre("Ingenieria Electrica");
        carreraUpdate.setDepartamento(1);
        carreraUpdate.setCuatrimestres(7);
        Carrera carreraDespuesUpdate = carreraDAOMemorySpy.findAndUpdate(idCarrera, carreraUpdate);

        Assertions.assertNotNull(carreraDespuesUpdate);
        Assertions.assertFalse(carreraDespuesUpdate.getNombre().contains(nombreCarreraAntesUpdate));
        Assertions.assertEquals(carreraUpdate.getNombre(), carreraDespuesUpdate.getNombre());
        Assertions.assertEquals(carreraUpdate.getDepartamento(), carreraDespuesUpdate.getDepartamento());
        Assertions.assertEquals(carreraUpdate.getCuatrimestres(), carreraDespuesUpdate.getCuatrimestres());
        Assertions.assertEquals(carreraYaAgregada, carreraDAOMemorySpy.repoCarrera.size());
    }
    @Test
    public void findAndUpdateDAOFailNotFoundExceptionRepoSize() {
        Integer repoCarreraEmpty = carreraDAOMemorySpy.repoCarrera.size();
        Integer idCarrera = 1;

        Assertions.assertThrows(NotFoundException.class,
                () -> carreraDAOMemorySpy.findAndUpdate(idCarrera, c));
        Assertions.assertEquals(repoCarreraEmpty, carreraDAOMemorySpy.repoCarrera.size());
    }
    @Test
    public void findAndUpdateDAOFailNotFoundExceptionNotId() {
        carreraDAOMemorySpy.repoCarrera.add(c);
        Integer idCarrera = 2000;
        Carrera carreraUpdate = new Carrera();
        carreraUpdate.setNombre("Ingenieria Electrica");
        carreraUpdate.setDepartamento(1);
        carreraUpdate.setCuatrimestres(7);

        Assertions.assertThrows(NotFoundException.class,
                () -> carreraDAOMemorySpy.findAndUpdate(idCarrera, carreraUpdate));
        Assertions.assertFalse(carreraDAOMemorySpy.repoCarrera.contains(carreraUpdate));
    }

    @Test
    public void deleteDAOSuccess() throws NotFoundException {
        Integer idCarrera = 1;
        carreraDAOMemorySpy.repoCarrera.add(c);
        Integer repoCarreraUnaCarrera = carreraDAOMemorySpy.repoCarrera.size();
        carreraDAOMemorySpy.delete(idCarrera);

        Assertions.assertEquals(repoCarreraUnaCarrera - 1, carreraDAOMemorySpy.repoCarrera.size());
    }
    @Test
    public void deleteDAOFailNotFoundExceptionRepoSize() {
        Integer repoCarreraEmpty = carreraDAOMemorySpy.repoCarrera.size();
        Integer idCarrera = 1;

        Assertions.assertThrows(NotFoundException.class,
                () -> carreraDAOMemorySpy.delete(idCarrera));
        Assertions.assertEquals(repoCarreraEmpty, carreraDAOMemorySpy.repoCarrera.size());

    }
    @Test
    public void deleteDAOFailNotFoundExceptionNotId() {
        carreraDAOMemorySpy.repoCarrera.add(c);
        Integer repoCarreraUnaCarrera = carreraDAOMemorySpy.repoCarrera.size();
        Integer idCarrera = 2000;

        Assertions.assertThrows(NotFoundException.class,
                () -> carreraDAOMemorySpy.delete(idCarrera));
        Assertions.assertEquals(repoCarreraUnaCarrera,carreraDAOMemorySpy.repoCarrera.size());
    }
}