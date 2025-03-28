package oh.josecvdev.pruebatecnica.empleados.application.service;

import oh.josecvdev.pruebatecnica.empleados.application.request.RegistrarEmpleadoRequest;
import oh.josecvdev.pruebatecnica.empleados.application.response.ErrorMensajeResponse;
import oh.josecvdev.pruebatecnica.empleados.application.validators.EmpleadoValidator;
import oh.josecvdev.pruebatecnica.empleados.domain.repository.IEmpleadoJpaRepository;
import oh.josecvdev.pruebatecnica.empleados.infrastructure.entity.Empleado;
import oh.josecvdev.pruebatecnica.empleados.infrastructure.exception.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmpleadoServiceImp implements IEmpleadoService{

    private final IEmpleadoJpaRepository iEmpleadoJpaRepository;

    public EmpleadoServiceImp (IEmpleadoJpaRepository iEmpleadoJpaRepository) {
        this.iEmpleadoJpaRepository = iEmpleadoJpaRepository;
    }

    @Override
    public List<Empleado> obtenerEmpleados() {
        return iEmpleadoJpaRepository.obtenerEmpleadosActivos();
    }

    @Override
    public Empleado registrarEmpleado(RegistrarEmpleadoRequest registrarEmpleadoRequest) {
        Map<String, Object> validacionRegistroEmpleado = EmpleadoValidator.registrarEmpleadoValidator(registrarEmpleadoRequest, iEmpleadoJpaRepository);
        ErrorMensajeResponse errorMensaje = (ErrorMensajeResponse) validacionRegistroEmpleado.get("errorValidaciones");
        if (Objects.nonNull(errorMensaje))
        {
            throw new BadRequestException(errorMensaje.getTipo(), errorMensaje.getMensaje());
        }

        // Calcular edad
        int edad = calcularEdad(registrarEmpleadoRequest.getFechaNacimiento());

        // Crear instancia del nuevo empleado
        Empleado nuevoEmpleado = new Empleado();
        nuevoEmpleado.setDocumento(registrarEmpleadoRequest.getDocumento());
        nuevoEmpleado.setNombres(registrarEmpleadoRequest.getNombres());
        nuevoEmpleado.setApellidoPaterno(registrarEmpleadoRequest.getApellidoPaterno());
        nuevoEmpleado.setApellidoMaterno(registrarEmpleadoRequest.getApellidoMaterno());
        nuevoEmpleado.setEdad(edad);
        nuevoEmpleado.setFechaNacimiento(registrarEmpleadoRequest.getFechaNacimiento());
        nuevoEmpleado.setSalario(registrarEmpleadoRequest.getSalario());
        nuevoEmpleado.setEstado(true); // Se registra como activo

        // Guardar el nuevo empleado en la base de datos
        return iEmpleadoJpaRepository.save(nuevoEmpleado);
    }

    @Override
    public Empleado actualizarEmpleado(long idEmpleado, RegistrarEmpleadoRequest actualizarEmpleadoRequest) {
        Optional<Empleado> empleadoExistente = iEmpleadoJpaRepository.findById(idEmpleado);
        if (empleadoExistente.isEmpty()) {
            throw new BadRequestException("VALIDACION", "El empleado con ID " + idEmpleado + " no existe.");
        }

        Map<String, Object> validacionRegistroEmpleado = EmpleadoValidator.actualizarEmpleadoValidator(idEmpleado, actualizarEmpleadoRequest, iEmpleadoJpaRepository);
        ErrorMensajeResponse errorMensaje = (ErrorMensajeResponse) validacionRegistroEmpleado.get("errorValidaciones");
        if (Objects.nonNull(errorMensaje)) {
            throw new BadRequestException(errorMensaje.getTipo(), errorMensaje.getMensaje());
        }

        Empleado empleado = empleadoExistente.get();
        empleado.setDocumento(actualizarEmpleadoRequest.getDocumento());
        empleado.setNombres(actualizarEmpleadoRequest.getNombres());
        empleado.setApellidoPaterno(actualizarEmpleadoRequest.getApellidoPaterno());
        empleado.setApellidoMaterno(actualizarEmpleadoRequest.getApellidoMaterno());
        empleado.setEdad(calcularEdad(actualizarEmpleadoRequest.getFechaNacimiento()));
        empleado.setFechaNacimiento(actualizarEmpleadoRequest.getFechaNacimiento());
        empleado.setSalario(actualizarEmpleadoRequest.getSalario());

        return iEmpleadoJpaRepository.save(empleado);
    }

    @Override
    public boolean eliminarEmpleado(Long id) {
        Optional<Empleado> empleadoOptional = iEmpleadoJpaRepository.findById(id);
        if (empleadoOptional.isPresent()) {
            Empleado empleado = empleadoOptional.get();
            empleado.setEstado(false);
            iEmpleadoJpaRepository.save(empleado);
            return true;
        }
        return false;
    }

    private int calcularEdad(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) {
            return 0;
        }
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

}
