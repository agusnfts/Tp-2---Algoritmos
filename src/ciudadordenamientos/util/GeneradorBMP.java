package ciudadordenamientos.util;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;
import java.io.File;

import javax.imageio.ImageIO;

public class GeneradorBMP {

    public static void generarBMP(int[] datos, String nombre) {

        try {

            BufferedImage imagen =
                    new BufferedImage(
                            800,
                            600,
                            BufferedImage.TYPE_INT_RGB
                    );

            Graphics2D g = imagen.createGraphics();

            g.setColor(Color.WHITE);
            g.fillRect(0, 0, 800, 600);

            int anchoBarra = 800 / datos.length;

            for(int i = 0; i < datos.length; i++) {

                int altura = datos[i] * 5;

                g.setColor(Color.BLUE);

                g.fillRect(
                        i * anchoBarra,
                        600 - altura,
                        anchoBarra - 2,
                        altura
                );
            }

            ImageIO.write(imagen, "bmp", new File(nombre));

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}