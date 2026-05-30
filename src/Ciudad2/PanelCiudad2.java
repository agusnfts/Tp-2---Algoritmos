package Ciudad2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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

    private JLabel labelImagen;
    private JLabel labelInfo;
    private JSlider sliderPasos;
    private JButton btnAnterior;
    private JButton btnSiguiente;
    private JButton btnResolver;
    private JSpinner spinnerReinas;
    private JSpinner spinnerFila;
    private JSpinner spinnerColumna;

    private int pasoActual;
    private int totalPasos;

    // Inicializamos el panel y construimos todos los componentes graficos
    public PanelCiudad2() {
        this.pasoActual = 0;
        this.totalPasos = 0;
        setLayout(new BorderLayout(5, 5));
        setBackground(new Color(30, 30, 30));
        construirPanel();
    }

    // Armamos todos los subpaneles y los agregamos al panel principal
    private void construirPanel() {
        add(construirPanelTitulo(), BorderLayout.NORTH);
        add(construirPanelImagen(), BorderLayout.CENTER);
        add(construirPanelControles(), BorderLayout.SOUTH);
    }

    // Creamos el titulo de la ciudad
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

    // Creamos el panel donde se muestra la imagen BMP con tamaño fijo TAM_TABLERO
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

    // Creamos el panel inferior con los controles
    private JPanel construirPanelControles() {
        JPanel panel = new JPanel(new BorderLayout(3, 3));
        panel.setBackground(new Color(20, 20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 8, 10));
        panel.add(construirPanelEntrada(), BorderLayout.NORTH);
        panel.add(construirPanelNavegacion(), BorderLayout.SOUTH);
        return panel;
    }

    // Creamos los spinners en la primera fila y el boton resolver en la segunda
    private JPanel construirPanelEntrada() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(20, 20, 20));

        // Primera fila: los tres spinners
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

        // Cuando cambia la cantidad de reinas, actualizamos el rango de fila y columna
        spinnerReinas.addChangeListener(e -> actualizarRangoPosicionInicial());

        filaSpinners.add(lblReinas);
        filaSpinners.add(spinnerReinas);
        filaSpinners.add(lblFila);
        filaSpinners.add(spinnerFila);
        filaSpinners.add(lblColumna);
        filaSpinners.add(spinnerColumna);

        // Segunda fila: el boton resolver centrado
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

    // Creamos el slider y botones para navegar entre los pasos del backtracking
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

        panelBotones.add(btnAnterior);
        panelBotones.add(btnSiguiente);

        panel.add(sliderPasos, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        return panel;
    }

    // Actualizamos el rango maximo de fila y columna segun la cantidad de reinas elegida
    private void actualizarRangoPosicionInicial() {
        int n = (int) spinnerReinas.getValue();
        SpinnerNumberModel modeloFila = (SpinnerNumberModel) spinnerFila.getModel();
        SpinnerNumberModel modeloColumna = (SpinnerNumberModel) spinnerColumna.getModel();

        // Ajustamos el maximo al nuevo N
        modeloFila.setMaximum(n);
        modeloColumna.setMaximum(n);

        // Si el valor actual supera el nuevo maximo, lo corregimos
        if ((int) spinnerFila.getValue() > n) {
            spinnerFila.setValue(n);
        }
        if ((int) spinnerColumna.getValue() > n) {
            spinnerColumna.setValue(n);
        }
    }

    // Leemos los parametros, ejecutamos el backtracking y generamos las imagenes BMP
    private void lanzarResolucion() {
        int cantidadReinas = (int) spinnerReinas.getValue();
        // Convertimos de 1-based a 0-based para uso interno
        int filaInicial = (int) spinnerFila.getValue() - 1;
        int columnaInicial = (int) spinnerColumna.getValue() - 1;

        // Creamos el tablero 8x8 y colocamos la posicion inicial
        TableroReinas tablero = new TableroReinas(cantidadReinas);
        tablero.colocarReina(filaInicial, columnaInicial);

        // Ejecutamos el backtracking
        BacktrackingReinas solver = new BacktrackingReinas(tablero);
        boolean encontro = solver.resolver(filaInicial);
        List<int[]> pasos = solver.getPasos();

        // Generamos las imagenes BMP de cada paso con el tablero fijo 8x8
        Interfaz renderizador = new Interfaz(CARPETA_PASOS);
        renderizador.generarPasos(pasos, cantidadReinas, TAM_TABLERO);

        // Actualizamos el slider con la cantidad de pasos generados
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
    }

    // Mostramos la imagen BMP del paso indicado en el label central
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

    // Navegamos al paso indicado, validando que este dentro del rango
    private void irAPaso(int numeroPaso) {
        if (numeroPaso < 0 || numeroPaso >= totalPasos) {
            return;
        }
        pasoActual = numeroPaso;
        sliderPasos.setValue(pasoActual);
        mostrarPaso(pasoActual);
    }

    // Main de prueba para lanzar la ciudad 2 de forma standalone
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Al-Quest - Ciudad 2");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(650, 800);
            frame.setMinimumSize(new Dimension(650, 800));
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.add(new PanelCiudad2());
            frame.setVisible(true);
        });
    }
}