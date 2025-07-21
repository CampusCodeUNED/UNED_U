/*
UNIVERSIDAD ESTATAL A DISTANCIA
Curso:Sistemas operativos
Código: 0881
Proyecto final:  Algorítmo del banquero
Tutor:Bernarda Delgado Molina
Grupo:03
Estudiante: Francisco Campos Sandi
Cédula: 114750560
II Cuatrimestre 2024
*/
package algoritmobanquero;


import javax.swing.JOptionPane;

public class Main {
    private static final int MAX_CARROS = 1000; // Define un límite máximo razonable para los carros

    public static void main(String[] args) {
        while (true) {
            try {
                String input = JOptionPane.showInputDialog("Ingrese la cantidad de carros (mayor a 5 y menor o igual a " + MAX_CARROS + "):");
                
                
                if (input == null) {
                    System.exit(0); // Salir del programa
                }
                
                int cantidadCarros = Integer.parseInt(input);
                int ESPACIOS_TOTALES = 5;

                if (cantidadCarros <= ESPACIOS_TOTALES || cantidadCarros > MAX_CARROS) {
                    JOptionPane.showMessageDialog(null, "La cantidad de carros debe ser mayor a " + ESPACIOS_TOTALES + " y menor o igual a " + MAX_CARROS + ".");
                    continue;
                }

                javax.swing.SwingUtilities.invokeLater(() -> {
                    SimulacionEstacionamiento frame = new SimulacionEstacionamiento(cantidadCarros);
                    frame.setVisible(true);
                });

                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido.");
            }
        }
    }
}
