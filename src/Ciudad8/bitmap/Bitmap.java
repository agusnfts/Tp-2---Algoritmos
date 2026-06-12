package Ciudad8.bitmap;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import utiles.ValidacionesUtiles;

public class Bitmap {

    private BufferedImage imagen;
    private Graphics2D g;

    /**
     * PRE:
     * - ancho > 0
     * - alto > 0
     *
     * POST:
     * - Se crea una imagen en memoria (BufferedImage)
     * - Se inicializa el contexto gráfico (Graphics2D)
     * - Se rellena la imagen con color negro
     */
    public Bitmap(int ancho, int alto) {

        ValidacionesUtiles.validarMayorACero(ancho, "ancho");
        ValidacionesUtiles.validarMayorACero(alto, "alto");

        imagen = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
        g = imagen.createGraphics();

        rellenar(Color.BLACK);
    }

    public BufferedImage getImage() {
        return imagen;
    }

    public int getWidth() {
        return imagen.getWidth();
    }

    public int getHeight() {
        return imagen.getHeight();
    }

    /**
     * PRE:
     * - color != null
     *
     * POST:
     * - Se rellena toda la imagen con el color indicado
     */
    public void rellenar(Color color) {

        ValidacionesUtiles.esDistintoDeNull(color, "color");

        g.setColor(color);

        g.fillRect(0, 0, imagen.getWidth(), imagen.getHeight());
    }

    /**
     * PRE:
     * - color != null
     *
     * POST:
     * - Se dibuja una línea entre los puntos (x1, y1) y (x2, y2)
     */
    public void drawLine(
            int x1,
            int y1,
            int x2,
            int y2,
            Color color
    ) {

        ValidacionesUtiles.esDistintoDeNull(color, "color");

        g.setColor(color);

        g.drawLine(x1, y1, x2, y2);
    }

    /**
     * PRE:
     * - ancho >= 0
     * - alto >= 0
     * - color != null
     *
     * POST:
     * - Se dibuja un rectángulo vacío con esquina superior izquierda (x, y)
     */
    public void drawRectangle(
            int x,
            int y,
            int ancho,
            int alto,
            Color color
    ) {

        ValidacionesUtiles.validarMayorOIgualACero(ancho, "ancho");
        ValidacionesUtiles.validarMayorOIgualACero(alto, "alto");
        ValidacionesUtiles.esDistintoDeNull(color, "color");

        g.setColor(color);

        g.drawRect(x, y, ancho, alto);
    }

    /**
     * PRE:
     * - ancho >= 0
     * - alto >= 0
     * - color != null
     *
     * POST:
     * - Se dibuja un rectángulo relleno con esquina (x, y)
     */
    public void fillRectangle(
            int x,
            int y,
            int ancho,
            int alto,
            Color color
    ) {

        ValidacionesUtiles.validarMayorOIgualACero(ancho, "ancho");
        ValidacionesUtiles.validarMayorOIgualACero(alto, "alto");
        ValidacionesUtiles.esDistintoDeNull(color, "color");

        g.setColor(color);

        g.fillRect(x, y, ancho, alto);
    }

    /**
     * PRE:
     * - radio >= 0
     * - color != null
     *
     * POST:
     * - Se dibuja un círculo (contorno) con centro en (centroX, centroY)
     */
    public void drawCircle(
            int centroX,
            int centroY,
            int radio,
            Color color
    ) {

        ValidacionesUtiles.validarMayorOIgualACero(radio, "radio");
        ValidacionesUtiles.esDistintoDeNull(color, "color");

        g.setColor(color);

        g.drawOval(
                centroX - radio,
                centroY - radio,
                radio * 2,
                radio * 2
        );
    }

    /**
     * PRE:
     * - radio >= 0
     * - color != null
     *
     * POST:
     * - Se dibuja un círculo relleno con centro en (centroX, centroY)
     */
    public void fillCircle(
            int centroX,
            int centroY,
            int radio,
            Color color
    ) {

        ValidacionesUtiles.validarMayorOIgualACero(radio, "radio");
        ValidacionesUtiles.esDistintoDeNull(color, "color");

        g.setColor(color);

        g.fillOval(
                centroX - radio,
                centroY - radio,
                radio * 2,
                radio * 2
        );
    }

    /**
     * PRE:
     * - texto != null
     * - fuente != null
     * - color != null
     * - fondo != null (aunque no se utiliza directamente en el dibujo actual)
     *
     * POST:
     * - Se dibuja el texto en la posición (x, y) con la fuente y color indicados
     */
    public void drawText(
            String texto,
            int x,
            int y,
            Font fuente,
            Color color,
            Color fondo
    ) {

        ValidacionesUtiles.esDistintoDeNull(texto, "texto");
        ValidacionesUtiles.esDistintoDeNull(fuente, "fuente");
        ValidacionesUtiles.esDistintoDeNull(color, "color");
        ValidacionesUtiles.esDistintoDeNull(fondo, "fondo");

        g.setFont(fuente);
        g.setColor(color);

        g.drawString(texto, x, y);
    }
}


