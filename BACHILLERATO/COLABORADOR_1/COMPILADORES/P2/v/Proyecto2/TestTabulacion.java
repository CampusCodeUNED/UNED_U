import PascalAnalizador.Proyecto1.Diagnostico;
import PascalAnalizador.Proyecto2.ValidadorTabulacionAvanzada;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TestTabulacion {
    public static void main(String[] args) throws Exception {
        String codigo = new String(Files.readAllBytes(Paths.get("calendario.pas")));
        List<Diagnostico> errores = ValidadorTabulacionAvanzada.validarTabulacionesCompletas(codigo);
        
        System.out.println("Total errores: " + errores.size());
        for (Diagnostico diag : errores) {
            if (diag.linea >= 105 && diag.linea <= 110) {
                System.out.println("Error " + diag.codigo + " Línea " + String.format("%04d", diag.linea) + 
                                 ": " + diag.mensaje);
            }
        }
    }
}
