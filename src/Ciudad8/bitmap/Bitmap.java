package Ciudad8.bitmap;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import utiles.ValidacionesUtiles;

public class Bitmap {

    private BufferedImage imagen;
    private Graphics2D g;

    public Bitmap(int ancho, int alto) {

        ValidacionesUtiles.validarMayorACero(
                ancho,
                "ancho"
        );

        ValidacionesUtiles.validarMayorACero(
                alto,
                "alto"
        );

        imagen = new BufferedImage(
                ancho,
                alto,
                BufferedImage.TYPE_INT_RGB
        );

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

    public void rellenar(Color color) {

        ValidacionesUtiles.esDistintoDeNull(
                color,
                "color"
        );

        g.setColor(color);

        g.fillRect(
                0,
                0,
                imagen.getWidth(),
                imagen.getHeight()
        );
    }

    public void drawLine(
            int x1,
            int y1,
            int x2,
            int y2,
            Color color
    ) {

        ValidacionesUtiles.esDistintoDeNull(
                color,
                "color"
        );

        g.setColor(color);

        g.drawLine(
                x1,
                y1,
                x2,
                y2
        );
    }

    public void drawRectangle(
            int x,
            int y,
            int ancho,
            int alto,
            Color color
    ) {

        ValidacionesUtiles.validarMayorOIgualACero(
                ancho,
                "ancho"
        );

        ValidacionesUtiles.validarMayorOIgualACero(
                alto,
                "alto"
        );

        ValidacionesUtiles.esDistintoDeNull(
                color,
                "color"
        );

        g.setColor(color);

        g.drawRect(
                x,
                y,
                ancho,
                alto
        );
    }

    public void fillRectangle(
            int x,
            int y,
            int ancho,
            int alto,
            Color color
    ) {

        ValidacionesUtiles.validarMayorOIgualACero(
                ancho,
                "ancho"
        );

        ValidacionesUtiles.validarMayorOIgualACero(
                alto,
                "alto"
        );

        ValidacionesUtiles.esDistintoDeNull(
                color,
                "color"
        );

        g.setColor(color);

        g.fillRect(
                x,
                y,
                ancho,
                alto
        );
    }

    public void drawCircle(
            int centroX,
            int centroY,
            int radio,
            Color color
    ) {

        ValidacionesUtiles.validarMayorOIgualACero(
                radio,
                "radio"
        );

        ValidacionesUtiles.esDistintoDeNull(
                color,
                "color"
        );

        g.setColor(color);

        g.drawOval(
                centroX - radio,
                centroY - radio,
                radio * 2,
                radio * 2
        );
    }

    public void fillCircle(
            int centroX,
            int centroY,
            int radio,
            Color color
    ) {

        ValidacionesUtiles.validarMayorOIgualACero(
                radio,
                "radio"
        );

        ValidacionesUtiles.esDistintoDeNull(
                color,
                "color"
        );

        g.setColor(color);

        g.fillOval(
                centroX - radio,
                centroY - radio,
                radio * 2,
                radio * 2
        );
    }

    public void drawText(
            String texto,
            int x,
            int y,
            Font fuente,
            Color color,
            Color fondo
    ) {

        ValidacionesUtiles.esDistintoDeNull(
                texto,
                "texto"
        );

        ValidacionesUtiles.esDistintoDeNull(
                fuente,
                "fuente"
        );

        ValidacionesUtiles.esDistintoDeNull(
                color,
                "color"
        );

        ValidacionesUtiles.esDistintoDeNull(
                fondo,
                "fondo"
        );

        g.setFont(fuente);

        g.setColor(color);

        g.drawString(
                texto,
                x,
                y
        );
    }
}


