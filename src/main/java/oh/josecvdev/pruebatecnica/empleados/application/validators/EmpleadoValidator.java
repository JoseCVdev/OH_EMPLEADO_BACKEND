package oh.josecvdev.pruebatecnica.empleados.application.validators;

import oh.josecvdev.pruebatecnica.empleados.application.request.RegistrarEmpleadoRequest;
import oh.josecvdev.pruebatecnica.empleados.application.response.ErrorMensajeResponse;
import oh.josecvdev.pruebatecnica.empleados.domain.repository.IEmpleadoJpaRepository;
import oh.josecvdev.pruebatecnica.empleados.infrastructure.entity.Empleado;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EmpleadoValidator {

    public static Map<String, Object> registrarEmpleadoValidator(
            RegistrarEmpleadoRequest registrarEmpleadoRequest,
            IEmpleadoJpaRepository iEmpleadoJpaRepository) {

        Map<String, Object> resultado = new HashMap<>();
        ErrorMensajeResponse errorValidaciones = new ErrorMensajeResponse("VALIDACIONES", false, null);

        Optional<Empleado> empleadoExistente = iEmpleadoJpaRepository.findByDocumentoAndEstado(registrarEmpleadoRequest.getDocumento(), true);

        if (empleadoExistente.isPresent()) {
            errorValidaciones.setExisteError(true);
            errorValidaciones.setMensaje("El documento " + registrarEmpleadoRequest.getDocumento() + " ya existe.");
            resultado.put("errorValidaciones", errorValidaciones);
        } else {
            resultado.put("success", errorValidaciones);
        }
        return resultado;
    }

    public static Map<String, Object> actualizarEmpleadoValidator(
            long idEmpleado,
            RegistrarEmpleadoRequest actualizarEmpleadoRequest,
            IEmpleadoJpaRepository iEmpleadoJpaRepository) {

        Map<String, Object> resultado = new HashMap<>();
        ErrorMensajeResponse errorValidaciones = new ErrorMensajeResponse("VALIDACIONES", false, null);

        Optional<Empleado> empleadoExistente = iEmpleadoJpaRepository.findByDocumentoAndEstado(actualizarEmpleadoRequest.getDocumento(), true);

        if (empleadoExistente.isPresent() && empleadoExistente.get().getIdEmpleado() != idEmpleado) {
            errorValidaciones.setExisteError(true);
            errorValidaciones.setMensaje("El documento " + actualizarEmpleadoRequest.getDocumento() + " ya está en uso por otro empleado.");
            resultado.put("errorValidaciones", errorValidaciones);
        } else {
            resultado.put("success", errorValidaciones);
        }
        return resultado;
    }

}
