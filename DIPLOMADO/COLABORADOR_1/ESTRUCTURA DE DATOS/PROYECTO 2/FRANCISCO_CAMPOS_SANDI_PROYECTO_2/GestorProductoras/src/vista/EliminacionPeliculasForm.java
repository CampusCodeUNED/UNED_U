package vista;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Pelicula;
import modelo.Productora;

public class EliminacionPeliculasForm extends javax.swing.JFrame {

    private DefaultTableModel modeloTabla;
    private Productora[] productoras;

    public EliminacionPeliculasForm(Productora[] productoras) {
        initComponents();

        this.productoras = productoras;
        setTitle("Eliminación de Películas");
        setSize(700, 400);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        cargarProductoras();

        // Configurar el modelo de la tabla
        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Nombre", "Género", "Tipo de Audiencia"}, 0);
        jTable1.setModel(modeloTabla);

        // Seleccionar la primera productora por defecto y actualizar la tabla
        if (jComboBox1.getItemCount() > 0) {
            jComboBox1.setSelectedIndex(0); // Seleccionar la primera productora
            actualizarTablaPeliculas(); // Actualizar la tabla con las películas de la primera productora
        }

        ImageIcon wallpaper = new ImageIcon("src/images/peliculas.jpg");
        Icon icono = new ImageIcon(wallpaper.getImage().getScaledInstance(jLabel1.getWidth(),
                jLabel1.getHeight(), Image.SCALE_DEFAULT));
        jLabel1.setIcon(icono);
        this.repaint();
    }

    public EliminacionPeliculasForm() {
    }

    private void cargarProductoras() {
        for (Productora productora : productoras) {
            if (productora != null) {
                jComboBox1.addItem(productora.getDescripcion());
            }
        }

        // Agregar listener para el JComboBox de productoras
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actualizarTablaPeliculas();
            }
        });
    }
    
    private void actualizarTablaPeliculas() {
    int selectedIndex = jComboBox1.getSelectedIndex();
    if (selectedIndex != -1) {
        modeloTabla.setRowCount(0);

        Productora selectedProductora = productoras[selectedIndex];
        Pelicula[] peliculas = selectedProductora.getPeliculas();
        int indicePeliculas = selectedProductora.getIndicePeliculas();

        for (int i = 0; i < indicePeliculas; i++) {
            Pelicula pelicula = peliculas[i];
            if (pelicula != null) {
                modeloTabla.addRow(new Object[]{pelicula.getDni(), pelicula.getNombre(), pelicula.getGenero(), pelicula.getAudiencia()});
            }
        }
    }
}


    private void eliminarPelicula() {
        int selectedIndex = jComboBox1.getSelectedIndex();
        if (selectedIndex != -1) {
            Productora selectedProductora = productoras[selectedIndex];
            Pelicula peliculaEliminada = selectedProductora.eliminarPrimeraPelicula();
            if (peliculaEliminada != null) {
                JOptionPane.showMessageDialog(this, "Película eliminada exitosamente.");
                actualizarTablaPeliculas();
            } else {
                JOptionPane.showMessageDialog(this, "No hay películas para eliminar.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una productora.");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 700, 240));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Eliminar Pelicula");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, -1, -1));
        getContentPane().add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 120, -1));

        jButton1.setText("Eliminar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 120, -1, -1));

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Seleccione productora");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 400));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        eliminarPelicula();
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
            java.util.logging.Logger.getLogger(EliminacionPeliculasForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EliminacionPeliculasForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EliminacionPeliculasForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EliminacionPeliculasForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EliminacionPeliculasForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
