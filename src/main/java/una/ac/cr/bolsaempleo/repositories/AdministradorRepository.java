package una.ac.cr.bolsaempleo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import una.ac.cr.bolsaempleo.models.Administrador;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    Administrador findByIdentificacionAndClave(String identificacion, String clave);
    Administrador findByIdentificacion(String identificacion);
}