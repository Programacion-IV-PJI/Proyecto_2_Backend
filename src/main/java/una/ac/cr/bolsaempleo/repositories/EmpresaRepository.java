package una.ac.cr.bolsaempleo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import una.ac.cr.bolsaempleo.models.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    List<Empresa> findByAprobadoFalse();
    List<Empresa> findByAprobadoTrue();
    Empresa findByCorreo(String correo);
}