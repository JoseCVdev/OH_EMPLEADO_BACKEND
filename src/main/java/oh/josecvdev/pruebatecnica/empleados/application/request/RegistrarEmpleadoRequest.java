package oh.josecvdev.pruebatecnica.empleados.application.request;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class RegistrarEmpleadoRequest {
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String documento;
    private LocalDate fechaNacimiento;
    private BigDecimal salario;
}
