package com.example.ms_estudiantes.dtos;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class AnotacionResponseDTO {
    private Long idAnotacion;
    private Long idEstudiante;
    private String tipoAnotacion;
    private String descripcionAnotacion;
    private LocalDateTime fecha;
}
