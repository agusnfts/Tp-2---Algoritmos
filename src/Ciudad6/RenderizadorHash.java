package Ciudad6;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
import utiles.ValidacionesUtiles;

/*
 * Se encarga de dibujar el estado de la tabla hash y guardarlo como imagen BMP.
 * Usa BufferedImage para trabajar los pixeles y Graphics2D como pincel.
 * Genera una imagen por cada paso de una operacion (insertar o buscar) para
 * mostrar el paso a paso. En cada imagen se dibuja la tabla completa con sus
 * cadenas y se resalta el bucket (y el elemento) involucrado en ese paso.
 *
 * Atributos:
 * - COLOR_FONDO: color de fondo de la imagen
 * - COLOR_CELDA: color de relleno de las celdas no resaltadas
 * - COLOR_BORDE: color de los bordes y el texto
 * - COLOR_CALCULO: color de resaltado para los pasos de calculo
 * - COLOR_COMPARACION: color de resaltado para las comparaciones
 * - COLOR_COLISION: color de resaltado para las colisiones
 * - COLOR_EXITO: color de resaltado para insertado/encontrado
 * - COLOR_ERROR: color de resaltado para no encontrado/ya existe
 * - ALTO_FILA: alto en pixeles que ocupa cada bucket
 * - ANCHO_CELDA: ancho en pixeles de cada celda (bucket o elemento)
 * - SEPARACION: largo de la linea que une un elemento con el siguiente
 * - MARGEN: margen general de la imagen
 * - ALTO_ENCABEZADO: alto reservado arriba para el rotulo del paso
 * - carpetaSalida: carpeta donde se guardan las imagenes generadas
 */
public class RenderizadorHash {

	private static final Color COLOR_FONDO       = new Color(245, 245, 245);
	private static final Color COLOR_CELDA       = new Color(255, 255, 255);
	private static final Color COLOR_BORDE       = Color.BLACK;
	private static final Color COLOR_CALCULO     = new Color(255, 235, 130);
	private static final Color COLOR_COMPARACION = new Color(130, 220, 240);
	private static final Color COLOR_COLISION    = new Color(255, 175, 80);
	private static final Color COLOR_EXITO       = new Color(120, 220, 120);
	private static final Color COLOR_ERROR       = new Color(235, 110, 110);

	private static final int ALTO_FILA      = 50;
	private static final int ANCHO_CELDA    = 95;
	private static final int SEPARACION     = 30;
	private static final int MARGEN         = 20;
	private static final int ALTO_ENCABEZADO = 35;

	private String carpetaSalida;

	/**
	 * pre: carpetaSalida != null y no vacía.
	 * post: inicializa el renderizador y crea la carpeta de salida si no existe.
	 */
	public RenderizadorHash(String carpetaSalida) {
		ValidacionesUtiles.validarLongitudDeTexto(carpetaSalida, 1, null, "carpeta de salida");
		this.carpetaSalida = carpetaSalida;
		new File(carpetaSalida).mkdirs();
	}

	/**
	 * pre: tabla != null. pasos != null.
	 * post: genera una imagen BMP por cada paso recibido, dibujando el estado
	 *       actual de la tabla y resaltando el bucket y el elemento de ese paso.
	 */
	public void generarPasos(TablaHash tabla, List<PasoHash> pasos) {
		ValidacionesUtiles.esDistintoDeNull(tabla, "tabla");
		ValidacionesUtiles.esDistintoDeNull(pasos, "pasos");
		for (int i = 0; i < pasos.size(); i++) {
			BufferedImage imagen = dibujarEstado(tabla, pasos.get(i), i, pasos.size());
			guardarImagen(imagen, i);
		}
	}

	/**
	 * pre: tabla != null. paso != null. totalPasos > 0.
	 * post: devuelve la imagen con la tabla hash completa, resaltando el bucket y,
	 *       si corresponde, el elemento de la cadena indicado por el paso, con el
	 *       color asociado al tipo de paso.
	 */
	private BufferedImage dibujarEstado(TablaHash tabla, PasoHash paso, int numeroPaso, int totalPasos) {
		int maxCadena = maxLargoCadena(tabla);
		int ancho = MARGEN + ANCHO_CELDA + maxCadena * (SEPARACION + ANCHO_CELDA) + MARGEN;
		if (ancho < 400) {
			ancho = 400;
		}
		int alto = ALTO_ENCABEZADO + tabla.getTamanio() * ALTO_FILA + MARGEN;

		BufferedImage imagen = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = imagen.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.setColor(COLOR_FONDO);
		g.fillRect(0, 0, ancho, alto);

		g.setColor(COLOR_BORDE);
		g.setFont(new Font("Arial", Font.BOLD, 14));
		g.drawString("Paso " + (numeroPaso + 1) + " de " + totalPasos, MARGEN, 24);

		int bucketResaltado = paso.getBucket();
		int posResaltada = paso.getPosicionEnCadena();
		Color colorResaltado = colorDe(paso.getTipo());

		g.setFont(new Font("Arial", Font.PLAIN, 13));
		g.setStroke(new BasicStroke(1));

		for (int i = 0; i < tabla.getTamanio(); i++) {
			int y = ALTO_ENCABEZADO + i * ALTO_FILA;

			if (i == bucketResaltado) {
				g.setColor(colorResaltado);
			} else {
				g.setColor(COLOR_CELDA);
			}
			g.fillRect(MARGEN, y, ANCHO_CELDA, ALTO_FILA - 10);
			g.setColor(COLOR_BORDE);
			g.drawRect(MARGEN, y, ANCHO_CELDA, ALTO_FILA - 10);
			g.drawString("Bucket " + i, MARGEN + 10, y + 25);

			LinkedList<String> cadena = tabla.getBucket(i);
			int x = MARGEN + ANCHO_CELDA;
			for (int j = 0; j < cadena.size(); j++) {
				x += SEPARACION;
				g.setColor(COLOR_BORDE);
				g.drawLine(x - SEPARACION, y + 20, x, y + 20);

				if (i == bucketResaltado && j == posResaltada) {
					g.setColor(colorResaltado);
				} else {
					g.setColor(COLOR_CELDA);
				}
				g.fillRect(x, y, ANCHO_CELDA, ALTO_FILA - 10);
				g.setColor(COLOR_BORDE);
				g.drawRect(x, y, ANCHO_CELDA, ALTO_FILA - 10);
				g.drawString(cadena.get(j), x + 10, y + 25);
				x += ANCHO_CELDA;
			}
		}

		g.dispose();
		return imagen;
	}

	/**
	 * pre: tipo != null.
	 * post: devuelve el color de resaltado asociado al tipo de paso.
	 */
	private Color colorDe(PasoHash.Tipo tipo) {
		switch (tipo) {
			case COMPARACION:
				return COLOR_COMPARACION;
			case COLISION:
				return COLOR_COLISION;
			case ENCONTRADO:
			case INSERTADO:
				return COLOR_EXITO;
			case NO_ENCONTRADO:
			case YA_EXISTE:
				return COLOR_ERROR;
			default:
				return COLOR_CALCULO;
		}
	}

	/**
	 * pre: tabla != null.
	 * post: devuelve la cantidad de elementos del bucket con la cadena más larga.
	 */
	private int maxLargoCadena(TablaHash tabla) {
		int max = 0;
		for (int i = 0; i < tabla.getTamanio(); i++) {
			int largo = tabla.getBucket(i).size();
			if (largo > max) {
				max = largo;
			}
		}
		return max;
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
}