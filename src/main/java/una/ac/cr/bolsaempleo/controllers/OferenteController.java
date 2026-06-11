package una.ac.cr.bolsaempleo.controllers;

import una.ac.cr.bolsaempleo.dtos.OferenteDTOs;
import una.ac.cr.bolsaempleo.models.Caracteristica;
import una.ac.cr.bolsaempleo.models.Habilidad;
import una.ac.cr.bolsaempleo.models.Oferente;
import una.ac.cr.bolsaempleo.repositories.HabilidadRepository;
import una.ac.cr.bolsaempleo.security.JwtService;
import una.ac.cr.bolsaempleo.services.CaracteristicaService;
import una.ac.cr.bolsaempleo.services.OferenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/oferentes")
public class OferenteController {

    @Autowired private OferenteService oferenteService;
    @Autowired private CaracteristicaService caracteristicaService;
    @Autowired private HabilidadRepository habilidadRepository;
    @Autowired private JwtService jwtService;

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody OferenteDTOs.RegistroOferenteRequest req) {
        Oferente o = new Oferente();
        o.setIdentificacion(req.getIdentificacion());
        o.setNombre(req.getNombre());
        o.setPrimerApellido(req.getPrimerApellido());
        o.setNacionalidad(req.getNacionalidad());
        o.setTelefono(req.getTelefono());
        o.setCorreo(req.getCorreo());
        o.setResidencia(req.getResidencia());
        o.setAprobado(false);
        oferenteService.guardar(o);
        return ResponseEntity.status(201).body("Registro enviado. Espere aprobacion del administrador.");
    }

    @GetMapping("/perfil")
    public ResponseEntity<OferenteDTOs.OferentePerfilDTO> perfil(
            @RequestHeader("Authorization") String authHeader) {
        Long id = extraerId(authHeader);
        Oferente o = oferenteService.obtenerPorId(id);
        if (o == null) return ResponseEntity.status(403).build();
        return ResponseEntity.ok(toDTO(o));
    }

    @PostMapping("/habilidades")
    public ResponseEntity<?> agregarHabilidad(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody OferenteDTOs.HabilidadRequest req) {
        Long id = extraerId(authHeader);
        Caracteristica car = caracteristicaService.obtenerPorId(req.getCaracteristicaId());
        if (car == null) return ResponseEntity.badRequest().body("Caracteristica no encontrada");

        // Si ya tiene esa caracteristica, la reemplaza
        habilidadRepository.findByOferenteId(id).stream()
                .filter(h -> h.getCaracteristica().getId().equals(req.getCaracteristicaId()))
                .findFirst()
                .ifPresent(habilidadRepository::delete);

        Habilidad h = new Habilidad();
        h.setOferente(oferenteService.obtenerPorId(id));
        h.setCaracteristica(car);
        h.setNivel(req.getNivel());
        habilidadRepository.save(h);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/habilidades/{habilidadId}")
    public ResponseEntity<Void> eliminarHabilidad(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long habilidadId) {
        habilidadRepository.deleteById(habilidadId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cv")
    public ResponseEntity<?> subirCV(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestParam("archivo") MultipartFile archivo) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(403).body("No autorizado");
            }
            String token = authHeader.substring(7);
            Long id = jwtService.obtenerIdUsuario(token);
            String nombre = "cv_" + id + ".pdf";
            Path ruta = Paths.get("uploads/", nombre);
            Files.createDirectories(ruta.getParent());
            Files.write(ruta, archivo.getBytes());
            Oferente o = oferenteService.obtenerPorId(id);
            o.setCvPath(nombre);
            oferenteService.guardar(o);
            return ResponseEntity.ok("CV subido correctamente");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al subir el archivo");
        }
    }

    @GetMapping("/cv/{nombre}")
    public ResponseEntity<byte[]> descargarCV(@PathVariable String nombre) {
        try {
            Path ruta = Paths.get("uploads/", nombre);
            byte[] contenido = Files.readAllBytes(ruta);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + nombre)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(contenido);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OferenteDTOs.OferentePerfilDTO> verOferente(@PathVariable Long id) {
        Oferente o = oferenteService.obtenerPorId(id);
        if (o == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(toDTO(o));
    }

    private Long extraerId(String authHeader) {
        return jwtService.obtenerIdUsuario(authHeader.substring(7));
    }

    private OferenteDTOs.OferentePerfilDTO toDTO(Oferente o) {
        List<OferenteDTOs.HabilidadDTO> habs = habilidadRepository
                .findByOferenteId(o.getId()).stream()
                .map(h -> new OferenteDTOs.HabilidadDTO(
                        h.getId(),
                        h.getCaracteristica().getId(),
                        h.getCaracteristica().getNombre(),
                        h.getCaracteristica().getPadre() != null
                                ? h.getCaracteristica().getPadre().getNombre() : null,
                        h.getNivel()))
                .collect(Collectors.toList());

        return new OferenteDTOs.OferentePerfilDTO(
                o.getId(), o.getNombre(), o.getPrimerApellido(),
                o.getIdentificacion(), o.getNacionalidad(),
                o.getTelefono(), o.getCorreo(), o.getResidencia(),
                o.getCvPath(), habs);
    }
}
