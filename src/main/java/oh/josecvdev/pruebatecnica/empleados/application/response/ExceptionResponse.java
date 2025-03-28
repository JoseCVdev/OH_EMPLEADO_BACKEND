package oh.josecvdev.pruebatecnica.empleados.application.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse
{
    private LocalDateTime datetime;
    private String type;
    private String message;
    private String details;
}
