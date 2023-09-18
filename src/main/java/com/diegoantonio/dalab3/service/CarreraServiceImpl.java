package com.diegoantonio.dalab3.service;

import com.diegoantonio.dalab3.model.Carrera;

import com.diegoantonio.dalab3.model.dto.CarreraDTO;
import com.diegoantonio.dalab3.persistence.CarreraDAO;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.NotFoundException;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.SaveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarreraServiceImpl implements CarreraService{

    @Autowired
    private CarreraDAO carreraDAO;

    @Override
    public Carrera addCarrera(CarreraDTO carreraDTO) throws SaveException {
        Carrera c = carreraDTOToCarrera(carreraDTO);
        return carreraDAO.save(c);
    }

    @Override
    public Carrera updateCarrera(Integer idCarrera, CarreraDTO carreraDTO) throws  NotFoundException {
        Carrera c = carreraDTOToCarrera(carreraDTO);
        return carreraDAO.findAndUpdate(idCarrera, c);
    }

    @Override
    public void deleteCarrera(Integer idCarrera) throws NotFoundException {
        carreraDAO.delete(idCarrera);
    }

    private Carrera carreraDTOToCarrera(CarreraDTO carreraDTO){
        Carrera c = new Carrera();
        c.setNombre(carreraDTO.getNombre());
        c.setDepartamento(carreraDTO.getDepartamento());
        c.setCuatrimestres(carreraDTO.getCuatrimestres());
        return c;
    }
}
