package com.diegoantonio.dalab3.persistence;

import com.diegoantonio.dalab3.model.Materia;
import com.diegoantonio.dalab3.model.Profesor;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.NotFoundException;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.SaveException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class MateriaDAOMemoryTest {

    @Spy
    private MateriaDAOMemory materiaDAOMemorySpy;
    private Materia m;
    private Profesor p;
    private Materia materia1;
    private Materia materia2;
    private Materia materia3;

    @BeforeEach
    public void setUp() {
        p = new Profesor("Diego", "Antonio Del Greco");
        m = new Materia();
        m.setNombre("Bases de datos");
        m.setAnio(2024);
        m.setCuatrimestre(1);
        m.setProfesorId(p.getId());

        materia1 = new Materia();
        materia1.setNombre("NombreA");
        materia1.setAnio(2024);
        materia1.setCuatrimestre(1);
        materia1.setProfesorId(p.getId());
        materia2 = new Materia();
        materia2.setNombre("NombreB");
        materia2.setAnio(2024);
        materia2.setCuatrimestre(2);
        materia2.setProfesorId(p.getId());
        materia3 = new Materia();
        materia3.setNombre("NombreC");
        materia3.setAnio(2023);
        materia3.setCuatrimestre(1);
        materia3.setProfesorId(p.getId());
    }

    @AfterEach
    public void tearDown(){
        materiaDAOMemorySpy.repoMateria.clear();

        try {
            Field contadorField = Materia.class.getDeclaredField("contador");
            contadorField.setAccessible(true);
            contadorField.set(null, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void saveDAOSuccess () throws SaveException {
        Integer repoMateriaEmpty = materiaDAOMemorySpy.repoMateria.size();
        Materia materiaEsperada = materiaDAOMemorySpy.save(m);

        Assertions.assertNotNull(materiaEsperada);
        Assertions.assertEquals(m, materiaEsperada);
        Assertions.assertEquals(repoMateriaEmpty + 1, materiaDAOMemorySpy.repoMateria.size());
    }
    @Test
    public void saveDAOFailSaveException () throws SaveException {
        materiaDAOMemorySpy.repoMateria.add(m);
        Integer materiaYaAgregada = materiaDAOMemorySpy.repoMateria.size();

        Assertions.assertThrows(SaveException.class, () -> materiaDAOMemorySpy.save(m));
        Assertions.assertEquals(materiaYaAgregada, materiaDAOMemorySpy.repoMateria.size());
    }

    @Test
    public void findAndUpdateDAOSuccess() throws NotFoundException, SaveException {
        Integer idMateria = 1;
        Materia materiaAntesUpdate = materiaDAOMemorySpy.save(m);
        String nombreMateriaAntesUpdate = materiaAntesUpdate.getNombre();
        Integer materiaYaAgregada = materiaDAOMemorySpy.repoMateria.size();
        Materia materiaUpdate = new Materia();
        materiaUpdate.setNombre("Inteligencia artificial");
        materiaUpdate.setAnio(2024);
        materiaUpdate.setCuatrimestre(2);
        materiaUpdate.setProfesorId(p.getId());
        Materia materiaDespuesUpdate = materiaDAOMemorySpy.findAndUpdate(idMateria, materiaUpdate);

        Assertions.assertNotNull(materiaDespuesUpdate);
        Assertions.assertFalse(materiaDespuesUpdate.getNombre().contains(nombreMateriaAntesUpdate));
        Assertions.assertEquals(materiaUpdate.getNombre(), materiaDespuesUpdate.getNombre());
        Assertions.assertEquals(materiaUpdate.getAnio(), materiaDespuesUpdate.getAnio());
        Assertions.assertEquals(materiaUpdate.getCuatrimestre(), materiaDespuesUpdate.getCuatrimestre());
        Assertions.assertEquals(materiaYaAgregada, materiaDAOMemorySpy.repoMateria.size());
    }
    @Test
    public void findAndUpdateDAOFailNotFoundExceptionNotId() throws NotFoundException {
        materiaDAOMemorySpy.repoMateria.add(m);
        Integer idMateria = 450;
        Materia materiaUpdate = new Materia();
        materiaUpdate.setNombre("Inteligencia artificial");
        materiaUpdate.setAnio(2024);
        materiaUpdate.setCuatrimestre(2);
        materiaUpdate.setProfesorId(p.getId());

        Assertions.assertThrows(NotFoundException.class,
                () -> materiaDAOMemorySpy.findAndUpdate(idMateria, materiaUpdate));
        Assertions.assertFalse(materiaDAOMemorySpy.repoMateria.contains(materiaUpdate));
    }
    @Test
    public void findAndUpdateDAOFailNotFoundExceptionRepoSize() throws NotFoundException {
        Integer repoMateriaEmpty = materiaDAOMemorySpy.repoMateria.size();
        Integer idMateria = 1;

        Assertions.assertThrows(NotFoundException.class,
                () -> materiaDAOMemorySpy.findAndUpdate(idMateria, m));
        Assertions.assertEquals(repoMateriaEmpty, materiaDAOMemorySpy.repoMateria.size());
    }

    @Test
    public void deleteDAOSuccess() throws NotFoundException {
        Integer idMateria = 1;
        materiaDAOMemorySpy.repoMateria.add(m);
        Integer repoMateriaUnaMateria = materiaDAOMemorySpy.repoMateria.size();
        materiaDAOMemorySpy.delete(idMateria);

        Assertions.assertEquals(repoMateriaUnaMateria - 1, materiaDAOMemorySpy.repoMateria.size());
    }
    @Test
    public void deleteDAOFailNotFoundExceptionRepoSize() {
        Integer repoMateriaEmpty = materiaDAOMemorySpy.repoMateria.size();
        Integer idMateria = 1;

        Assertions.assertThrows(NotFoundException.class,
                () -> materiaDAOMemorySpy.delete(idMateria));
        Assertions.assertEquals(repoMateriaEmpty, materiaDAOMemorySpy.repoMateria.size());

    }
    @Test
    public void deleteDAOFailNotFoundExceptionNotId() {
        materiaDAOMemorySpy.repoMateria.add(m);
        Integer repoMateriaUnaMateria = materiaDAOMemorySpy.repoMateria.size();
        Integer idMateria = 450;

        Assertions.assertThrows(NotFoundException.class,
                () -> materiaDAOMemorySpy.delete(idMateria));
        Assertions.assertEquals(repoMateriaUnaMateria, materiaDAOMemorySpy.repoMateria.size());
    }

    @Test
    public void findByDAOSuccess() throws NotFoundException {
        String nombreMateria = "Bases de datos";
        Materia m2 = new Materia();
        m2.setNombre("Inteligencia artificial");
        m2.setAnio(2024);
        m2.setCuatrimestre(2);
        m2.setProfesorId(p.getId());
        materiaDAOMemorySpy.repoMateria.add(m);
        materiaDAOMemorySpy.repoMateria.add(m2);

        Materia materiaEsperada = materiaDAOMemorySpy.findBy(nombreMateria);

        Assertions.assertTrue(materiaDAOMemorySpy.repoMateria.contains(m));
        Assertions.assertTrue(materiaDAOMemorySpy.repoMateria.contains(m2));
        Assertions.assertNotNull(materiaEsperada);
        Assertions.assertEquals(m, materiaEsperada);
    }
    @Test
    public void findByDAOFailNotFoundExceptionRepoSize() {
        Integer repoMateriaEmpty = materiaDAOMemorySpy.repoMateria.size();
        String nombreMateria = "Bases de datos";

        Assertions.assertThrows(NotFoundException.class,
                () -> materiaDAOMemorySpy.findBy(nombreMateria));
        Assertions.assertEquals(repoMateriaEmpty, materiaDAOMemorySpy.repoMateria.size());
    }
    @Test
    public void findByDAOFailNotFoundExceptionNotName() {
        materiaDAOMemorySpy.repoMateria.add(m);
        String nombreMateria = "matematica";

        Assertions.assertThrows(NotFoundException.class,
                () -> materiaDAOMemorySpy.findBy(nombreMateria));
        Assertions.assertTrue(materiaDAOMemorySpy.repoMateria.contains(m));
        Assertions.assertEquals(1, materiaDAOMemorySpy.repoMateria.size());
    }

    @Test
    public void orderDAOSuccessNombreAsc() throws NotFoundException {
        Materia[] ordenArregloBuscado = { materia1, materia2, materia3 };
        materiaDAOMemorySpy.repoMateria.add(materia1);
        materiaDAOMemorySpy.repoMateria.add(materia3);
        materiaDAOMemorySpy.repoMateria.add(materia2);
        String order = "nombre_asc";

        ArrayList<Materia> ordenArregloEsperado = materiaDAOMemorySpy.order(order);

        Assertions.assertNotNull(ordenArregloEsperado);
        Assertions.assertEquals(Arrays.asList(ordenArregloBuscado), ordenArregloEsperado);
    }
    @Test
    public void orderDAOSuccessNombreDesc() throws NotFoundException {
        Materia[] ordenArregloBuscado = { materia3, materia2, materia1 };
        materiaDAOMemorySpy.repoMateria.add(materia1);
        materiaDAOMemorySpy.repoMateria.add(materia3);
        materiaDAOMemorySpy.repoMateria.add(materia2);
        String order = "nombre_desc";

        ArrayList<Materia> ordenArregloEsperado = materiaDAOMemorySpy.order(order);

        Assertions.assertNotNull(ordenArregloEsperado);
        Assertions.assertEquals(Arrays.asList(ordenArregloBuscado), ordenArregloEsperado);
    }
    @Test
    public void orderDAOSuccessCodigoAsc() throws NotFoundException {
        Materia[] ordenArregloBuscado = { materia1, materia2, materia3 };
        materiaDAOMemorySpy.repoMateria.add(materia1);
        materiaDAOMemorySpy.repoMateria.add(materia3);
        materiaDAOMemorySpy.repoMateria.add(materia2);
        String order = "codigo_asc";

        ArrayList<Materia> ordenArregloEsperado = materiaDAOMemorySpy.order(order);

        Assertions.assertNotNull(ordenArregloEsperado);
        Assertions.assertEquals(Arrays.asList(ordenArregloBuscado), ordenArregloEsperado);
    }
    @Test
    public void orderDAOSuccessCodigoDesc() throws NotFoundException {
        Materia[] ordenArregloBuscado = { materia3, materia2, materia1 };
        materiaDAOMemorySpy.repoMateria.add(materia1);
        materiaDAOMemorySpy.repoMateria.add(materia3);
        materiaDAOMemorySpy.repoMateria.add(materia2);
        String order = "codigo_desc";

        ArrayList<Materia> ordenArregloEsperado = materiaDAOMemorySpy.order(order);

        Assertions.assertNotNull(ordenArregloEsperado);
        Assertions.assertEquals(Arrays.asList(ordenArregloBuscado), ordenArregloEsperado);
    }
    @Test
    public void orderDAOFailNotFoundExceptionRepoSize() {
        Integer repoMateriaEmpty = materiaDAOMemorySpy.repoMateria.size();
        String order = "codigo_desc";

        Assertions.assertThrows(NotFoundException.class, ()  -> materiaDAOMemorySpy.order(order));
        Assertions.assertEquals(repoMateriaEmpty, materiaDAOMemorySpy.repoMateria.size());
    }
}