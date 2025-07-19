package arbolesBinariosImpresoras;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Panel con fondo de imagen.
 */
public class panelFondo extends JPanel {

    private Image backgroundImage;

    public panelFondo() {
        try {
            backgroundImage = new ImageIcon("src/imagen/impresora.png").getImage();
            if (backgroundImage == null) {
                System.out.println("La imagen no se pudo cargar.");
            }
        } catch (Exception e) {
            System.out.println("Error al cargar la imagen: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
