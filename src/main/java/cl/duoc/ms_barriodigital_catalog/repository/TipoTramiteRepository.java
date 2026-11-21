package cl.duoc.ms_barriodigital_catalog.repository;

import cl.duoc.ms_barriodigital_catalog.model.TipoTramite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TipoTramiteRepository extends JpaRepository<TipoTramite, Long> {

    Optional<TipoTramite> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    // Descuenta un cupo en una sola sentencia (atómica). Devuelve 1 si descontó, 0 si no había cupo
    @Modifying
    @Query("UPDATE TipoTramite t SET t.cupoDisponible = t.cupoDisponible - 1 WHERE t.codigo = ?1 AND t.activo = true AND t.cupoDisponible > 0")
    int descontarCupo(String codigo);
}
