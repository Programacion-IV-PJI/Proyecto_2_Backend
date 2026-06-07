package una.ac.cr.bolsaempleo.controllers;

import una.ac.cr.bolsaempleo.dtos.EmpresaDTOs;
import una.ac.cr.bolsaempleo.models.Puesto;
import una.ac.cr.bolsaempleo.services.PuestoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/puestos")
public class PuestoController {

    @Autowired private PuestoService puestoService;

    @GetMapping("/publicos/recientes")
    public ResponseEntity<List<EmpresaDTOs.PuestoResumenDTO>> recientes() {
        List<EmpresaDTOs.PuestoResumenDTO> lista = puestoService.obtenerPublicosRecientes()
                .stream()
                .map(p -> toDTO(p))
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<EmpresaDTOs.PuestoResumenDTO>> buscar(
            @RequestParam(required = false) List<Long> caracteristicas,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        boolean tieneToken = authHeader != null && authHeader.startsWith("Bearer ");

        List<EmpresaDTOs.PuestoResumenDTO> lista = puestoService.buscarPorCaracteristicas(caracteristicas, !tieneToken)
                .stream()
                .map(p -> toDTO(p))
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    private EmpresaDTOs.PuestoResumenDTO toDTO(Puesto p) {
        List<EmpresaDTOs.RequisitoDetalleDTO> reqs = puestoService.obtenerRequisitos(p.getId())
                .stream()
                .map(r -> new EmpresaDTOs.RequisitoDetalleDTO(
                        r.getCaracteristica().getId(),
                        r.getCaracteristica().getNombre(),
                        r.getCaracteristica().getPadre() != null
                                ? r.getCaracteristica().getPadre().getNombre() : null,
                        r.getNivelRequerido()))
                .collect(Collectors.toList());

        return new EmpresaDTOs.PuestoResumenDTO(
                p.getId(), p.getDescripcion(), p.getSalario(),
                p.isPublico(), p.isActivo(),
                p.getEmpresa().getNombre(), reqs);
    }
}