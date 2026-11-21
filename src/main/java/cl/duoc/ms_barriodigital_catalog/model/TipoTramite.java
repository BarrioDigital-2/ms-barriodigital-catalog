package cl.duoc.ms_barriodigital_catalog.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Tipo de trámite del municipio con sus cupos. "codigo" es el mismo valor que usa ms-requests en tipoTramite
@Entity
@Table(name = "tipo_tramite")
@Getter
@Setter
@NoArgsConstructor
public class TipoTramite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String codigo;

    private String nombre;
    private String requisitos;
    private Integer plazoEstimadoDias;
    private Integer cupoMaximo;
    private Integer cupoDisponible;
    private Boolean activo = true;
}
