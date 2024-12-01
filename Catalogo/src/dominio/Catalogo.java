package dominio;

import excepciones.CatalogoException;
import java.util.ArrayList;
import java.io.*;

public class Catalogo implements Serializable {
    private ArrayList<Zapatillas> listaZapatillas;
    private static final String FILE_NAME = "catalogo.dat";

    public Catalogo() throws CatalogoException {
        leer();
    }

    public void addZapatilla(Zapatillas zapatilla) throws CatalogoException {
        try {
            listaZapatillas.add(zapatilla);
            escribir();
        } catch (Exception e) {
            throw new CatalogoException("Error al añadir la zapatilla", e);
        }
    }

    public boolean eliminarZapatilla(int id) throws CatalogoException {
        try {
            Zapatillas zapatillaEliminar = buscarZapatillaPorId(id);
            if (zapatillaEliminar != null) {
                listaZapatillas.remove(zapatillaEliminar);
                escribir();
                return true;
            }
        } catch (Exception e) {
            throw new CatalogoException("Error al eliminar la zapatilla", e);
        }
        return false;
    }

    public boolean modificarZapatilla(int id, Zapatillas nuevaZapatilla) throws CatalogoException {
        try {
            Zapatillas zapatillaExistente = buscarZapatillaPorId(id);
            if (zapatillaExistente != null) {
                zapatillaExistente.setMarca(nuevaZapatilla.getMarca());
                zapatillaExistente.setModelo(nuevaZapatilla.getModelo());
                zapatillaExistente.setColor(nuevaZapatilla.getColor());
                zapatillaExistente.setTalla(nuevaZapatilla.getTalla());
                zapatillaExistente.setPrecio(nuevaZapatilla.getPrecio());
                escribir(); // Guardar después de modificar
                return true;
            }
        } catch (Exception e) {
            throw new CatalogoException("Error al modificar la zapatilla", e);
        }
        return false;
    }

    public Zapatillas buscarZapatillaPorId(int id) throws CatalogoException {
        try {
            for (Zapatillas zapatilla : listaZapatillas) {
                if (zapatilla.getId() == id) {
                    return zapatilla;
                }
            }
        } catch (Exception e) {
            throw new CatalogoException("Error al buscar la zapatilla por ID", e);
        }
        return null;
    }

    public void listarZapatillas() throws CatalogoException {
        try {
            System.out.printf("%-4s | %-20s | %-30s | %-10s | %-6s | $%-8s | \n",
                    "ID", "Marca", "Modelo", "Color", "Talla", "Precio");
            System.out.println("------------------------------------------------------------------------------------------------");
            for (Zapatillas zapatillas : listaZapatillas) {
                System.out.printf("%-4d | %-20s | %-30s | %-10s | %-6s | $%-8.2f\n",
                        zapatillas.getId(),
                        zapatillas.getMarca(),
                        zapatillas.getModelo(),
                        zapatillas.getColor(),
                        zapatillas.getTalla(),
                        zapatillas.getPrecio());
            }
        } catch (Exception e) {
            throw new CatalogoException("Error al listar las zapatillas", e);
        }
    }

    public boolean esIdUnico(int id) throws CatalogoException {
        try {
            return buscarZapatillaPorId(id) == null;
        } catch (Exception e) {
            throw new CatalogoException("Error al verificar si el ID es único", e);
        }
    }

    public ArrayList<Zapatillas> consultaZapatilla(String marca, String modelo) throws CatalogoException {
        ArrayList<Zapatillas> resultado = new ArrayList<>();
        try {
            for (Zapatillas zapatilla : listaZapatillas) {
                if ((marca != null && zapatilla.getMarca().equalsIgnoreCase(marca)) ||
                        (modelo != null && zapatilla.getModelo().equalsIgnoreCase(modelo))) {
                    resultado.add(zapatilla);
                }
            }
        } catch (Exception e) {
            throw new CatalogoException("Error al consultar las zapatillas", e);
        }
        return resultado;
    }

    public void escribir() throws CatalogoException {
        try (ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oo.writeObject(listaZapatillas);
        } catch (IOException e) {
            throw new CatalogoException("Error al guardar los datos en el fichero", e);
        }
    }

    public void leer() throws CatalogoException {
        try (ObjectInputStream oi = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            listaZapatillas = (ArrayList<Zapatillas>) oi.readObject();
        } catch (IOException | ClassNotFoundException e) {
            listaZapatillas = new ArrayList<>();
            throw new CatalogoException("Error al leer los datos del fichero", e);
        }
    }

    public ArrayList<Zapatillas> getListaZapatillas() {
        return listaZapatillas;
    }

    public boolean existeZapatilla(int id) throws CatalogoException {
        try {
            return buscarZapatillaPorId(id) != null;
        } catch (Exception e) {
            throw new CatalogoException("Error al verificar si la zapatilla existe", e);
        }
    }
}