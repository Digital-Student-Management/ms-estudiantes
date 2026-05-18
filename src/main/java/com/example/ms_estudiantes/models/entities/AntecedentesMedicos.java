package com.example.ms_estudiantes.models.entities;
import jakarta.persistence.*;
import lombok.*;

@Entity 
@Table(name = "antecedentes_medicos")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class AntecedentesMedicos {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAntMedico;
    private String alergias;
    private String condicionesCronicas;
    private String medicamentosRecetados;
    @Column(nullable = false, unique = true) private Long idEstudiante; // 1 a 1
}