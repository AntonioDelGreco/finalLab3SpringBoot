package com.diegoantonio.dalab3.controller;

import com.diegoantonio.dalab3.controller.annotations.ValidId;
import com.diegoantonio.dalab3.controller.annotations.ValidNombre;
import com.diegoantonio.dalab3.controller.annotations.ValidOrders;
import com.diegoantonio.dalab3.model.Materia;
import com.diegoantonio.dalab3.model.dto.MateriaDTO;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.NotFoundException;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.SaveException;
import com.diegoantonio.dalab3.service.MateriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
@RequestMapping("materia")
@Validated
public class MateriaController {

    @Autowired
    private MateriaService materiaService;

    @PostMapping
    public ResponseEntity<Materia> addMateria(@RequestBody @Valid MateriaDTO materiaDTO) throws NotFoundException, SaveException {
        return ResponseEntity.ok(materiaService.addMateria(materiaDTO));
    }

    @PutMapping("/{idMateria}")
    public ResponseEntity<Materia> updateMateria(@PathVariable @ValidId Integer idMateria, @RequestBody @Valid MateriaDTO materiaDTO) throws NotFoundException{
        return ResponseEntity.ok(materiaService.updateMateria(idMateria, materiaDTO));
    }

    @DeleteMapping("/{idMateria}")
    public ResponseEntity<String> deleteMateria(@PathVariable @ValidId Integer idMateria) throws NotFoundException {
        materiaService.deleteMateria(idMateria);
        return ResponseEntity.ok("La materia fue borrada satisfactoriamente.");
    }

    @GetMapping("materia")
    public ResponseEntity<Materia> findByName(@RequestParam @ValidNombre String nombre) throws NotFoundException {
        return ResponseEntity.ok(materiaService.findByName(nombre));
    }

    @GetMapping("materias")
    public ResponseEntity<ArrayList<Materia>> orderBy(@RequestParam @ValidOrders String order) throws NotFoundException {
        return ResponseEntity.ok(materiaService.orderBy(order));
    }
}
