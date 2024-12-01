package excepciones;

public class CatalogoException extends Exception {
    public CatalogoException(String mensaje) {
        super(mensaje);
    }

    public CatalogoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}