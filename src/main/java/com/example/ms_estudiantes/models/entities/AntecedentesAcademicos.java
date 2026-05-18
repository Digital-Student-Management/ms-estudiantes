package com.example.ms_estudiantes.models.entities;
import jakarta.persistence.*;
import lombok.*;

@Entity 
@Table(name = "antecedentes_academicos")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class AntecedentesAcademicos {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAntAcademico;
    private String colegioProcedencia;
    private String historialConductualAnterior;
    private Integer anioProcedencia;
    private Double promFinalGen;
    @Column(nullable = false, unique = true) private Long idEstudiante; // 1 a 1
}
