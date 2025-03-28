package oh.josecvdev.pruebatecnica.empleados.domain.repository;

import oh.josecvdev.pruebatecnica.empleados.infrastructure.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IEmpleadoJpaRepository extends JpaRepository<Empleado, Long> {
    @Query("SELECT e FROM Empleado e WHERE e.estado = true ORDER BY e.idEmpleado DESC")
    List<Empleado> obtenerEmpleadosActivos();
    Optional<Empleado> findByDocumentoAndEstado(String vDocumento, boolean bEstado);
}
