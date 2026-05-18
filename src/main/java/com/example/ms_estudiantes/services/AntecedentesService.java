package com.example.ms_estudiantes.services;
import com.example.ms_estudiantes.dtos.FichaAntecedentesDTO;
import com.example.ms_estudiantes.models.entities.AntecedentesAcademicos;
import com.example.ms_estudiantes.models.entities.AntecedentesApoderado;
import com.example.ms_estudiantes.models.entities.AntecedentesMedicos;
import com.example.ms_estudiantes.repositories.AntecedentesAcademicosRepository;
import com.example.ms_estudiantes.repositories.AntecedentesApoderadoRepository;
import com.example.ms_estudiantes.repositories.AntecedentesMedicosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor

public class AntecedentesService {

    private final AntecedentesMedicosRepository medicosRepo;
    private final AntecedentesAcademicosRepository academicosRepo;
    private final AntecedentesApoderadoRepository apoderadoRepo;
    private final RestTemplate restTemplate;

    public void guardarOActualizarAntecedentesEstudiante(Long idEstudiante, FichaAntecedentesDTO dto) {
        try {
            restTemplate.getForEntity("http://localhost:8080/api/usuarios/" + idEstudiante, Object.class);
        } catch (Exception e) {
            throw new RuntimeException("Estudiante no válido en el sistema base.");
        }

        AntecedentesMedicos med = medicosRepo.findByIdEstudiante(idEstudiante)
                .orElse(AntecedentesMedicos.builder().idEstudiante(idEstudiante).build());
        med.setAlergias(dto.getAlergias());
        med.setCondicionesCronicas(dto.getCondicionesCronicas());
        med.setMedicamentosRecetados(dto.getMedicamentosRecetados());
        medicosRepo.save(med);

        AntecedentesAcademicos acad = academicosRepo.findByIdEstudiante(idEstudiante)
                .orElse(AntecedentesAcademicos.builder().idEstudiante(idEstudiante).build());
        acad.setColegioProcedencia(dto.getColegioProcedencia());
        acad.setHistorialConductualAnterior(dto.getHistorialConductualAnterior());
        acad.setAnioProcedencia(dto.getAnioProcedencia());
        acad.setPromFinalGen(dto.getPromFinalGen());
        academicosRepo.save(acad);
    }

    public FichaAntecedentesDTO obtenerAntecedentesEstudiante(Long idEstudiante) {
        AntecedentesMedicos med = medicosRepo.findByIdEstudiante(idEstudiante).orElse(new AntecedentesMedicos());
        AntecedentesAcademicos acad = academicosRepo.findByIdEstudiante(idEstudiante).orElse(new AntecedentesAcademicos());

        FichaAntecedentesDTO dto = new FichaAntecedentesDTO();
        dto.setAlergias(med.getAlergias());
        dto.setCondicionesCronicas(med.getCondicionesCronicas());
        dto.setMedicamentosRecetados(med.getMedicamentosRecetados());
        dto.setColegioProcedencia(acad.getColegioProcedencia());
        dto.setHistorialConductualAnterior(acad.getHistorialConductualAnterior());
        dto.setAnioProcedencia(acad.getAnioProcedencia());
        dto.setPromFinalGen(acad.getPromFinalGen());
        return dto;
    }

    public void eliminarAntecedentesEstudiante(Long idEstudiante) {
        medicosRepo.findByIdEstudiante(idEstudiante).ifPresent(medicosRepo::delete);
        academicosRepo.findByIdEstudiante(idEstudiante).ifPresent(academicosRepo::delete);
    }

    public AntecedentesApoderado guardarOActualizarApoderado(Long idApoderado, String infoSocio, String nivelEd) {
        AntecedentesApoderado ap = apoderadoRepo.findByIdApoderado(idApoderado)
                .orElse(AntecedentesApoderado.builder().idApoderado(idApoderado).build());
        ap.setInfoSociofamiliar(infoSocio);
        ap.setNivelEducacional(nivelEd);
        return apoderadoRepo.save(ap);
    }

    public AntecedentesApoderado obtenerAntecedentesApoderado(Long idApoderado) {
        return apoderadoRepo.findByIdApoderado(idApoderado)
                .orElseThrow(() -> new RuntimeException("No existen registros sociofamiliares para este apoderado."));
    }
}
