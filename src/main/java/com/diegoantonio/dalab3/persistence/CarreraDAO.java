package com.diegoantonio.dalab3.persistence;

import com.diegoantonio.dalab3.model.Carrera;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.NotFoundException;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.SaveException;

public interface CarreraDAO {
    Carrera save(Carrera c) throws SaveException;

    Carrera findAndUpdate(Integer idCarrera, Carrera c) throws NotFoundException;

    void delete(Integer idCarrera) throws NotFoundException;
}
