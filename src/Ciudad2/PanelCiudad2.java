package Ciudad2;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import principal.ProgresoJuego;

/*
 * Panel principal de la Ciudad 2 - Problema de las N Reinas.
 * El tablero es siempre 8x8. El jugador elige cuantas reinas colocar (1-8)
 * y la posicion inicial de la primera reina.
 *
 * Atributos:
 * - CARPETA_PASOS: carpeta donde se guardan las imagenes BMP generadas
 * - TAM_TABLERO: tamaño fijo en pixeles del tablero en pantalla (siempre 8x8)
 * - labelImagen: label donde se muestra la imagen BMP del paso actual
 * - labelInfo: label con texto informativo del estado actual
 * - sliderPasos: slider para navegar entre los pasos del backtracking
 * - btnAnterior: boton para ir al paso anterior
 * - btnSiguiente: boton para ir al paso siguiente
 * - btnResolver: boton para lanzar la resolucion
 * - spinnerReinas: selector de cantidad de reinas a colocar (1-8)
 * - spinnerFila: selector de fila de la posicion inicial (1-8)
 * - spinnerColumna: selector de columna de la posicion inicial (1-8)
 * - pasoActual: indice del paso que se esta mostrando
 * - totalPasos: cantidad total de pasos generados
 */
public class PanelCiudad2 extends JPanel {

	private static final String CARPETA_PASOS = "resources/ciudad2/pasos";
	private static final int TAM_TABLERO = 560;
	private static final int MAX_MOVIMIENTOS = 10;

	private JLabel labelImagen;
	private JLabel labelInfo;
	private JSlider sliderPasos;
	private JButton btnAnterior;
	private JButton btnSiguiente;
	private JButton btnResolver;
	private JButton btnSalirCiudades;
	private JSpinner spinnerReinas;
	private JSpinner spinnerFila;
	private JSpinner spinnerColumna;

	private int pasoActual;
	private int totalPasos;
	private boolean ganada;
	private Runnable accionSalir;
	
	private ProgresoJuego progreso;

	/**
	 * pre: -
	 * post: inicializa el panel con dos pantallas (presentación y juego) y muestra
	 *       primero la de presentación.
	 */
	public PanelCiudad2(ProgresoJuego progreso) {
		
		this.progreso=progreso;
		this.pasoActual = 0;
		this.totalPasos = 0;
		this.ganada = false;
		setLayout(new CardLayout());
		add(construirPanelPresentacion(), "presentacion");
		add(construirPanelJuego(), "juego");
	}

	/**
	 * pre: -
	 * post: devuelve el texto que explica de qué se trata la ciudad y cómo ganarla.
	 */
	private String textoPresentacion() {
		return "En esta ciudad resolvés el clásico problema de las N reinas: ubicar N "
			+ "reinas en un tablero N x N sin que ninguna ataque a otra (ni en la misma "
			+ "fila, ni columna, ni diagonal).\n\n"
			+ "Elegís cuántas reinas usar y dónde va la primera. El algoritmo de "
			+ "BACKTRACKING prueba posiciones y, cuando una no lleva a una solución, "
			+ "retrocede (lo vas a ver en rojo) y prueba otra. Podés recorrer todo el "
			+ "paso a paso con el slider.\n\n"
			+ "CÓMO GANAR: encontrá una posición inicial con la que el backtracking llegue "
			+ "a la solución en menos de " + MAX_MOVIMIENTOS + " pasos. Cuanto mejor elijas "
			+ "el arranque, menos retrocesos necesita. Al lograrlo se habilita el botón "
			+ "para salir al mapa de ciudades.";
	}

	/**
	 * pre: -
	 * post: devuelve la pantalla de presentación con el título, la explicación y el
	 *       botón Comenzar, que lleva a la pantalla de juego.
	 */
	private JPanel construirPanelPresentacion() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBackground(new Color(30, 30, 30));
		panel.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

		JLabel titulo = new JLabel("Ciudad 2 - Problema de las N Reinas", SwingConstants.CENTER);
		titulo.setFont(new Font("Arial", Font.BOLD, 22));
		titulo.setForeground(new Color(255, 215, 0));

