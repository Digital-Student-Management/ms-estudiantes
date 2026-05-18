package com.example.ms_estudiantes.repositories;
import com.example.ms_estudiantes.models.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface AntecedentesAcademicosRepository extends JpaRepository<AntecedentesAcademicos, Long> {
    Optional<AntecedentesAcademicos> findByIdEstudiante(Long idEstudiante);
}
