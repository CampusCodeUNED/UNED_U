package modelo;

public class Productora {

    private static int contadorIds = 1;
    private int id;
    private String descripcion;
    private Pelicula[] peliculas;
    private int indicePeliculas;
    private int capacidadMaxima;

    public Productora(String descripcion) {
        this.id = contadorIds++;
        this.descripcion = descripcion;
        this.capacidadMaxima = 100;
        this.peliculas = new Pelicula[this.capacidadMaxima];
        this.indicePeliculas = 0;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Pelicula[] getPeliculas() {
        return peliculas;
    }

    public int getIndicePeliculas() {
        return indicePeliculas;
    }

    public void setIndicePeliculas(int indicePeliculas) {
        this.indicePeliculas = indicePeliculas;
    }

    public boolean agregarPelicula(Pelicula pelicula) {
        if (indicePeliculas < capacidadMaxima) {
            peliculas[indicePeliculas++] = pelicula;
            return true;
        } else {
            System.out.println("No se pueden agregar más películas. Capacidad máxima alcanzada.");
            return false;
        }
    }

    public Pelicula eliminarPrimeraPelicula() {
        if (indicePeliculas > 0) {
            Pelicula primeraPelicula = peliculas[0];
            for (int i = 1; i < indicePeliculas; i++) {
                peliculas[i - 1] = peliculas[i];
            }
            peliculas[--indicePeliculas] = null;
            return primeraPelicula;
        } else {
            System.out.println("No hay películas para eliminar.");
            return null;
        }
    }

    public boolean estaVacia() {
        return indicePeliculas == 0;
    }

    public void setPeliculas(Pelicula[] peliculas) {
        this.peliculas = peliculas;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

}
