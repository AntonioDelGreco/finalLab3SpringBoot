package com.diegoantonio.dalab3.persistence;

import com.diegoantonio.dalab3.model.Materia;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.NotFoundException;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.SaveException;

import java.util.ArrayList;

public interface MateriaDAO {

    Materia save(Materia m) throws SaveException;

    Materia findAndUpdate(Integer idMateria, Materia m) throws NotFoundException;

    void delete(Integer idMateria) throws NotFoundException;

    Materia findBy(String nombre) throws NotFoundException;

    ArrayList<Materia> order(String order) throws NotFoundException;
}
