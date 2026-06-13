package Ciudad6;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import principal.ProgresoJuego;
import utiles.SistemaUtiles;

public class PanelCiudad6 extends JPanel {

	private static final String CARPETA_PASOS = "resources/ciudad6/pasos";

	private TablaHash tabla;
	private RenderizadorHash renderizador;
	private ProgresoJuego progreso;
	private JTextField campoPalabra;
	private JTextArea areaExplicacion;
	private JLabel etiquetaProgreso;
	private JLabel labelImagen;
	private JSlider sliderPasos;
	private JButton botonAnterior;
	private JButton botonSiguientePaso;
	private boolean animando;
	private int totalPasos;
	private int bucketsRequeridos;
	private int minPorBucket;
	private boolean ganada;
	private JButton botonSiguiente;
	private Runnable accionSiguiente;

	/**
	 * pre: tamanioTabla > 1. bucketsRequeridos > 0. minPorBucket > 0.
	 * post: arma el panel con sus controles, crea la tabla hash a visualizar y deja
	 *       todo listo para insertar y buscar. El paso a paso se muestra mediante
	 *       imágenes BMP generadas en cada operación. Conserva la referencia al
	 *       progreso para desbloquear la siguiente ciudad al ganar. La ciudad se
	 *       gana cuando al menos 'bucketsRequeridos' buckets tienen 'minPorBucket'
	 *       palabras o más.
	 */
	public PanelCiudad6(int tamanioTabla, int bucketsRequeridos, int minPorBucket, ProgresoJuego progreso) {
		this.tabla = new TablaHash(tamanioTabla);
		this.renderizador = new RenderizadorHash(CARPETA_PASOS);
		this.progreso = progreso;
		this.animando = false;
		this.totalPasos = 0;
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

		add(construirPanelImagen(), BorderLayout.CENTER);

		areaExplicacion = new JTextArea(10, 40);
		areaExplicacion.setEditable(false);
		areaExplicacion.setText(textoIntroduccion());
		add(new JScrollPane(areaExplicacion), BorderLayout.SOUTH);

		botonInsertar.addActionListener(e -> ejecutar(true));
		botonBuscar.addActionListener(e -> ejecutar(false));
		botonSiguiente.addActionListener(e -> pasarASiguienteCiudad());
	}

	/**
	 * pre: progreso != null.
	 * post: crea la Ciudad 6 con la configuración por defecto del juego (tabla de
	 *       tamaño 7, objetivo de 3 buckets con al menos 2 palabras) y conserva la
	 *       referencia al progreso para desbloquear la siguiente ciudad al ganar.
	 */
	public PanelCiudad6(ProgresoJuego progreso) {
		this(7, 3, 2, progreso);
	}

