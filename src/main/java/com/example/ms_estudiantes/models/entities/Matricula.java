package com.example.ms_estudiantes.models.entities;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name = "matricula")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Matricula {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMatricula;
    private LocalDateTime fechaInscripcion;
    private String estadoMatricula; 
    
    // 1 a N: No lleva unique, un estudiante puede tener varias matrículas históricas
    @Column(nullable = false) private Long idEstudiante; 
    // 1 a N: Un directivo hace muchas matrículas
    @Column(nullable = false) private Long idDirectivo; 
}