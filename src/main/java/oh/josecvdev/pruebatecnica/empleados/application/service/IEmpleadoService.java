package oh.josecvdev.pruebatecnica.empleados.application.service;

import oh.josecvdev.pruebatecnica.empleados.application.request.RegistrarEmpleadoRequest;
import oh.josecvdev.pruebatecnica.empleados.infrastructure.entity.Empleado;

import java.io.IOException;
import java.util.List;

public interface IEmpleadoService {
    List<Empleado> obtenerEmpleados();

    Empleado registrarEmpleado(RegistrarEmpleadoRequest registrarEmpleadoRequest) throws IOException;

    Empleado actualizarEmpleado(long idEmpleado, RegistrarEmpleadoRequest actualizarEmpleadoRequest) throws IOException;

    boolean eliminarEmpleado(Long id);

}
