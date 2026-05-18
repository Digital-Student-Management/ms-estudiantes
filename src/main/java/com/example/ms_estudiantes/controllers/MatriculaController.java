package com.example.ms_estudiantes.controllers;

import com.example.ms_estudiantes.dtos.MatriculaRequestDTO;
import com.example.ms_estudiantes.dtos.MatriculaResponseDTO;
import com.example.ms_estudiantes.services.MatriculaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/matriculas")
@RequiredArgsConstructor
public class MatriculaController {

    private final MatriculaService matriculaService;

    @PostMapping
    public ResponseEntity<MatriculaResponseDTO> registrar(@Valid @RequestBody MatriculaRequestDTO request) {
        return new ResponseEntity<>(matriculaService.registrar(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MatriculaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(matriculaService.listarTodas());
    }

    @GetMapping("/estudiante/{idEstudiante}")
    public ResponseEntity<List<MatriculaResponseDTO>> listarPorEstudiante(@PathVariable Long idEstudiante) {
        return ResponseEntity.ok(matriculaService.listarPorEstudiante(idEstudiante));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatriculaResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody MatriculaRequestDTO request) {
        return ResponseEntity.ok(matriculaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        matriculaService.eliminar(id);
        return ResponseEntity.ok("Registro de matrícula eliminado del historial.");
    }
}