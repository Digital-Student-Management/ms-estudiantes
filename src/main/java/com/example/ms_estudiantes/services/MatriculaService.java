package com.example.ms_estudiantes.services;
import com.example.ms_estudiantes.dtos.MatriculaRequestDTO;
import com.example.ms_estudiantes.dtos.MatriculaResponseDTO;
import com.example.ms_estudiantes.models.entities.Matricula;
import com.example.ms_estudiantes.repositories.MatriculaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final RestTemplate restTemplate;
    private final String MS_USUARIOS_URL = "http://localhost:8080/api/usuarios/";

    public MatriculaResponseDTO registrar(MatriculaRequestDTO request) {
        validarUsuario(request.getIdEstudiante(), "Estudiante no encontrado en el sistema escolar.");
        validarUsuario(request.getIdDirectivo(), "Directivo/Funcionario no registrado.");

        Matricula matricula = Matricula.builder()
                .idEstudiante(request.getIdEstudiante())
                .idDirectivo(request.getIdDirectivo())
                .estadoMatricula(request.getEstadoMatricula())
                .fechaInscripcion(LocalDateTime.now())
                .build();
                
        return mapearAResponse(matriculaRepository.save(matricula));
    }

    public List<MatriculaResponseDTO> listarTodas() {
        return matriculaRepository.findAll()
                .stream().map(this::mapearAResponse).toList();
    }

    public List<MatriculaResponseDTO> listarPorEstudiante(Long idEstudiante) {
        return matriculaRepository.findByIdEstudiante(idEstudiante)
                .stream().map(this::mapearAResponse).toList();
    }

    public MatriculaResponseDTO actualizar(Long id, MatriculaRequestDTO request) {
        Matricula existente = matriculaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro de matrícula no encontrado."));
        
        existente.setEstadoMatricula(request.getEstadoMatricula());
        return mapearAResponse(matriculaRepository.save(existente));
    }

    public void eliminar(Long id) {
        if (!matriculaRepository.existsById(id)) {
            throw new RuntimeException("La matrícula especificada no existe.");
        }
        matriculaRepository.deleteById(id);
    }

    private void validarUsuario(Long id, String errorMsg) {
        try {
            restTemplate.getForEntity(MS_USUARIOS_URL + id, Object.class);
        } catch (Exception e) {
            throw new RuntimeException(errorMsg);
        }
    }

    private MatriculaResponseDTO mapearAResponse(Matricula matricula) {
        return MatriculaResponseDTO.builder()
                .idMatricula(matricula.getIdMatricula())
                .idEstudiante(matricula.getIdEstudiante())
                .idDirectivo(matricula.getIdDirectivo())
                .estadoMatricula(matricula.getEstadoMatricula())
                .fechaInscripcion(matricula.getFechaInscripcion())
                .build();
    }
}
