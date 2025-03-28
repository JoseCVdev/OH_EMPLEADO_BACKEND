package oh.josecvdev.pruebatecnica.empleados.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "TBL_EMPLEADO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private long idEmpleado;

    @JsonProperty
    private String documento;

    @JsonProperty
    private String nombres;

    @JsonProperty
    private String apellidoPaterno;

    @JsonProperty
    private String apellidoMaterno;

    @JsonProperty
    private int edad;

    @JsonProperty
    private LocalDate fechaNacimiento;

    @Column(precision = 18, scale = 2)
    @JsonProperty
    private BigDecimal salario;

    @JsonProperty
    private boolean estado;

}
