/*
UNIVERSIDAD ESTATAL A DISTANCIA
Curso:Estructuras de datos
C�digo: 0825
Proyecto #3: Gesti�n de �rbol Binario de Impresoras
Tutor:Steve Brenes Torres
Grupo:03
Estudiante: Francisco Campos Sandi
C�dula: 114750560
II Cuatrimestre 2024
 */
package arbolesBinariosImpresoras;

import javax.swing.*;
import java.awt.*;

public class ArbolPanel extends JPanel {

    private Nodo raiz;

    // Constructor que inicializa el panel con un nodo ra�z
    public ArbolPanel(Nodo raiz) {
        this.raiz = raiz;
        setPreferredSize(new Dimension(800, 600)); // Establece el tama�o preferido del panel
        setBackground(Color.white); // Fondo blanco para mejor contraste
    }

    // M�todo que se llama autom�ticamente para dibujar el panel
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

    // M�todo recursivo para dibujar cada nodo del �rbol
    private void dibujarNodo(Graphics2D grafico, Nodo nodo, int x, int y, int xOffset) {
        if (nodo == null) {
            return; // Si el nodo es null, no hace nada
        }

        grafico.setFont(new Font("Arial", Font.BOLD, 16)); // Tama�o de fuente

        // Dibuja el nodo
        grafico.setColor(Color.YELLOW); // Color de relleno del nodo
        int nodeX = x - 20; // Coordenada x del nodo
        int nodeY = y - 20; // Coordenada y del nodo
        grafico.fillOval(nodeX, nodeY, 40, 40); // Dibuja el nodo como un c�rculo

        // Dibuja el contorno del nodo m�s grueso
        grafico.setStroke(new BasicStroke(3)); // Grosor de 2 p�xeles
        grafico.setColor(Color.BLACK); // Color del contorno
        grafico.drawOval(nodeX, nodeY, 40, 40); // Dibuja el contorno del nodo

        // Dibuja el texto dentro del nodo en negro
        grafico.setColor(Color.BLACK); // Color del texto dentro del nodo
        String texto = Integer.toString(nodo.getImpresora().getId());
        FontMetrics fm = grafico.getFontMetrics();
        int textoX = x - fm.stringWidth(texto) / 2; // Coordenada x para centrar el texto
        int textoY = y + fm.getAscent() / 2; // Coordenada y para centrar el texto
        grafico.drawString(texto, textoX, textoY);

        // Dibuja las conexiones con los nodos hijos con l�neas m�s gruesas
        grafico.setStroke(new BasicStroke(3)); // Grosor de 2 p�xeles
        if (nodo.getIzquierda() != null) {
            grafico.setColor(Color.BLACK); // Color de la l�nea de conexi�n
            grafico.drawLine(x - 10, y + 20, x - xOffset + 10, y + 50); // L�nea hacia el nodo hijo izquierdo
            dibujarNodo(grafico, nodo.getIzquierda(), x - xOffset, y + 50, xOffset / 2); // Dibuja el nodo hijo izquierdo
        }
        if (nodo.getDerecha() != null) {
            grafico.setColor(Color.BLACK); // Color de la l�nea de conexi�n
            grafico.drawLine(x + 10, y + 20, x + xOffset - 10, y + 50); // L�nea hacia el nodo hijo derecho
            dibujarNodo(grafico, nodo.getDerecha(), x + xOffset, y + 50, xOffset / 2); // Dibuja el nodo hijo derecho
        }
    }

    // M�todo para actualizar el �rbol y redibujar el panel
    public void actualizarArbol(Nodo raiz) {
        this.raiz = raiz;
        repaint(); // Redibuja el panel con el nuevo �rbol
    }
}