		JTextArea texto = new JTextArea(textoPresentacion());
		texto.setEditable(false);
		texto.setLineWrap(true);
		texto.setWrapStyleWord(true);
		texto.setBackground(new Color(30, 30, 30));
		texto.setForeground(Color.WHITE);
		texto.setFont(new Font("Arial", Font.PLAIN, 15));
		texto.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

		JButton btnComenzar = new JButton("Comenzar");
		btnComenzar.setBackground(new Color(255, 215, 0));
		btnComenzar.setForeground(Color.BLACK);
		btnComenzar.setFont(new Font("Arial", Font.BOLD, 16));
		btnComenzar.setPreferredSize(new Dimension(140, 36));
		btnComenzar.addActionListener(e -> mostrarJuego());

		JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelBoton.setBackground(new Color(30, 30, 30));
		panelBoton.add(btnComenzar);

		panel.add(titulo, BorderLayout.NORTH);
		panel.add(texto, BorderLayout.CENTER);
		panel.add(panelBoton, BorderLayout.SOUTH);
		return panel;
	}

	/**
	 * pre: -
	 * post: cambia de la pantalla de presentación a la pantalla de juego.
	 */
	private void mostrarJuego() {
		((CardLayout) getLayout()).show(this, "juego");
	}

	/**
	 * pre: -
	 * post: devuelve la pantalla de juego con el título, el área de imagen y los
	 *       controles.
	 */
	private JPanel construirPanelJuego() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.setBackground(new Color(30, 30, 30));
		panel.add(construirPanelTitulo(), BorderLayout.NORTH);
		panel.add(construirPanelImagen(), BorderLayout.CENTER);
		panel.add(construirPanelControles(), BorderLayout.SOUTH);
		return panel;
	}

	/**
	 * pre: -
	 * post: devuelve el panel con el título de la ciudad.
	 */
	private JPanel construirPanelTitulo() {
		JPanel panel = new JPanel();
		panel.setBackground(new Color(20, 20, 20));
		panel.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
		JLabel titulo = new JLabel("Ciudad 2 - Problema de las N Reinas", SwingConstants.CENTER);
		titulo.setFont(new Font("Arial", Font.BOLD, 20));
		titulo.setForeground(new Color(255, 215, 0));
		panel.add(titulo);
		return panel;
	}

	/**
	 * pre: -
	 * post: devuelve el panel donde se muestra la imagen BMP del paso actual, con un
	 *       tamaño fijo igual a TAM_TABLERO.
	 */
	private JPanel construirPanelImagen() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.setBackground(new Color(30, 30, 30));
		panel.setBorder(BorderFactory.createEmptyBorder(8, 10, 5, 10));

		labelImagen = new JLabel("Ingresa los parametros y presiona Resolver", SwingConstants.CENTER);
		labelImagen.setFont(new Font("Arial", Font.PLAIN, 14));
		labelImagen.setForeground(Color.WHITE);
		labelImagen.setPreferredSize(new Dimension(TAM_TABLERO, TAM_TABLERO));
		labelImagen.setMinimumSize(new Dimension(TAM_TABLERO, TAM_TABLERO));
		labelImagen.setMaximumSize(new Dimension(TAM_TABLERO, TAM_TABLERO));
		labelImagen.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80)));

		labelInfo = new JLabel(" ", SwingConstants.CENTER);
		labelInfo.setFont(new Font("Arial", Font.PLAIN, 13));
		labelInfo.setForeground(new Color(200, 200, 200));

		JPanel panelCentro = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		panelCentro.setBackground(new Color(30, 30, 30));
		panelCentro.add(labelImagen);

		panel.add(panelCentro, BorderLayout.CENTER);
		panel.add(labelInfo, BorderLayout.SOUTH);
		return panel;
	}

	/**
	 * pre: -
	 * post: devuelve el panel inferior con los controles de entrada y navegación.
	 */
	private JPanel construirPanelControles() {
		JPanel panel = new JPanel(new BorderLayout(3, 3));
		panel.setBackground(new Color(20, 20, 20));
		panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 8, 10));
		panel.add(construirPanelEntrada(), BorderLayout.NORTH);
		panel.add(construirPanelNavegacion(), BorderLayout.SOUTH);
		return panel;
	}

	/**
	 * pre: -
	 * post: devuelve el panel con los tres spinners (reinas, fila y columna) y el
	 *       botón Resolver.
	 */
	private JPanel construirPanelEntrada() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(new Color(20, 20, 20));

		JPanel filaSpinners = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 4));
		filaSpinners.setBackground(new Color(20, 20, 20));

		JLabel lblReinas = new JLabel("Cantidad de reinas (1-8):");
		lblReinas.setForeground(Color.WHITE);
		spinnerReinas = new JSpinner(new SpinnerNumberModel(4, 1, 8, 1));
		spinnerReinas.setPreferredSize(new Dimension(50, 28));

		JLabel lblFila = new JLabel("Fila inicial:");
		lblFila.setForeground(Color.WHITE);
		spinnerFila = new JSpinner(new SpinnerNumberModel(1, 1, 4, 1));
		spinnerFila.setPreferredSize(new Dimension(50, 28));

		JLabel lblColumna = new JLabel("Columna inicial:");
		lblColumna.setForeground(Color.WHITE);
		spinnerColumna = new JSpinner(new SpinnerNumberModel(1, 1, 4, 1));
		spinnerColumna.setPreferredSize(new Dimension(50, 28));

		spinnerReinas.addChangeListener(e -> actualizarRangoPosicionInicial());

		filaSpinners.add(lblReinas);
		filaSpinners.add(spinnerReinas);
		filaSpinners.add(lblFila);
		filaSpinners.add(spinnerFila);
		filaSpinners.add(lblColumna);
		filaSpinners.add(spinnerColumna);

		JPanel filaResolver = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 4));
		filaResolver.setBackground(new Color(20, 20, 20));

		btnResolver = new JButton("  Resolver  ");
		btnResolver.setBackground(new Color(255, 215, 0));
		btnResolver.setForeground(Color.BLACK);
		btnResolver.setFont(new Font("Arial", Font.BOLD, 14));
		btnResolver.setPreferredSize(new Dimension(130, 32));
		btnResolver.addActionListener(e -> lanzarResolucion());
		filaResolver.add(btnResolver);

		panel.add(filaSpinners, BorderLayout.NORTH);
		panel.add(filaResolver, BorderLayout.SOUTH);
		return panel;
	}

	/**
	 * pre: -
	 * post: devuelve el panel con el slider y los botones para navegar entre los
	 *       pasos, más el botón (oculto) para salir a las ciudades al ganar.
	 */
	private JPanel construirPanelNavegacion() {
		JPanel panel = new JPanel(new BorderLayout(3, 3));
		panel.setBackground(new Color(20, 20, 20));

		sliderPasos = new JSlider(0, 0, 0);
		sliderPasos.setBackground(new Color(20, 20, 20));
		sliderPasos.setEnabled(false);
		sliderPasos.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				pasoActual = sliderPasos.getValue();
				mostrarPaso(pasoActual);
			}
		});

		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 4));
		panelBotones.setBackground(new Color(20, 20, 20));

		btnAnterior = new JButton("< Anterior");
		btnAnterior.setEnabled(false);
		btnAnterior.addActionListener(e -> irAPaso(pasoActual - 1));

		btnSiguiente = new JButton("Siguiente >");
		btnSiguiente.setEnabled(false);
		btnSiguiente.addActionListener(e -> irAPaso(pasoActual + 1));

		btnSalirCiudades = new JButton("Salir a las ciudades");
		btnSalirCiudades.setVisible(false);
		btnSalirCiudades.addActionListener(e -> salirACiudades());

		panelBotones.add(btnAnterior);
		panelBotones.add(btnSiguiente);
		panelBotones.add(btnSalirCiudades);

		panel.add(sliderPasos, BorderLayout.CENTER);
		panel.add(panelBotones, BorderLayout.SOUTH);
		return panel;
	}

	/**
	 * pre: -
	 * post: ajusta el máximo de los spinners de fila y columna a la cantidad de
	 *       reinas elegida, corrigiendo el valor actual si quedó fuera de rango.
	 */
	private void actualizarRangoPosicionInicial() {
		int n = (int) spinnerReinas.getValue();
		SpinnerNumberModel modeloFila = (SpinnerNumberModel) spinnerFila.getModel();
		SpinnerNumberModel modeloColumna = (SpinnerNumberModel) spinnerColumna.getModel();

		modeloFila.setMaximum(n);
		modeloColumna.setMaximum(n);

		if ((int) spinnerFila.getValue() > n) {
			spinnerFila.setValue(n);
		}
		if ((int) spinnerColumna.getValue() > n) {
			spinnerColumna.setValue(n);
		}
	}

	/**
	 * pre: -
	 * post: lee los parámetros, ejecuta el backtracking, genera las imágenes BMP de
	 *       cada paso, habilita la navegación, muestra el resultado y verifica si se
	 *       ganó la ciudad.
	 */
	private void lanzarResolucion() {
		int cantidadReinas = (int) spinnerReinas.getValue();
		int filaInicial = (int) spinnerFila.getValue() - 1;
		int columnaInicial = (int) spinnerColumna.getValue() - 1;

		TableroReinas tablero = new TableroReinas(cantidadReinas);
		tablero.colocarReina(filaInicial, columnaInicial);

		BacktrackingReinas solver = new BacktrackingReinas(tablero);
		boolean encontro = solver.resolver(filaInicial);
		List<int[]> pasos = solver.getPasos();

		Interfaz renderizador = new Interfaz(CARPETA_PASOS);
		renderizador.generarPasos(pasos, cantidadReinas, TAM_TABLERO);

		totalPasos = pasos.size();
		sliderPasos.setMinimum(0);
		sliderPasos.setMaximum(totalPasos - 1);
		sliderPasos.setValue(0);
		sliderPasos.setEnabled(true);
		btnAnterior.setEnabled(true);
		btnSiguiente.setEnabled(true);

		pasoActual = 0;
		mostrarPaso(0);

		if (encontro) {
			labelInfo.setForeground(new Color(100, 220, 100));
			labelInfo.setText("Solucion encontrada en " + totalPasos + " pasos");
		} else {
			labelInfo.setForeground(new Color(220, 80, 80));
			labelInfo.setText("No existe solucion para " + cantidadReinas + " reinas con esa posicion inicial");
		}

		verificarVictoria(encontro);
	}

	/**
	 * pre: -
	 * post: si se encontró solución en menos de MAX_MOVIMIENTOS pasos y la ciudad no
	 *       estaba ganada, la marca como ganada, avisa al jugador y habilita el botón
	 *       para salir a las ciudades.
	 */
	private void verificarVictoria(boolean encontro) {
		if (!ganada && encontro && totalPasos < MAX_MOVIMIENTOS) {

			ganada = true;
			btnSalirCiudades.setVisible(true);

			
			if (progreso != null) {
				progreso.desbloquear(3);
				progreso.guardar();
			}

			revalidate();
			repaint();

			JOptionPane.showMessageDialog(this,
					"¡Ciudad 2 superada! Resolviste el tablero en " + totalPasos
							+ " pasos (menos de " + MAX_MOVIMIENTOS + ").\n"
							+ "Ciudad 3 desbloqueada.",
					"Victoria",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * pre: -
	 * post: define la acción que se ejecuta al salir a las ciudades (por ejemplo,
	 *       volver al mapa del juego). Si no se define, el botón no hace nada.
	 */
	public void setAccionSalir(Runnable accion) {
		this.accionSalir = accion;
	}

	/**
	 * pre: -
	 * post: ejecuta la acción de salir a las ciudades, si fue definida.
	 */
	private void salirACiudades() {
		if (accionSalir != null) {
			accionSalir.run();
		}
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
	 * post: navega al paso indicado si está dentro del rango válido, actualizando el
	 *       slider y la imagen mostrada.
	 */
	private void irAPaso(int numeroPaso) {
		if (numeroPaso < 0 || numeroPaso >= totalPasos) {
			return;
		}
		pasoActual = numeroPaso;
		sliderPasos.setValue(pasoActual);
		mostrarPaso(pasoActual);
	}


}