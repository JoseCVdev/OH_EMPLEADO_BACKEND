package oh.josecvdev.pruebatecnica.empleados.application.response;


public class ErrorMensajeResponse
{
    private String tipo;
    private boolean existeError;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isExisteError() {
        return existeError;
    }

    public void setExisteError(boolean existeError) {
        this.existeError = existeError;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    private String mensaje;

    public ErrorMensajeResponse(String tipo, boolean existeError, String mensaje) {
        this.tipo = tipo;
        this.existeError = existeError;
        this.mensaje = mensaje;
    }
}

