package Ciudad4.bitmap;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import utiles.ValidacionesUtiles;
public class Bitmap {

    private BufferedImage imagen;
    private Graphics2D g;

    /**
     * PRE: ancho > 0 y alto > 0.
     * POST: crea un bitmap de las dimensiones indicadas y lo inicializa en blanco.
     */
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

    /**
     * POST: devuelve la imagen asociada al bitmap.
     */
    public BufferedImage getImage() {
        return imagen;
    }

    /**
     * PRE: graphics e imagen deben estar inicializados.
     * POST: limpia completamente el bitmap rellenándolo de color blanco.
     */
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

    /**
     * PRE: datos != null y datos.length > 0.
     * POST: dibuja un gráfico de barras representando los valores contenidos
     *       en el arreglo y muestra el valor numérico de cada barra.
     */
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
    
    /**
     * PRE: texto != null.
     * POST: limpia la imagen y muestra el texto recibido,
     *       respetando los saltos de línea.
     */
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