package com.example.ms_estudiantes.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MatriculaRequestDTO {
    @NotNull(message = "El ID del estudiante es obligatorio")
    private Long idEstudiante;
    
    @NotNull(message = "El ID del directivo es obligatorio")
    private Long idDirectivo;
    
    @NotBlank(message = "El estado de la matrícula no puede estar vacío")
    private String estadoMatricula;
}