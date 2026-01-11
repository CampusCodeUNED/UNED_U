/*
UNIVERSIDAD ESTATAL A DISTANCIA
Curso:Compiladores
Código: 03307
Proyecto #1: Analizador Pascal
Tutor:  CHACÓN CHINCHILLA CARLOS
Grupo: 10
Estudiante: Francisco Campos Sandi
Cédula: 114750560
III Cuatrimestre 2025 */
package PascalAnalizador;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class Main {

    private static final int EX_OK = 0;
    private static final int EX_USO = 64;
    private static final int EX_SIN_ENTRADA = 66;
    private static final int EX_ERROR_DATOS = 65;

    // Punto de entrada principal del analizador de Pascal
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

        // 2) Analizar archivo
        List<Diagnostico> diagnosticos = analizarArchivo(archivoPas);

        // 3) Escribir resultados
        escribirResultados(nombreErrores, diagnosticos, archivoPas);

        System.out.println("Generado archivo de salida: " + nombreErrores);
        System.exit(EX_OK);
    }

    // Muestra las instrucciones de uso del programa
    private static void mostrarUso() {
        System.err.println("Uso: java -jar PascalAnalizador.jar <archivo.pas>");
        System.err.println("Nota: El archivo .pas debe estar en el MISMO directorio que el .jar.");
    }

    // Resuelve la ruta del archivo a analizar y maneja errores si el archivo no existe
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

    // Ejecuta todos los análisis en el archivo Pascal y devuelve los diagnósticos
    private static List<Diagnostico> analizarArchivo(File archivoPas) {
        List<Diagnostico> diagnosticos = new ArrayList<>();

        try {
            Fuente fuente = new LectorFuente().cargar(archivoPas);
            String raizNombre = obtenerNombreRaiz(archivoPas);

            // Ejecutar todos los análisis
            diagnosticos.addAll(validarEncabezado(fuente, raizNombre));
            diagnosticos.addAll(validarBeginEnd(fuente));

            ValidadorOrdenEstructural.Resultado estructural = analizarEstructura(fuente);
            diagnosticos.addAll(estructural.diags);

            ParClaveValor todosDeclarados = analizarDeclaraciones(fuente, diagnosticos);

            if (estructural.lineaBegin > 0) {
                int lineaFin = encontrarUltimaLineaEnd(fuente);
                diagnosticos.addAll(validarWrite(fuente, estructural.lineaBegin, lineaFin, todosDeclarados));
                diagnosticos.addAll(validarUsos(fuente, estructural.lineaBegin, lineaFin, todosDeclarados, diagnosticos));
            }

            diagnosticos.addAll(validarComentarios(fuente));

        } catch (Exception ex) {
            System.err.println("[Error] No se pudo leer el archivo: " + ex.getMessage());
            String nombreErrores = new ResolutorArchivos().nombreErrores(archivoPas);
            escribirErrorRespaldo(nombreErrores, "Error 101. No se pudo leer el archivo: " + ex.getMessage());
            System.exit(EX_SIN_ENTRADA);
        }

        return diagnosticos;
    }

    // Obtiene el nombre base del archivo sin la extensión
    private static String obtenerNombreRaiz(File archivo) {
        String nombreBase = archivo.getName();
        int punto = nombreBase.lastIndexOf('.');
        return (punto >= 0 ? nombreBase.substring(0, punto) : nombreBase);
    }

    // Valida el encabezado del programa Pascal
    private static List<Diagnostico> validarEncabezado(Fuente fuente, String raizNombre) {
        ValidadorEncabezadoProgram validador = new ValidadorEncabezadoProgram();
        return validador.verificar(fuente, raizNombre);
    }

    // Valida la correcta estructura de begin-end en el programa
    private static List<Diagnostico> validarBeginEnd(Fuente fuente) {
        ValidadorBeginEnd validador = new ValidadorBeginEnd();
        return validador.verificar(fuente);
    }

    // Analiza el orden estructural del programa Pascal
    private static ValidadorOrdenEstructural.Resultado analizarEstructura(Fuente fuente) {
        ValidadorOrdenEstructural validador = new ValidadorOrdenEstructural();
        return validador.analizar(fuente);
    }

    // Analiza las declaraciones de variables y constantes
    private static ParClaveValor analizarDeclaraciones(Fuente fuente, List<Diagnostico> diagnosticos) {
        // Constantes
        ValidadorConst validadorConst = new ValidadorConst();
        ValidadorConst.Resultado resultadoConst = validadorConst.analizar(fuente);
        diagnosticos.addAll(resultadoConst.diags);

        // Variables
        AnalizadorVarSeccion analizadorVar = new AnalizadorVarSeccion();
        AnalizadorVarSeccion.Resultado resultadoVar = analizadorVar.analizar(fuente);
        diagnosticos.addAll(resultadoVar.diags);

        // Combinar todos los declarados
        ParClaveValor todosDeclarados = new ParClaveValor();
        todosDeclarados.ponerTodo(resultadoConst.lineaDeclarada);
        todosDeclarados.ponerTodo(resultadoVar.lineaDeclarada);

        return todosDeclarados;
    }

    // Valida las sentencias write/writeln en el código
    private static List<Diagnostico> validarWrite(Fuente fuente, int lineaBegin, int lineaFin, ParClaveValor declarados) {
        ValidadorWrite validador = new ValidadorWrite();
        ValidadorWrite.Resultado resultado = validador.verificar(fuente, lineaBegin, lineaFin, declarados.claves());
        return resultado.diags;
    }

    // Valida los usos de identificadores no declarados
    private static List<Diagnostico> validarUsos(Fuente fuente, int lineaBegin, int lineaFin,
            ParClaveValor declarados, List<Diagnostico> diagnosticosPrevios) {
        ValidadorUso validador = new ValidadorUso();
        List<Diagnostico> diagnosticosUso = validador.encontrarUsosNoDeclarados(
                fuente, lineaBegin, lineaFin, declarados.claves(), AnalizadorVarSeccion.E_USADO_NO_DECLARADO
        );

        // Filtrar duplicados con errores de write/writeln
        List<Integer> lineasConErroresWrite = diagnosticosPrevios.stream()
                .filter(d -> d.codigo == AnalizadorVarSeccion.E_USADO_NO_DECLARADO)
                .map(d -> d.linea)
                .toList();

        if (!lineasConErroresWrite.isEmpty()) {
            diagnosticosUso.removeIf(d -> d.codigo == AnalizadorVarSeccion.E_USADO_NO_DECLARADO
                    && lineasConErroresWrite.contains(d.linea));
        }

        return diagnosticosUso;
    }

    // Valida los comentarios en el código fuente
    private static List<Diagnostico> validarComentarios(Fuente fuente) {
        ValidadorComentarios validador = new ValidadorComentarios();
        ValidadorComentarios.Resultado resultado = validador.verificar(fuente);
        return resultado.diags;
    }

    // Escribe los diagnósticos en un archivo de salida
    private static void escribirResultados(String nombreErrores, List<Diagnostico> diagnosticos, File archivoPas) {
        try {
            Fuente fuente = new LectorFuente().cargar(archivoPas);
            new EscritorErrores().escribir(new File(nombreErrores), fuente, diagnosticos);
        } catch (Exception ex) {
            System.err.println("[Error] No se pudo escribir " + nombreErrores + ": " + ex.getMessage());
            System.exit(1);
        }
    }

    // Escribe un error de respaldo cuando ocurre un error crítico
    private static void escribirErrorRespaldo(String nombreArchivoError, String lineaErrorUnica) {
        try {
            new EscritorErrores()
                    .escribir(new File(nombreArchivoError), new Fuente(""), List.of(
                            new Diagnostico(999, 0, null, lineaErrorUnica.replaceFirst("^Error\\s+\\d+\\.\\s*", ""))
                    ));
        } catch (Exception ignorado) {
        }
    }

    // Encuentra la última línea donde aparece end en el código fuente
    private static int encontrarUltimaLineaEnd(Fuente src) {
        String[] lineas = src.getTexto().split("\\r?\\n", -1);
        int ultimoEnd = -1;
        for (int i = 0; i < lineas.length; i++) {
            if (lineas[i].toLowerCase().contains("end")) {
                ultimoEnd = i + 1;
            }
        }
        return ultimoEnd;
    }
}