	/**
	 * pre: -
	 * post: devuelve el panel central con el área donde se muestra la imagen BMP
	 *       del paso actual y, debajo, el slider y los botones para navegarlos.
	 */
	private JPanel construirPanelImagen() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));

		labelImagen = new JLabel("Insertá o buscá una palabra para ver el paso a paso", SwingConstants.CENTER);
		labelImagen.setVerticalAlignment(SwingConstants.TOP);
		labelImagen.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(labelImagen, BorderLayout.CENTER);

		sliderPasos = new JSlider(0, 0, 0);
		sliderPasos.setEnabled(false);
		sliderPasos.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				mostrarPaso(sliderPasos.getValue());
			}
		});

		botonAnterior = new JButton("< Anterior");
		botonAnterior.setEnabled(false);
		botonAnterior.addActionListener(e -> irAPaso(sliderPasos.getValue() - 1));

		botonSiguientePaso = new JButton("Siguiente >");
		botonSiguientePaso.setEnabled(false);
		botonSiguientePaso.addActionListener(e -> irAPaso(sliderPasos.getValue() + 1));

		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 4));
		panelBotones.add(botonAnterior);
		panelBotones.add(botonSiguientePaso);

		JPanel panelNavegacion = new JPanel(new BorderLayout(3, 3));
		panelNavegacion.add(sliderPasos, BorderLayout.CENTER);
		panelNavegacion.add(panelBotones, BorderLayout.SOUTH);

		panel.add(panelNavegacion, BorderLayout.SOUTH);
		return panel;
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
			+ "Una tabla hash sirve para encontrar datos muy rápido. Una función convierte\n"
			+ "cada palabra en un número (acá, la suma de sus valores ASCII módulo "
			+ tabla.getTamanio() + ") que\n"
			+ "decide en qué bucket se guarda. Si dos palabras caen en el mismo bucket hay\n"
			+ "una COLISION y se encadenan en una lista dentro de ese bucket.\n\n"
			+ "Insertá y buscá palabras para ver el paso a paso. OBJETIVO: llenar al menos "
			+ bucketsRequeridos + " de\n"
			+ "los " + tabla.getTamanio() + " buckets con " + minPorBucket + " palabras o más.";
	}

	/**
	 * pre: -
	 * post: si hay texto válido en el campo, ejecuta la inserción (insertar=true)
	 *       o la búsqueda (insertar=false), genera las imágenes BMP de cada paso y
	 *       las reproduce. Si la validación falla, muestra el error en el área de
	 *       explicación.
	 */
	private void ejecutar(boolean insertar) {
		if (animando) {
			return;
		}
		String palabra = campoPalabra.getText().trim();
		Queue<PasoHash> cola;
		try {
			cola = insertar ? tabla.insertar(palabra) : tabla.buscar(palabra);
		} catch (RuntimeException ex) {
			areaExplicacion.setText("Error: " + ex.getMessage());
			return;
		}

		List<PasoHash> pasos = new ArrayList<PasoHash>(cola);
		renderizador.generarPasos(tabla, pasos);
		totalPasos = pasos.size();

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
	 * post: configura el slider con la cantidad de pasos y recorre la secuencia
	 *       mostrando un paso por vez (su imagen BMP más su descripción en el
	 *       texto), con una pausa entre cada uno y sin congelar la ventana, ya que
	 *       corre en un hilo aparte. Al terminar habilita la navegación manual; si
	 *       hay mensajeFinal lo agrega; si fue una inserción, actualiza el progreso
	 *       y chequea la victoria.
	 */
	private void reproducir(String titulo, List<PasoHash> pasos, boolean esInsercion, String mensajeFinal) {
		animando = true;
		SwingUtilities.invokeLater(() -> {
			areaExplicacion.setText(titulo + "\n");
			sliderPasos.setMinimum(0);
			sliderPasos.setMaximum(totalPasos - 1);
			sliderPasos.setValue(0);
			sliderPasos.setEnabled(false);
			botonAnterior.setEnabled(false);
			botonSiguientePaso.setEnabled(false);
		});

		new Thread(() -> {
			for (int i = 0; i < pasos.size(); i++) {
				final int indice = i;
				final PasoHash paso = pasos.get(i);
				SwingUtilities.invokeLater(() -> {
					areaExplicacion.append("  " + paso.getDescripcion() + "\n");
					sliderPasos.setValue(indice);
					mostrarPaso(indice);
				});
				SistemaUtiles.esperar(800);
			}
			animando = false;
			SwingUtilities.invokeLater(() -> {
				sliderPasos.setEnabled(true);
				botonAnterior.setEnabled(true);
				botonSiguientePaso.setEnabled(true);
			});
			if (mensajeFinal != null) {
				final String texto = mensajeFinal;
				SwingUtilities.invokeLater(() -> areaExplicacion.append("  " + texto + "\n"));
			}
			if (esInsercion) {
				SwingUtilities.invokeLater(this::actualizarProgreso);
			}
		}).start();
	}

	/**
	 * pre: -
	 * post: muestra en el label central la imagen BMP del paso indicado, si existe.
	 */
	private void mostrarPaso(int numeroPaso) {
		String ruta = String.format("%s/paso_%04d.bmp", CARPETA_PASOS, numeroPaso);
		File archivo = new File(ruta);
		if (!archivo.exists()) {
			return;
		}
		try {
			Image imagen = ImageIO.read(archivo);
			labelImagen.setIcon(new ImageIcon(imagen));
			labelImagen.setText("");
		} catch (IOException e) {
			labelImagen.setText("Error al cargar la imagen del paso " + numeroPaso);
		}
	}

	/**
	 * pre: -
	 * post: navega al paso indicado si está dentro del rango válido, actualizando
	 *       el slider y la imagen mostrada.
	 */
	private void irAPaso(int numeroPaso) {
		if (numeroPaso < 0 || numeroPaso >= totalPasos) {
			return;
		}
		sliderPasos.setValue(numeroPaso);
		mostrarPaso(numeroPaso);
	}

	/**
	 * pre: -
	 * post: actualiza la etiqueta de progreso y, si se alcanzó el objetivo y la
	 *       ciudad aún no estaba ganada, la marca como ganada, desbloquea y guarda
	 *       el avance de la Ciudad 7 y avisa al jugador.
	 */
	private void actualizarProgreso() {
		int logrados = tabla.cantidadBucketsConAlMenos(minPorBucket);
		etiquetaProgreso.setText("Buckets con >= " + minPorBucket + ": " + logrados + " / " + bucketsRequeridos);
		if (!ganada && logrados >= bucketsRequeridos) {
			ganada = true;

			if (progreso != null && !progreso.estaDesbloqueada(7)) {
				progreso.desbloquear(7);
				progreso.guardar();
			}

			JOptionPane.showMessageDialog(this,
					"¡Ciudad 6 superada! Llenaste " + bucketsRequeridos
							+ " buckets con al menos " + minPorBucket + " palabras cada uno.\n"
							+ "Ciudad 7 desbloqueada.",
					"Victoria", JOptionPane.INFORMATION_MESSAGE);
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
}