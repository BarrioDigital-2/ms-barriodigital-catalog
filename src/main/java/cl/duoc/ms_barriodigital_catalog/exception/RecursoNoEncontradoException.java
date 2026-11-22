package cl.duoc.ms_barriodigital_catalog.exception;

// Se lanza cuando un tipo de trámite no existe → 404
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
