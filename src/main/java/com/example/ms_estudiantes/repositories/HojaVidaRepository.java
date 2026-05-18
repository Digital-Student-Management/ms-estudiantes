package com.example.ms_estudiantes.repositories;
import com.example.ms_estudiantes.models.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface HojaVidaRepository extends JpaRepository<HojaVida, Long> {
    Optional<HojaVida> findByIdEstudiante(Long idEstudiante); // Retorna Uno
}