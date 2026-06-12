package Ciudad9.bitmap;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Bitmap {

    private BufferedImage imagen;
    private Graphics2D g;

    public Bitmap(int ancho, int alto) {
        imagen = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
        g = imagen.createGraphics();
        rellenar(Color.BLACK);
    }

    public void rellenar(Color color) {
        g.setColor(color);
        g.fillRect(0, 0, imagen.getWidth(), imagen.getHeight());
    }

    public void drawLine(int x1, int y1, int x2, int y2, Color color) {
        g.setColor(color);
        g.drawLine(x1, y1, x2, y2);
    }

    public void drawText(String texto, int x, int y, Font fuente, Color color, Color fondo) {
        g.setFont(fuente);
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(texto);
        int textHeight = fm.getHeight();

        if (fondo != null) {
            g.setColor(fondo);
            g.fillRect(x - 3, y - textHeight + 3, textWidth + 6, textHeight + 3);
        }

        g.setColor(color);
        g.drawString(texto, x, y);
    }

    public BufferedImage getImage() {
        return imagen;
    }
}