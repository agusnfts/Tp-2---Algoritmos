package Ciudad6;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.*;

import principal.ProgresoJuego;
import utiles.SistemaUtiles;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.*;
import utiles.SistemaUtiles;

public class PanelCiudad6 extends JPanel {

	private TablaHash tabla;
	private JTextField campoPalabra;
	private JTextArea areaExplicacion;
	private JLabel etiquetaProgreso;
	private PanelDibujo panelDibujo;
	private PasoHash pasoActual;
	private boolean animando;
	private int bucketsRequeridos;
	private int minPorBucket;
	private boolean ganada;
	private JButton botonSiguiente;
	private Runnable accionSiguiente;
	
	private ProgresoJuego progreso;

	/**
	 * pre: tamanioTabla > 1. bucketsRequeridos > 0. minPorBucket > 0.
	 * post: arma el panel con sus controles, crea la tabla hash a visualizar y deja
	 *       todo listo para insertar y buscar. La ciudad se gana cuando al menos
	 *       'bucketsRequeridos' buckets tienen 'minPorBucket' palabras o más.
	 */
	public PanelCiudad6(int tamanioTabla, int bucketsRequeridos, int minPorBucket,ProgresoJuego progreso) {
		
		this.progreso = progreso;
		
		this.tabla = new TablaHash(tamanioTabla);
		this.pasoActual = null;
		this.animando = false;
		this.bucketsRequeridos = bucketsRequeridos;
		this.minPorBucket = minPorBucket;
		this.ganada = false;
		setLayout(new BorderLayout());

		JPanel controles = new JPanel();
		campoPalabra = new JTextField(12);
		JButton botonInsertar = new JButton("Insertar");
		JButton botonBuscar = new JButton("Buscar");
		etiquetaProgreso = new JLabel("Buckets con >= " + minPorBucket + ": 0 / " + bucketsRequeridos);
		botonSiguiente = new JButton("Pasar a la siguiente ciudad");
		botonSiguiente.setVisible(false);
		controles.add(new JLabel("Palabra:"));
		controles.add(campoPalabra);
		controles.add(botonInsertar);
		controles.add(botonBuscar);
		controles.add(etiquetaProgreso);
		controles.add(botonSiguiente);
		add(controles, BorderLayout.NORTH);

		panelDibujo = new PanelDibujo();
		add(new JScrollPane(panelDibujo), BorderLayout.CENTER);

		areaExplicacion = new JTextArea(8, 40);
		areaExplicacion.setEditable(false);
		areaExplicacion.setText(textoIntroduccion());
		add(new JScrollPane(areaExplicacion), BorderLayout.SOUTH);

		botonInsertar.addActionListener(e -> ejecutar(true));
		botonBuscar.addActionListener(e -> ejecutar(false));
		botonSiguiente.addActionListener(e -> pasarASiguienteCiudad());
	}
	
	/**
	 * pre: progreso != null.
	 * post: crea la Ciudad 6 usando la configuración por defecto del juego
	 *       (tabla de tamaño 7, objetivo de 3 buckets con al menos 2 palabras)
	 *       y conserva la referencia al progreso para permitir desbloquear
	 *       la siguiente ciudad al completar el desafío.
	 */
	public PanelCiudad6(ProgresoJuego progreso) {
	    this(7, 3, 2, progreso);
	}
	
	/**
	 * pre: -
	 * post: define la acción que se ejecuta al pasar a la siguiente ciudad (por
	 *       ejemplo, volver al mapa del juego). Si no se define, el botón no hace nada.
	 */
	public void setAccionSiguiente(Runnable accion) {
		this.accionSiguiente = accion;
	}

	/**
	 * pre: -
	 * post: ejecuta la acción de pasar a la siguiente ciudad, si fue definida.
	 */
	private void pasarASiguienteCiudad() {
		if (accionSiguiente != null) {
			accionSiguiente.run();
		}
	}

