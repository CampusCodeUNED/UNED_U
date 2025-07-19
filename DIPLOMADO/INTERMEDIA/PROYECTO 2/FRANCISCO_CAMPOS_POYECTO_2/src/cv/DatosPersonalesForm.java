package cv;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DatosPersonalesForm extends javax.swing.JFrame {

    private static final String FILE_PATH = "src/cv/datosPersonales.txt"; // Ruta del archivo de datos personales
    private static int contadorId = 1; // Contador de id

    public DatosPersonalesForm() {
        this.getContentPane().setBackground(new Color(173,216,230));//color de fondo
        initComponents();
        cargarContadorId();
        setTitle("Datos personales");
        setSize(630, 385);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        // Inicializar el contador de ID al abrir la ventana
        txtId.setText(String.valueOf(contadorId));
    }
    // método para dar formato a la fecha  "dd/MM/yyyy"
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
        txtNombre = new javax.swing.JTextField();
        CapturarDatos = new javax.swing.JButton();
        txtCedula = new javax.swing.JTextField();
        txtId = new javax.swing.JTextField();
        txtObjetivoProfesional = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        txtNacionalidad = new javax.swing.JTextField();
        txtEstadoCivil = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        fecha1 = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Datos personales");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, -1, -1));
        getContentPane().add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 100, 180, -1));

        CapturarDatos.setText("Agregar");
        CapturarDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CapturarDatosActionPerformed(evt);
            }
        });
        getContentPane().add(CapturarDatos, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 280, 100, 30));
        getContentPane().add(txtCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 100, 180, -1));

        txtId.setEditable(false);
        getContentPane().add(txtId, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 180, -1));
        getContentPane().add(txtObjetivoProfesional, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 220, 180, -1));
        getContentPane().add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 160, 180, -1));
        getContentPane().add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 180, -1));
        getContentPane().add(txtNacionalidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 180, -1));
        getContentPane().add(txtEstadoCivil, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 220, 180, -1));

        jLabel2.setText("Correo electrónico");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 140, -1, -1));

        jLabel3.setText("ID");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, -1));

        jLabel4.setText("Dirección");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, -1));

        jLabel5.setText("Nacionalidad");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, -1, -1));

        jLabel6.setText("Estado civil");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 200, -1, -1));

        jLabel7.setText("Objetivo profesional");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 200, -1, -1));

        jLabel8.setText("Fecha nacimiento");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 140, -1, -1));

        jLabel9.setText("Nombre");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 80, -1, -1));

        jLabel10.setText("Cédula");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 80, -1, -1));

        fecha1.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.DAY_OF_WEEK_IN_MONTH));
        fecha1.setEditor(new javax.swing.JSpinner.DateEditor(fecha1, "dd/MM/yy"));
        getContentPane().add(fecha1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 160, 170, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Método para capturar los datos al hacer clic en el botón "Agregar
    private void CapturarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CapturarDatosActionPerformed
        try {

            int id = contadorId;
            String cedula = txtCedula.getText();
            String nombre = txtNombre.getText();
            String direccion = txtDireccion.getText();
            String correoElectronico = txtCorreo.getText();
            String fechaNacimiento = formatDate((Date) fecha1.getValue()); // Obtener la fecha 
            String nacionalidad = txtNacionalidad.getText();
            String estadoCivil = txtEstadoCivil.getText();
            String objetivoProfesional = txtObjetivoProfesional.getText();

            // Validar que los demás campos no estén vacíos
            if (nombre.isEmpty() || direccion.isEmpty() || correoElectronico.isEmpty()
                    || nacionalidad.isEmpty() || estadoCivil.isEmpty() || objetivoProfesional.isEmpty()) {
                throw new IllegalArgumentException("Todos los campos deben ser llenados.");
            }
            // Si todas las validaciones pasan se guardan con el siguiente orden
            DatosPersonales datosPersonales = new DatosPersonales(id, cedula, nombre, direccion, correoElectronico, fechaNacimiento, nacionalidad, estadoCivil, objetivoProfesional);
            datosPersonales.guardarEnArchivo();
            JOptionPane.showMessageDialog(this, "Datos guardados correctamente.");
            limpiarCampos();
            // Incrementar el contador de ID para el próximo registro
            contadorId++;
            // Actualizar el campo de ID en la interfaz
            txtId.setText(String.valueOf(contadorId));
            // Manejar excepción cuando se encuentra un valor nulo inesperado
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Error de valor nulo: " + e.getMessage(), "Error de datos", JOptionPane.ERROR_MESSAGE);
            // Capturar excepciones específicas de validación
        }catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_CapturarDatosActionPerformed
    //Métedo para limpiar los campos cada ocasión depués de capturar los datos
    private void limpiarCampos() {
        txtId.setText("");
        txtCedula.setText("");
        txtNombre.setText("");
        txtDireccion.setText("");
        txtCorreo.setText("");
        fecha1.setValue(new java.util.Date()); // Limpia el JSpinner fecha
        txtNacionalidad.setText("");
        txtEstadoCivil.setText("");
        txtObjetivoProfesional.setText("");
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
            java.util.logging.Logger.getLogger(DatosPersonalesForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DatosPersonalesForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DatosPersonalesForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DatosPersonalesForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DatosPersonalesForm().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CapturarDatos;
    private javax.swing.JSpinner fecha1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEstadoCivil;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNacionalidad;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtObjetivoProfesional;
    // End of variables declaration//GEN-END:variables
}
