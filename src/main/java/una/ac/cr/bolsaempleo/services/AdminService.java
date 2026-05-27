package una.ac.cr.bolsaempleo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import una.ac.cr.bolsaempleo.models.Administrador;
import una.ac.cr.bolsaempleo.repositories.AdministradorRepository;

@Service
public class AdminService {

    @Autowired
    private AdministradorRepository adminRepository;

    public Administrador buscarPorIdentificacion(String identificacion) {
        return adminRepository.findByIdentificacion(identificacion);
    }

    public Administrador buscarPorCredenciales(String identificacion, String clave) {
        return adminRepository.findByIdentificacionAndClave(identificacion, clave);
    }

    public Administrador guardar(Administrador admin) {
        return adminRepository.save(admin);
    }
}