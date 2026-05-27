package una.ac.cr.bolsaempleo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import una.ac.cr.bolsaempleo.models.Empresa;
import una.ac.cr.bolsaempleo.repositories.EmpresaRepository;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    public List<Empresa> obtenerTodas() {
        return empresaRepository.findAll();
    }

    public List<Empresa> obtenerPendientes() {
        return empresaRepository.findByAprobadoFalse();
    }

    public List<Empresa> obtenerAprobadas() {
        return empresaRepository.findByAprobadoTrue();
    }

    public Empresa obtenerPorId(Long id) {
        return empresaRepository.findById(id).orElse(null);
    }

    public Empresa obtenerPorCorreo(String correo) {
        return empresaRepository.findByCorreo(correo);
    }

    public Empresa guardar(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    public void aprobar(Long id) {
        Empresa e = obtenerPorId(id);
        if (e != null) {
            e.setAprobado(true);
            if (e.getPassword() == null || e.getPassword().isBlank()) {
                e.setPassword(e.getCorreo());
            }
            empresaRepository.save(e);
        }
    }
}