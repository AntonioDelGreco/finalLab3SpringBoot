package com.diegoantonio.dalab3.persistence;

import com.diegoantonio.dalab3.model.Materia;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.NotFoundException;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.SaveException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Repository
public class MateriaDAOMemory implements MateriaDAO{

    protected static final ArrayList<Materia> repoMateria = new ArrayList<Materia>();

    @Override
    public Materia save(Materia m) throws SaveException{
        for ( Materia materia : repoMateria ) {
            if (materia.getNombre().equalsIgnoreCase(m.getNombre())){
                throw new SaveException("No fue posible guardar la materia, por que esta materia ya existe.");
            }
        }
        repoMateria.add(m);
        return m;
    }

    @Override
    public Materia findAndUpdate(Integer idMateria, Materia m) throws NotFoundException {
        if (repoMateria.size() == 0) throw new NotFoundException("No existen materias actualmente, por lo que no sera posible actualizar nada.");
        for (Materia materia : repoMateria) {
            if (materia.getMateriaId().equals(idMateria)){
                materia.setNombre(m.getNombre());
                materia.setAnio(m.getAnio());
                materia.setCuatrimestre(m.getCuatrimestre());
                return materia;
            }
        }
        throw new NotFoundException("No fue posible actualizar su materia. El id de la materia no fue encontrado.");
    }

    @Override
    public void delete(Integer idMateria) throws NotFoundException {
        if (repoMateria.size() == 0) throw new NotFoundException("No existen materias actualmente, por lo que no se borrara nada.");

        List<Materia> materiasFiltradas = repoMateria.stream()
                .filter(materia -> !materia.getMateriaId().equals(idMateria))
                .collect(Collectors.toList());

        if (materiasFiltradas.size() == repoMateria.size()) {
            throw new NotFoundException("No fue posible borrar su materia ya que no fue encontrada.");
        }

        repoMateria.clear();
        repoMateria.addAll(materiasFiltradas);
    }

    @Override
    public Materia findBy(String nombre) throws NotFoundException {
        if (repoMateria.size() == 0) throw new NotFoundException("No existen materias actualmente, por lo que no se puede encontrar ningun nombre.");
        for ( Materia m : repoMateria ) {
            if (m.getNombre().contains(nombre)){
                return m;
            }
        }
        throw new NotFoundException("No se encontro una materia con ese nombre.");
    }

    @Override
    public ArrayList<Materia> order(String order) throws NotFoundException {
        if (repoMateria.size() == 0) throw new NotFoundException("No existen materias actualmente, por lo que no se podran ordenar.");
        Comparator<Materia> comparador = null;

        if(order.equals("nombre_asc")){
            comparador = Comparator.comparing(Materia::getNombre);
        } if (order.equals("nombre_desc")) {
            comparador = Comparator.comparing(Materia::getNombre).reversed();
        } if (order.equals("codigo_asc")) {
            comparador = Comparator.comparing(Materia::getCodigo);
        } if (order.equals("codigo_desc")) {
            comparador = Comparator.comparing(Materia::getCodigo).reversed();
        }

        repoMateria.sort(comparador);
        return repoMateria;
    }
}
