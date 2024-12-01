package principal;

import dominio.Catalogo;
import excepciones.CatalogoException;
import interfaz.Menu;

public class Main {
    public static void main(String[] args) {
        try {
            Catalogo catalogo = new Catalogo();
            Menu menu = new Menu(catalogo);
            menu.mostrarMenu();
        } catch (CatalogoException e) {
            System.out.println("Error al iniciar el catálogo: " + e.getMessage());
        }
    }
}
