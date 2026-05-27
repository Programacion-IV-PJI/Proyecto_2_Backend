package una.ac.cr.bolsaempleo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import una.ac.cr.bolsaempleo.models.Habilidad;

public interface HabilidadRepository extends JpaRepository<Habilidad, Long> {
    List<Habilidad> findByOferenteId(Long oferenteId);
}