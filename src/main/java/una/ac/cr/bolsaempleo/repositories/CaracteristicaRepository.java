package una.ac.cr.bolsaempleo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import una.ac.cr.bolsaempleo.models.Caracteristica;

public interface CaracteristicaRepository extends JpaRepository<Caracteristica, Long> {
    List<Caracteristica> findByPadreIsNull();
    List<Caracteristica> findByPadreId(Long padreId);
}