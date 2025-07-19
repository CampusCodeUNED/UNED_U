package cv;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class CVPersonalizadoForm extends javax.swing.JFrame {

    // Listas para almacenar datos personales, experiencia laboral e información de contacto
    private List<DatosPersonales> datosPersonalesList;
    private List<ExperienciaLaboral> experienciaLaboralList;
    private List<InformacionContacto> informacionContactoList;

    public CVPersonalizadoForm() {
        // Configuración inicial del formulario
        this.getContentPane().setBackground(new Color(173, 216, 230));//color de fondo
        initComponents();
        setTitle("CV personalizado");
        setSize(420, 420);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        try {
            // Leer datos desde archivos
            datosPersonalesList = DatosPersonales.leerDatos("src/cv/datosPersonales.txt");
            experienciaLaboralList = ExperienciaLaboral.leerDatos("src/cv/experienciaLaboral.txt");
            informacionContactoList = InformacionContacto.leerDatos("src/cv/informacionContacto.txt");

            // Validar si alguna lista está vacía
            if (datosPersonalesList.isEmpty()) {
                throw new IllegalArgumentException("El archivo de datos personales está vacío.");
            }
            if (experienciaLaboralList.isEmpty()) {
                throw new IllegalArgumentException("El archivo de experiencia laboral está vacío.");
            }
            if (informacionContactoList.isEmpty()) {
                throw new IllegalArgumentException("El archivo de información de contacto está vacío.");
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al leer los archivos de datos. " + e.getMessage(), " ", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            // Mostrar el mensaje de advertencia para los archivos vacíos
            JOptionPane.showMessageDialog(this, e.getMessage(), "Advertencia archivos vacíos", JOptionPane.WARNING_MESSAGE);

            // Asegurarse de que dispose() se ejecute en el hilo de eventos de Swing
            SwingUtilities.invokeLater(() -> dispose());
            return; // Salir del constructor para evitar ejecutar el código que sigue
        }

        // Solo ejecutar si no se ha producido una excepción, cargar los Combo Box
        for (DatosPersonales dp : datosPersonalesList) {
            jComboBox1.addItem(dp.getId() + ": " + dp.getCedula() + " - " + dp.getNombre());
        }

        for (ExperienciaLaboral el : experienciaLaboralList) {
            jComboBox2.addItem(el.getId() + ": " + el.getNombreEmpresa() + " - " + el.getPuesto());
        }

        for (InformacionContacto ic : informacionContactoList) {
            jComboBox3.addItem(ic.getId() + ": " + ic.getTelefono() + " - " + ic.getDireccionCorreo());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        GenerarCv = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, 290, -1));
        getContentPane().add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, 290, -1));

        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, 290, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Generador de CV");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, -1, -1));

        GenerarCv.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        GenerarCv.setText("Generar CV");
        GenerarCv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GenerarCvActionPerformed(evt);
            }
        });
        getContentPane().add(GenerarCv, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 260, -1, -1));

        jLabel2.setText("Datos Personales:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, -1, -1));

        jLabel3.setText("Experiencia Laboral:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, -1, -1));

        jLabel4.setText("Información de Contacto:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void GenerarCvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GenerarCvActionPerformed
        // Obtener los índices seleccionados en los comboboxes
        int idDatosPersonales = jComboBox1.getSelectedIndex() + 1;
        int idExperienciaLaboral = jComboBox2.getSelectedIndex() + 1;
        int idInformacionContacto = jComboBox3.getSelectedIndex() + 1;
        // Crear una instancia de CvPersonalizado con los índices seleccionados
        CvPersonalizado cv = new CvPersonalizado(idDatosPersonales, idExperienciaLaboral, idInformacionContacto);
        try {
            // Guardar los datos en el archivo "registrosCv.txt"
            cv.guardarDatos("registrosCv.txt");
            DatosPersonales dp = datosPersonalesList.get(jComboBox1.getSelectedIndex());// Obtener el objeto DatosPersonales 
            String archivoCv = dp.getNombre().replace(" ", "_") + "-CV.txt";// Crear un nombre de archivo basado en el nombre de la persona, reemplazando los espacios por guiones bajos
            cv.generarCv(archivoCv, datosPersonalesList, experienciaLaboralList, informacionContactoList);
            JOptionPane.showMessageDialog(null, "CV generado exitosamente!");// Generar el CV en un archivo de texto
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al generar el CV. " + ex.getMessage(), " ", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_GenerarCvActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

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
            java.util.logging.Logger.getLogger(CVPersonalizadoForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CVPersonalizadoForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CVPersonalizadoForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CVPersonalizadoForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CVPersonalizadoForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton GenerarCv;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables
}
