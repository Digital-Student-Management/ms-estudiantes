package com.example.ms_estudiantes.models.entities;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity 
@Table(name = "hoja_vida_estudiante")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class HojaVida {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHojaVida;
    private LocalDateTime fechaCreacion;
    private String observacionGeneral;
    
    // 1 a 1: Solo una hoja de vida por estudiante
    @Column(nullable = false, unique = true) private Long idEstudiante;
}