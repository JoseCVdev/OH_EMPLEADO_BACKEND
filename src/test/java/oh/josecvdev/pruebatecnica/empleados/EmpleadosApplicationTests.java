package oh.josecvdev.pruebatecnica.empleados;

import oh.josecvdev.pruebatecnica.empleados.application.request.RegistrarEmpleadoRequest;
import oh.josecvdev.pruebatecnica.empleados.application.service.EmpleadoServiceImp;
import oh.josecvdev.pruebatecnica.empleados.domain.repository.IEmpleadoJpaRepository;
import oh.josecvdev.pruebatecnica.empleados.infrastructure.entity.Empleado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpleadoServiceImpTest {

	@Mock
	private IEmpleadoJpaRepository empleadoJpaRepository;

	@InjectMocks
	private EmpleadoServiceImp empleadoService;

	private Empleado empleado;

	@BeforeEach
	void setUp() {
		empleado = new Empleado();
		empleado.setIdEmpleado(1L);
		empleado.setNombres("Jose");
		empleado.setApellidoPaterno("Cueva");
		empleado.setApellidoMaterno("Villanueva");
		empleado.setDocumento("12345678");
		empleado.setFechaNacimiento(LocalDate.of(1990, 1, 1));
		empleado.setSalario(BigDecimal.valueOf(3000.0));
		empleado.setEstado(true);
	}

	@Test
	void obtenerEmpleados() {
		when(empleadoJpaRepository.obtenerEmpleadosActivos()).thenReturn(List.of(empleado));

		List<Empleado> empleados = empleadoService.obtenerEmpleados();

		assertNotNull(empleados);
		assertEquals(1, empleados.size());

		verify(empleadoJpaRepository, times(1)).obtenerEmpleadosActivos();
	}

	@Test
	void registrarEmpleado() {
		RegistrarEmpleadoRequest request = new RegistrarEmpleadoRequest();
		request.setNombres("Jose");
		request.setApellidoPaterno("Cueva");
		request.setApellidoMaterno("Villanueva");
		request.setDocumento("12345678");
		request.setFechaNacimiento(LocalDate.of(1990, 1, 1));
		request.setSalario(BigDecimal.valueOf(3000.0));

		when(empleadoJpaRepository.save(any(Empleado.class))).thenReturn(empleado);

		Empleado nuevoEmpleado = empleadoService.registrarEmpleado(request);

		assertNotNull(nuevoEmpleado);
		assertEquals("Jose", nuevoEmpleado.getNombres());

		verify(empleadoJpaRepository, times(1)).save(any(Empleado.class));
	}

	@Test
	void actualizarEmpleado() {
		RegistrarEmpleadoRequest request = new RegistrarEmpleadoRequest();
		request.setNombres("Juan");
		request.setApellidoPaterno("Perez");
		request.setApellidoMaterno("Alvites");
		request.setDocumento("87654321");
		request.setFechaNacimiento(LocalDate.of(1985, 5, 15));
		request.setSalario(BigDecimal.valueOf(3000.0));

		when(empleadoJpaRepository.findById(1L)).thenReturn(Optional.of(empleado));
		when(empleadoJpaRepository.save(any(Empleado.class))).thenReturn(empleado);

		Empleado actualizado = empleadoService.actualizarEmpleado(1L, request);

		assertNotNull(actualizado);
		assertEquals("Juan", actualizado.getNombres());

		verify(empleadoJpaRepository, times(1)).findById(1L);
		verify(empleadoJpaRepository, times(1)).save(any(Empleado.class));
	}

	@Test
	void eliminarEmpleado() {
		when(empleadoJpaRepository.findById(1L)).thenReturn(Optional.of(empleado));
		when(empleadoJpaRepository.save(any(Empleado.class))).thenReturn(empleado);

		boolean eliminado = empleadoService.eliminarEmpleado(1L);

		assertTrue(eliminado);
		assertFalse(empleado.isEstado());

		verify(empleadoJpaRepository, times(1)).findById(1L);
		verify(empleadoJpaRepository, times(1)).save(any(Empleado.class));
	}
}
