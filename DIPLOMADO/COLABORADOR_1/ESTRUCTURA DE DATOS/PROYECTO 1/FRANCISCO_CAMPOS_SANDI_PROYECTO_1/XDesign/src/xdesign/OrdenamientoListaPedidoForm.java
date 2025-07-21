package xdesign;

import java.awt.Image;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

public class OrdenamientoListaPedidoForm extends javax.swing.JFrame {

    private ArrayList<Pedido> pedidos;
    private DefaultTableModel tableModel;

    public OrdenamientoListaPedidoForm(ArrayList<Pedido> pedidos) {
        initComponents();

        this.pedidos = pedidos;
        setTitle("Ordenar Pedidos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Object[] columnas = {"ID", "Tipo Camiseta", "Talla", "Cantidad", "Código Diseño", "Tipo Pedido", "Dirección", "Forma Pago", "Teléfono", "Nombre Cliente"};
        tableModel = new DefaultTableModel(columnas, 0);
        jTable1.setModel(tableModel);

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

    private OrdenamientoListaPedidoForm() {
    }

    // Método para actualizar la tabla con los pedidos
    private void actualizarTabla(ArrayList<Pedido> pedidosOrdenados) {
        tableModel.setRowCount(0);

        for (Pedido pedido : pedidosOrdenados) {
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

    // Ordenamiento por inserción (ascendente por número telefónico)
    private void ordenarPorInsercion(String[] arreglo) {
        for (int i = 1; i < arreglo.length; i++) {
            String key = arreglo[i];
            int j = i - 1;
            while (j >= 0 && arreglo[j].compareTo(key) > 0) {
                arreglo[j + 1] = arreglo[j];
                j = j - 1;
            }
            arreglo[j + 1] = key;
        }
    }

    // Ordenamiento merge sort (descendente por código de diseño)
    private void mergeSort(String[] arreglo, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            mergeSort(arreglo, l, m);
            mergeSort(arreglo, m + 1, r);
            merge(arreglo, l, m, r);
        }
    }

    private void merge(String[] arreglo, int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        String[] L = new String[n1];
        String[] R = new String[n2];

        System.arraycopy(arreglo, l, L, 0, n1);
        System.arraycopy(arreglo, m + 1, R, 0, n2);

        int i = 0, j = 0, k = l;
        while (i < n1 && j < n2) {
            if (L[i].compareTo(R[j]) >= 0) {
                arreglo[k] = L[i];
                i++;
            } else {
                arreglo[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arreglo[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            arreglo[k] = R[j];
            j++;
            k++;
        }
    }

    // Método para ordenar y mostrar los pedidos según el tipo de ordenamiento
    private void ordenarPedidos(String tipoOrdenamiento) {
        String[] arreglo;
        if (tipoOrdenamiento.equals("Telefono")) {
            arreglo = new String[pedidos.size()];
            for (int i = 0; i < pedidos.size(); i++) {
                arreglo[i] = pedidos.get(i).getNumeroTelefonico();
            }
            ordenarPorInsercion(arreglo);
        } else if (tipoOrdenamiento.equals("CodigoDiseno")) {
            arreglo = new String[pedidos.size()];
            for (int i = 0; i < pedidos.size(); i++) {
                arreglo[i] = pedidos.get(i).getCodigoDiseno();
            }
            mergeSort(arreglo, 0, arreglo.length - 1);
        } else {
            return;
        }

        ArrayList<Pedido> pedidosOrdenados = new ArrayList<>();
        for (String valor : arreglo) {
            for (Pedido pedido : pedidos) {
                if ((tipoOrdenamiento.equals("Telefono") && pedido.getNumeroTelefonico().equals(valor))
                        || (tipoOrdenamiento.equals("CodigoDiseno") && pedido.getCodigoDiseno().equals(valor))) {
                    pedidosOrdenados.add(pedido);
                    break;
                }
            }
        }

        actualizarTabla(pedidosOrdenados);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setText("Ordenar por teléfono");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, -1, -1));

        jButton2.setText("Ordenar por código");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 80, -1, -1));

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

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 750, 230));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setText("Ordenar pedidos");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, -1, -1));
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 374));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ordenarPedidos("Telefono");

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ordenarPedidos("CodigoDiseno");

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
            java.util.logging.Logger.getLogger(OrdenamientoListaPedidoForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OrdenamientoListaPedidoForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OrdenamientoListaPedidoForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrdenamientoListaPedidoForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OrdenamientoListaPedidoForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
