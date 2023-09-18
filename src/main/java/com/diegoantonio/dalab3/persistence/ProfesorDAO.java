package com.diegoantonio.dalab3.persistence;

import com.diegoantonio.dalab3.persistence.exceptionsCustoms.NotFoundException;

public interface ProfesorDAO {
    Integer getId() throws NotFoundException;
}
