package cl.duoc.ms_barriodigital_catalog.service.impl;

import cl.duoc.ms_barriodigital_catalog.dto.ReservaResponse;
import cl.duoc.ms_barriodigital_catalog.dto.TipoTramiteRequest;
import cl.duoc.ms_barriodigital_catalog.dto.TipoTramiteResponse;
import cl.duoc.ms_barriodigital_catalog.exception.CodigoDuplicadoException;
import cl.duoc.ms_barriodigital_catalog.exception.RecursoNoEncontradoException;
import cl.duoc.ms_barriodigital_catalog.model.TipoTramite;
import cl.duoc.ms_barriodigital_catalog.repository.TipoTramiteRepository;
import cl.duoc.ms_barriodigital_catalog.service.TipoTramiteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TipoTramiteServiceImpl implements TipoTramiteService {

    private final TipoTramiteRepository repository;

    public TipoTramiteServiceImpl(TipoTramiteRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TipoTramiteResponse> listar() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public TipoTramiteResponse obtener(Long id) {
        return toResponse(buscarPorId(id));
    }

    @Override
    public TipoTramiteResponse crear(TipoTramiteRequest request) {
        if (repository.existsByCodigo(request.getCodigo())) {
            throw new CodigoDuplicadoException("Ya existe un tipo de trámite con código " + request.getCodigo());
        }
        TipoTramite tipo = new TipoTramite();
        copiarDatos(request, tipo);
        // Si no se indica, parte con todos los cupos disponibles
        tipo.setCupoDisponible(request.getCupoDisponible() != null ? request.getCupoDisponible() : request.getCupoMaximo());
        tipo.setActivo(request.getActivo() != null ? request.getActivo() : true);
        return toResponse(repository.save(tipo));
    }

    @Override
    public TipoTramiteResponse actualizar(Long id, TipoTramiteRequest request) {
        TipoTramite tipo = buscarPorId(id);
        // Si cambia el código, no puede chocar con otro existente
        if (!tipo.getCodigo().equals(request.getCodigo()) && repository.existsByCodigo(request.getCodigo())) {
            throw new CodigoDuplicadoException("Ya existe un tipo de trámite con código " + request.getCodigo());
        }
        copiarDatos(request, tipo);
        // Solo se tocan si vienen en el request (cupoDisponible = reinicio manual de cupos)
        if (request.getCupoDisponible() != null) {
            tipo.setCupoDisponible(request.getCupoDisponible());
        }
        if (request.getActivo() != null) {
            tipo.setActivo(request.getActivo());
        }
        return toResponse(repository.save(tipo));
    }

    // @Transactional es obligatorio: el UPDATE con @Modifying necesita una transacción
    @Override
    @Transactional
    public ReservaResponse reservar(String codigo) {
        if (!repository.existsByCodigo(codigo)) {
            throw new RecursoNoEncontradoException("No existe el tipo de trámite " + codigo);
        }
        boolean reservado = repository.descontarCupo(codigo) == 1;
        // Se lee DESPUÉS del UPDATE para devolver el cupo ya descontado
        TipoTramite tipo = repository.findByCodigo(codigo).orElseThrow();
        return new ReservaResponse(codigo, reservado, tipo.getCupoDisponible());
    }

    private TipoTramite buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el tipo de trámite con id " + id));
    }

    // Campos comunes entre crear y actualizar
    private void copiarDatos(TipoTramiteRequest request, TipoTramite tipo) {
        tipo.setCodigo(request.getCodigo());
        tipo.setNombre(request.getNombre());
        tipo.setRequisitos(request.getRequisitos());
        tipo.setPlazoEstimadoDias(request.getPlazoEstimadoDias());
        tipo.setCupoMaximo(request.getCupoMaximo());
    }

    private TipoTramiteResponse toResponse(TipoTramite tipo) {
        return new TipoTramiteResponse(
                tipo.getId(),
                tipo.getCodigo(),
                tipo.getNombre(),
                tipo.getRequisitos(),
                tipo.getPlazoEstimadoDias(),
                tipo.getCupoMaximo(),
                tipo.getCupoDisponible(),
                tipo.getActivo());
    }
}
