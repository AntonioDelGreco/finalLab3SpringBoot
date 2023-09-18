package com.diegoantonio.dalab3.service;

import com.diegoantonio.dalab3.model.Materia;
import com.diegoantonio.dalab3.model.dto.MateriaDTO;
import com.diegoantonio.dalab3.persistence.MateriaDAO;
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

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class MateriaServiceImplTest {

    @Mock
    private ProfesorService profesorServiceMock;

    @Mock
    private MateriaDAO materiaDAOMock;

    @InjectMocks
    private MateriaServiceImpl materiaServiceImplMock;

    private MateriaDTO mDTO;
    private Materia m;

    @BeforeEach
    public void setUp() {
        mDTO = new MateriaDTO();
        mDTO.setNombre("matematica");
        mDTO.setAnio(2023);
        mDTO.setCuatrimestre(1);

        m = new Materia();
        m.setNombre(mDTO.getNombre());
        m.setAnio(mDTO.getAnio());
        m.setCuatrimestre(mDTO.getCuatrimestre());
    }

    @Test
    public void addMateriaServiceSuccess() throws NotFoundException, SaveException {
        Mockito.when(profesorServiceMock.getIdProfesor()).thenReturn(1);
        m.setProfesorId(1);

        Mockito.when(materiaDAOMock.save(any(Materia.class))).thenReturn(m);

        Materia materiaEsperada = materiaServiceImplMock.addMateria(mDTO);

        Assertions.assertNotNull(materiaEsperada);
        Assertions.assertEquals(m, materiaEsperada);
        Mockito.verify(profesorServiceMock, Mockito.times(1)).getIdProfesor();
        Mockito.verify(materiaDAOMock, Mockito.times(1)).save(any(Materia.class));
    }
    @Test
    public void addMateriaServiceFailThrowsNotFoundException() throws NotFoundException, SaveException {
        Mockito.when(profesorServiceMock.getIdProfesor()).thenThrow(NotFoundException.class);

        Assertions.assertThrows(NotFoundException.class, () -> materiaServiceImplMock.addMateria(mDTO));
        Mockito.verify(profesorServiceMock, Mockito.times(1)).getIdProfesor();
        Mockito.verify(materiaDAOMock, Mockito.times(0)).save(any(Materia.class));
    }
    @Test
    public void addMateriaServiceFailThrowsSaveException() throws NotFoundException, SaveException {
        Mockito.when(profesorServiceMock.getIdProfesor()).thenReturn(1);
        m.setProfesorId(1);

        Mockito.when(materiaDAOMock.save(any(Materia.class))).thenThrow(SaveException.class);

        Assertions.assertThrows(SaveException.class, () -> materiaServiceImplMock.addMateria(mDTO));
        Mockito.verify(profesorServiceMock, Mockito.times(1)).getIdProfesor();
        Mockito.verify(materiaDAOMock, Mockito.times(1)).save(any(Materia.class));
    }

    @Test
    public void updateMateriaServiceSuccess() throws NotFoundException {
        Integer idMateria = 1;
        Mockito.when(materiaDAOMock.findAndUpdate(Mockito.eq(idMateria),any(Materia.class))).thenReturn(m);

        Materia materiaEsperada = materiaServiceImplMock.updateMateria(idMateria, mDTO);

        Assertions.assertNotNull(materiaEsperada);
        Assertions.assertEquals(m, materiaEsperada);
        Mockito.verify(materiaDAOMock, Mockito.times(1))
                .findAndUpdate(Mockito.eq(idMateria), any(Materia.class));
    }
    @Test
    public void updateMateriaServiceFailNotFoundException() throws NotFoundException {
        Integer idMateria = 1;
        Mockito.when(materiaDAOMock.findAndUpdate(Mockito.eq(idMateria),any(Materia.class)))
                .thenThrow(NotFoundException.class);

        Assertions.assertThrows(NotFoundException.class,
                () -> materiaServiceImplMock.updateMateria(idMateria, mDTO));

        Mockito.verify(materiaDAOMock, Mockito.times(1))
                .findAndUpdate(Mockito.eq(idMateria), any(Materia.class));
    }

    @Test
    public void deleteMateriaServiceSuccess() throws NotFoundException {
        Integer idMateria = 1;

        materiaServiceImplMock.deleteMateria(idMateria);

        Mockito.verify(materiaDAOMock, Mockito.times(1)).delete(Mockito.eq(idMateria));
    }
    @Test
    public void deleteMateriaServiceFailNotFoundException() throws NotFoundException {
        Integer idMateria = 1;

        Mockito.doThrow(NotFoundException.class)
                .when(materiaDAOMock).delete(Mockito.eq(idMateria));

        Assertions.assertThrows(NotFoundException.class,
                () -> materiaServiceImplMock.deleteMateria(idMateria));

        Mockito.verify(materiaDAOMock, Mockito.times(1)).delete(Mockito.eq(idMateria));
    }

    @Test
    public void findByNameServiceSuccess() throws NotFoundException{
        String nombreParam = "matematica";

        Mockito.when(materiaDAOMock.findBy(nombreParam)).thenReturn(m);

        Materia materiaEsperadaSegunNombre = materiaServiceImplMock.findByName(nombreParam);

        Assertions.assertNotNull(materiaEsperadaSegunNombre);
        Assertions.assertEquals(m.getNombre(), materiaEsperadaSegunNombre.getNombre());
        Mockito.verify(materiaDAOMock, Mockito.times(1)).findBy(nombreParam);
    }
    @Test
    public void findByNameServiceFailNotFoundException() throws NotFoundException{
        String nombreParam = "matematica";

        Mockito.when(materiaDAOMock.findBy(nombreParam)).thenThrow(NotFoundException.class);

        Assertions.assertThrows(NotFoundException.class, () -> materiaServiceImplMock.findByName(nombreParam));
        Mockito.verify(materiaDAOMock, Mockito.times(1)).findBy(nombreParam);
    }

    @Test
    public void orderByServiceSuccess() throws NotFoundException{
        String orderParam = "nombre_asc";
        ArrayList<Materia> materias = new ArrayList<Materia>();

        Materia m2 = new Materia();
        m2.setNombre("programacion 3");
        m2.setAnio(2023);
        m2.setCuatrimestre(1);

        Materia m3 = new Materia();
        m3.setNombre("laboratorio 3");
        m3.setAnio(2023);
        m3.setCuatrimestre(2);

        materias.add(m2);
        materias.add(m3);

        Mockito.when(materiaDAOMock.order(orderParam)).thenReturn(materias);

        ArrayList<Materia> listadoMateriasEsperadas = materiaServiceImplMock.orderBy(orderParam);

        Assertions.assertNotNull(listadoMateriasEsperadas);
        Assertions.assertEquals(materias, listadoMateriasEsperadas);
        Mockito.verify(materiaDAOMock, Mockito.times(1)).order(orderParam);
    }
    @Test
    public void orderByServiceFailNotFoundException() throws NotFoundException{
        String orderParam = "nombre_asc";

        Mockito.when(materiaDAOMock.order(orderParam)).thenThrow(NotFoundException.class);

        Assertions.assertThrows(NotFoundException.class, () -> materiaServiceImplMock.orderBy(orderParam));
        Mockito.verify(materiaDAOMock, Mockito.times(1)).order(orderParam);
    }
}