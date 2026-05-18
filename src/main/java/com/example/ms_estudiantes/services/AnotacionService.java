package com.example.ms_estudiantes.services;
import com.example.ms_estudiantes.dtos.AnotacionRequestDTO;
import com.example.ms_estudiantes.dtos.AnotacionResponseDTO;
import com.example.ms_estudiantes.models.entities.Anotacion;
import com.example.ms_estudiantes.models.entities.HojaVida;
import com.example.ms_estudiantes.repositories.AnotacionRepository;
import com.example.ms_estudiantes.repositories.HojaVidaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnotacionService {

    private final AnotacionRepository anotacionRepository;
    private final HojaVidaRepository hojaVidaRepository;
    private final RestTemplate restTemplate;

    public AnotacionResponseDTO crearAnotacion(AnotacionRequestDTO request) {
        try {
            restTemplate.getForEntity("http://localhost:8080/api/usuarios/" + request.getIdEstudiante(), Object.class);
        } catch (Exception e) {
            throw new RuntimeException("El estudiante no existe para asignarle una anotación.");
        }

        Anotacion anotacion = Anotacion.builder()
                .idEstudiante(request.getIdEstudiante())
                .tipoAnotacion(request.getTipoAnotacion())
                .descripcionAnotacion(request.getDescripcionAnotacion())
                .fecha(LocalDateTime.now())
                .build();
        
        if (hojaVidaRepository.findByIdEstudiante(request.getIdEstudiante()).isEmpty()) {
            HojaVida nuevaHoja = HojaVida.builder()
                    .idEstudiante(request.getIdEstudiante())
                    .fechaCreacion(LocalDateTime.now())
                    .observacionGeneral("Ficha iniciada en el sistema de bitácora.")
                    .build();
            hojaVidaRepository.save(nuevaHoja);
        }

        return mapearAResponse(anotacionRepository.save(anotacion));
    }

    public List<AnotacionResponseDTO> listarPorEstudiante(Long idEstudiante) {
        return anotacionRepository.findByIdEstudianteOrderByFechaDesc(idEstudiante)
                .stream().map(this::mapearAResponse).toList();
    }

    public AnotacionResponseDTO actualizarAnotacion(Long id, AnotacionRequestDTO request) {
        Anotacion existente = anotacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anotación no encontrada."));
        existente.setTipoAnotacion(request.getTipoAnotacion());
        existente.setDescripcionAnotacion(request.getDescripcionAnotacion());
        return mapearAResponse(anotacionRepository.save(existente));
    }

    public void eliminarAnotacion(Long id) {
        anotacionRepository.deleteById(id);
    }

    public HojaVida obtenerHojaVida(Long idEstudiante) {
        return hojaVidaRepository.findByIdEstudiante(idEstudiante)
                .orElseThrow(() -> new RuntimeException("El estudiante no registra hoja de vida activa."));
    }

    public HojaVida actualizarHojaVida(Long idEstudiante, String observacion) {
        HojaVida hv = obtenerHojaVida(idEstudiante);
        hv.setObservacionGeneral(observacion);
        return hojaVidaRepository.save(hv);
    }

    private AnotacionResponseDTO mapearAResponse(Anotacion anotacion) {
        return AnotacionResponseDTO.builder()
                .idAnotacion(anotacion.getIdAnotacion())
                .idEstudiante(anotacion.getIdEstudiante())
                .tipoAnotacion(anotacion.getTipoAnotacion())
                .descripcionAnotacion(anotacion.getDescripcionAnotacion())
                .fecha(anotacion.getFecha())
                .build();
    }
}