package com.example.ms_estudiantes.dtos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AnotacionRequestDTO {
    @NotNull(message = "El ID del estudiante es obligatorio")
    private Long idEstudiante;
    
    @NotBlank(message = "El tipo de anotación (POSITIVA/NEGATIVA) es obligatorio")
    private String tipoAnotacion;
    
    @NotBlank(message = "La descripción de la anotación es obligatoria")
    private String descripcionAnotacion;
}