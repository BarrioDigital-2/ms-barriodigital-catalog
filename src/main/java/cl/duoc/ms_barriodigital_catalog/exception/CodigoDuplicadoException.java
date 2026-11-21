package cl.duoc.ms_barriodigital_catalog.exception;

// Se lanza al crear un tipo de trámite con un código que ya existe → 409
public class CodigoDuplicadoException extends RuntimeException {

    public CodigoDuplicadoException(String mensaje) {
        super(mensaje);
    }
}