	/**
	 * pre: -
	 * post: devuelve el texto de bienvenida que explica de qué se trata la ciudad
	 *       y cuál es el objetivo para ganarla.
	 */
	private String textoIntroduccion() {
		return "Ciudad 6: HASHING\n\n"
			+ "Una tabla hash sirve para encontrar datos muy rápido. La idea es que una\n"
			+ "función convierte cada palabra en un número, y ese número decide en qué\n"
			+ "casillero (bucket) se guarda. Para buscarla después no hace falta recorrer\n"
			+ "toda la tabla: se va directo al bucket que le corresponde.\n\n"
			+ "Acá la función suma los valores ASCII de las letras y le aplica el módulo "
			+ tabla.getTamanio() + ",\n"
			+ "que es la cantidad de buckets. Cuando dos palabras distintas caen en el\n"
			+ "mismo bucket hay una COLISION, y se resuelven encadenándolas en una lista\n"
			+ "dentro de ese bucket.\n\n"
			+ "Insertá y buscá palabras para ver el paso a paso. Al buscar, además se\n"
			+ "muestra cuánto tarda y cuántas comparaciones hace.\n\n"
			+ "OBJETIVO: llenar al menos " + bucketsRequeridos + " de los " + tabla.getTamanio()
			+ " buckets con " + minPorBucket + " palabras o más.";
	}

	/**
	 * pre: -
	 * post: si hay texto válido en el campo, ejecuta la inserción (insertar=true)
	 *       o la búsqueda (insertar=false) y reproduce sus pasos animados. Si la
	 *       validación falla, muestra el error en el área de explicación.
	 */
	private void ejecutar(boolean insertar) {
		if (animando) {
			return;
		}
		String palabra = campoPalabra.getText().trim();
		Queue<PasoHash> pasos;
		try {
			pasos = insertar ? tabla.insertar(palabra) : tabla.buscar(palabra);
		} catch (RuntimeException ex) {
			areaExplicacion.setText("Error: " + ex.getMessage());
			return;
		}
		String titulo = (insertar ? "INSERTAR \"" : "BUSCAR \"") + palabra + "\"";
		String mensajeFinal = null;
		if (!insertar) {
			Medicion m = tabla.medir(palabra);
			double micros = m.getNanos() / 1000.0;
			mensajeFinal = String.format(
					"Tiempo real de busqueda: %.2f microseg  -  comparaciones: %d",
					micros, m.getComparaciones());
		}
		reproducir(titulo, pasos, insertar, mensajeFinal);
	}

	/**
	 * pre: pasos != null.
	 * post: recorre la cola de pasos mostrando uno por vez (resaltado en el dibujo
	 *       más su descripción en el texto), con una pausa entre cada uno y sin
	 *       congelar la ventana, ya que corre en un hilo aparte. Al terminar, si
	 *       hay mensajeFinal lo agrega (por ejemplo el tiempo de búsqueda); si fue
	 *       una inserción, actualiza el progreso y chequea la victoria.
	 */
	private void reproducir(String titulo, Queue<PasoHash> pasos, boolean esInsercion, String mensajeFinal) {
		animando = true;
		SwingUtilities.invokeLater(() -> areaExplicacion.setText(titulo + "\n"));
		new Thread(() -> {
			while (!pasos.isEmpty()) {
				PasoHash paso = pasos.poll();
				pasoActual = paso;
				SwingUtilities.invokeLater(() -> {
					areaExplicacion.append("  " + paso.getDescripcion() + "\n");
					panelDibujo.repaint();
				});
				SistemaUtiles.esperar(800);
			}
			animando = false;
			if (mensajeFinal != null) {
				SwingUtilities.invokeLater(() -> areaExplicacion.append("  " + mensajeFinal + "\n"));
			}
			if (esInsercion) {
				SwingUtilities.invokeLater(this::actualizarProgreso);
			}
		}).start();
	}

