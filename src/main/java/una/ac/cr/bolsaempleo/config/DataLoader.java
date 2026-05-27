package una.ac.cr.bolsaempleo.config;

import una.ac.cr.bolsaempleo.models.*;
import una.ac.cr.bolsaempleo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired private AdministradorRepository adminRepo;
    @Autowired private CaracteristicaRepository caracteristicaRepo;
    @Autowired private EmpresaRepository empresaRepo;
    @Autowired private OferenteRepository oferenteRepo;
    @Autowired private HabilidadRepository habilidadRepo;

    @Override
    public void run(String... args) {
        // Solo cargar si la BD está vacía
        if (adminRepo.count() > 0) return;

        // ── Admin inicial ─────────────────────────────────────
        Administrador admin = new Administrador();
        admin.setIdentificacion("admin01");
        admin.setNombre("Administrador");
        admin.setClave("admin123");
        adminRepo.save(admin);

        // ── Características jerárquicas ───────────────────────
        Caracteristica lenguajes = car("Lenguajes de Programación", null);
        Caracteristica java      = car("Java", lenguajes);
        Caracteristica python    = car("Python", lenguajes);
        Caracteristica csharp    = car("C#", lenguajes);

        Caracteristica webTech   = car("Tecnologías Web", null);
        Caracteristica html      = car("HTML", webTech);
        Caracteristica css       = car("CSS", webTech);
        Caracteristica react     = car("React", webTech);
        Caracteristica spring    = car("Spring Boot", webTech);

        Caracteristica baseDatos = car("Bases de Datos", null);
        Caracteristica mysql     = car("MySQL", baseDatos);
        Caracteristica postgres  = car("PostgreSQL", baseDatos);

        Caracteristica testing   = car("Testing", null);
        Caracteristica junit     = car("JUnit", testing);
        Caracteristica selenium  = car("Selenium", testing);

        // ── Empresas aprobadas ────────────────────────────────
        Empresa softlab = empresa("SoftLab CR", "San José", "rrhh@softlab.cr",
                "8888-1111", "Empresa de desarrollo de software.", true);
        Empresa websoft = empresa("WebSoft Solutions", "Heredia", "contacto@websoft.com",
                "8888-2222", "Agencia de desarrollo web.", true);
        Empresa startup = empresa("StartupNova", "Alajuela", "info@startupnova.cr",
                "8888-4444", "Startup de inteligencia artificial.", false);

        // ── Oferentes aprobados ───────────────────────────────
        Oferente jose  = oferente("Jose", "Sanchez", "1-0101-0101",
                "Costarricense", "jose.sanchez@gmail.com", "8700-1111", "San José", true);
        Oferente maria = oferente("Maria", "González", "1-0202-0202",
                "Costarricense", "maria.gonzalez@gmail.com", "8700-2222", "Heredia", true);
        Oferente sofia = oferente("Sofia", "Rojas", "1-0404-0404",
                "Costarricense", "sofia.rojas@gmail.com", "8700-4444", "Limón", false);

        // ── Habilidades ───────────────────────────────────────
        habilidad(jose, java, 5);
        habilidad(jose, spring, 4);
        habilidad(jose, mysql, 4);
        habilidad(jose, html, 3);
        habilidad(jose, junit, 3);

        habilidad(maria, html, 5);
        habilidad(maria, css, 5);
        habilidad(maria, react, 4);
        habilidad(maria, selenium, 3);

        System.out.println("✅ DataLoader: datos cargados correctamente.");
    }

    private Caracteristica car(String nombre, Caracteristica padre) {
        Caracteristica c = new Caracteristica();
        c.setNombre(nombre);
        c.setPadre(padre);
        return caracteristicaRepo.save(c);
    }

    private Empresa empresa(String nombre, String loc, String correo,
                            String tel, String desc, boolean aprobado) {
        Empresa e = new Empresa();
        e.setNombre(nombre);
        e.setLocalizacion(loc);
        e.setCorreo(correo);
        e.setTelefono(tel);
        e.setDescripcion(desc);
        e.setAprobado(aprobado);
        if (aprobado) e.setPassword(correo);
        return empresaRepo.save(e);
    }

    private Oferente oferente(String nombre, String apellido, String id,
                              String nac, String correo, String tel,
                              String res, boolean aprobado) {
        Oferente o = new Oferente();
        o.setNombre(nombre);
        o.setPrimerApellido(apellido);
        o.setIdentificacion(id);
        o.setNacionalidad(nac);
        o.setCorreo(correo);
        o.setTelefono(tel);
        o.setResidencia(res);
        o.setAprobado(aprobado);
        if (aprobado) o.setPassword(id);
        return oferenteRepo.save(o);
    }

    private void habilidad(Oferente oferente, Caracteristica car, int nivel) {
        Habilidad h = new Habilidad();
        h.setOferente(oferente);
        h.setCaracteristica(car);
        h.setNivel(nivel);
        habilidadRepo.save(h);
    }
}