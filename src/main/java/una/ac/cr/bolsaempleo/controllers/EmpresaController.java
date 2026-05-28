package una.ac.cr.bolsaempleo.controllers;

import una.ac.cr.bolsaempleo.dtos.CandidatoResultadoDTO;
import una.ac.cr.bolsaempleo.dtos.EmpresaDTOs;
import una.ac.cr.bolsaempleo.models.Empresa;
import una.ac.cr.bolsaempleo.models.Puesto;
import una.ac.cr.bolsaempleo.models.RequisitoPuesto;
import una.ac.cr.bolsaempleo.security.JwtService;
import una.ac.cr.bolsaempleo.services.EmpresaService;
import una.ac.cr.bolsaempleo.services.MatchingService;
import una.ac.cr.bolsaempleo.services.PuestoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {

    @Autowired private EmpresaService empresaService;
    @Autowired private PuestoService puestoService;
    @Autowired private MatchingService matchingService;
    @Autowired private JwtService jwtService;

    // ── Registro público ──────────────────────────────────────────────────────

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody EmpresaDTOs.RegistroEmpresaRequest req) {
        Empresa nueva = new Empresa();
        nueva.setNombre(req.getNombre());
        nueva.setLocalizacion(req.getLocalizacion());
        nueva.setCorreo(req.getCorreo());
        nueva.setTelefono(req.getTelefono());
        nueva.setDescripcion(req.getDescripcion());
        nueva.setAprobado(false);
        empresaService.guardar(nueva);
        return ResponseEntity.status(201).body("Registro enviado. Espere aprobación del administrador.");
    }

    // ── Dashboard — mis puestos ───────────────────────────────────────────────

    @GetMapping("/mis-puestos")
    public ResponseEntity<List<EmpresaDTOs.PuestoResumenDTO>> misPuestos(
            @RequestHeader("Authorization") String authHeader) {
        Long empresaId = extraerId(authHeader);
        Empresa empresa = empresaService.obtenerPorId(empresaId);
        if (empresa == null) return ResponseEntity.status(403).build();

        List<EmpresaDTOs.PuestoResumenDTO> lista = puestoService.obtenerPorEmpresa(empresa)
                .stream()
                .map(p -> toDTO(p))
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    // ── Crear puesto ──────────────────────────────────────────────────────────

    @PostMapping("/puestos")
    public ResponseEntity<?> crearPuesto(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody EmpresaDTOs.CrearPuestoRequest req) {
        Long empresaId = extraerId(authHeader);

        List<Long[]> requisitos = req.getRequisitos().stream()
                .map(r -> new Long[]{r.getCaracteristicaId(), (long) r.getNivelRequerido()})
                .collect(Collectors.toList());

        Puesto nuevo = puestoService.crear(empresaId, req.getDescripcion(),
                req.getSalario(), req.isPublico(), requisitos, empresaService);

        if (nuevo == null) return ResponseEntity.badRequest().body("Empresa no encontrada.");
        return ResponseEntity.status(201).body(toDTO(nuevo));
    }

    // ── Desactivar puesto ─────────────────────────────────────────────────────

    @PostMapping("/puestos/{id}/desactivar")
    public ResponseEntity<Void> desactivar(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long id) {
        Long empresaId = extraerId(authHeader);
        Puesto p = puestoService.obtenerPorId(id);
        if (p == null || !p.getEmpresa().getId().equals(empresaId))
            return ResponseEntity.status(403).build();
        puestoService.desactivar(id);
        return ResponseEntity.ok().build();
    }

    // ── Buscar candidatos para un puesto ──────────────────────────────────────

    @GetMapping("/puestos/{id}/candidatos")
    public ResponseEntity<List<CandidatoResultadoDTO>> buscarCandidatos(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long id) {
        Long empresaId = extraerId(authHeader);
        Puesto p = puestoService.obtenerPorId(id);
        if (p == null || !p.getEmpresa().getId().equals(empresaId))
            return ResponseEntity.status(403).build();
        return ResponseEntity.ok(matchingService.buscarCandidatos(id));
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private Long extraerId(String authHeader) {
        String token = authHeader.substring(7);
        return jwtService.obtenerIdUsuario(token);
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