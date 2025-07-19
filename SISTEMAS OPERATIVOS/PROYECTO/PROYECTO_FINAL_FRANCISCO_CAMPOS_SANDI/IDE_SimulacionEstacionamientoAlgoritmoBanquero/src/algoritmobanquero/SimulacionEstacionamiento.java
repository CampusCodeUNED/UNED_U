package algoritmobanquero;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.util.Timer;
import java.util.TimerTask;

public class SimulacionEstacionamiento extends JFrame {

    private static final int ESPACIOS_TOTALES = 5;
    private int maxCarros;

    private JButton[] espaciosEstacionamiento;
    private Carro[] carrosEstacionados;
    private Carro[] carrosEnEspera;
    private int cuentaEstacionados = 0;
    private int conteoEnEspera = 0;
    private int totalProcesados = 0;

    private int[] disponibles = {ESPACIOS_TOTALES}; // Recursos disponibles
    private int[][] maximo; // Máximo de espacios que cada coche puede necesitar
    private int[][] asignado; // Espacios actualmente asignados a cada coche
    private int[][] necesario; // Espacios adicionales que cada coche puede necesitar

    public SimulacionEstacionamiento(int maxCarros) {

        this.maxCarros = maxCarros;
        initComponents();

        carrosEstacionados = new Carro[ESPACIOS_TOTALES];
        carrosEnEspera = new Carro[maxCarros - ESPACIOS_TOTALES];
        maximo = new int[maxCarros][1]; // Asumimos que cada carro puede necesitar 1 espacio
        asignado = new int[maxCarros][1]; // Espacios asignados por coche
        necesario = new int[maxCarros][1]; // Espacios adicionales que cada coche podría necesitar

        for (int i = 0; i < maxCarros; i++) {
            maximo[i][0] = 1; // Cada coche requiere un máximo de 1 espacio
            asignado[i][0] = 0; // Inicialmente no se ha asignado ningún espacio
            necesario[i][0] = maximo[i][0]; // Necesidad inicial igual al máximo
        }

        setTitle("Simulación de Estacionamiento con Algoritmo del Banquero");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        espaciosEstacionamiento = new JButton[ESPACIOS_TOTALES];

        for (int i = 0; i < ESPACIOS_TOTALES; i++) {
            espaciosEstacionamiento[i] = new JButton("Libre");
            espaciosEstacionamiento[i].setBackground(Color.GREEN);
            espaciosEstacionamiento[i].setEnabled(false);
            parkingPanel.add(espaciosEstacionamiento[i]);
        }

        add(parkingPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private void agregarCarro() {
        if (totalProcesados >= maxCarros) {
            JOptionPane.showMessageDialog(this, "Todos los carros han sido procesados.");
            checkFinalizacion();
            return;
        }

        int tiempoEstacionamiento = (int) (Math.random() * 10 + 1); // Tiempo aleatorio entre 1 y 10 segundos
        Carro carro = new Carro(tiempoEstacionamiento);
        totalProcesados++;

        // Verificar si el carro puede ser estacionado de manera segura
        if (estadoSeguro(carro)) {
            estacionarCarro(carro);
        } else {
            // Comprobar si hay espacio en la zona de espera
            if (conteoEnEspera < carrosEnEspera.length) {
                carrosEnEspera[conteoEnEspera++] = carro;
                JOptionPane.showMessageDialog(this, "No hay espacios disponibles. El Carro " + carro.getId() + " está en espera.");
            } else {
                JOptionPane.showMessageDialog(this, "El estacionamiento y la zona de espera están llenos.");
            }
        }
        checkFinalizacion(); // Verifica si se debe finalizar
    }

    private boolean estadoSeguro(Carro carro) {
        int carroId = carro.getId();
        int solicitado = 1;

        // Verifica que carroId esté dentro de los límites permitidos
        if (carroId < 0 || carroId >= maxCarros) {
            return false; // ID del carro fuera de los límites
        }

        if (solicitado > necesario[carroId][0]) {
            return false; // No se puede pedir más de lo que se necesita
        }

        if (solicitado > disponibles[0]) {
            return false; // No hay suficientes recursos disponibles
        }

        // Prueba de estado seguro
        int[] disponiblesTemp = disponibles.clone();
        int[][] asignadoTemp = new int[maxCarros][1];
        int[][] necesarioTemp = new int[maxCarros][1];

        for (int i = 0; i < maxCarros; i++) {
            asignadoTemp[i] = asignado[i].clone();
            necesarioTemp[i] = necesario[i].clone();
        }

        disponiblesTemp[0] -= solicitado;
        asignadoTemp[carroId][0] += solicitado;
        necesarioTemp[carroId][0] -= solicitado;

        boolean[] finalizados = new boolean[maxCarros];
        int conteoTerminado = 0;

        while (conteoTerminado < maxCarros) {
            boolean progreso = false;
            for (int i = 0; i < maxCarros; i++) {
                if (!finalizados[i] && necesarioTemp[i][0] <= disponiblesTemp[0]) {
                    disponiblesTemp[0] += asignadoTemp[i][0];
                    finalizados[i] = true;
                    conteoTerminado++;
                    progreso = true;
                }
            }

            if (!progreso) {
                break;
            }
        }

        return conteoTerminado == maxCarros;
    }

    private void estacionarCarro(Carro carro) {
        int carroId = carro.getId();
        if (carroId >= maxCarros || carroId < 0) {
            JOptionPane.showMessageDialog(this, "ID del carro fuera de los límites.");
            return;
        }

        int indiceEspacio = buscarEspacioLibre();
        if (indiceEspacio == -1) {
            return;
        }

        carro.setIndiceEspacio(indiceEspacio);
        carrosEstacionados[indiceEspacio] = carro;
        cuentaEstacionados++;

        asignado[carroId][0] = 1;
        necesario[carroId][0] = 0;
        disponibles[0]--;
        // Actualizar el texto del espacio con el ID del carro en negrita y tamaño grande
        Font font = new Font("Arial", Font.BOLD, 26); // Tamaño de fuente de 16

        espaciosEstacionamiento[indiceEspacio].setText("Carro #" + carroId);
        espaciosEstacionamiento[indiceEspacio].setBackground(Color.RED);
        espaciosEstacionamiento[indiceEspacio].setFont(font);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                liberarEspacio(carro);
            }
        }, carro.getTiempoEstacionamiento() * 1000);
    }

    private void liberarEspacio(Carro carro) {
        int carroId = carro.getId();
        if (carroId >= maxCarros || carroId < 0) {
            JOptionPane.showMessageDialog(this, "ID del carro fuera de los límites.");
            return;
        }

        asignado[carroId][0] = 0;
        necesario[carroId][0] = maximo[carroId][0];
        disponibles[0]++;

        carrosEstacionados[carro.getIndiceEspacio()] = null;
        cuentaEstacionados--;

        int indiceEspacio = carro.getIndiceEspacio();
        // Actualizar el texto del espacio con el ID del carro en negrita y tamaño grande
        Font font = new Font("Arial", Font.BOLD, 26); // Tamaño de fuente de 16
        espaciosEstacionamiento[indiceEspacio].setText("Libre");
        espaciosEstacionamiento[indiceEspacio].setBackground(Color.GREEN);
        espaciosEstacionamiento[indiceEspacio].setFont(font);

        if (conteoEnEspera > 0) {
            Carro proximoCarro = carrosEnEspera[0];
            for (int i = 0; i < conteoEnEspera - 1; i++) {
                carrosEnEspera[i] = carrosEnEspera[i + 1];
            }
            carrosEnEspera[conteoEnEspera - 1] = null;
            conteoEnEspera--;
            estacionarCarro(proximoCarro);
        }
    }

    private int buscarEspacioLibre() {
        for (int i = 0; i < carrosEstacionados.length; i++) {
            if (carrosEstacionados[i] == null) {
                return i;
            }
        }
        return -1;
    }

    private void checkFinalizacion() {
        if (totalProcesados >= maxCarros && cuentaEstacionados == 0 && conteoEnEspera == 0) {
            JOptionPane.showMessageDialog(this, "Simulación finalizada. Todos los carros han sido procesados.");
            System.exit(0);
        }
    }

    private void mostrarEstado() {
        StringBuilder estado = new StringBuilder("Estado actual:\n");
        for (int i = 0; i < ESPACIOS_TOTALES; i++) {
            if (carrosEstacionados[i] != null) {
                estado.append("Espacio ").append(i).append(": Carro ").append(carrosEstacionados[i].getId()).append("\n");
            } else {
                estado.append("Espacio ").append(i).append(": Libre\n");
            }
        }
        estado.append("Carros en espera:\n");
        for (int i = 0; i < conteoEnEspera; i++) {
            estado.append("Carro: ").append(carrosEnEspera[i].getId()).append("\n");
        }
        JOptionPane.showMessageDialog(this, estado.toString());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        parkingPanel = new javax.swing.JPanel();
        controlPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        parkingPanel.setLayout(new java.awt.GridLayout(1, 5));

        controlPanel.setLayout(new java.awt.GridLayout(1, 3));

        jButton1.setText("Agregar carro");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        controlPanel.add(jButton1);

        jButton2.setText("Ver estado");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        controlPanel.add(jButton2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(parkingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
            .addComponent(controlPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(parkingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(controlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        agregarCarro();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        mostrarEstado();
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel controlPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel parkingPanel;
    // End of variables declaration//GEN-END:variables
}
