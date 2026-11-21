package cl.duoc.ms_barriodigital_catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoTramiteResponse {

    private Long id;
    private String codigo;
    private String nombre;
    private String requisitos;
    private Integer plazoEstimadoDias;
    private Integer cupoMaximo;
    private Integer cupoDisponible;
    private Boolean activo;
}
