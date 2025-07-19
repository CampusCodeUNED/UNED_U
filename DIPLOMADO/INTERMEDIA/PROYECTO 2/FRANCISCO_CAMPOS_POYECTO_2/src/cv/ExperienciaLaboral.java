package cv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExperienciaLaboral {
    // Atributos para almacenar la información de la experiencia laboral
    
    private int id;
    private String nombreEmpresa;
    private String puesto;
    private String fechaInicio;
    private String fechaFin;
    private String descripcion;
    
// Constructor para inicializar la experiencia laboral
    public ExperienciaLaboral(int id, String nombreEmpresa, String puesto, String fechaInicio, String fechaFin, String descripcion) {
        this.id = id;
        this.nombreEmpresa = nombreEmpresa;
        this.puesto = puesto;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descripcion = descripcion;
    }
   // Métodos getter y setter para los atributos
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    // Método para leer datos desde un archivo
    public static List<ExperienciaLaboral> leerDatos(String archivo) throws IOException {
        List<ExperienciaLaboral> datos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {// Uso del BufferedWriter,Reader adaptado de:https://www.youtube.com/watch?v=6f7eIWgr8Io
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                ExperienciaLaboral el = new ExperienciaLaboral(Integer.parseInt(partes[0]), partes[1], partes[2],
                        partes[3], partes[4], partes[5]);
                datos.add(el);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return datos;
    }

   public void guardarEnArchivo() {
        final String rutaArchivo = "src/cv/experienciaLaboral.txt"; // Ruta del archivo
        File archivo = new File(rutaArchivo);
        
        try {
            // Verificar si el archivo existe, si no, crear uno nuevo
            if (!archivo.exists()) {
                archivo.createNewFile();
            }

            // Escribir los datos en el archivo
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, true))) {
                writer.write(this.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String toString() {
        return id + "," + nombreEmpresa + "," + puesto + "," + fechaInicio + "," + fechaFin + "," + descripcion;
    }
}
