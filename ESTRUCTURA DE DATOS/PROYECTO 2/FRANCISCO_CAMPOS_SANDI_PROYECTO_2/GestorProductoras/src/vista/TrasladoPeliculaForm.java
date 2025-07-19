package vista;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import modelo.Pelicula;
import modelo.Productora;

public class TrasladoPeliculaForm extends javax.swing.JFrame {

    private Productora[] productoras;
    MainForm mainForm;

    public TrasladoPeliculaForm(Productora[] productoras, MainForm mainForm) {
        initComponents();

        this.productoras = productoras;
        this.mainForm = mainForm;
        setTitle("Traslado de Películas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        cargarProductoras();

        ImageIcon wallpaper = new ImageIcon("src/images/peliculas.jpg");
        Icon icono = new ImageIcon(wallpaper.getImage().getScaledInstance(jLabel1.getWidth(),
                jLabel1.getHeight(), Image.SCALE_DEFAULT));
        jLabel1.setIcon(icono);
        this.repaint();
    }

    public TrasladoPeliculaForm() {
    }

    private void cargarProductoras() {
        for (Productora productora : productoras) {
            if (productora != null) {
                jComboBox1.addItem(productora.getDescripcion());
                jComboBox2.addItem(productora.getDescripcion());
            }
        }
    }

    private void trasladarPeliculas() {
        int indexOrigen = jComboBox1.getSelectedIndex();
        int indexDestino = jComboBox2.getSelectedIndex();

        // Verificar que la pila de productoras tenga al menos 2 elementos
        if (mainForm.getIndiceProductoras() < 2) {
            JOptionPane.showMessageDialog(this, "La pila de productoras debe tener al menos 2 elementos.");
            return;
        }

        // Verificar que se hayan seleccionado productoras válidas y que no sean las mismas
        if (indexOrigen == -1 || indexDestino == -1 || indexOrigen == indexDestino) {
            JOptionPane.showMessageDialog(this, "Seleccione productoras válidas, no pueden ser las mismas");
            return;
        }

        Productora productoraOrigen = productoras[indexOrigen];
        Productora productoraDestino = productoras[indexDestino];

        // Verificar que la productora origen no esté vacía
        if (productoraOrigen.getIndicePeliculas() == 0) {
            JOptionPane.showMessageDialog(this, "La productora origen no tiene películas.");
            return;
        }

        Pelicula[] peliculasOrigen = productoraOrigen.getPeliculas();
        int indicePeliculasOrigen = productoraOrigen.getIndicePeliculas();
        int indicePeliculasDestino = productoraDestino.getIndicePeliculas();
        int capacidadMaximaDestino = productoraDestino.getCapacidadMaxima();

        // Verificar si la productora destino tiene suficiente capacidad
        if (indicePeliculasDestino + indicePeliculasOrigen > capacidadMaximaDestino) {
            JOptionPane.showMessageDialog(this, "La productora destino no tiene suficiente capacidad.");
            return;
        }

        // Trasladar películas de origen a destino, agregándolas al final de la cola
        for (int i = 0; i < indicePeliculasOrigen; i++) {
            productoraDestino.agregarPelicula(peliculasOrigen[i]);
        }

        // Reinicializar la lista de películas de la productora origen con su capacidad máxima
        productoraOrigen.setPeliculas(new Pelicula[productoraOrigen.getCapacidadMaxima()]);
        productoraOrigen.setIndicePeliculas(0); // Reiniciar el índice de películas de la productora origen

        JOptionPane.showMessageDialog(this, "Películas trasladadas exitosamente.");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Traslado de peliculas");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, -1, -1));

        jButton1.setText("Trasladar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 180, -1, -1));

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Productora origen");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, -1, -1));

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Productora destino");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 100, -1, -1));

        getContentPane().add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, 100, -1));

        getContentPane().add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(252, 120, 100, -1));
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 300));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        trasladarPeliculas();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(TrasladoPeliculaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TrasladoPeliculaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TrasladoPeliculaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TrasladoPeliculaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TrasladoPeliculaForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables
}
