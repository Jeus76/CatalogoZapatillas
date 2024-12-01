package interfaz;

import dominio.Catalogo;
import dominio.Zapatillas;
import excepciones.CatalogoException;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private final Catalogo catalogo;
    private final Scanner scanner;

    public Menu(Catalogo catalogo) {
        this.catalogo = catalogo;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion;
        System.out.println("\nBienvenido al Catalogo de Zapatillas!");
        System.out.println("Por favor escriba un numero dependiendo de la accion que desea hacer: ");
        do {
            System.out.println("\n[1] Anadir Zapatillas");
            System.out.println("[2] Modificar Zapatillas");
            System.out.println("[3] Eliminar Zapatillas");
            System.out.println("[4] Listar Zapatillas");
            System.out.println("[5] Consultar Zapatillas");
            System.out.println("[6] Salir");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        addZapatillas();
                        break;
                    case 2:
                        modZapatillas();
                        break;
                    case 3:
                        rmZapatillas();
                        break;
                    case 4:
                        lsZapatillas();
                        break;
                    case 5:
                        consultaZapatilla();
                        break;
                    case 6:
                        System.out.println("Saliendo");
                        break;
                    default:
                        System.out.println("Opcion no valida");
                }
            } catch (InputMismatchException e) {
                System.out.println("Mensaje de entrada no valido, ingrese un numero.");
                scanner.nextLine();
                opcion = -1;
            }

        } while (opcion != 6);
    }

    private void addZapatillas() {
        try {
            System.out.println("Ingrese el ID del Zapatilla: ");
            int id = obtenerIdZapatilla();
            if (id <= 0) return;

            if (!catalogo.esIdUnico(id)) {
                System.out.println("️ El ID ya está en uso. Por favor, ingrese otro ID.");
                return;
            }

            System.out.print("Ingrese Marca: ");
            String marca = scanner.nextLine();
            System.out.print("Ingrese Modelo: ");
            String modelo = scanner.nextLine();
            System.out.print("Ingrese Color: ");
            String color = scanner.nextLine();
            System.out.print("Ingrese talla (En formato ES): ");
            String talla = scanner.nextLine();
            System.out.print("Ingrese Precio (mayor que 0): ");
            double precio = scanner.nextDouble();

            if (precio <= 0) {
                System.out.println("El precio debe ser mayor que 0.");
                return;
            }

            Zapatillas zapatillas = new Zapatillas(id, marca, modelo, color, talla, precio);
            catalogo.addZapatilla(zapatillas);
            System.out.println("Zapatillas agregadas correctamente.");
        } catch (InputMismatchException e) {
            System.out.println("Entrada no válida. Por favor, ingrese los datos correctamente.");
            scanner.nextLine(); // Limpiar el buffer
        } catch (CatalogoException e) {
            System.out.println("Error al añadir zapatillas: " + e.getMessage());
        }
    }

    private void modZapatillas() {
        try {
            if (catalogo.getListaZapatillas().isEmpty()) {
                System.out.println("No hay zapatillas en el catalogo para modificar.");
                return;
            }

            System.out.println("Ingrese el ID del Zapatilla que desea editar: ");
            int id = obtenerIdZapatilla();
            if (id <= 0) return;

            if (!catalogo.existeZapatilla(id)) {
                System.out.println("No se han encontrado las zapatillas con ese ID");
                return;
            }

            System.out.print("Ingrese nueva marca: ");
            String marca = scanner.nextLine();
            System.out.print("Ingrese nuevo modelo: ");
            String modelo = scanner.nextLine();
            System.out.print("Ingrese nuevo color: ");
            String color = scanner.nextLine();
            System.out.print("Ingrese nueva talla (En formato ES): ");
            String talla = scanner.nextLine();
            System.out.print("Ingrese nuevo precio (mayor que 0): ");
            double precio = scanner.nextDouble();

            if (precio <= 0) {
                System.out.println("El precio debe ser mayor que 0.");
                return;
            }

            Zapatillas nuevaZapatilla = new Zapatillas(id, marca, modelo, color, talla, precio);
            boolean modificado = catalogo.modificarZapatilla(id, nuevaZapatilla);
            if (modificado) {
                System.out.println("Zapatilla modificada correctamente.");
            } else {
                System.out.println("No se han encontrado las zapatillas con ese ID.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada no válida. Por favor, ingrese los datos correctamente.");
            scanner.nextLine(); // Limpiar el buffer
        } catch (CatalogoException e) {
            System.out.println("Error al modificar zapatillas: " + e.getMessage());
        }
    }

    private void rmZapatillas() {
        try {
            if (catalogo.getListaZapatillas().isEmpty()) {
                System.out.println("No hay zapatillas en el catalogo que se puedan eliminar.");
                return;
            }

            System.out.println("Ingrese el ID del Zapatilla que desea eliminar: ");
            int id = obtenerIdZapatilla();
            if (id <= 0) return;

            boolean eliminado = catalogo.eliminarZapatilla(id);
            if (eliminado) {
                System.out.println("Zapatilla eliminada correctamente.");
            } else {
                System.out.println("No se han encontrado las zapatillas con ese ID.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada no válida. Por favor, ingrese los datos correctamente.");
            scanner.nextLine(); // Limpiar el buffer
        } catch (CatalogoException e) {
            System.out.println("Error al eliminar zapatillas: " + e.getMessage());
        }
    }

    private void lsZapatillas() {
        try {
            if (catalogo.getListaZapatillas().isEmpty()) {
                System.out.println("No hay zapatillas en el catalogo para listar.");
            } else {
                catalogo.listarZapatillas();
            }
        } catch (CatalogoException e) {
            System.out.println("Error al listar zapatillas: " + e.getMessage());
        }
    }

    private void consultaZapatilla() {
        try {
            System.out.println("Ingrese la marca de las zapatillas (o presione Enter para omitir): ");
            String marca = scanner.nextLine();
            if (marca.isEmpty()) marca = null;

            System.out.println("Ingrese el modelo de las zapatillas (o presione Enter para omitir): ");
            String modelo = scanner.nextLine();
            if (modelo.isEmpty()) modelo = null;

            ArrayList<Zapatillas> resultados = catalogo.consultaZapatilla(marca, modelo);

            if (resultados.isEmpty()) {
                System.out.println("No se encontraron zapatillas con los criterios especificados.");
            } else {
                System.out.printf("%-4s | %-20s | %-30s | %-10s | %-6s | $%-8s | \n",
                        "ID", "Marca", "Modelo", "Color", "Talla", "Precio");
                System.out.println("------------------------------------------------------------------------------------------------");
                for (Zapatillas zapatilla : resultados) {
                    System.out.printf("%-4d | %-20s | %-30s | %-10s | %-6s | $%-8.2f\n",
                            zapatilla.getId(),
                            zapatilla.getMarca(),
                            zapatilla.getModelo(),
                            zapatilla.getColor(),
                            zapatilla.getTalla(),
                            zapatilla.getPrecio());
                }
            }
        } catch (CatalogoException e) {
            System.out.println("Error al consultar zapatillas: " + e.getMessage());
        }
    }

    private int obtenerIdZapatilla() {
        int id = 0;
        boolean encontrado = false;
        while (!encontrado) {
            try {
                id = scanner.nextInt();
                scanner.nextLine();

                if (id <= 0) {
                    System.out.println("El id debe ser mayor que 0.");
                } else {
                    encontrado = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada no valida");
                scanner.nextLine();
            }
        }
        return id;
    }
}
