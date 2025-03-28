package oh.josecvdev.pruebatecnica.empleados.infrastructure.controller;

import oh.josecvdev.pruebatecnica.empleados.application.request.RegistrarEmpleadoRequest;
import oh.josecvdev.pruebatecnica.empleados.application.service.IEmpleadoService;
import oh.josecvdev.pruebatecnica.empleados.infrastructure.entity.Empleado;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/empleado")
public class EmpleadoController {

    public final IEmpleadoService iEmpleadoService;

    public EmpleadoController (IEmpleadoService iEmpleadoService) {
        this.iEmpleadoService = iEmpleadoService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Empleado>> buscarTodos()
    {
        return new ResponseEntity<>(iEmpleadoService.obtenerEmpleados(), HttpStatus.OK);
    }

    @PostMapping("/registrar-empleado")
    @ResponseStatus(code = HttpStatus.OK)
    public Object registrarEmpleado(@RequestBody RegistrarEmpleadoRequest registrarEmpleadoRequest)
    {
        try
        {
            return ResponseEntity.ok(iEmpleadoService.registrarEmpleado(registrarEmpleadoRequest));
        } catch (IOException e)
        {
            return ResponseEntity.badRequest().body(Collections.singletonList(e.getMessage()));
        }
    }

    @PutMapping("/actualizar-empleado/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Object actualizarEmpleado(
            @PathVariable Long id,
            @RequestBody RegistrarEmpleadoRequest actualizarEmpleadoRequest) {
        try {
            return ResponseEntity.ok(iEmpleadoService.actualizarEmpleado(id, actualizarEmpleadoRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonList(e.getMessage()));
        }
    }

    @DeleteMapping("/eliminar-empleado/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Map<String, String>> eliminarEmpleado(@PathVariable Long id) {
        try {
            boolean eliminado = iEmpleadoService.eliminarEmpleado(id);
            if (eliminado) {
                return ResponseEntity.ok(Collections.singletonMap("mensaje", "Empleado eliminado correctamente."));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "Empleado no encontrado."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Error al eliminar el empleado."));
        }
    }

}
