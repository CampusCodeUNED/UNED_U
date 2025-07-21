package cv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DatosPersonales {

    // Atributos para almacenar la información personal
    private int id;
    private String cedula;
    private String nombre;
    private String direccion;
    private String correoElectronico;
    private String fechaNacimiento;
    private String nacionalidad;
    private String estadoCivil;
    private String objetivoProfesional;

// Constructor para inicializar los datos personales
    public DatosPersonales(int id, String cedula, String nombre, String direccion, String correoElectronico, String fechaNacimiento, String nacionalidad, String estadoCivil, String objetivoProfesional) {
        this.id = id;
        this.cedula = cedula;
        this.nombre = nombre;
        this.direccion = direccion;
        this.correoElectronico = correoElectronico;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.estadoCivil = estadoCivil;
        this.objetivoProfesional = objetivoProfesional;
    }

    // Métodos getter y setter para los atributos
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getObjetivoProfesional() {
        return objetivoProfesional;
    }

    public void setObjetivoProfesional(String objetivoProfesional) {
        this.objetivoProfesional = objetivoProfesional;
    }

    // Métodos getter y setter para los atributos
    public static List<DatosPersonales> leerDatos(String archivo) throws IOException {
        List<DatosPersonales> datos = new ArrayList<>();
         // Usamos BufferedReader para leer el archivo línea por línea
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                DatosPersonales dp = new DatosPersonales(Integer.parseInt(partes[0]), partes[1], partes[2],
                        partes[3], partes[4], partes[5], partes[6], partes[7], partes[8]);
                datos.add(dp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return datos;
    }

     // Método para guardar datos en un archivo
    public void guardarEnArchivo() {
        final String rutaArchivo = "src/cv/datosPersonales.txt"; // Ruta del archivo
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
        return id + "," + cedula + "," + nombre + "," + direccion + "," + correoElectronico + "," + fechaNacimiento + "," + nacionalidad + "," + estadoCivil + "," + objetivoProfesional;
    }
}
