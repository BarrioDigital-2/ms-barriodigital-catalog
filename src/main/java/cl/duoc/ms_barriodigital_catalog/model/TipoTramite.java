package cl.duoc.ms_barriodigital_catalog.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Tipo de trámite que ofrece el municipio, con sus requisitos y cupos.
 * El campo "codigo" es el mismo valor que ms-requests guarda en "tipoTramite".
 */
@Entity
@Table(name = "tipo_tramite")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoTramite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Identificador de negocio (ej. PERMISO_OBRAS). Único: es la clave que usa ms-requests
    @Column(nullable = false, unique = true, length = 50)
    private String codigo;

    @Column(nullable = false, length = 100)
    private String nombre;

    // Texto libre con los documentos o condiciones que se piden
    @Column(length = 1000)
    private String requisitos;

    @Column(name = "plazo_estimado_dias", nullable = false)
    private Integer plazoEstimadoDias;

    // Cupo total configurado; sirve de referencia para reiniciar a mano con PUT
    @Column(name = "cupo_maximo", nullable = false)
    private Integer cupoMaximo;

    // Cupos que quedan; se descuenta de a uno al admitir un trámite
    @Column(name = "cupo_disponible", nullable = false)
    private Integer cupoDisponible;

    // Un tipo inactivo no acepta reservas, pero no se borra (conserva el historial)
    @Column(nullable = false)
    @Builder.Default
    private Boolean activo = true;
}
