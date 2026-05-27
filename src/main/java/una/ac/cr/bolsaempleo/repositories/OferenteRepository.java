package una.ac.cr.bolsaempleo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import una.ac.cr.bolsaempleo.models.Oferente;

public interface OferenteRepository extends JpaRepository<Oferente, Long> {
    List<Oferente> findByAprobadoFalse();
    List<Oferente> findByAprobadoTrue();
    Oferente findByCorreo(String correo);
}