package com.example.ms_estudiantes.controllers;
import com.example.ms_estudiantes.dtos.FichaAntecedentesDTO;
import com.example.ms_estudiantes.models.entities.AntecedentesApoderado;
import com.example.ms_estudiantes.services.AntecedentesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/antecedentes")
@RequiredArgsConstructor
public class AntecedentesController {

    private final AntecedentesService antecedentesService;

    @PutMapping("/estudiante/{idEstudiante}")
    public ResponseEntity<String> guardarOActualizarEstudiante(@PathVariable Long idEstudiante, @RequestBody FichaAntecedentesDTO dto) {
        antecedentesService.guardarOActualizarAntecedentesEstudiante(idEstudiante, dto);
        return ResponseEntity.ok("Ficha clínica y académica actualizada.");
    }

    @GetMapping("/estudiante/{idEstudiante}")
    public ResponseEntity<FichaAntecedentesDTO> obtenerEstudiante(@PathVariable Long idEstudiante) {
        return ResponseEntity.ok(antecedentesService.obtenerAntecedentesEstudiante(idEstudiante));
    }

    @DeleteMapping("/estudiante/{idEstudiante}")
    public ResponseEntity<String> eliminarEstudiante(@PathVariable Long idEstudiante) {
        antecedentesService.eliminarAntecedentesEstudiante(idEstudiante);
        return ResponseEntity.ok("Antecedentes eliminados.");
    }

    @PutMapping("/apoderado/{idApoderado}")
    public ResponseEntity<AntecedentesApoderado> guardarApoderado(
            @PathVariable Long idApoderado, 
            @RequestParam String infoSocio, 
            @RequestParam String nivelEd) {
        return ResponseEntity.ok(antecedentesService.guardarOActualizarApoderado(idApoderado, infoSocio, nivelEd));
    }

    @GetMapping("/apoderado/{idApoderado}")
    public ResponseEntity<AntecedentesApoderado> obtenerApoderado(@PathVariable Long idApoderado) {
        return ResponseEntity.ok(antecedentesService.obtenerAntecedentesApoderado(idApoderado));
    }
}