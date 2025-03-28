package oh.josecvdev.pruebatecnica.empleados.infrastructure.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ResponseException extends RuntimeException
{
    private HttpStatus httpStatus;
    private Object responseObject;
}

