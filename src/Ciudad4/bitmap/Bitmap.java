package Ciudad4.bitmap;

import java.awt.Color;
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

    public void dibujarBarras(
            int[] datos
    ) {

        ValidacionesUtiles.esDistintoDeNull(
                datos,
                "datos"
        );

        ValidacionesUtiles.validarMayorACero(
                datos.length,
                "cantidad de datos"
        );

        limpiar();

        int anchoBarra =
                imagen.getWidth()
                / datos.length;

        for (int i = 0;
             i < datos.length;
             i++) {

            int altura =
                    datos[i] * 5;

            g.setColor(Color.BLUE);

            g.fillRect(
                    i * anchoBarra,
                    imagen.getHeight() - altura,
                    anchoBarra - 2,
                    altura
            );

            g.setColor(Color.BLACK);

            g.drawString(
                    String.valueOf(
                            datos[i]
                    ),
                    i * anchoBarra + 5,
                    imagen.getHeight()
                            - altura - 5
            );
        }
    }
}



