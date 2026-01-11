package PascalAnalizador.Proyecto2;

import PascalAnalizador.Proyecto1.Diagnostico;
import PascalAnalizador.Proyecto1.EscritorErrores;
import PascalAnalizador.Proyecto1.Fuente;
import PascalAnalizador.Proyecto1.LectorFuente;
import PascalAnalizador.Proyecto1.ResolutorArchivos;
import java.io.File;
import java.util.List;

public class AnalizadorPascal {
    public static void main(String[] args) {
        try {
            String nombreArchivo = (args.length > 0) ? args[0] : "calendario.pas";

            ResolutorArchivos resolvedor = new ResolutorArchivos();
            File archivoPas = resolvedor.resolverEnDirectorio(nombreArchivo);
            String nombreErrores = resolvedor.nombreErrores(archivoPas);

            AnalizadorPascalExtendido analizador = new AnalizadorPascalExtendido();
            AnalizadorPascalExtendido.ResultadoAnalisis resultado = analizador.analizarArchivo(archivoPas);
            List<Diagnostico> diagnosticos = resultado.getTodosDiagnosticos();

            Fuente fuente = new LectorFuente().cargar(archivoPas);
            new EscritorErrores().escribir(new File(nombreErrores), fuente, diagnosticos);

            System.out.println("Análisis completado. Archivo generado: " + nombreErrores);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}