package com.example.ms_estudiantes.models.entities;
import jakarta.persistence.*;
import lombok.*;

@Entity 
@Table(name = "antecedentes_apoderado")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class AntecedentesApoderado {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAntApoderado;
    private String infoSociofamiliar;
    private String nivelEducacional;
    @Column(nullable = false, unique = true) private Long idApoderado; // 1 a 1
}