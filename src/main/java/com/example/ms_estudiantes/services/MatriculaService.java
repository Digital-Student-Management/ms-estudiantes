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
        validarRolUsuario(request.getIdEstudiante(), "ESTUDIANTE");
        validarRolUsuario(request.getIdDirectivo(), "DIRECTIVO");

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


        // Método Auxiliar de Validación Estricta
        @SuppressWarnings("unchecked")
        private void validarRolUsuario(Long id, String rolEsperado) {
        try {
            java.util.Map<String, Object> response = restTemplate.getForObject(
                    MS_USUARIOS_URL + id, 
                    java.util.Map.class
            );
            
            String tipoUsuario = (String) response.get("tipoUsuario");
            if (!rolEsperado.equals(tipoUsuario)) {
                throw new RuntimeException("Incongruencia de roles: Se esperaba un " + rolEsperado + " pero el ID corresponde a un " + tipoUsuario);
            }
        } catch (org.springframework.web.client.HttpClientErrorException.NotFound e) {
            throw new RuntimeException("El " + rolEsperado + " con ID " + id + " no existe en el sistema.");
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("Incongruencia")) {
                throw new RuntimeException(e.getMessage());
            }
            throw new RuntimeException("Fallo en la comunicación con MS-Usuarios: " + e.getMessage());
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
