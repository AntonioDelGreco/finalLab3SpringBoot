package com.diegoantonio.dalab3.controller;

import com.diegoantonio.dalab3.controller.annotations.ValidId;
import com.diegoantonio.dalab3.model.Carrera;
import com.diegoantonio.dalab3.model.dto.CarreraDTO;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.NotFoundException;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.SaveException;
import com.diegoantonio.dalab3.service.CarreraService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("carrera")
@Validated
public class CarreraController {

    @Autowired
    private CarreraService carreraService;

    @PostMapping
    public ResponseEntity<Carrera> addCarrera(@RequestBody @Valid CarreraDTO carreraDTO) throws SaveException {
        return ResponseEntity.ok(carreraService.addCarrera(carreraDTO));
    }

    @PutMapping("/{idCarrera}")
    public ResponseEntity<Carrera> updateCarrera(@PathVariable @ValidId Integer idCarrera, @RequestBody @Valid CarreraDTO carreraDTO) throws NotFoundException {
        return ResponseEntity.ok(carreraService.updateCarrera(idCarrera, carreraDTO));
    }

    @DeleteMapping("/{idCarrera}")
    public ResponseEntity<String> deleteCarrera(@PathVariable @ValidId Integer idCarrera) throws NotFoundException {
        carreraService.deleteCarrera(idCarrera);
        return ResponseEntity.ok("La carrera fue borrada satisfactoriamente.");
    }
}
