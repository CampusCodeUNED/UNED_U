/*
UNIVERSIDAD ESTATAL A DISTANCIA
Curso:Estructuras de datos
Código: 0825
Proyecto #2: Gestor de produtoras
Tutor:Steve Brenes Torres
Grupo:03
Estudiante: Francisco Campos Sandi
Cédula: 114750560
II Cuatrimestre 2024
*/


package vista;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Productora;

public class MainForm extends javax.swing.JFrame {

    private DefaultTableModel modeloTabla;
    private Productora[] productoras;
    private int indiceProductoras;

    public MainForm() {
        initComponents();
        setTitle("Menú principal");
        setLocationRelativeTo(null);
        setSize(450, 350);
        setResizable(false);

        // Inicialización de productoras
        productoras = new Productora[100];
        indiceProductoras = 0;

        ImageIcon wallpaper = new ImageIcon("src/images/peliculas.jpg");
        Icon icono = new ImageIcon(wallpaper.getImage().getScaledInstance(jLabel1.getWidth(),
                jLabel1.getHeight(), Image.SCALE_DEFAULT));
        jLabel1.setIcon(icono);
        this.repaint();
    }

    public void agregarProductora(Productora productora) {
        if (indiceProductoras < productoras.length) {
            productoras[indiceProductoras++] = productora;
        } else {
            JOptionPane.showMessageDialog(this, "No se pueden agregar más productoras. Capacidad máxima alcanzada.");
        }
    }

    public void eliminarProductora(int indice) {
        if (indice >= 0 && indice < indiceProductoras) {
            // Mover las productoras una posición hacia atrás
            for (int i = indice; i < indiceProductoras - 1; i++) {
                productoras[i] = productoras[i + 1];
            }
            productoras[--indiceProductoras] = null; // Liberar la última posición ocupada
        } else {
            JOptionPane.showMessageDialog(this, "Índice de productora inválido.");
        }
    }

    public void actualizarTablaProductoras() {
        modeloTabla.setRowCount(0); 
        for (int i = 0; i < indiceProductoras; i++) {
            Productora productora = productoras[i];
            if (productora != null) {
                modeloTabla.addRow(new Object[]{productora.getId(), productora.getDescripcion()});
            }
        }
    }

    public int getIndiceProductoras() {
        return indiceProductoras;
    }

    public Productora[] getProductoras() {
        return productoras;
    }

    public void eliminarUltimaProductora() {
        if (indiceProductoras > 0) {
            productoras[--indiceProductoras] = null;
        }
    }

    public int calcularSiguienteId() {
        int maxId = 0;
        for (int i = 0; i < indiceProductoras; i++) {
            Productora productora = productoras[i];
            if (productora != null && productora.getId() > maxId) {
                maxId = productora.getId();
            }
        }
        return maxId + 1;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 450, 330));

        jMenu2.setText("Menu");
        jMenu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu2ActionPerformed(evt);
            }
        });

        jMenuItem1.setText("Gestión Productora");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuItem2.setText("Registro Pelicula");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem3.setText("Eliminación Pelicula");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuItem4.setText("Traslado Pelicula");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        GestionProductoraForm form = new GestionProductoraForm(productoras, this);
        form.setLocationRelativeTo(this); // Centrar en el MainForm
        form.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        RegistroPeliculaForm form = new RegistroPeliculaForm(productoras);
        form.setLocationRelativeTo(this); // Centrar en el MainForm
        form.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        EliminacionPeliculasForm form = new EliminacionPeliculasForm(productoras);
        form.setLocationRelativeTo(this); // Centrar en el MainForm
        form.setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        TrasladoPeliculaForm form = new TrasladoPeliculaForm(productoras, this);
        form.setLocationRelativeTo(this); // Centrar en el MainForm
        form.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu2ActionPerformed

    }//GEN-LAST:event_jMenu2ActionPerformed

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
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    // End of variables declaration//GEN-END:variables
}
