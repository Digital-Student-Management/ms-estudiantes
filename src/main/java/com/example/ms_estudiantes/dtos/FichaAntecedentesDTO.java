package com.example.ms_estudiantes.dtos;
import lombok.Data;

@Data
public class FichaAntecedentesDTO {
    // Antecedentes Médicos
    private String alergias;
    private String condicionesCronicas;
    private String medicamentosRecetados;
    
    // Antecedentes Académicos
    private String colegioProcedencia;
    private String historialConductualAnterior;
    private Integer anioProcedencia;
    private Double promFinalGen;
}
