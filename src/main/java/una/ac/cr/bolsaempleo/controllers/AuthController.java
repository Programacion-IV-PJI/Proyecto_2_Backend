package una.ac.cr.bolsaempleo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import una.ac.cr.bolsaempleo.dtos.AdminDTOs;
import una.ac.cr.bolsaempleo.models.Administrador;
import una.ac.cr.bolsaempleo.models.Empresa;
import una.ac.cr.bolsaempleo.models.Oferente;
import una.ac.cr.bolsaempleo.security.JwtService;
import una.ac.cr.bolsaempleo.services.AdminService;
import una.ac.cr.bolsaempleo.services.EmpresaService;
import una.ac.cr.bolsaempleo.services.OferenteService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private AdminService adminService;
    @Autowired private EmpresaService empresaService;
    @Autowired private OferenteService oferenteService;
    @Autowired private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AdminDTOs.LoginRequest req) {

        // 1. Intentar como admin
        Administrador admin = adminService.buscarPorCredenciales(req.getIdentificacion(), req.getClave());
        if (admin != null) {
            String token = jwtService.generarToken(admin.getIdentificacion(), "ADMIN", admin.getId());
            return ResponseEntity.ok(new AdminDTOs.LoginResponse(token, "ADMIN", admin.getNombre()));
        }

        // 2. Intentar como empresa
        Empresa empresa = empresaService.obtenerPorCorreo(req.getIdentificacion());
        if (empresa != null && empresa.isAprobado() && req.getClave().equals(empresa.getPassword())) {
            String token = jwtService.generarToken(empresa.getCorreo(), "EMPRESA", empresa.getId());
            return ResponseEntity.ok(new AdminDTOs.LoginResponse(token, "EMPRESA", empresa.getNombre()));
        }

        // 3. Intentar como oferente
        Oferente oferente = oferenteService.obtenerPorCorreo(req.getIdentificacion());
        if (oferente != null && oferente.isAprobado() && req.getClave().equals(oferente.getPassword())) {
            String token = jwtService.generarToken(oferente.getCorreo(), "OFERENTE", oferente.getId());
            return ResponseEntity.ok(new AdminDTOs.LoginResponse(token, "OFERENTE",
                    oferente.getNombre() + " " + oferente.getPrimerApellido()));
        }

        return ResponseEntity.status(401).body("Credenciales incorrectas o cuenta no aprobada");
    }
}