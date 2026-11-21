package cl.duoc.ms_barriodigital_catalog.controller;

import cl.duoc.ms_barriodigital_catalog.dto.ReservaResponse;
import cl.duoc.ms_barriodigital_catalog.dto.TipoTramiteRequest;
import cl.duoc.ms_barriodigital_catalog.dto.TipoTramiteResponse;
import cl.duoc.ms_barriodigital_catalog.service.TipoTramiteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog/procedures")
public class TipoTramiteController {

    private final TipoTramiteService service;

    public TipoTramiteController(TipoTramiteService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TipoTramiteResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoTramiteResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @PostMapping
    public ResponseEntity<TipoTramiteResponse> crear(@Valid @RequestBody TipoTramiteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoTramiteResponse> actualizar(@PathVariable Long id,
                                                          @Valid @RequestBody TipoTramiteRequest request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }

    // Lo llama ms-requests al admitir un trámite: 200 si reservó, 409 si no hay cupo
    @PostMapping("/{tipo}/reserve")
    public ResponseEntity<ReservaResponse> reservar(@PathVariable String tipo) {
        ReservaResponse respuesta = service.reservar(tipo);
        HttpStatus estado = respuesta.isReservado() ? HttpStatus.OK : HttpStatus.CONFLICT;
        return ResponseEntity.status(estado).body(respuesta);
    }
}
