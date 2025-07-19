package cv;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ExperienciaLaboralForm extends javax.swing.JFrame {

    private static final String FILE_PATH = "src/cv/experienciaLaboral.txt"; // Ruta del archivo de experienciaLaboral
    private static int contadorId = 1; // Contador de IDs

    public ExperienciaLaboralForm() {
       this.getContentPane().setBackground(new Color(173,216,230));//color de fondo
        initComponents();
        cargarContadorId();
        setTitle("Experiencia laboral");
        setSize(630, 385);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        // Inicializar el contador de ID al abrir la ventana
        txtId.setText(String.valueOf(contadorId));
    }
    
    // Método para dar formato a la fecha  "dd/MM/yyyy"
    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }
    //Método para cargar el contador depués de cerrar y no pierda el conteo
    private void cargarContadorId() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linea;
            String ultimaLinea = "";
            while ((linea = br.readLine()) != null) {
                ultimaLinea = linea;
            }
            if (!ultimaLinea.isEmpty()) {
                String[] partes = ultimaLinea.split(",");
                // Actualiza el contadorId al último ID + 1
                contadorId = Integer.parseInt(partes[0]) + 1;
            }
        } catch (IOException | NumberFormatException e) {
            // Si hay un error al leer el archivo, iniciar el contador en 1
            contadorId = 1;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtEmpresa = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtPuesto = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        txtFechaInicio = new javax.swing.JSpinner();
        txtFechaFin = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Experiencia laboral");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, -1, -1));

        jLabel3.setText("ID");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, -1));

        txtId.setEditable(false);
        getContentPane().add(txtId, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 180, -1));

        jLabel4.setText("Fecha inicio");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, -1));

        jLabel2.setText("Fecha fin");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 140, -1, -1));

        jLabel10.setText("Empresa");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 80, -1, -1));
        getContentPane().add(txtEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 100, 180, -1));

        jLabel9.setText("Puesto");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 80, -1, -1));
        getContentPane().add(txtPuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 100, 180, -1));

        jLabel8.setText("Descripción");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 140, -1, -1));
        getContentPane().add(txtDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 160, 180, -1));

        jButton1.setText("Agregar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 220, 100, 30));

        txtFechaInicio.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.DAY_OF_WEEK_IN_MONTH));
        txtFechaInicio.setEditor(new javax.swing.JSpinner.DateEditor(txtFechaInicio, "dd/MM/yy"));
        getContentPane().add(txtFechaInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 170, 30));

        txtFechaFin.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.DAY_OF_WEEK_IN_MONTH));
        txtFechaFin.setEditor(new javax.swing.JSpinner.DateEditor(txtFechaFin, "dd/MM/yy"));
        getContentPane().add(txtFechaFin, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 160, 170, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents
   // Método para capturar los datos al hacer clic en el botón "Agregar
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            int id = Integer.parseInt(txtId.getText());
            String nombreEmpresa = txtEmpresa.getText();
            String puesto = txtPuesto.getText();
            String fechaInicio = formatDate((Date) txtFechaInicio.getValue()); // Obtener la fecha 
            String fechaFin = formatDate((Date) txtFechaFin.getValue()); // Obtener la fecha 
            String descripcion = txtDescripcion.getText();
            // Validar que los campos de texto no estén vacíos
            if (nombreEmpresa.isEmpty() || puesto.isEmpty() || descripcion.isEmpty()) {
                throw new IllegalArgumentException("Todos los campos deben estar llenos.");
            }
            // Si todas las validaciones pasan se guardan con el siguiente orden
            ExperienciaLaboral experiencia = new ExperienciaLaboral(id, nombreEmpresa, puesto, fechaInicio, fechaFin, descripcion);
            experiencia.guardarEnArchivo();
            JOptionPane.showMessageDialog(null, "Experiencia laboral guardada correctamente");
            limpiarCampos();
            // Incrementar el contador de ID para el próximo registro
            contadorId++;
            // Actualizar el campo de ID en la interfaz
            txtId.setText(String.valueOf(contadorId));
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Error de valor nulo: " + e.getMessage(), "Error de datos", JOptionPane.ERROR_MESSAGE);
            // Capturar excepciones específicas de validación
        }catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
        }
        // Manejar errores  al intentar guardar los datos
        
    }//GEN-LAST:event_jButton1ActionPerformed
    //Métedo para limpiar los campos cada ocasión depués de capturar los datos
    private void limpiarCampos() {
        txtId.setText("");
        txtEmpresa.setText("");
        txtPuesto.setText("");
        txtFechaInicio.setValue(new java.util.Date()); // Resetear fecha al valor actual
        txtFechaFin.setValue(new java.util.Date()); // Resetear fecha al valor actua
        txtDescripcion.setText("");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ExperienciaLaboralForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExperienciaLaboralForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExperienciaLaboralForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExperienciaLaboralForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ExperienciaLaboralForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtEmpresa;
    private javax.swing.JSpinner txtFechaFin;
    private javax.swing.JSpinner txtFechaInicio;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtPuesto;
    // End of variables declaration//GEN-END:variables
}
