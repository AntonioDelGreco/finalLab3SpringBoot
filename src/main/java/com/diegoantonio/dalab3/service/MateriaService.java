package com.diegoantonio.dalab3.service;

import com.diegoantonio.dalab3.model.Materia;
import com.diegoantonio.dalab3.model.dto.MateriaDTO;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.NotFoundException;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.SaveException;

import java.util.ArrayList;

public interface MateriaService {

    Materia addMateria(MateriaDTO materiaDTO) throws NotFoundException, SaveException;

    Materia updateMateria(Integer idMateria, MateriaDTO materiaDTO) throws NotFoundException;

    void deleteMateria(Integer idMateria) throws NotFoundException;

    Materia findByName(String nombre) throws NotFoundException;

    ArrayList<Materia> orderBy(String order) throws NotFoundException;
}
