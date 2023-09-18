package com.diegoantonio.dalab3.service;

import com.diegoantonio.dalab3.model.Carrera;
import com.diegoantonio.dalab3.model.dto.CarreraDTO;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.NotFoundException;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.SaveException;

public interface CarreraService {
    Carrera addCarrera(CarreraDTO carreraDTO) throws SaveException;

    Carrera updateCarrera(Integer idCarrera, CarreraDTO carreraDTO) throws NotFoundException;

    void deleteCarrera(Integer idCarrera) throws NotFoundException;
}
