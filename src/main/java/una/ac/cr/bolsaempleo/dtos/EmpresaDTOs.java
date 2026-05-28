package una.ac.cr.bolsaempleo.dtos;

import lombok.Data;
import java.util.List;

public class EmpresaDTOs {

    @Data
    public static class RegistroEmpresaRequest {
        private String nombre;
        private String localizacion;
        private String correo;
        private String telefono;
        private String descripcion;
    }

    @Data
    public static class CrearPuestoRequest {
        private String descripcion;
        private Double salario;
        private boolean publico;
        private List<RequisitoDTO> requisitos;
    }

    @Data
    public static class RequisitoDTO {
        private Long caracteristicaId;
        private int nivelRequerido;
    }

    @Data
    public static class PuestoResumenDTO {
        private Long id;
        private String descripcion;
        private Double salario;
        private boolean publico;
        private boolean activo;
        private String empresaNombre;
        private List<RequisitoDetalleDTO> requisitos;

        public PuestoResumenDTO(Long id, String descripcion, Double salario,
                                boolean publico, boolean activo, String empresaNombre,
                                List<RequisitoDetalleDTO> requisitos) {
            this.id = id;
            this.descripcion = descripcion;
            this.salario = salario;
            this.publico = publico;
            this.activo = activo;
            this.empresaNombre = empresaNombre;
            this.requisitos = requisitos;
        }
    }

    @Data
    public static class RequisitoDetalleDTO {
        private Long caracteristicaId;
        private String caracteristicaNombre;
        private String padreNombre;
        private int nivelRequerido;

        public RequisitoDetalleDTO(Long caracteristicaId, String caracteristicaNombre,
                                   String padreNombre, int nivelRequerido) {
            this.caracteristicaId = caracteristicaId;
            this.caracteristicaNombre = caracteristicaNombre;
            this.padreNombre = padreNombre;
            this.nivelRequerido = nivelRequerido;
        }
    }
}