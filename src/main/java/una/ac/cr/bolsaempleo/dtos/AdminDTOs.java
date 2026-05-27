package una.ac.cr.bolsaempleo.dtos;

import lombok.Data;

public class AdminDTOs {

    @Data
    public static class LoginRequest {
        private String identificacion;
        private String clave;
    }

    @Data
    public static class LoginResponse {
        private String token;
        private String rol;
        private String nombre;

        public LoginResponse(String token, String rol, String nombre) {
            this.token = token;
            this.rol = rol;
            this.nombre = nombre;
        }
    }

    @Data
    public static class CrearCaracteristicaRequest {
        private String nombre;
        private Long padreId;
    }

    @Data
    public static class CaracteristicaDTO {
        private Long id;
        private String nombre;
        private Long padreId;
        private String padreNombre;
        private boolean esRaiz;

        public CaracteristicaDTO(Long id, String nombre, Long padreId, String padreNombre) {
            this.id = id;
            this.nombre = nombre;
            this.padreId = padreId;
            this.padreNombre = padreNombre;
            this.esRaiz = padreId == null;
        }
    }

    @Data
    public static class EmpresaPendienteDTO {
        private Long id;
        private String nombre;
        private String correo;
        private String localizacion;
        private String telefono;
        private String descripcion;

        public EmpresaPendienteDTO(Long id, String nombre, String correo,
                                    String localizacion, String telefono, String descripcion) {
            this.id = id;
            this.nombre = nombre;
            this.correo = correo;
            this.localizacion = localizacion;
            this.telefono = telefono;
            this.descripcion = descripcion;
        }
    }

    @Data
    public static class OferentePendienteDTO {
        private Long id;
        private String nombre;
        private String primerApellido;
        private String correo;
        private String identificacion;
        private String nacionalidad;
        private String telefono;
        private String residencia;

        public OferentePendienteDTO(Long id, String nombre, String primerApellido,
                                     String correo, String identificacion, String nacionalidad,
                                     String telefono, String residencia) {
            this.id = id;
            this.nombre = nombre;
            this.primerApellido = primerApellido;
            this.correo = correo;
            this.identificacion = identificacion;
            this.nacionalidad = nacionalidad;
            this.telefono = telefono;
            this.residencia = residencia;
        }
    }
}