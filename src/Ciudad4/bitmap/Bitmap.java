package Ciudad4.bitmap;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Bitmap {

    private BufferedImage imagen;
    private Graphics2D g;

    public Bitmap(int ancho, int alto) {

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

        limpiar();

        if(datos == null
                || datos.length == 0) {

            return;
        }

        int anchoBarra =
                imagen.getWidth()
                / datos.length;

        for(int i = 0;
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


