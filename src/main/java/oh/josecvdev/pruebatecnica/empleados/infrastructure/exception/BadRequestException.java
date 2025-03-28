package oh.josecvdev.pruebatecnica.empleados.infrastructure.exception;

public class BadRequestException extends RuntimeException
{
    private final String type;

    public BadRequestException(String type, String msg)
    {
        super(msg);
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

