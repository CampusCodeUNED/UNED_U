/*
UNIVERSIDAD ESTATAL A DISTANCIA
Curso:Estructuras de datos
Código: 0825
Proyecto #3: Gestión de Árbol Binario de Impresoras
Tutor:Steve Brenes Torres
Grupo:03
Estudiante: Francisco Campos Sandi
Cédula: 114750560
II Cuatrimestre 2024
 */
package arbolesBinariosImpresoras;

import javax.swing.*;
import java.awt.*;

public class ArbolPanel extends JPanel {

    private Nodo raiz;

    // Constructor que inicializa el panel con un nodo raíz
    public ArbolPanel(Nodo raiz) {
        this.raiz = raiz;
        setPreferredSize(new Dimension(800, 600)); // Establece el tamaño preferido del panel
        setBackground(Color.white); // Fondo blanco para mejor contraste
    }

    // Método que se llama automáticamente para dibujar el panel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (raiz != null) {
            Graphics2D g2d = (Graphics2D) g;

            // Activar antialiasing para texto y formas
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            dibujarNodo(g2d, raiz, getWidth() / 2, 30, getWidth() / 4);
        }
    }

    // Método recursivo para dibujar cada nodo del árbol
    private void dibujarNodo(Graphics2D grafico, Nodo nodo, int x, int y, int xOffset) {
        if (nodo == null) {
            return; // Si el nodo es null, no hace nada
        }

        grafico.setFont(new Font("Arial", Font.BOLD, 16)); // Tamaño de fuente

        // Dibuja el nodo
        grafico.setColor(Color.YELLOW); // Color de relleno del nodo
        int nodeX = x - 20; // Coordenada x del nodo
        int nodeY = y - 20; // Coordenada y del nodo
        grafico.fillOval(nodeX, nodeY, 40, 40); // Dibuja el nodo como un círculo

        // Dibuja el contorno del nodo más grueso
        grafico.setStroke(new BasicStroke(3)); // Grosor de 2 píxeles
        grafico.setColor(Color.BLACK); // Color del contorno
        grafico.drawOval(nodeX, nodeY, 40, 40); // Dibuja el contorno del nodo

        // Dibuja el texto dentro del nodo en negro
        grafico.setColor(Color.BLACK); // Color del texto dentro del nodo
        String texto = Integer.toString(nodo.getImpresora().getId());
        FontMetrics fm = grafico.getFontMetrics();
        int textoX = x - fm.stringWidth(texto) / 2; // Coordenada x para centrar el texto
        int textoY = y + fm.getAscent() / 2; // Coordenada y para centrar el texto
        grafico.drawString(texto, textoX, textoY);

        // Dibuja las conexiones con los nodos hijos con líneas más gruesas
        grafico.setStroke(new BasicStroke(3)); // Grosor de 2 píxeles
        if (nodo.getIzquierda() != null) {
            grafico.setColor(Color.BLACK); // Color de la línea de conexión
            grafico.drawLine(x - 10, y + 20, x - xOffset + 10, y + 50); // Línea hacia el nodo hijo izquierdo
            dibujarNodo(grafico, nodo.getIzquierda(), x - xOffset, y + 50, xOffset / 2); // Dibuja el nodo hijo izquierdo
        }
        if (nodo.getDerecha() != null) {
            grafico.setColor(Color.BLACK); // Color de la línea de conexión
            grafico.drawLine(x + 10, y + 20, x + xOffset - 10, y + 50); // Línea hacia el nodo hijo derecho
            dibujarNodo(grafico, nodo.getDerecha(), x + xOffset, y + 50, xOffset / 2); // Dibuja el nodo hijo derecho
        }
    }

    // Método para actualizar el árbol y redibujar el panel
    public void actualizarArbol(Nodo raiz) {
        this.raiz = raiz;
        repaint(); // Redibuja el panel con el nuevo árbol
    }
}
