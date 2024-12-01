package dominio;
import java.io.Serializable;

public class Zapatillas implements Serializable{
    private String marca;
    private String modelo;
    private String color;
    private String talla;
    private double precio;
    private final int id;

    public Zapatillas(int id, String marca, String modelo, String color, String talla, double precio) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.talla = talla;
        this.precio = precio;
    }


    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        if (marca == null || marca.trim().isEmpty()) {
            throw new IllegalArgumentException("La marca no puede estar vacía.");
        }
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        if (modelo == null || modelo.trim().isEmpty()) {
            throw new IllegalArgumentException("El modelo no puede estar vacío.");
        }
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        if (color == null || color.trim().isEmpty()) {
            throw new IllegalArgumentException("El color no puede estar vacío.");
        }
        this.color = color;
    }

    public String getTalla() {
        return talla;
    }


    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que 0.");
        }
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setTalla(String talla) {
        if (talla == null || talla.trim().isEmpty()) {
            throw new IllegalArgumentException("La talla no puede estar vacía.");
        }
        this.talla = talla;
    }
    public String toString() {
        return "Zapatillas - ID: " + id +
                ", Marca: '" + marca + '\'' +
                ", Modelo: '" + modelo + '\'' +
                ", Color: '" + color + '\'' +
                ", talla: '" + talla + '\'' +
                ", Precio: $" + String.format("%.2f", precio);

    }
}
