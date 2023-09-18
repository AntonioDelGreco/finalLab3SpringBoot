package com.diegoantonio.dalab3.service;

import com.diegoantonio.dalab3.model.Materia;
import com.diegoantonio.dalab3.model.dto.MateriaDTO;
import com.diegoantonio.dalab3.persistence.MateriaDAO;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.NotFoundException;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.SaveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MateriaServiceImpl implements MateriaService{

    @Autowired
    private MateriaDAO dao;
    @Autowired
    private ProfesorService profesorService;

    @Override
    public Materia addMateria(MateriaDTO materiaDTO) throws NotFoundException, SaveException {
        Materia m = materiaDTOToMateria(materiaDTO);
        m.setProfesorId(profesorService.getIdProfesor());
        return dao.save(m);
    }

    @Override
    public Materia updateMateria(Integer idMateria, MateriaDTO materiaDTO) throws NotFoundException {
        Materia m = materiaDTOToMateria(materiaDTO);
        return dao.findAndUpdate(idMateria, m);
    }

    @Override
    public void deleteMateria(Integer idMateria) throws NotFoundException {
        dao.delete(idMateria);
    }

    @Override
    public Materia findByName(String nombre) throws NotFoundException {
        return dao.findBy(nombre);
    }

    @Override
    public ArrayList<Materia> orderBy(String order) throws NotFoundException {
        return dao.order(order);
    }


    private Materia materiaDTOToMateria(MateriaDTO materiaDTO){
        Materia m = new Materia();
        m.setNombre(materiaDTO.getNombre());
        m.setAnio(materiaDTO.getAnio());
        m.setCuatrimestre(materiaDTO.getCuatrimestre());
        return m;
    }
}
