/*
UNIVERSIDAD ESTATAL A DISTANCIA
Curso:Compiladores
Código: 03307
Proyecto #2: Analizador Pascal
Tutor:  CHACÓN CHINCHILLA CARLOS
Grupo: 10
Estudiante: Francisco Campos Sandi
Cédula: 114750560
III Cuatrimestre 2025 */
package PascalAnalizador;

import PascalAnalizador.Proyecto1.Diagnostico;
import PascalAnalizador.Proyecto1.EscritorErrores;
import PascalAnalizador.Proyecto1.Fuente;
import PascalAnalizador.Proyecto1.LectorFuente;
import PascalAnalizador.Proyecto1.ResolutorArchivos;
import PascalAnalizador.Proyecto2.AnalizadorPascalExtendido;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal del analizador Pascal que integra validaciones del Proyecto1 y Proyecto2
 * Esta versión optimizada elimina la redundancia de código y delega todas las validaciones
 * al AnalizadorPascalExtendido
 */
public final class Main {

    private static final int EX_OK = 0;
    private static final int EX_USO = 64;
    private static final int EX_SIN_ENTRADA = 66;
    private static final int EX_ERROR_DATOS = 65;

    /**
     * Punto de entrada principal del analizador de Pascal
     * @param args argumentos de línea de comandos (nombre del archivo a analizar)
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            mostrarUso();
            System.exit(EX_USO);
        }

        String nombreSolicitado = args[0].trim();
        ResolutorArchivos resolvedor = new ResolutorArchivos();

        // 1) Resolver archivo
        File archivoPas = resolverArchivo(nombreSolicitado, resolvedor);
        String nombreErrores = resolvedor.nombreErrores(archivoPas);

        // 2) Analizar archivo (proyecto 1 y 2 integrados)
        List<Diagnostico> diagnosticos = analizarArchivoExtendido(archivoPas);

        // 3) Escribir resultados
        escribirResultados(nombreErrores, diagnosticos, archivoPas);

        System.out.println("Generado archivo de salida: " + nombreErrores + " (integra validaciones de Proyecto1 y Proyecto2)");
        System.exit(EX_OK);
    }

    /**
     * Muestra las instrucciones de uso del programa
     */
    private static void mostrarUso() {
        System.err.println("Uso: java -jar PascalAnalizador.jar <archivo.pas>");
        System.err.println("Nota: El archivo .pas debe estar en el MISMO directorio que el .jar.");
        System.err.println("Este analizador integra las funcionalidades del Proyecto1 y Proyecto2.");
        System.err.println("Validaciones disponibles:");
        System.err.println("  - Proyecto1: encabezado, begin/end, estructura, const, var, comentarios, writeln");
        System.err.println("  - Proyecto2: sentencias FOR, IF-THEN-ELSE, REPEAT-UNTIL");
    }

    /**
     * Resuelve la ruta del archivo a analizar y maneja errores si el archivo no existe
     * @param nombreSolicitado nombre del archivo solicitado
     * @param resolvedor objeto para resolver rutas
     * @return archivo a analizar
     */
    private static File resolverArchivo(String nombreSolicitado, ResolutorArchivos resolvedor) {
        try {
            return resolvedor.resolverEnDirectorio(nombreSolicitado);
        } catch (IllegalArgumentException ex) {
            System.err.println("[Error] " + ex.getMessage());
            String nombreError = resolvedor.nombreErroresDefecto(nombreSolicitado);
            escribirErrorRespaldo(nombreError, "Error 100. " + ex.getMessage());
            System.exit(EX_ERROR_DATOS);
            return null; // nunca se ejecuta
        }
    }
    
    /**
     * Método integrado que ejecuta los análisis de Proyecto1 y Proyecto2
     * @param archivoPas el archivo a analizar
     * @return lista combinada de diagnósticos
     */
    private static List<Diagnostico> analizarArchivoExtendido(File archivoPas) {
        try {
            System.out.println("\n==== ANALIZANDO ARCHIVO: " + archivoPas.getName() + " ====");
            System.out.println("Ejecutando validaciones del Proyecto1 y Proyecto2...");
            
            // Utilizar el analizador extendido que integra Proyecto1 y Proyecto2
            AnalizadorPascalExtendido analizador = new AnalizadorPascalExtendido();
            AnalizadorPascalExtendido.ResultadoAnalisis resultado = analizador.analizarArchivo(archivoPas);
            
            // Obtener todos los diagnósticos combinados
            List<Diagnostico> todosDiagnosticos = resultado.getTodosDiagnosticos();
            
            // Mostrar un resumen por consola
            mostrarResumen(resultado);
            
            return todosDiagnosticos;
        } catch (Exception ex) {
            System.err.println("[Error] Error en análisis extendido: " + ex.getMessage());
            ex.printStackTrace();
            List<Diagnostico> diagnosticosError = new ArrayList<>();
            diagnosticosError.add(new Diagnostico(999, 0, null, "Error en análisis extendido: " + ex.getMessage()));
            return diagnosticosError;
        }
    }
    
    /**
     * Muestra un resumen de los errores encontrados
     */
    private static void mostrarResumen(AnalizadorPascalExtendido.ResultadoAnalisis resultado) {
        System.out.println("\n=== RESUMEN DEL ANÁLISIS ===");
        System.out.println("Errores del Proyecto1: " + resultado.diagnosticosProyecto1.size());
        System.out.println("Errores del Proyecto2: " + resultado.diagnosticosProyecto2.size());
        System.out.println("Total errores: " + resultado.getTodosDiagnosticos().size());
        
        if (resultado.getTodosDiagnosticos().isEmpty()) {
            System.out.println("¡Felicidades! No se encontraron errores en el código Pascal.");
        }
    }

    /**
     * Escribe los diagnósticos en un archivo de salida
     */
    private static void escribirResultados(String nombreErrores, List<Diagnostico> diagnosticos, File archivoPas) {
        try {
            Fuente fuente = new LectorFuente().cargar(archivoPas);
            new EscritorErrores().escribir(new File(nombreErrores), fuente, diagnosticos);
        } catch (Exception ex) {
            System.err.println("[Error] No se pudo escribir " + nombreErrores + ": " + ex.getMessage());
            System.exit(1);
        }
    }

    /**
     * Escribe un error de respaldo cuando ocurre un error crítico
     */
    private static void escribirErrorRespaldo(String nombreArchivoError, String lineaErrorUnica) {
        try {
            new EscritorErrores()
                    .escribir(new File(nombreArchivoError), new Fuente(""), List.of(
                            new Diagnostico(999, 0, null, lineaErrorUnica.replaceFirst("^Error\\s+\\d+\\.\\s*", ""))
                    ));
        } catch (Exception ignorado) {
            // Ignoramos errores al escribir el respaldo
        }
    }
}