	/**
	 * pre: -
	 * post: actualiza la etiqueta de progreso y, si se alcanzó el objetivo y la
	 *       ciudad aún no estaba ganada, la marca como ganada, desbloquea la
	 *       Ciudad 7 y avisa al jugador.
	 */
	private void actualizarProgreso() {

	    int logrados =
	            tabla.cantidadBucketsConAlMenos(
	                    minPorBucket
	            );

	    etiquetaProgreso.setText(
	            "Buckets con >= "
	            + minPorBucket
	            + ": "
	            + logrados
	            + " / "
	            + bucketsRequeridos
	    );

	    if (!ganada
	            && logrados >= bucketsRequeridos) {

	        ganada = true;

	        if (progreso != null
	                && !progreso.estaDesbloqueada(7)) {

	            progreso.desbloquear(7);

	            progreso.guardar();

	            System.out.println(
	                    "[CIUDAD 6] COMPLETADA"
	            );
	        }

	        JOptionPane.showMessageDialog(
	                this,
	                "¡Ciudad 6 superada!\n"
	                + "Has cumplido el objetivo.\n"
	                + "La Ciudad 7 ha sido desbloqueada."
	        );

	        botonSiguiente.setVisible(true);

	        revalidate();
	        repaint();
	    }
	}

	/**
	 * pre: -
	 * post: devuelve true si la ciudad ya fue ganada.
	 */
	public boolean estaGanada() {
		return ganada;
	}

	/**
	 * pre: tipo != null.
	 * post: devuelve el color de resaltado asociado al tipo de paso.
	 */
	private Color colorDe(PasoHash.Tipo tipo) {
		switch (tipo) {
			case COMPARACION:
				return Color.CYAN;
			case COLISION:
				return Color.ORANGE;
			case ENCONTRADO:
			case INSERTADO:
				return Color.GREEN;
			case NO_ENCONTRADO:
				return Color.RED;
			case YA_EXISTE:
				return Color.RED;
			default:
				return Color.YELLOW;
		}
	}

	private class PanelDibujo extends JPanel {

		private static final int ALTO_FILA = 50;
		private static final int ANCHO_CELDA = 95;
		private static final int MARGEN = 20;

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(800, MARGEN * 2 + tabla.getTamanio() * ALTO_FILA);
		}

		/**
		 * pre: -
		 * post: dibuja el arreglo de buckets con sus cadenas y resalta el bucket
		 *       y el elemento del paso que se está mostrando.
		 */
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			int bucketResaltado = (pasoActual != null) ? pasoActual.getBucket() : -1;
			int posResaltada = (pasoActual != null) ? pasoActual.getPosicionEnCadena() : -1;
			Color color = (pasoActual != null) ? colorDe(pasoActual.getTipo()) : Color.YELLOW;

			for (int i = 0; i < tabla.getTamanio(); i++) {
				int y = MARGEN + i * ALTO_FILA;

				if (i == bucketResaltado) {
					g2.setColor(color);
					g2.fillRect(MARGEN, y, ANCHO_CELDA, ALTO_FILA - 10);
				}
				g2.setColor(Color.BLACK);
				g2.drawRect(MARGEN, y, ANCHO_CELDA, ALTO_FILA - 10);
				g2.drawString("Bucket " + i, MARGEN + 10, y + 25);

				LinkedList<String> cadena = tabla.getBucket(i);
				int x = MARGEN + ANCHO_CELDA;
				for (int j = 0; j < cadena.size(); j++) {
					x += 30;
					g2.setColor(Color.BLACK);
					g2.drawLine(x - 30, y + 20, x, y + 20);

					if (i == bucketResaltado && j == posResaltada) {
						g2.setColor(color);
						g2.fillRect(x, y, ANCHO_CELDA, ALTO_FILA - 10);
					}
					g2.setColor(Color.BLACK);
					g2.drawRect(x, y, ANCHO_CELDA, ALTO_FILA - 10);
					g2.drawString(cadena.get(j), x + 10, y + 25);
					x += ANCHO_CELDA;
				}
			}
		}
	}
}