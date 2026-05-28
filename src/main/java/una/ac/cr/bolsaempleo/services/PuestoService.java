package una.ac.cr.bolsaempleo.services;

import una.ac.cr.bolsaempleo.models.Empresa;
import una.ac.cr.bolsaempleo.models.Puesto;
import una.ac.cr.bolsaempleo.models.RequisitoPuesto;
import una.ac.cr.bolsaempleo.models.Caracteristica;
import una.ac.cr.bolsaempleo.repositories.PuestoRepository;
import una.ac.cr.bolsaempleo.repositories.RequisitoPuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PuestoService {

    @Autowired private PuestoRepository puestoRepository;
    @Autowired private RequisitoPuestoRepository requisitoPuestoRepository;
    @Autowired private CaracteristicaService caracteristicaService;

    public List<Puesto> obtenerPublicosRecientes() {
        return puestoRepository.findTop5ByPublicoTrueAndActivoTrueOrderByIdDesc();
    }

    public List<Puesto> buscarPorCaracteristicas(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return puestoRepository.findByActivoTrue();
        }
        return puestoRepository.findByCaracteristicas(ids);
    }

    public List<Puesto> obtenerPorEmpresa(Empresa empresa) {
        return puestoRepository.findByEmpresa(empresa);
    }

    public Puesto obtenerPorId(Long id) {
        return puestoRepository.findById(id).orElse(null);
    }

    public Puesto crear(Long empresaId, String descripcion, Double salario,
                        boolean publico, List<Long[]> requisitos,
                        EmpresaService empresaService) {
        Empresa empresa = empresaService.obtenerPorId(empresaId);
        if (empresa == null) return null;

        Puesto puesto = new Puesto();
        puesto.setDescripcion(descripcion);
        puesto.setSalario(salario);
        puesto.setPublico(publico);
        puesto.setActivo(true);
        puesto.setFechaCreacion(LocalDate.now());
        puesto.setEmpresa(empresa);
        Puesto guardado = puestoRepository.save(puesto);

        for (Long[] req : requisitos) {
            Caracteristica car = caracteristicaService.obtenerPorId(req[0]);
            if (car == null) continue;
            RequisitoPuesto rp = new RequisitoPuesto();
            rp.setPuesto(guardado);
            rp.setCaracteristica(car);
            rp.setNivelRequerido(req[1].intValue());
            requisitoPuestoRepository.save(rp);
        }

        return guardado;
    }

    public void desactivar(Long id) {
        Puesto p = obtenerPorId(id);
        if (p != null) {
            p.setActivo(false);
            puestoRepository.save(p);
        }
    }

    public List<RequisitoPuesto> obtenerRequisitos(Long puestoId) {
        return requisitoPuestoRepository.findByPuestoId(puestoId);
    }
}