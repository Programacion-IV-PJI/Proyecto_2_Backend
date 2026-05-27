package una.ac.cr.bolsaempleo.controllers;

import una.ac.cr.bolsaempleo.dtos.AdminDTOs;
import una.ac.cr.bolsaempleo.dtos.CandidatoResultadoDTO;
import una.ac.cr.bolsaempleo.models.Caracteristica;
import una.ac.cr.bolsaempleo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired private EmpresaService empresaService;
    @Autowired private OferenteService oferenteService;
    @Autowired private CaracteristicaService caracteristicaService;
    @Autowired private MatchingService matchingService;

    // ── Empresas pendientes ───────────────────────────────────────────────────

    @GetMapping("/empresas/pendientes")
    public ResponseEntity<List<AdminDTOs.EmpresaPendienteDTO>> empresasPendientes() {
        List<AdminDTOs.EmpresaPendienteDTO> lista = empresaService.obtenerPendientes()
                .stream()
                .map(e -> new AdminDTOs.EmpresaPendienteDTO(
                        e.getId(), e.getNombre(), e.getCorreo(),
                        e.getLocalizacion(), e.getTelefono(), e.getDescripcion()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PostMapping("/empresas/{id}/aprobar")
    public ResponseEntity<Void> aprobarEmpresa(@PathVariable Long id) {
        empresaService.aprobar(id);
        return ResponseEntity.ok().build();
    }

    // ── Oferentes pendientes ──────────────────────────────────────────────────

    @GetMapping("/oferentes/pendientes")
    public ResponseEntity<List<AdminDTOs.OferentePendienteDTO>> oferentesPendientes() {
        List<AdminDTOs.OferentePendienteDTO> lista = oferenteService.obtenerPendientes()
                .stream()
                .map(o -> new AdminDTOs.OferentePendienteDTO(
                        o.getId(), o.getNombre(), o.getPrimerApellido(), o.getCorreo(),
                        o.getIdentificacion(), o.getNacionalidad(), o.getTelefono(), o.getResidencia()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PostMapping("/oferentes/{id}/aprobar")
    public ResponseEntity<Void> aprobarOferente(@PathVariable Long id) {
        oferenteService.aprobar(id);
        return ResponseEntity.ok().build();
    }

    // ── Características ───────────────────────────────────────────────────────

    @GetMapping("/caracteristicas")
    public ResponseEntity<List<AdminDTOs.CaracteristicaDTO>> listarCaracteristicas() {
        List<AdminDTOs.CaracteristicaDTO> lista = caracteristicaService.obtenerTodas()
                .stream()
                .map(c -> new AdminDTOs.CaracteristicaDTO(
                        c.getId(), c.getNombre(),
                        c.getPadre() != null ? c.getPadre().getId() : null,
                        c.getPadre() != null ? c.getPadre().getNombre() : null))
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/caracteristicas/raices")
    public ResponseEntity<List<AdminDTOs.CaracteristicaDTO>> listarRaices() {
        List<AdminDTOs.CaracteristicaDTO> lista = caracteristicaService.obtenerRaices()
                .stream()
                .map(c -> new AdminDTOs.CaracteristicaDTO(c.getId(), c.getNombre(), null, null))
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/caracteristicas/{id}/hijos")
    public ResponseEntity<List<AdminDTOs.CaracteristicaDTO>> listarHijos(@PathVariable Long id) {
        Caracteristica padre = caracteristicaService.obtenerPorId(id);
        if (padre == null) return ResponseEntity.notFound().build();
        List<AdminDTOs.CaracteristicaDTO> lista = caracteristicaService.obtenerHijos(id)
                .stream()
                .map(c -> new AdminDTOs.CaracteristicaDTO(c.getId(), c.getNombre(), id, padre.getNombre()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PostMapping("/caracteristicas")
    public ResponseEntity<AdminDTOs.CaracteristicaDTO> crearCaracteristica(
            @RequestBody AdminDTOs.CrearCaracteristicaRequest req) {
        Caracteristica nueva = new Caracteristica();
        nueva.setNombre(req.getNombre());
        if (req.getPadreId() != null) {
            Caracteristica padre = caracteristicaService.obtenerPorId(req.getPadreId());
            if (padre == null) return ResponseEntity.badRequest().build();
            nueva.setPadre(padre);
        }
        Caracteristica guardada = caracteristicaService.guardar(nueva);
        return ResponseEntity.ok(new AdminDTOs.CaracteristicaDTO(
                guardada.getId(), guardada.getNombre(),
                guardada.getPadre() != null ? guardada.getPadre().getId() : null,
                guardada.getPadre() != null ? guardada.getPadre().getNombre() : null));
    }

    @DeleteMapping("/caracteristicas/{id}")
    public ResponseEntity<Void> eliminarCaracteristica(@PathVariable Long id) {
        Caracteristica c = caracteristicaService.obtenerPorId(id);
        if (c == null) return ResponseEntity.notFound().build();
        if (c.getHijos() != null && !c.getHijos().isEmpty())
            return ResponseEntity.badRequest().build();
        caracteristicaService.eliminar(id);
        return ResponseEntity.ok().build();
    }

    // ── Matching (lo consumen Persona 1 y 2) ─────────────────────────────────

    @GetMapping("/matching/{puestoId}")
    public ResponseEntity<List<CandidatoResultadoDTO>> buscarCandidatos(@PathVariable Long puestoId) {
        return ResponseEntity.ok(matchingService.buscarCandidatos(puestoId));
    }
}