package com.example.ms_estudiantes.models.entities;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity 
@Table(name = "anotaciones")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class Anotacion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAnotacion;
    private String tipoAnotacion;
    private String descripcionAnotacion;
    private LocalDateTime fecha;
    
    // 1 a N: Un estudiante puede tener muchas anotaciones (No unique)
    @Column(nullable = false) private Long idEstudiante;
}