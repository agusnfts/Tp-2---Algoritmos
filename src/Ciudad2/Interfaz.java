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

import utiles.ValidacionesUtiles;

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

	/**
	 * pre: carpetaSalida != null y no vacía.
	 * post: inicializa el renderizador y crea la carpeta de salida si no existe.
	 */
	public Interfaz(String carpetaSalida) {
		ValidacionesUtiles.validarLongitudDeTexto(carpetaSalida, 1, null, "carpeta de salida");
		this.carpetaSalida = carpetaSalida;
		new File(carpetaSalida).mkdirs();
	}

	/**
	 * pre: pasos != null. cantidadReinas > 0. tamTotal > 0.
	 * post: genera una imagen BMP por cada paso recibido, dibujando el tablero al
	 *       tamaño fijo indicado.
	 */
	public void generarPasos(List<int[]> pasos, int cantidadReinas, int tamTotal) {
		ValidacionesUtiles.esDistintoDeNull(pasos, "pasos");
		ValidacionesUtiles.validarMayorACero(cantidadReinas, "cantidad de reinas");
		ValidacionesUtiles.validarMayorACero(tamTotal, "tamaño total");
		for (int i = 0; i < pasos.size(); i++) {
			int[] posiciones = pasos.get(i);
			boolean esBacktrack = false;
			if (i > 0) {
				esBacktrack = esRetroceso(pasos.get(i - 1), posiciones, cantidadReinas);
			}
			BufferedImage imagen = dibujarTablero(posiciones, cantidadReinas, esBacktrack, i, tamTotal);
			guardarImagen(imagen, i);
		}
	}

	/**
	 * pre: posiciones != null. n > 0. tamTotal > 0.
	 * post: devuelve la imagen del tablero con las reinas en las posiciones dadas,
	 *       marcando en rojo la última reina si el paso fue un retroceso.
	 */
	private BufferedImage dibujarTablero(int[] posiciones, int n, boolean esBacktrack, int numeroPaso, int tamTotal) {
		int tamCelda = tamTotal / n;
		int margen = 0;

		BufferedImage imagen = new BufferedImage(tamTotal, tamTotal + 25, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = imagen.createGraphics();

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.setColor(COLOR_FONDO);
		g.fillRect(0, 0, tamTotal, tamTotal + 25);

		for (int fila = 0; fila < n; fila++) {
			for (int col = 0; col < n; col++) {
				int x = margen + col * tamCelda;
				int y = margen + fila * tamCelda;

				if ((fila + col) % 2 == 0) {
					g.setColor(COLOR_CELDA_CLARA);
				} else {
					g.setColor(COLOR_CELDA_OSCURA);
				}
				g.fillRect(x, y, tamCelda, tamCelda);

				if (posiciones[fila] == col) {
					boolean esEstaBacktrack = esBacktrack && fila == ultimaFilaConReina(posiciones, n);
					dibujarReina(g, x, y, tamCelda, esEstaBacktrack);
				}
			}
		}

		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(2));
		g.drawRect(margen, margen, n * tamCelda, n * tamCelda);

		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 13));
		g.drawString("Paso: " + numeroPaso, 8, tamTotal + 18);

		g.dispose();
		return imagen;
	}

	/**
	 * pre: -
	 * post: dibuja la reina en la celda dada, dorada si es una colocación o roja si
	 *       corresponde a un retroceso.
	 */
	private void dibujarReina(Graphics2D g, int x, int y, int tamCelda, boolean esBacktrack) {
		int margenReina = tamCelda / 8;
		int tamReina = tamCelda - 2 * margenReina;

		if (esBacktrack) {
			g.setColor(COLOR_BACKTRACK);
		} else {
			g.setColor(COLOR_REINA);
		}

		g.fillOval(x + margenReina, y + margenReina, tamReina, tamReina);

		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(2));
		g.drawOval(x + margenReina, y + margenReina, tamReina, tamReina);

		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, tamReina / 2));
		int xTexto = x + tamCelda / 2 - tamReina / 6;
		int yTexto = y + tamCelda / 2 + tamReina / 6;
		g.drawString("Q", xTexto, yTexto);
	}

	/**
	 * pre: imagen != null.
	 * post: guarda la imagen en la carpeta de salida con el nombre paso_XXXX.bmp.
	 */
	private void guardarImagen(BufferedImage imagen, int numeroPaso) {
		String nombreArchivo = String.format("%s/paso_%04d.bmp", carpetaSalida, numeroPaso);
		try {
			ImageIO.write(imagen, "BMP", new File(nombreArchivo));
		} catch (IOException e) {
			System.out.println("Error al guardar la imagen: " + nombreArchivo);
			e.printStackTrace();
		}
	}

	/**
	 * pre: posAnterior != null. posActual != null.
	 * post: devuelve true si entre el paso anterior y el actual se quitó una reina,
	 *       es decir, si hubo un retroceso.
	 */
	private boolean esRetroceso(int[] posAnterior, int[] posActual, int n) {
		return contarReinas(posActual, n) < contarReinas(posAnterior, n);
	}

	/**
	 * pre: posiciones != null.
	 * post: devuelve cuántas reinas hay colocadas en el vector de posiciones.
	 */
	private int contarReinas(int[] posiciones, int n) {
		int contador = 0;
		for (int i = 0; i < n; i++) {
			if (posiciones[i] != TableroReinas.VACIO) {
				contador++;
			}
		}
		return contador;
	}

	/**
	 * pre: posiciones != null.
	 * post: devuelve la última fila que tiene una reina, o -1 si no hay ninguna.
	 */
	private int ultimaFilaConReina(int[] posiciones, int n) {
		for (int i = n - 1; i >= 0; i--) {
			if (posiciones[i] != TableroReinas.VACIO) {
				return i;
			}
		}
		return -1;
	}
}