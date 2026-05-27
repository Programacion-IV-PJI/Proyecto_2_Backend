package una.ac.cr.bolsaempleo.services;

import una.ac.cr.bolsaempleo.dtos.CandidatoResultadoDTO;
import una.ac.cr.bolsaempleo.models.Habilidad;
import una.ac.cr.bolsaempleo.models.Oferente;
import una.ac.cr.bolsaempleo.models.Puesto;
import una.ac.cr.bolsaempleo.models.RequisitoPuesto;
import una.ac.cr.bolsaempleo.repositories.HabilidadRepository;
import una.ac.cr.bolsaempleo.repositories.PuestoRepository;
import una.ac.cr.bolsaempleo.repositories.RequisitoPuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class MatchingService {

    @Autowired private PuestoRepository puestoRepository;
    @Autowired private RequisitoPuestoRepository requisitoPuestoRepository;
    @Autowired private HabilidadRepository habilidadRepository;
    @Autowired private OferenteService oferenteService;

    public List<CandidatoResultadoDTO> buscarCandidatos(Long puestoId) {
        Puesto puesto = puestoRepository.findById(puestoId).orElse(null);
        if (puesto == null) return new ArrayList<>();

        List<RequisitoPuesto> requisitos = requisitoPuestoRepository.findByPuestoId(puestoId);
        List<Oferente> oferentes = oferenteService.obtenerAprobados();
        List<CandidatoResultadoDTO> resultados = new ArrayList<>();

        for (Oferente oferente : oferentes) {
            List<Habilidad> habilidades = habilidadRepository.findByOferenteId(oferente.getId());
            int cumplidos = 0;

            for (RequisitoPuesto req : requisitos) {
                boolean cumple = habilidades.stream().anyMatch(h ->
                    h.getCaracteristica().getId().equals(req.getCaracteristica().getId())
                    && h.getNivel() >= req.getNivelRequerido()
                );
                if (cumple) cumplidos++;
            }

            resultados.add(new CandidatoResultadoDTO(
                oferente.getId(),
                oferente.getNombre(),
                oferente.getPrimerApellido(),
                oferente.getCorreo(),
                oferente.getIdentificacion(),
                oferente.getTelefono(),
                oferente.getResidencia(),
                oferente.getCvPath(),
                cumplidos,
                requisitos.size()
            ));
        }

        resultados.sort((a, b) -> Double.compare(b.getPorcentaje(), a.getPorcentaje()));
        return resultados;
    }
}