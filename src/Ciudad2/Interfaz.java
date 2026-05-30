package Ciudad2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

/*
 * Se encarga de dibujar el estado del tablero y guardarlo como imagen BMP.
 * Usa BufferedImage para trabajar los pixeles y Graphics2D como pincel.
 * Genera una imagen por cada paso del backtracking para mostrar el paso a paso.
 * El tablero siempre se dibuja al tamaño fijo que recibe, sin importar N.
 *
 * Atributos:
 * - COLOR_CELDA_CLARA: color de las celdas claras del tablero
 * - COLOR_CELDA_OSCURA: color de las celdas oscuras del tablero
 * - COLOR_REINA: color del circulo cuando se coloca una reina
 * - COLOR_BACKTRACK: color del circulo cuando se hace backtracking
 * - COLOR_FONDO: color del fondo general de la imagen
 * - carpetaSalida: carpeta donde se guardan las imagenes generadas
 */
public class Interfaz {

    private static final Color COLOR_CELDA_CLARA  = new Color(240, 217, 181);
    private static final Color COLOR_CELDA_OSCURA = new Color(181, 136, 99);
    private static final Color COLOR_REINA        = new Color(255, 215, 0);
    private static final Color COLOR_BACKTRACK    = new Color(220, 50, 50);
    private static final Color COLOR_FONDO        = new Color(30, 30, 30);

    private String carpetaSalida;

    // Inicializamos el renderizador con la carpeta donde se van a guardar las imagenes
    public Interfaz(String carpetaSalida) {
        this.carpetaSalida = carpetaSalida;
        new File(carpetaSalida).mkdirs();
    }

    // Generamos una imagen BMP por cada paso, siempre del tamaño fijo recibido
    public void generarPasos(List<int[]> pasos, int cantidadReinas, int tamTotal) {
        for (int i = 0; i < pasos.size(); i++) {
            int[] posiciones = pasos.get(i);
            // Detectamos si este paso fue un backtrack comparando con el anterior
            boolean esBacktrack = false;
            if (i > 0) {
                esBacktrack = esRetroceso(pasos.get(i - 1), posiciones, cantidadReinas);
            }
            BufferedImage imagen = dibujarTablero(posiciones, cantidadReinas, esBacktrack, i, tamTotal);
            guardarImagen(imagen, i);
        }
    }

    // Dibujamos el tablero completo, siempre 8x8, escalado al tamaño fijo recibido
    private BufferedImage dibujarTablero(int[] posiciones, int n, boolean esBacktrack, int numeroPaso, int tamTotal) {
        // Calculamos el tamaño de cada celda en base a N y el tamaño total fijo
        int tamCelda = tamTotal / n;
        int margen = 0;

        BufferedImage imagen = new BufferedImage(tamTotal, tamTotal + 25, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = imagen.createGraphics();

        // Activamos suavizado para que se vea mejor
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Pintamos el fondo general
        g.setColor(COLOR_FONDO);
        g.fillRect(0, 0, tamTotal, tamTotal + 25);

        // Aqui iteramos las celdas del tablero para pintarlas
        for (int fila = 0; fila < n; fila++) {
            for (int col = 0; col < n; col++) {
                int x = margen + col * tamCelda;
                int y = margen + fila * tamCelda;

                // Alternamos los colores de las celdas como un tablero de ajedrez
                if ((fila + col) % 2 == 0) {
                    g.setColor(COLOR_CELDA_CLARA);
                } else {
                    g.setColor(COLOR_CELDA_OSCURA);
                }
                g.fillRect(x, y, tamCelda, tamCelda);

                // Si hay una reina en esta celda la dibujamos
                if (posiciones[fila] == col) {
                    boolean esEstaBacktrack = esBacktrack && fila == ultimaFilaConReina(posiciones, n);
                    dibujarReina(g, x, y, tamCelda, esEstaBacktrack);
                }
            }
        }

        // Dibujamos el borde del tablero
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2));
        g.drawRect(margen, margen, n * tamCelda, n * tamCelda);

        // Mostramos el numero de paso en la parte inferior
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 13));
        g.drawString("Paso: " + numeroPaso, 8, tamTotal + 18);

        g.dispose();
        return imagen;
    }

    // Dibujamos el simbolo de la reina como un circulo con la letra Q
    private void dibujarReina(Graphics2D g, int x, int y, int tamCelda, boolean esBacktrack) {
        int margenReina = tamCelda / 8;
        int tamReina = tamCelda - 2 * margenReina;

        // Elegimos el color segun si es backtrack o no
        if (esBacktrack) {
            g.setColor(COLOR_BACKTRACK);
        } else {
            g.setColor(COLOR_REINA);
        }

        // Dibujamos el circulo principal de la reina
        g.fillOval(x + margenReina, y + margenReina, tamReina, tamReina);

        // Dibujamos el borde del circulo
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2));
        g.drawOval(x + margenReina, y + margenReina, tamReina, tamReina);

        // Dibujamos la letra Q en el centro
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, tamReina / 2));
        int xTexto = x + tamCelda / 2 - tamReina / 6;
        int yTexto = y + tamCelda / 2 + tamReina / 6;
        g.drawString("Q", xTexto, yTexto);
    }

    // Guardamos la imagen en la carpeta de salida con el nombre paso_XXXX.bmp
    private void guardarImagen(BufferedImage imagen, int numeroPaso) {
        String nombreArchivo = String.format("%s/paso_%04d.bmp", carpetaSalida, numeroPaso);
        try {
            ImageIO.write(imagen, "BMP", new File(nombreArchivo));
        } catch (IOException e) {
            System.out.println("Error al guardar la imagen: " + nombreArchivo);
            e.printStackTrace();
        }
    }

    // Comparamos dos posiciones para detectar si hubo un retroceso entre pasos
    private boolean esRetroceso(int[] posAnterior, int[] posActual, int n) {
        return contarReinas(posActual, n) < contarReinas(posAnterior, n);
    }

    // Contamos cuantas reinas hay colocadas en el vector de posiciones
    private int contarReinas(int[] posiciones, int n) {
        int contador = 0;
        for (int i = 0; i < n; i++) {
            if (posiciones[i] != TableroReinas.VACIO) {
                contador++;
            }
        }
        return contador;
    }

    // Buscamos la ultima fila que tiene una reina colocada
    private int ultimaFilaConReina(int[] posiciones, int n) {
        for (int i = n - 1; i >= 0; i--) {
            if (posiciones[i] != TableroReinas.VACIO) {
                return i;
            }
        }
        return -1;
    }
}