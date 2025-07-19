package cv;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CvPersonalizado {

    private int idDatosPersonales;
    private int idExperienciaLaboral;
    private int idInformacionContacto;

    // Constructor para inicializar los IDs
    public CvPersonalizado(int idDatosPersonales, int idExperienciaLaboral, int idInformacionContacto) {
        this.idDatosPersonales = idDatosPersonales;
        this.idExperienciaLaboral = idExperienciaLaboral;
        this.idInformacionContacto = idInformacionContacto;
    }

    // Getters y Setters para los atributos
    public int getIdDatosPersonales() {
        return idDatosPersonales;
    }

    public void setIdDatosPersonales(int idDatosPersonales) {
        this.idDatosPersonales = idDatosPersonales;
    }

    public int getIdExperienciaLaboral() {
        return idExperienciaLaboral;
    }

    public void setIdExperienciaLaboral(int idExperienciaLaboral) {
        this.idExperienciaLaboral = idExperienciaLaboral;
    }

    public int getIdInformacionContacto() {
        return idInformacionContacto;
    }

    public void setIdInformacionContacto(int idInformacionContacto) {
        this.idInformacionContacto = idInformacionContacto;
    }

    // Método para guardar los datos en un archivo registrosCv
    public void guardarDatos(String archivo) throws IOException {
        try (BufferedWriter cv = new BufferedWriter(new FileWriter("src/cv/" + archivo, true))) {
            cv.write(idDatosPersonales + "," + idExperienciaLaboral + "," + idInformacionContacto);
            cv.newLine();
        }
    }

    // Método para generar el CV en un archivo
    public void generarCv(String archivo, List<DatosPersonales> datosPersonalesList,
            List<ExperienciaLaboral> experienciaLaboralList,
            List<InformacionContacto> informacionContactoList) throws IOException {
        DatosPersonales dp = datosPersonalesList.stream().filter(d -> d.getId() == idDatosPersonales).findFirst().orElse(null); // Busca los datos personales por ID, (filtra por ID,obtiene el primer resultado que cumple, si no se encuentra, devolver null
        ExperienciaLaboral el = experienciaLaboralList.stream().filter(e -> e.getId() == idExperienciaLaboral).findFirst().orElse(null); // Busca la experiencia laboral por ID
        InformacionContacto ic = informacionContactoList.stream().filter(i -> i.getId() == idInformacionContacto).findFirst().orElse(null);// Busca la información de contacto por ID

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/cv/" + archivo))) {// Uso del BufferedWriter,Reader adaptado de:https://www.youtube.com/watch?v=6f7eIWgr8Io
            // Escribir Datos Personales
            if (dp != null) {
                bw.write("==========================================================================\n");
                bw.write("|                            Currículum Vitae                             |\n");
                bw.write("==========================================================================\n");
                bw.write(" Datos Personales:\n");
                bw.write("--------------------------------------------------------------------------\n");
                bw.write(" ID                    : " + dp.getId() + "\n");
                bw.write(" Cédula                : " + dp.getCedula() + "\n");
                bw.write(" Nombre                : " + dp.getNombre() + "\n");
                bw.write(" Dirección             : " + dp.getDireccion() + "\n");
                bw.write(" Correo Electrónico    : " + dp.getCorreoElectronico() + "\n");
                bw.write(" Fecha Nacimiento      : " + dp.getFechaNacimiento() + "\n");
                bw.write(" Nacionalidad          : " + dp.getNacionalidad() + "\n");
                bw.write(" Estado Civil          : " + dp.getEstadoCivil() + "\n");
                bw.write(" Objetivo Profesional  : " + dp.getObjetivoProfesional() + "\n");
                bw.write("--------------------------------------------------------------------------\n");
            }

            // Escribir Experiencia Laboral
            if (el != null) {
                bw.write(" Experiencia Laboral:\n");
                bw.write("--------------------------------------------------------------------------\n");
                bw.write(" ID                    : " + el.getId() + "\n");
                bw.write(" Nombre Empresa        : " + el.getNombreEmpresa() + "\n");
                bw.write(" Puesto                : " + el.getPuesto() + "\n");
                bw.write(" Fecha Inicio          : " + el.getFechaInicio() + "\n");
                bw.write(" Fecha Fin             : " + el.getFechaFin() + "\n");
                bw.write(" Descripción           : " + el.getDescripcion() + "\n");
                bw.write("--------------------------------------------------------------------------\n");
            }

            // Escribir Información de Contacto
            if (ic != null) {
                bw.write(" Información de Contacto:\n");
                bw.write("--------------------------------------------------------------------------\n");
                bw.write(" ID                    : " + ic.getId() + "\n");
                bw.write(" Teléfono              : " + ic.getTelefono() + "\n");
                bw.write(" Dirección Correo      : " + ic.getDireccionCorreo() + "\n");
                bw.write(" País                  : " + ic.getPais() + "\n");
                bw.write(" Ciudad                : " + ic.getCiudad() + "\n");
                bw.write(" Código Postal         : " + ic.getCodigoPostal() + "\n");
                bw.write("--------------------------------------------------------------------------\n");
                bw.write("|                                 FIN                                    |\n");
                bw.write("--------------------------------------------------------------------------\n");
            }
        } catch (IOException e) {
           
        }
    }
}
