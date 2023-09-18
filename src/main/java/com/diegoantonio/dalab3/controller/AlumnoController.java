package com.diegoantonio.dalab3.controller;

import com.diegoantonio.dalab3.controller.annotations.ValidAsignatura;
import com.diegoantonio.dalab3.controller.annotations.ValidNota;
import com.diegoantonio.dalab3.controller.annotations.ValidId;
import com.diegoantonio.dalab3.model.Alumno;
import com.diegoantonio.dalab3.model.dto.AlumnoDTO;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.NotFoundException;
import com.diegoantonio.dalab3.persistence.exceptionsCustoms.SaveException;
import com.diegoantonio.dalab3.service.AlumnoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("alumno")
@Validated
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @PostMapping
    public ResponseEntity<Alumno> addAlumno(@RequestBody @Valid AlumnoDTO alumnoDTO) throws SaveException {
        return ResponseEntity.ok(alumnoService.addAlumno(alumnoDTO));
    }

    @PutMapping("/{idAlumno}")
    public ResponseEntity<Alumno> updateAlumno(
            @PathVariable @ValidId Integer idAlumno,
            @RequestBody @Valid AlumnoDTO alumnoDTO
    ) throws NotFoundException {
        return ResponseEntity.ok(alumnoService.updateAlumno(idAlumno, alumnoDTO));
    }

    @DeleteMapping("/{idAlumno}")
    public ResponseEntity<String> deleteAlumno(@PathVariable @ValidId Integer idAlumno) throws NotFoundException {
        alumnoService.deleteAlumno(idAlumno);
        return ResponseEntity.ok("Alumno borrado satisfactoriamente.");
    }

    @PutMapping("/{idAlumno}/asignatura/{idAsignatura}")
    public ResponseEntity<Alumno> updateAsignatura(
            @PathVariable @ValidId Integer idAlumno,
            @PathVariable @ValidId Integer idAsignatura,
            @RequestParam @ValidAsignatura String estado,
            @RequestParam @ValidNota Integer nota
    ) throws NotFoundException {
        return ResponseEntity.ok(alumnoService.updateAsignatura(idAlumno, idAsignatura, estado, nota));
    }
}
