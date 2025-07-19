package cv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InformacionContacto {
    // Atributos para almacenar la información de contacto

    private int id;
    private String telefono;
    private String direccionCorreo;
    private String pais;
    private String ciudad;
    private String codigoPostal;

    // Constructor para inicializar la información de contacto
    public InformacionContacto(int id, String telefono, String direccionCorreo, String pais, String ciudad, String codigoPostal) {
        this.id = id;
        this.telefono = telefono;
        this.direccionCorreo = direccionCorreo;
        this.pais = pais;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
    }

    // Métodos getter y setter para los atributos
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccionCorreo() {
        return direccionCorreo;
    }

    public void setDireccionCorreo(String direccionCorreo) {
        this.direccionCorreo = direccionCorreo;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    // Método para leer datos desde un archivo
    public static List<InformacionContacto> leerDatos(String archivo) throws IOException {
        List<InformacionContacto> datos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                InformacionContacto ic = new InformacionContacto(Integer.parseInt(partes[0]), partes[1], partes[2],
                        partes[3], partes[4], partes[5]);
                datos.add(ic);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return datos;
    }

    public void guardarEnArchivo() {
        final String rutaArchivo = "src/cv/informacionContacto.txt"; // Ruta del archivo
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
        return id + "," + telefono + "," + direccionCorreo + "," + pais + "," + ciudad + "," + codigoPostal;
    }
}
