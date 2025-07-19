package xdesign;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ListarPedidosForm extends javax.swing.JFrame {

    private ArrayList<Pedido> pedidos;
    private DefaultTableModel tableModel;

    public ListarPedidosForm(ArrayList<Pedido> pedidos) {
        initComponents();
        this.pedidos = pedidos;

        setTitle("Listar y Editar Pedidos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Object[] columnas = {"ID", "Tipo Camiseta", "Talla", "Cantidad", "Código Diseño", "Tipo Pedido", "Dirección", "Forma Pago", "Teléfono", "Nombre Cliente"};
        tableModel = new DefaultTableModel(columnas, 0);
        jTable1.setModel(tableModel);
        actualizarTabla();

        //metodo de la clase imageicon para importar la imagen mediante el directorio
        ImageIcon wallpaper = new ImageIcon("src/images/pedido.jpg");
        //objeto de la clase icon dentro del constructor llamamos a la variable wallpaper lseguido del metodo getimage getscaledistance 
        //(traemos el label y obtenemos el ancho  por el metodo getWidth(), alto por el metodo getHeight(), y por ultimo la escala mediante la clase Image el metodo .scaledefault())
        //con este metodo adaptamos el tamaño de la imagen al label que contiene la imagen
        Icon icono = new ImageIcon(wallpaper.getImage().getScaledInstance(jLabel1.getWidth(),
                jLabel1.getHeight(), Image.SCALE_DEFAULT));

        //metodo setIcon para asignarle el wallpaper al label
        jLabel1.setIcon(icono);

        //metodo para que se apliquen los cambios realizados
        this.repaint();

    }

    private ListarPedidosForm() {
    }

    // Método para cargar todos los pedidos en la tabla utilizando Iterator
    public void actualizarTabla() {
        tableModel.setRowCount(0); // Limpiar tabla

        Iterator<Pedido> iterator = pedidos.iterator();
        while (iterator.hasNext()) {
            Pedido pedido = iterator.next();
            Object[] fila = {
                pedido.getId(),
                pedido.getTipoCamiseta(),
                pedido.getTalla(),
                pedido.getCantidad(),
                pedido.getCodigoDiseno(),
                pedido.getTipoPedido(),
                pedido.getDireccionEntrega(),
                pedido.getFormaPago(),
                pedido.getNumeroTelefonico(),
                pedido.getNombreCliente()
            };
            tableModel.addRow(fila);
        }
    }

    // Método para buscar pedidos por número telefónico
    private void buscarPedido() {
        String numeroTelefonico = txtBuscarTelefono.getText();
        ArrayList<Pedido> resultados = new ArrayList<>();

        Iterator<Pedido> iterator = pedidos.iterator();
        while (iterator.hasNext()) {
            Pedido pedido = iterator.next();
            if (pedido.getNumeroTelefonico().equals(numeroTelefonico)) {
                resultados.add(pedido);
            }
        }

        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron pedidos con ese número telefónico.", "Información", JOptionPane.INFORMATION_MESSAGE);
            tableModel.setRowCount(0); // Limpiar la tabla si no hay resultados
        } else {
            actualizarTabla(resultados);
        }
    }

    // Método para actualizar la tabla con nuevos pedidos (resultado de búsqueda)
    private void actualizarTabla(ArrayList<Pedido> nuevosPedidos) {
        tableModel.setRowCount(0); // Limpiar tabla si es necesario

        for (Pedido pedido : nuevosPedidos) {
            Object[] fila = {
                pedido.getId(),
                pedido.getTipoCamiseta(),
                pedido.getTalla(),
                pedido.getCantidad(),
                pedido.getCodigoDiseno(),
                pedido.getTipoPedido(),
                pedido.getDireccionEntrega(),
                pedido.getFormaPago(),
                pedido.getNumeroTelefonico(),
                pedido.getNombreCliente()
            };
            tableModel.addRow(fila);
        }
    }

    // Método para editar el pedido seleccionado
    private void editarPedido() {
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow != -1) {
            int idPedido = (int) jTable1.getValueAt(selectedRow, 0); // Obtener ID del pedido seleccionado
            Pedido pedidoSeleccionado = buscarPedidoPorId(idPedido);
            if (pedidoSeleccionado != null) {
                new EditarPedidoForm(pedidoSeleccionado, this).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró un pedido con el ID seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un pedido para editar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para buscar un pedido por su ID
    private Pedido buscarPedidoPorId(int id) {
        for (Pedido pedido : pedidos) {
            if (pedido.getId() == id) {
                return pedido;
            }
        }
        return null;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        txtBuscarTelefono = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("Buscar por telefono");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));
        getContentPane().add(txtBuscarTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 120, -1));

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

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 750, 220));

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, -1, -1));

        jButton2.setText("Editar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 110, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel3.setText("Listado de pedidos");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, -1, -1));
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 370));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        buscarPedido();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        editarPedido();

    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(ListarPedidosForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ListarPedidosForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ListarPedidosForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ListarPedidosForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ListarPedidosForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtBuscarTelefono;
    // End of variables declaration//GEN-END:variables
}
