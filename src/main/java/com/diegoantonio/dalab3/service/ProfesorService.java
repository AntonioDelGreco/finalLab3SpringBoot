package com.diegoantonio.dalab3.service;

import com.diegoantonio.dalab3.persistence.exceptionsCustoms.NotFoundException;

public interface ProfesorService {
    Integer getIdProfesor() throws NotFoundException;
}
