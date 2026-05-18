package com.example.ms_estudiantes.controllers;

import com.example.ms_estudiantes.dtos.AnotacionRequestDTO;
import com.example.ms_estudiantes.dtos.AnotacionResponseDTO;
import com.example.ms_estudiantes.models.entities.HojaVida;
import com.example.ms_estudiantes.services.AnotacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bitacora")
@RequiredArgsConstructor
public class AnotacionController {

    private final AnotacionService anotacionService;

    @PostMapping("/anotaciones")
    public ResponseEntity<AnotacionResponseDTO> crear(@Valid @RequestBody AnotacionRequestDTO request) {
        return ResponseEntity.ok(anotacionService.crearAnotacion(request));
    }

    @GetMapping("/anotaciones/estudiante/{idEstudiante}")
    public ResponseEntity<List<AnotacionResponseDTO>> listarPorEstudiante(@PathVariable Long idEstudiante) {
        return ResponseEntity.ok(anotacionService.listarPorEstudiante(idEstudiante));
    }

    @PutMapping("/anotaciones/{id}")
    public ResponseEntity<AnotacionResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody AnotacionRequestDTO request) {
        return ResponseEntity.ok(anotacionService.actualizarAnotacion(id, request));
    }

    @DeleteMapping("/anotaciones/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        anotacionService.eliminarAnotacion(id);
        return ResponseEntity.ok("Anotación removida con éxito.");
    }

    @GetMapping("/hojavida/{idEstudiante}")
    public ResponseEntity<HojaVida> verHojaVida(@PathVariable Long idEstudiante) {
        return ResponseEntity.ok(anotacionService.obtenerHojaVida(idEstudiante));
    }

    @PutMapping("/hojavida/{idEstudiante}")
    public ResponseEntity<HojaVida> modificarHojaVida(@PathVariable Long idEstudiante, @RequestBody String observacion) {
        return ResponseEntity.ok(anotacionService.actualizarHojaVida(idEstudiante, observacion));
    }
}
