package una.ac.cr.bolsaempleo.dtos;

import lombok.Getter;

@Getter
public class CandidatoResultadoDTO {

    private Long oferenteId;
    private String nombre;
    private String primerApellido;
    private String correo;
    private String identificacion;
    private String telefono;
    private String residencia;
    private String cvPath;
    private int cumplidas;
    private int total;
    private double porcentaje;

    public CandidatoResultadoDTO(Long oferenteId, String nombre, String primerApellido,
                                  String correo, String identificacion, String telefono,
                                  String residencia, String cvPath, int cumplidas, int total) {
        this.oferenteId = oferenteId;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.correo = correo;
        this.identificacion = identificacion;
        this.telefono = telefono;
        this.residencia = residencia;
        this.cvPath = cvPath;
        this.cumplidas = cumplidas;
        this.total = total;
        this.porcentaje = total == 0 ? 100.0 : Math.round((cumplidas * 100.0 / total) * 100.0) / 100.0;
    }
}