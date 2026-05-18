package com.example.ms_estudiantes.dtos;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class MatriculaResponseDTO {
    private Long idMatricula;
    private Long idEstudiante;
    private Long idDirectivo;
    private String estadoMatricula;
    private LocalDateTime fechaInscripcion;
}