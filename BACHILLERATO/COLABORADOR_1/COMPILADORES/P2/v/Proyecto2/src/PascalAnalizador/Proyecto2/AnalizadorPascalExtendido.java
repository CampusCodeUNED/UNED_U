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
package PascalAnalizador.Proyecto2;

import PascalAnalizador.Proyecto1.AnalizadorVarSeccion;
import PascalAnalizador.Proyecto1.Diagnostico;
import PascalAnalizador.Proyecto1.Fuente;
import PascalAnalizador.Proyecto1.LectorFuente;
import PascalAnalizador.Proyecto1.ParClaveValor;
import PascalAnalizador.Proyecto1.ValidadorBeginEnd;
import PascalAnalizador.Proyecto1.ValidadorComentarios;
import PascalAnalizador.Proyecto1.ValidadorConst;
import PascalAnalizador.Proyecto1.ValidadorEncabezadoProgram;
import PascalAnalizador.Proyecto1.ValidadorOrdenEstructural;
import PascalAnalizador.Proyecto1.ValidadorUso;
import PascalAnalizador.Proyecto1.ValidadorWrite;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que integra las validaciones del Proyecto1 con las nuevas validaciones
 * del Proyecto2 para realizar un análisis completo del código Pascal.
 */
public class AnalizadorPascalExtendido {
    
    /**
     * Clase que contiene el resultado del análisis extendido
     */
    public static class ResultadoAnalisis {
        public final List<Diagnostico> diagnosticosProyecto1;
        public final List<Diagnostico> diagnosticosProyecto2;
        public final ParClaveValor variablesDeclaradas;
        
        public ResultadoAnalisis(List<Diagnostico> diagnosticosProyecto1, 
                List<Diagnostico> diagnosticosProyecto2,
                ParClaveValor variablesDeclaradas) {
            this.diagnosticosProyecto1 = diagnosticosProyecto1;
            this.diagnosticosProyecto2 = diagnosticosProyecto2;
            this.variablesDeclaradas = variablesDeclaradas;
        }
        
        /**
         * Obtiene todos los diagnósticos combinados
         * @return lista combinada de diagnósticos
         */
        public List<Diagnostico> getTodosDiagnosticos() {
            List<Diagnostico> todos = new ArrayList<>(diagnosticosProyecto1);
            todos.addAll(diagnosticosProyecto2);
            return todos;
        }
    }
    
