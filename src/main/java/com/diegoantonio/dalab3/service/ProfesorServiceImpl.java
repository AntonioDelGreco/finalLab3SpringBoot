package com.diegoantonio.dalab3.service;

import com.diegoantonio.dalab3.persistence.ProfesorDAO;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfesorServiceImpl implements ProfesorService{

    @Autowired
    private ProfesorDAO profesorDAO;

    @Override
    public Integer getIdProfesor() throws NotFoundException {
        return profesorDAO.getId();
    }
}
