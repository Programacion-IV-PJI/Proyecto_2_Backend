package una.ac.cr.bolsaempleo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import una.ac.cr.bolsaempleo.models.RequisitoPuesto;

public interface RequisitoPuestoRepository extends JpaRepository<RequisitoPuesto, Long> {
    List<RequisitoPuesto> findByPuestoId(Long puestoId);
}