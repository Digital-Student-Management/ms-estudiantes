package com.example.ms_estudiantes.repositories;
import com.example.ms_estudiantes.models.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface MatriculaRepository extends JpaRepository<Matricula, Long>{
    List<Matricula> findByIdEstudiante(Long idEstudiante);

}
