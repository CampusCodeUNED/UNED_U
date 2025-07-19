package modelo;

import java.util.UUID;

public class Pelicula {
    private String dni;
    private String nombre;
    private String genero;
    private String audiencia;

    public Pelicula(String nombre, String genero, String audiencia) {
        this.dni = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.genero = genero;
        this.audiencia = audiencia;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getGenero() {
        return genero;
    }

    public String getAudiencia() {
        return audiencia;
    }
    
        public void setDni(String dni) {
        this.dni = dni;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setAudiencia(String audiencia) {
        this.audiencia = audiencia;
    }
}
