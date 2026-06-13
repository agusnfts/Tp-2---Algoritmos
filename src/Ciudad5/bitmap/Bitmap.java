package Ciudad5.bitmap;

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

        limpiar();
    }

    public BufferedImage getImage() {
        return imagen;
    }

    public void limpiar() {

        ValidacionesUtiles.esDistintoDeNull(
                g,
                "graphics"
        );

        ValidacionesUtiles.esDistintoDeNull(
                imagen,
                "imagen"
        );

        g.setColor(Color.WHITE);

        g.fillRect(
                0,
                0,
                imagen.getWidth(),
                imagen.getHeight()
        );
    }

    public void mostrarTexto(String texto) {

        ValidacionesUtiles.esDistintoDeNull(
                texto,
                "texto"
        );

        limpiar();

        g.setColor(Color.BLACK);

        g.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        18
                )
        );

        int y = 30;

        String[] lineas =
                texto.split("\n");

        for (String linea : lineas) {

            g.drawString(
                    linea,
                    20,
                    y
            );

            y += 25;
        }
    }
}


