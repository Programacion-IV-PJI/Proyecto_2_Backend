package una.ac.cr.bolsaempleo.repositories;

import una.ac.cr.bolsaempleo.models.Empresa;
import una.ac.cr.bolsaempleo.models.Puesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PuestoRepository extends JpaRepository<Puesto, Long> {
    List<Puesto> findTop5ByPublicoTrueAndActivoTrueOrderByIdDesc();
    List<Puesto> findByActivoTrue();
    List<Puesto> findByEmpresa(Empresa empresa);

    @Query("SELECT DISTINCT p FROM Puesto p JOIN p.caracteristicas r " +
           "WHERE r.caracteristica.id IN :ids AND p.publico = true AND p.activo = true")
    List<Puesto> findByCaracteristicas(@Param("ids") List<Long> ids);

    @Query("SELECT p FROM Puesto p WHERE FUNCTION('MONTH', p.fechaCreacion) = :mes AND FUNCTION('YEAR', p.fechaCreacion) = :anio")
    List<Puesto> findByMesYAnio(@Param("mes") int mes, @Param("anio") int anio);
}