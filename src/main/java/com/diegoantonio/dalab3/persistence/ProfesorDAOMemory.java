package com.diegoantonio.dalab3.persistence;

import com.diegoantonio.dalab3.model.Profesor;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.NotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public class ProfesorDAOMemory implements ProfesorDAO{

    @Override
    public Integer getId() throws NotFoundException {
        Profesor p = new Profesor("Diego", "Antonio");
        Integer id = p.getId();
        if(id != null){
            return id;
        }
        throw new NotFoundException("No se encontraron profesores disponibles.");
    }
}
