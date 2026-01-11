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
package principal;

import io.EscritorErrores;
import io.LectorFuente;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import modelo.Diagnostico;
import modelo.Fuente;
import validador.*;

public final class Main {

    private static final int EX_OK = 0;
    private static final int EX_USO = 64;
    private static final int EX_SIN_ENTRADA = 66;
    private static final int EX_ERROR_DATOS = 65;

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

    private static void mostrarUso() {
        System.err.println("Uso: java -jar PascalAnalyzer.jar <archivo.pas>");
        System.err.println("Nota: El archivo .pas debe estar en el MISMO directorio que el .jar.");
    }
        //método para resolver archivo
    private static File resolverArchivo(String nombreSolicitado, ResolutorArchivos resolvedor) {
        try {
            return resolvedor.resolverEnDirectorio(nombreSolicitado);
        } catch (IllegalArgumentException ex) {
            System.err.println("[Error] " + ex.getMessage());
            String nombreError = resolvedor.NombreErroresDefecto(nombreSolicitado);
            escribirErrorRespaldo(nombreError, "Error 100. " + ex.getMessage());
            System.exit(EX_ERROR_DATOS);
            return null; // nunca se ejecuta
        }
    }
     //método para analizar archivo
    private static List<Diagnostico> analizarArchivo(File archivoPas) {
        List<Diagnostico> diagnosticos = new ArrayList<>();

        try {
            Fuente fuente = new LectorFuente().load(archivoPas);
            String raizNombre = obtenerNombreRaiz(archivoPas);

            // Ejecutar todos los análisis
            diagnosticos.addAll(validarEncabezado(fuente, raizNombre));
            diagnosticos.addAll(validarBeginEnd(fuente));

            ValidadorOrdenEstructural.Result estructural = analizarEstructura(fuente);
            diagnosticos.addAll(estructural.diags);

            Map<String, Integer> todosDeclarados = analizarDeclaraciones(fuente, diagnosticos);

            if (estructural.lineaBegin > 0) {
                int lineaFin = encontrarUltimaLineaEnd(fuente);
                diagnosticos.addAll(validarWrite(fuente, estructural.lineaBegin, lineaFin, todosDeclarados));
                diagnosticos.addAll(validarUsos(fuente, estructural.lineaBegin, lineaFin, todosDeclarados, diagnosticos));
                diagnosticos.addAll(validarFor(fuente, estructural.lineaBegin, lineaFin, todosDeclarados));
                diagnosticos.addAll(validarIf(fuente, estructural.lineaBegin, lineaFin));
                diagnosticos.addAll(validarRepeat(fuente, estructural.lineaBegin, lineaFin));

            }

            diagnosticos.addAll(validarComentarios(fuente));

        } catch (Exception ex) {
            System.err.println("[Error] No se pudo leer el archivo: " + ex.getMessage());
            String nombreErrores = new ResolutorArchivos().nombreErrores(archivoPas);
            escribirErrorRespaldo(nombreErrores, "Error 101. No se pudo leer el archivo: " + ex.getMessage());
            System.exit(EX_SIN_ENTRADA);// nunca se ejecuta
        }

        return diagnosticos;
    }
     //método para obtener el nombre raíz del archivo
    private static String obtenerNombreRaiz(File archivo) {
        String nombreBase = archivo.getName();
        int punto = nombreBase.lastIndexOf('.');
        return (punto >= 0 ? nombreBase.substring(0, punto) : nombreBase);
    }
     //método para validar encabezado
    private static List<Diagnostico> validarEncabezado(Fuente fuente, String raizNombre) {
        ValidadorEncabezadoProgram validador = new ValidadorEncabezadoProgram();
        return validador.check(fuente, raizNombre);
    }
        //método para validar begin-end
    private static List<Diagnostico> validarBeginEnd(Fuente fuente) {
        ValidadorBeginEnd validador = new ValidadorBeginEnd();
        return validador.check(fuente);
    }
        //método para analizar estructura
    private static ValidadorOrdenEstructural.Result analizarEstructura(Fuente fuente) {
        ValidadorOrdenEstructural validador = new ValidadorOrdenEstructural();
        return validador.analyze(fuente);
    }
        //método para analizar declaraciones
    private static Map<String, Integer> analizarDeclaraciones(Fuente fuente, List<Diagnostico> diagnosticos) {
        // Constantes
        ValidadorConst validadorConst = new ValidadorConst();
        ValidadorConst.Result resultadoConst = validadorConst.analyze(fuente);
        diagnosticos.addAll(resultadoConst.diags);

        // Variables
        AnalizadorVarSeccion analizadorVar = new AnalizadorVarSeccion();
        AnalizadorVarSeccion.Result resultadoVar = analizadorVar.analyze(fuente);
        diagnosticos.addAll(resultadoVar.diags);

        // Combinar todos los declarados
        Map<String, Integer> todosDeclarados = new HashMap<>();
        todosDeclarados.putAll(resultadoConst.declaredLine);
        todosDeclarados.putAll(resultadoVar.lineaDeclarada);

        return todosDeclarados;
    }
    //método para validar write/writeln
    private static List<Diagnostico> validarWrite(Fuente fuente, int lineaBegin, int lineaFin, Map<String, Integer> declarados) {
        ValidadorWrite validador = new ValidadorWrite();
        ValidadorWrite.Result resultado = validador.check(fuente, lineaBegin, lineaFin, declarados.keySet());
        return resultado.diags;
    }
    //método para validar usos de variables
    private static List<Diagnostico> validarUsos(Fuente fuente, int lineaBegin, int lineaFin,
            Map<String, Integer> declarados, List<Diagnostico> diagnosticosPrevios) {
        ValidadorUso validador = new ValidadorUso();
        List<Diagnostico> diagnosticosUso = validador.findUndeclaredUsages(
                fuente, lineaBegin, lineaFin, declarados.keySet(), AnalizadorVarSeccion.E_USADO_NO_DECLARADO
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
    //método para validar comentarios
    private static List<Diagnostico> validarComentarios(Fuente fuente) {
        ValidadorComentarios validador = new ValidadorComentarios();
        ValidadorComentarios.Result resultado = validador.check(fuente);
        return resultado.diags;
    }
    //método para escribir resultados
    private static void escribirResultados(String nombreErrores, List<Diagnostico> diagnosticos, File archivoPas) {
        try {
            Fuente fuente = new LectorFuente().load(archivoPas);
            new EscritorErrores().write(new File(nombreErrores), fuente, diagnosticos);
        } catch (Exception ex) {
            System.err.println("[Error] No se pudo escribir " + nombreErrores + ": " + ex.getMessage());
            System.exit(1);
        }
    }
    //método para escribir error de respaldo
    private static void escribirErrorRespaldo(String nombreArchivoError, String lineaErrorUnica) {
        try {
            new EscritorErrores()
                    .write(new File(nombreArchivoError), new Fuente(""), List.of(
                            new Diagnostico(999, 0, null, lineaErrorUnica.replaceFirst("^Error\\s+\\d+\\.\\s*", ""))
                    ));
        } catch (Exception ignorado) {
        }
    }
    //método para encontrar la última línea con "end"
    private static int encontrarUltimaLineaEnd(Fuente src) {
        String[] lineas = src.getText().split("\\r?\\n", -1);
        int ultimoEnd = -1;
        for (int i = 0; i < lineas.length; i++) {
            if (lineas[i].toLowerCase().contains("end")) {
                ultimoEnd = i + 1;
            }
        }
        return ultimoEnd;
    }
    // Clase auxiliar para claves únicas de diagnósticos
    private record Clave(int code, int line, String msg) {

        Clave   {
            msg = (msg == null ? "" : msg);
        }

        @Override
        public int hashCode() {
            return Objects.hash(code, line, msg);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Clave)) {
                return false;
            }
            Clave k = (Clave) o;
            return code == k.code && line == k.line && Objects.equals(msg, k.msg);
        }
    }
    //método para validar for
    private static List<Diagnostico> validarFor(Fuente fuente, int lineaBegin, int lineaFin,
            Map<String, Integer> declarados) {
        ValidadorFor validador = new ValidadorFor();
        ValidadorFor.Resultado resultado = validador.validar(fuente, lineaBegin, lineaFin, declarados.keySet());
        return resultado.diags;
    }
    //método para validar if
    private static List<Diagnostico> validarIf(Fuente fuente, int lineaBegin, int lineaFin) {
        ValidadorIf validador = new ValidadorIf();
        ValidadorIf.Result resultado = validador.check(fuente, lineaBegin, lineaFin);
        return resultado.diags;
    }

    //método para validar repeat
    private static List<Diagnostico> validarRepeat(Fuente fuente, int lineaBegin, int lineaFin) {
        ValidadorRepeat validador = new ValidadorRepeat();
        ValidadorRepeat.Result resultado = validador.check(fuente, lineaBegin, lineaFin);
        return resultado.diags;
    }

}
