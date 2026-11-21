package cl.duoc.ms_barriodigital_catalog.service;

import cl.duoc.ms_barriodigital_catalog.dto.ReservaResponse;
import cl.duoc.ms_barriodigital_catalog.dto.TipoTramiteRequest;
import cl.duoc.ms_barriodigital_catalog.dto.TipoTramiteResponse;

import java.util.List;

public interface TipoTramiteService {

    List<TipoTramiteResponse> listar();

    TipoTramiteResponse obtener(Long id);

    TipoTramiteResponse crear(TipoTramiteRequest request);

    TipoTramiteResponse actualizar(Long id, TipoTramiteRequest request);

    // Descuenta un cupo del tipo indicado; lo llama ms-requests al admitir un trámite
    ReservaResponse reservar(String codigo);
}
