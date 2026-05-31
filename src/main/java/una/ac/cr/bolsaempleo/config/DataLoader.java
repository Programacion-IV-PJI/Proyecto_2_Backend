package una.ac.cr.bolsaempleo.config;

import una.ac.cr.bolsaempleo.models.*;
import una.ac.cr.bolsaempleo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired private AdministradorRepository adminRepo;
    @Autowired private CaracteristicaRepository caracteristicaRepo;
    @Autowired private EmpresaRepository empresaRepo;
    @Autowired private OferenteRepository oferenteRepo;
    @Autowired private HabilidadRepository habilidadRepo;
    @Autowired private PuestoRepository puestoRepo;
    @Autowired private RequisitoPuestoRepository requisitoPuestoRepo;

    @Override
    public void run(String... args) {
        if (adminRepo.count() > 0) return;

        // ── Admins ────────────────────────────────────────────
        admin("admin01", "Administrador Principal", "admin123");
        admin("admin02", "Administrador Secundario", "admin456");

        // ── Características ───────────────────────────────────
        Caracteristica lenguajes  = car("Lenguajes de Programación", null);
        Caracteristica java       = car("Java", lenguajes);
        Caracteristica python     = car("Python", lenguajes);
        Caracteristica csharp     = car("C#", lenguajes);
        Caracteristica kotlin     = car("Kotlin", lenguajes);
        Caracteristica go         = car("Go", lenguajes);
        Caracteristica ruby       = car("Ruby", lenguajes);
        Caracteristica php        = car("PHP", lenguajes);

        Caracteristica webTech    = car("Tecnologías Web", null);
        Caracteristica html       = car("HTML", webTech);
        Caracteristica css        = car("CSS", webTech);
        Caracteristica react      = car("React", webTech);
        Caracteristica angular    = car("Angular", webTech);
        Caracteristica vue        = car("Vue.js", webTech);
        Caracteristica spring     = car("Spring Boot", webTech);
        Caracteristica node       = car("Node.js", webTech);
        Caracteristica django     = car("Django", webTech);

        Caracteristica baseDatos  = car("Bases de Datos", null);
        Caracteristica mysql      = car("MySQL", baseDatos);
        Caracteristica postgres   = car("PostgreSQL", baseDatos);
        Caracteristica mongo      = car("MongoDB", baseDatos);
        Caracteristica oracle     = car("Oracle DB", baseDatos);
        Caracteristica redis      = car("Redis", baseDatos);

        Caracteristica devops     = car("DevOps", null);
        Caracteristica docker     = car("Docker", devops);
        Caracteristica kubernetes = car("Kubernetes", devops);
        Caracteristica jenkins    = car("Jenkins", devops);
        Caracteristica git        = car("Git", devops);
        Caracteristica aws        = car("AWS", devops);

        Caracteristica testing    = car("Testing", null);
        Caracteristica junit      = car("JUnit", testing);
        Caracteristica selenium   = car("Selenium", testing);
        Caracteristica cypress    = car("Cypress", testing);
        Caracteristica postman    = car("Postman", testing);

        Caracteristica soft       = car("Habilidades Blandas", null);
        Caracteristica liderazgo  = car("Liderazgo", soft);
        Caracteristica comunic    = car("Comunicación", soft);
        Caracteristica trabajo    = car("Trabajo en equipo", soft);

        // ── Empresas aprobadas ────────────────────────────────
        Empresa softlab  = empresa("SoftLab CR", "San José", "rrhh@softlab.cr",
                "2200-1111", "Empresa líder en desarrollo de software empresarial en Costa Rica.", true);
        Empresa websoft  = empresa("WebSoft Solutions", "Heredia", "contacto@websoft.com",
                "2200-2222", "Agencia especializada en desarrollo web y apps móviles.", true);
        Empresa infotica = empresa("Infótica S.A.", "Cartago", "empleo@infotica.cr",
                "2200-3333", "Consultora tecnológica con más de 15 años en el mercado.", true);
        Empresa novatech = empresa("NovaTech CR", "San José", "jobs@novatech.cr",
                "2200-4444", "Startup de tecnología financiera e innovación digital.", true);
        Empresa globalit = empresa("Global IT Services", "Alajuela", "rrhh@globalit.com",
                "2200-5555", "Proveedor de servicios IT para empresas multinacionales.", true);
        Empresa codewave = empresa("CodeWave", "Heredia", "careers@codewave.cr",
                "2200-6666", "Empresa de nearshoring especializada en equipos ágiles.", true);
        Empresa dataviz  = empresa("DataViz Analytics", "San José", "talento@dataviz.cr",
                "2200-7777", "Empresa de análisis de datos y business intelligence.", true);

        // ── Empresas pendientes ───────────────────────────────
        empresa("StartupNova", "Alajuela", "info@startupnova.cr",
                "8888-4444", "Startup de inteligencia artificial en etapa inicial.", false);
        empresa("TechDream CR", "Liberia", "hola@techdream.cr",
                "8888-5555", "Empresa nueva de desarrollo móvil.", false);

        // ── Oferentes aprobados ───────────────────────────────
        Oferente jose   = oferente("Jose",   "Sanchez",  "1-0101-0101", "Costarricense", "jose.sanchez@gmail.com",   "8700-1111", "San José",  true);
        Oferente maria  = oferente("Maria",  "González", "1-0202-0202", "Costarricense", "maria.gonzalez@gmail.com", "8700-2222", "Heredia",   true);
        Oferente carlos = oferente("Carlos", "Mora",     "1-0303-0303", "Costarricense", "carlos.mora@gmail.com",    "8700-3333", "Cartago",   true);
        Oferente ana    = oferente("Ana",    "Jiménez",  "1-0404-0404", "Costarricense", "ana.jimenez@gmail.com",    "8700-4444", "Alajuela",  true);
        Oferente luis   = oferente("Luis",   "Vargas",   "1-0505-0505", "Costarricense", "luis.vargas@gmail.com",    "8700-5555", "San José",  true);
        Oferente sofia  = oferente("Sofia",  "Rojas",    "1-0606-0606", "Costarricense", "sofia.rojas@gmail.com",    "8700-6666", "Heredia",   true);
        Oferente diego  = oferente("Diego",  "Castro",   "1-0707-0707", "Costarricense", "diego.castro@gmail.com",   "8700-7777", "Limón",     true);
        Oferente laura  = oferente("Laura",  "Herrera",  "1-0808-0808", "Costarricense", "laura.herrera@gmail.com",  "8700-8888", "San José",  true);

        // ── Oferentes pendientes ──────────────────────────────
        oferente("Pedro",   "Álvarez", "1-0909-0909", "Costarricense", "pedro.alvarez@gmail.com",  "8700-9999", "Guanacaste", false);
        oferente("Valeria", "Núñez",   "1-1010-1010", "Costarricense", "valeria.nunez@gmail.com",  "8701-0000", "Puntarenas", false);

        // ── Habilidades ───────────────────────────────────────
        // Jose - Backend Java senior
        habilidad(jose, java, 5);     habilidad(jose, spring, 5);
        habilidad(jose, mysql, 4);    habilidad(jose, postgres, 3);
        habilidad(jose, docker, 3);   habilidad(jose, git, 5);
        habilidad(jose, junit, 4);    habilidad(jose, trabajo, 4);

        // Maria - Frontend React senior
        habilidad(maria, html, 5);    habilidad(maria, css, 5);
        habilidad(maria, react, 5);   habilidad(maria, vue, 3);
        habilidad(maria, node, 3);    habilidad(maria, selenium, 3);
        habilidad(maria, cypress, 4); habilidad(maria, comunic, 5);

        // Carlos - Full Stack
        habilidad(carlos, java, 4);   habilidad(carlos, react, 4);
        habilidad(carlos, spring, 3); habilidad(carlos, postgres, 4);
        habilidad(carlos, docker, 4); habilidad(carlos, git, 4);
        habilidad(carlos, aws, 3);    habilidad(carlos, trabajo, 5);

        // Ana - QA Engineer
        habilidad(ana, selenium, 5);  habilidad(ana, cypress, 4);
        habilidad(ana, junit, 4);     habilidad(ana, postman, 5);
        habilidad(ana, python, 3);    habilidad(ana, git, 4);
        habilidad(ana, comunic, 4);

        // Luis - DevOps
        habilidad(luis, docker, 5);    habilidad(luis, kubernetes, 4);
        habilidad(luis, jenkins, 4);   habilidad(luis, aws, 5);
        habilidad(luis, git, 5);       habilidad(luis, python, 3);
        habilidad(luis, liderazgo, 3);

        // Sofia - Backend Python
        habilidad(sofia, python, 5);  habilidad(sofia, django, 4);
        habilidad(sofia, postgres, 4); habilidad(sofia, redis, 3);
        habilidad(sofia, docker, 3);  habilidad(sofia, git, 4);
        habilidad(sofia, trabajo, 4);

        // Diego - Mobile Kotlin
        habilidad(diego, kotlin, 5);  habilidad(diego, java, 3);
        habilidad(diego, git, 4);     habilidad(diego, mysql, 3);
        habilidad(diego, trabajo, 4); habilidad(diego, comunic, 3);

        // Laura - Frontend Angular
        habilidad(laura, angular, 5); habilidad(laura, html, 5);
        habilidad(laura, css, 4);     habilidad(laura, node, 4);
        habilidad(laura, git, 4);     habilidad(laura, cypress, 3);
        habilidad(laura, comunic, 5); habilidad(laura, trabajo, 5);

        // ── Puestos ───────────────────────────────────────────
        // SoftLab
        Puesto p1 = puesto(softlab, "Desarrollador Backend Java Senior", 1800000.0, true, LocalDate.now().minusDays(2));
        requisito(p1, java, 4);     requisito(p1, spring, 4);
        requisito(p1, postgres, 3); requisito(p1, docker, 2);
        requisito(p1, git, 3);

        Puesto p2 = puesto(softlab, "Desarrollador Frontend React", 1400000.0, true, LocalDate.now().minusDays(5));
        requisito(p2, react, 4); requisito(p2, html, 4);
        requisito(p2, css, 4);   requisito(p2, git, 3);

        Puesto p3 = puesto(softlab, "QA Automation Engineer", 1200000.0, true, LocalDate.now().minusDays(8));
        requisito(p3, selenium, 4); requisito(p3, cypress, 3);
        requisito(p3, postman, 4);  requisito(p3, git, 3);

        // WebSoft
        Puesto p4 = puesto(websoft, "Full Stack Java + React", 1600000.0, true, LocalDate.now().minusDays(1));
        requisito(p4, java, 3);   requisito(p4, react, 3);
        requisito(p4, spring, 3); requisito(p4, mysql, 3);

        Puesto p5 = puesto(websoft, "Desarrollador PHP Laravel", 1100000.0, true, LocalDate.now().minusDays(10));
        requisito(p5, php, 4);    requisito(p5, mysql, 3);
        requisito(p5, git, 3);    requisito(p5, docker, 2);

        Puesto p6 = puesto(websoft, "Diseñador UI/UX con HTML y CSS", 1000000.0, true, LocalDate.now().minusDays(3));
        requisito(p6, html, 5);   requisito(p6, css, 5);
        requisito(p6, comunic, 4);

        // Infótica
        Puesto p7 = puesto(infotica, "Arquitecto de Software", 2500000.0, true, LocalDate.now().minusDays(4));
        requisito(p7, java, 5);       requisito(p7, spring, 5);
        requisito(p7, docker, 4);     requisito(p7, kubernetes, 3);
        requisito(p7, liderazgo, 4);

        Puesto p8 = puesto(infotica, "Analista de Base de Datos Oracle", 1700000.0, true, LocalDate.now().minusDays(6));
        requisito(p8, oracle, 5); requisito(p8, mysql, 3);
        requisito(p8, postgres, 3);

        Puesto p9 = puesto(infotica, "Desarrollador C# .NET", 1500000.0, true, LocalDate.now().minusDays(7));
        requisito(p9, csharp, 4); requisito(p9, mysql, 3);
        requisito(p9, git, 3);    requisito(p9, docker, 2);

        // NovaTech
        Puesto p10 = puesto(novatech, "Ingeniero DevOps", 2000000.0, true, LocalDate.now().minusDays(2));
        requisito(p10, docker, 5);    requisito(p10, kubernetes, 4);
        requisito(p10, jenkins, 4);   requisito(p10, aws, 4);
        requisito(p10, git, 5);

        Puesto p11 = puesto(novatech, "Desarrollador Python Backend", 1600000.0, true, LocalDate.now().minusDays(9));
        requisito(p11, python, 4); requisito(p11, django, 3);
        requisito(p11, postgres, 3); requisito(p11, redis, 2);

        Puesto p12 = puesto(novatech, "Desarrollador Mobile Kotlin", 1700000.0, true, LocalDate.now().minusDays(1));
        requisito(p12, kotlin, 4); requisito(p12, java, 3);
        requisito(p12, mysql, 2);  requisito(p12, git, 4);

        // Global IT
        Puesto p13 = puesto(globalit, "Scrum Master / Tech Lead", 2200000.0, true, LocalDate.now().minusDays(3));
        requisito(p13, liderazgo, 5); requisito(p13, comunic, 5);
        requisito(p13, trabajo, 5);   requisito(p13, git, 4);

        Puesto p14 = puesto(globalit, "Desarrollador Angular Senior", 1800000.0, true, LocalDate.now().minusDays(5));
        requisito(p14, angular, 5); requisito(p14, html, 4);
        requisito(p14, css, 4);     requisito(p14, git, 4);

        // CodeWave
        Puesto p15 = puesto(codewave, "Backend Node.js Developer", 1500000.0, true, LocalDate.now().minusDays(2));
        requisito(p15, node, 4);   requisito(p15, mongo, 3);
        requisito(p15, redis, 2);  requisito(p15, docker, 3);

        Puesto p16 = puesto(codewave, "Vue.js Frontend Developer", 1300000.0, true, LocalDate.now().minusDays(4));
        requisito(p16, vue, 4);  requisito(p16, html, 4);
        requisito(p16, css, 4);  requisito(p16, git, 3);

        // DataViz
        Puesto p17 = puesto(dataviz, "Data Engineer Python + SQL", 2100000.0, true, LocalDate.now().minusDays(1));
        requisito(p17, python, 5);   requisito(p17, postgres, 4);
        requisito(p17, mongo, 3);    requisito(p17, redis, 3);

        Puesto p18 = puesto(dataviz, "Analista BI y Reportes", 1400000.0, true, LocalDate.now().minusDays(6));
        requisito(p18, mysql, 4);   requisito(p18, oracle, 3);
        requisito(p18, python, 3);  requisito(p18, comunic, 4);

        System.out.println("✅ DataLoader: datos cargados correctamente.");
    }

    // ── Helpers ───────────────────────────────────────────────

    private void admin(String identificacion, String nombre, String clave) {
        Administrador a = new Administrador();
        a.setIdentificacion(identificacion);
        a.setNombre(nombre);
        a.setClave(clave);
        adminRepo.save(a);
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

    private Puesto puesto(Empresa empresa, String descripcion, Double salario,
                          boolean publico, LocalDate fecha) {
        Puesto p = new Puesto();
        p.setEmpresa(empresa);
        p.setDescripcion(descripcion);
        p.setSalario(salario);
        p.setPublico(publico);
        p.setActivo(true);
        p.setFechaCreacion(fecha);
        return puestoRepo.save(p);
    }

    private void requisito(Puesto puesto, Caracteristica car, int nivel) {
        RequisitoPuesto r = new RequisitoPuesto();
        r.setPuesto(puesto);
        r.setCaracteristica(car);
        r.setNivelRequerido(nivel);
        requisitoPuestoRepo.save(r);
    }
}