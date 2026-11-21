package cl.duoc.ms_barriodigital_catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaResponse {

    private String codigo;
    private boolean reservado;
    private Integer cupoRestante;
}