    /**
     * Analiza un archivo Pascal utilizando validaciones de Proyecto1 y Proyecto2
     * @param archivoPas el archivo a analizar
     * @return resultado del análisis
     */
    public ResultadoAnalisis analizarArchivo(File archivoPas) {
        List<Diagnostico> diagnosticosProyecto1 = new ArrayList<>();
        List<Diagnostico> diagnosticosProyecto2 = new ArrayList<>();
        ParClaveValor variablesDeclaradas = new ParClaveValor();
        
        try {
            // Cargar el archivo
            Fuente fuente = new LectorFuente().cargar(archivoPas);
            String raizNombre = obtenerNombreRaiz(archivoPas);
            
            // === Validaciones del Proyecto1 ===
            
            // Validar encabezado del programa
            ValidadorEncabezadoProgram validadorProgram = new ValidadorEncabezadoProgram();
            diagnosticosProyecto1.addAll(validadorProgram.verificar(fuente, raizNombre));
            
            // Validar estructura begin-end
            ValidadorBeginEnd validadorBeginEnd = new ValidadorBeginEnd();
            diagnosticosProyecto1.addAll(validadorBeginEnd.verificar(fuente));
            
            // Validar orden estructural
            ValidadorOrdenEstructural validadorOrden = new ValidadorOrdenEstructural();
            ValidadorOrdenEstructural.Resultado estructural = validadorOrden.analizar(fuente);
            diagnosticosProyecto1.addAll(estructural.diags);
            
            // Analizar declaraciones (variables y constantes)
            ValidadorConst validadorConst = new ValidadorConst();
            ValidadorConst.Resultado resultadoConst = validadorConst.analizar(fuente);
            diagnosticosProyecto1.addAll(resultadoConst.diags);
            
            AnalizadorVarSeccion analizadorVar = new AnalizadorVarSeccion();
            AnalizadorVarSeccion.Resultado resultadoVar = analizadorVar.analizar(fuente);
            diagnosticosProyecto1.addAll(resultadoVar.diags);
            
            // Obtener todas las variables declaradas
            variablesDeclaradas.ponerTodo(resultadoConst.lineaDeclarada);
            variablesDeclaradas.ponerTodo(resultadoVar.lineaDeclarada);
            
            // Validar comentarios (validación especial de espacios en Proyecto 2)
            ValidadorComentarios validadorComentarios = new ValidadorComentarios();
            ValidadorComentarios.Resultado resultadoComentarios = validadorComentarios.verificar(fuente);
            
            // Filtrar los errores específicos de espacios en comentarios para el Proyecto 2
            for (Diagnostico diag : resultadoComentarios.diags) {
                if (diag.codigo == ValidadorComentarios.E_COMENTARIO_SLASH_CON_ESPACIO) {
                    // Los errores de espacios van al Proyecto 2
                    diagnosticosProyecto2.add(diag);
                } else {
                    // Los demás errores de comentarios van al Proyecto 1
                    diagnosticosProyecto1.add(diag);
                }
            }
            
            if (estructural.lineaBegin > 0) {
                int lineaFin = encontrarUltimaLineaEnd(fuente);
                
                // Validar sentencias write/writeln
                ValidadorWrite validadorWrite = new ValidadorWrite();
                ValidadorWrite.Resultado resultadoWrite = validadorWrite.verificar(
                        fuente, estructural.lineaBegin, lineaFin, variablesDeclaradas.claves());
                diagnosticosProyecto1.addAll(resultadoWrite.diags);
                
                // Validar usos de variables
                ValidadorUso validadorUso = new ValidadorUso();
                List<Diagnostico> diagnosticosUso = validadorUso.encontrarUsosNoDeclarados(
                        fuente, estructural.lineaBegin, lineaFin, 
                        variablesDeclaradas.claves(), AnalizadorVarSeccion.E_USADO_NO_DECLARADO);
                
                // Filtrar duplicados con errores de write/writeln
                List<Integer> lineasConErroresWrite = new ArrayList<>();
                for (Diagnostico diag : resultadoWrite.diags) {
                    if (diag.codigo == AnalizadorVarSeccion.E_USADO_NO_DECLARADO) {
                        lineasConErroresWrite.add(diag.linea);
                    }
                }
                
                if (!lineasConErroresWrite.isEmpty()) {
                    List<Diagnostico> diagnosticosUsoFiltrados = new ArrayList<>();
                    for (Diagnostico diag : diagnosticosUso) {
                        if (diag.codigo != AnalizadorVarSeccion.E_USADO_NO_DECLARADO || 
                                !lineasConErroresWrite.contains(diag.linea)) {
                            diagnosticosUsoFiltrados.add(diag);
                        }
                    }
                    diagnosticosProyecto1.addAll(diagnosticosUsoFiltrados);
                } else {
                    diagnosticosProyecto1.addAll(diagnosticosUso);
                }
            }
            
            // === Validaciones del Proyecto2 ===
            
            // 1. Análisis basado en tokens para estructuras de control (solo detección de problemas léxicos)
            List<Diagnostico> diagnosticosTokens = AnalizadorPrincipal.analizarEstructurasConTokens(fuente.getTexto());
            diagnosticosProyecto2.addAll(diagnosticosTokens);
            
            // Obtener líneas donde ya hay errores de tokens para evitar duplicados
            List<Integer> lineasConErroresTokens = new ArrayList<>();
            for (Diagnostico diag : diagnosticosTokens) {
                lineasConErroresTokens.add(diag.linea);
            }
            
            // 2. Validar bucles for (filtrar errores 900 si ya fueron reportados por tokens)
            ValidadorFor validadorFor = new ValidadorFor();
            ValidadorFor.Resultado resultadoFor = validadorFor.validar(fuente, variablesDeclaradas);
            for (Diagnostico diag : resultadoFor.diags) {
                // Saltar errores 900 en líneas ya reportadas por tokens
                if (diag.codigo == 900 && lineasConErroresTokens.contains(diag.linea)) {
                    continue;
                }
                diagnosticosProyecto2.add(diag);
            }
            
            // 3. Validar estructuras if-then-else
            ValidadorIf validadorIf = new ValidadorIf();
            ValidadorIf.Resultado resultadoIf = validadorIf.validar(fuente);
            diagnosticosProyecto2.addAll(resultadoIf.diags);
            
            // 4. Validar estructuras repeat-until
            ValidadorRepeat validadorRepeat = new ValidadorRepeat();
            ValidadorRepeat.Resultado resultadoRepeat = validadorRepeat.validar(fuente);
            diagnosticosProyecto2.addAll(resultadoRepeat.diags);
            
            // 5. Validación avanzada de tabulaciones (solo para casos no cubiertos por validadores específicos)
            List<Diagnostico> diagnosticosTabulacion = ValidadorTabulacionAvanzada.validarTabulacionesCompletas(fuente.getTexto());
            
            // Agregar todos los errores de tabulación (error 999)
            // No filtrar porque son validaciones complementarias
            diagnosticosProyecto2.addAll(diagnosticosTabulacion);
            
        } catch (Exception ex) {
            String mensajeError = ex.getMessage();
            int lineaError = 0;

            // Intentar obtener el número de línea del stack trace
            StackTraceElement[] stackTrace = ex.getStackTrace();
                for (StackTraceElement elemento : stackTrace) {
                    String className = elemento.getClassName();
                    if (className.contains("Validador") || className.contains("Analizador")) {
                        lineaError = elemento.getLineNumber();
                        break;
                    }
                }            // Traducir mensajes de error comunes del sistema
            if (mensajeError.contains("Cannot invoke")) {
                mensajeError = "No se puede procesar una sección del archivo porque está mal formada";
            }

            // Agregar el diagnóstico sin preformatear, Diagnostico.formatearConLinea() se encargará
            // de agregar el prefijo "Error 999. Línea NNNN." según corresponda
            diagnosticosProyecto1.add(new Diagnostico(999, lineaError, null, mensajeError));
        }
        
        return new ResultadoAnalisis(diagnosticosProyecto1, diagnosticosProyecto2, variablesDeclaradas);
    }
    
    /**
     * Obtiene el nombre base del archivo sin la extensión
     */
    private String obtenerNombreRaiz(File archivo) {
        String nombreArchivo = archivo.getName();
        int punto = nombreArchivo.lastIndexOf('.');
        return (punto >= 0 ? nombreArchivo.substring(0, punto) : nombreArchivo);
    }
    
    /**
     * Encuentra la última línea donde aparece "end" en el código fuente
     */
    private int encontrarUltimaLineaEnd(Fuente src) {
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
