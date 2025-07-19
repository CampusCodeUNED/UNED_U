package cv;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class InformacionContactoForm extends javax.swing.JFrame {

    private static final String FILE_PATH = "src/cv/informacionContacto.txt"; // Ruta del archivo de informacion Contacto
    private static int contadorId = 1; // Contador de IDs
    public InformacionContactoForm() {
        this.getContentPane().setBackground(new Color(173,216,230));//color de fondo
        initComponents();
        cargarContadorId();
        setTitle("Informacion de contacto");
        setSize(630, 385);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        // Inicializar el contador de ID al abrir la ventana
        txtId.setText(String.valueOf(contadorId));
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
    // Método para dar formato al teléfono

    private String formatearTelefono(String telefono) {
        // Eliminar caracteres no numéricos
        String numeros = telefono.replaceAll("\\D", "");
        // Verificar longitud correcta
        if (numeros.length() == 10) {
            return numeros.substring(0, 3) + "-" + numeros.substring(3, 6) + "-" + numeros.substring(6);
        } else {
            throw new IllegalArgumentException("El teléfono debe contener exactamente 10 dígitos numéricos.");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtCodigoPostal = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtCiudad = new javax.swing.JTextField();
        txtPais = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Información de contacto");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, -1, -1));

        jLabel3.setText("ID");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, -1));

        txtId.setEditable(false);
        getContentPane().add(txtId, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 180, -1));

        jLabel10.setText("Telefono");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 80, -1, -1));
        getContentPane().add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 100, 180, -1));

        jLabel9.setText("Correo");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 80, -1, -1));
        getContentPane().add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 100, 180, -1));

        jLabel8.setText("Codigo Postal");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 140, -1, -1));
        getContentPane().add(txtCodigoPostal, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 160, 180, -1));

        jLabel2.setText("Ciudad");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 140, -1, -1));
        getContentPane().add(txtCiudad, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 160, 180, -1));
        getContentPane().add(txtPais, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 180, -1));

        jLabel4.setText("Pais");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, -1));

        jButton1.setText("Agregar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 220, 100, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            int id = Integer.parseInt(txtId.getText());
            String telefono = txtTelefono.getText();
            String direccionCorreo = txtCorreo.getText();
            String pais = txtPais.getText();
            String ciudad = txtCiudad.getText();
            String codigoPostal = txtCodigoPostal.getText();

            if (telefono.isEmpty() || direccionCorreo.isEmpty() || pais.isEmpty() || ciudad.isEmpty() || codigoPostal.isEmpty()) {
                throw new IllegalArgumentException("Todos los campos deben estar llenos.");
            }
            // Formatear el teléfono y validar
            telefono = formatearTelefono(telefono);
            // Validar formato del código postal (solo números)
            if (!codigoPostal.matches("\\d+")) {
                throw new IllegalArgumentException("El código postal debe contener solo números.");
            }
            InformacionContacto infoContacto = new InformacionContacto(id, telefono, direccionCorreo, pais, ciudad, codigoPostal);
            infoContacto.guardarEnArchivo();

            JOptionPane.showMessageDialog(null, "Información de contacto guardada correctamente");
            limpiarCampos();
            // Incrementar el contador de ID para el próximo registro
            contadorId++;
            // Actualizar el campo de ID en la interfaz
            txtId.setText(String.valueOf(contadorId));
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void limpiarCampos() {
        txtId.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtPais.setText("");
        txtCiudad.setText("");
        txtCodigoPostal.setText("");
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
            java.util.logging.Logger.getLogger(InformacionContactoForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InformacionContactoForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InformacionContactoForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InformacionContactoForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InformacionContactoForm().setVisible(true);
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
    private javax.swing.JTextField txtCiudad;
    private javax.swing.JTextField txtCodigoPostal;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtPais;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
