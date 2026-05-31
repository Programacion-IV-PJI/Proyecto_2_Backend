package una.ac.cr.bolsaempleo.dtos;

import lombok.Data;
import java.util.List;

public class OferenteDTOs {

    @Data
    public static class RegistroOferenteRequest {
        private String identificacion;
        private String nombre;
        private String primerApellido;
        private String nacionalidad;
        private String telefono;
        private String correo;
        private String residencia;
    }

    @Data
    public static class HabilidadRequest {
        private Long caracteristicaId;
        private int nivel;
    }

    @Data
    public static class HabilidadDTO {
        private Long id;
        private Long caracteristicaId;
        private String caracteristicaNombre;
        private String padreNombre;
        private int nivel;

        public HabilidadDTO(Long id, Long caracteristicaId, String caracteristicaNombre,
                            String padreNombre, int nivel) {
            this.id = id;
            this.caracteristicaId = caracteristicaId;
            this.caracteristicaNombre = caracteristicaNombre;
            this.padreNombre = padreNombre;
            this.nivel = nivel;
        }
    }

    @Data
    public static class OferentePerfilDTO {
        private Long id;
        private String nombre;
        private String primerApellido;
        private String identificacion;
        private String nacionalidad;
        private String telefono;
        private String correo;
        private String residencia;
        private String cvPath;
        private List<HabilidadDTO> habilidades;

        public OferentePerfilDTO(Long id, String nombre, String primerApellido,
                                 String identificacion, String nacionalidad,
                                 String telefono, String correo, String residencia,
                                 String cvPath, List<HabilidadDTO> habilidades) {
            this.id = id;
            this.nombre = nombre;
            this.primerApellido = primerApellido;
            this.identificacion = identificacion;
            this.nacionalidad = nacionalidad;
            this.telefono = telefono;
            this.correo = correo;
            this.residencia = residencia;
            this.cvPath = cvPath;
            this.habilidades = habilidades;
        }
    }
}
