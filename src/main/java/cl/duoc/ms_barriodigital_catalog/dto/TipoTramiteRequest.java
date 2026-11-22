package cl.duoc.ms_barriodigital_catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoTramiteRequest {

    @NotBlank(message = "El código es obligatorio")
    private String codigo;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String requisitos;

    @NotNull(message = "El plazo estimado es obligatorio")
    @PositiveOrZero(message = "El plazo no puede ser negativo")
    private Integer plazoEstimadoDias;

    @NotNull(message = "El cupo máximo es obligatorio")
    @PositiveOrZero(message = "El cupo máximo no puede ser negativo")
    private Integer cupoMaximo;

    // Opcional: al crear, si viene vacío parte igual al cupo máximo. En PUT sirve para reiniciar cupos a mano
    @PositiveOrZero(message = "El cupo disponible no puede ser negativo")
    private Integer cupoDisponible;

    // Opcional: si viene vacío se asume activo
    private Boolean activo;
}
