package una.ac.cr.bolsaempleo.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Entity
@Table(name = "oferente")
@Data
@NoArgsConstructor
public class Oferente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identificacion;
    private String nombre;

    @Column(name = "primer_apellido")
    private String primerApellido;

    private String nacionalidad;
    private String telefono;
    private String correo;
    private String residencia;
    private String password;

    @Column(name = "cv_path")
    private String cvPath;

    private boolean aprobado;

    @OneToMany(mappedBy = "oferente", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Habilidad> habilidades;
}