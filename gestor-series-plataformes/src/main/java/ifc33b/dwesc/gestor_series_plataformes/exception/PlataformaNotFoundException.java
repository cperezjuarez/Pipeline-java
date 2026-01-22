package ifc33b.dwesc.gestor_series_plataformes.exception;

public class PlataformaNotFoundException extends RuntimeException {
    public PlataformaNotFoundException(Long id) {
        super("No se ha encontrado la plataforma con la ID: " + id);
    }
}
