package ciudadordenamientos.vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import ciudadordenamientos.modelo.BubbleSort;
import ciudadordenamientos.modelo.EstadoOrdenamiento;
import ciudadordenamientos.modelo.QuickSort;
import ciudadordenamientos.persistencia.Persistencia;
import ciudadordenamientos.util.GeneradorBMP;
import principal.Jugador;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.util.Queue;

public class VentanaOrdenamientos extends JFrame {
	

	private JTextField txtNumeros;

    private JComboBox<String> combo;

    private JButton btnOrdenar;
    private JButton btnGuardar;
    private JButton btnCargar;

    private JLabel lblPuntos;

    private PanelGrafico panelGrafico;

    // DATOS

    private Queue<int[]> pasos;

    private Jugador jugador;

    private int pivoteCorrecto;
	
    public VentanaOrdenamientos() {
    	
    	jugador = new Jugador("Jugador");

        setTitle("Ciudad Ordenamientos");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializar();
    }

    private void inicializar() {

        JPanel superior = new JPanel();

        superior.add(new JLabel("Numeros:"));

        txtNumeros = new JTextField(30);
        superior.add(txtNumeros);

        combo = new JComboBox<>();
        combo.addItem("BubbleSort");
        combo.addItem("QuickSort");

        superior.add(combo);

        btnOrdenar = new JButton("Ordenar");
        superior.add(btnOrdenar);

        btnGuardar = new JButton("Guardar");
        superior.add(btnGuardar);

        btnCargar = new JButton("Cargar");
        superior.add(btnCargar);
        
        lblPuntos = new JLabel("Puntos: 0");

        superior.add(lblPuntos);

        add(superior, BorderLayout.NORTH);

        panelGrafico = new PanelGrafico();

        add(panelGrafico, BorderLayout.CENTER);

        eventos();
    }

    private void eventos() {

        btnOrdenar.addActionListener(e -> ordenar());

        btnGuardar.addActionListener(e -> guardar());

        btnCargar.addActionListener(e -> cargar());
    }
    private int[] obtenerVector() {

        try {

            String[] partes = txtNumeros.getText().split(",");

            int[] vector = new int[partes.length];

            for(int i = 0; i < partes.length; i++) {

                vector[i] = Integer.parseInt(partes[i].trim());
            }

            return vector;

        } catch(Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Datos invalidos"
            );
        }

        return null;
    }

    private void ordenar() {

        int[] vector = obtenerVector();

        if(vector == null) {
            return;
        }

        String algoritmo = combo.getSelectedItem().toString();
        
        pivoteCorrecto = vector[vector.length - 1];

        if(algoritmo.equals("BubbleSort")) {

            pasos = BubbleSort.ordenar(vector);

        } else {

            pasos = QuickSort.ordenar(vector);
        }

        animar();
    }

    private void animar() {

        Timer timer = new Timer(500, null);

        timer.addActionListener(e -> {

            if(!pasos.isEmpty()) {

                int[] estado = pasos.poll();

                panelGrafico.setDatos(estado);

                GeneradorBMP.generarBMP(
                        estado,
                        "paso_" + System.nanoTime() + ".bmp"
                );

            } else {

                timer.stop();
                hacerPregunta();
            }
        });

        timer.start();
    }
    
    private void hacerPregunta() {

        String algoritmo =
                combo.getSelectedItem().toString();

        if(algoritmo.equals("QuickSort")) {

            String respuesta =
                    JOptionPane.showInputDialog(
                            this,
                            "¿Cuál fue el pivote?"
                    );

            verificarPivote(respuesta);

        } else {

            String respuesta =
                    JOptionPane.showInputDialog(
                            this,
                            "¿Qué algoritmo es más eficiente para muchos datos?\n"
                                    + "1) BubbleSort\n"
                                    + "2) QuickSort"
                    );

            verificarRespuestaBubble(respuesta);
        }
    }

    private void verificarPivote(String respuesta) {

        try {

            int pivoteUsuario =
                    Integer.parseInt(respuesta);

            if(pivoteUsuario == pivoteCorrecto) {

                jugador.sumarPuntos(20);

                JOptionPane.showMessageDialog(
                        this,
                        "Correcto! +20 puntos"
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Incorrecto"
                );
            }

            actualizarPuntos();

        } catch(Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Respuesta invalida"
            );
        }
    }

    private void verificarRespuestaBubble(String respuesta) {

        if(respuesta.equals("2")) {

            jugador.sumarPuntos(10);

            JOptionPane.showMessageDialog(
                    this,
                    "Correcto! +10 puntos"
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Incorrecto"
            );
        }

        actualizarPuntos();
    }

    private void actualizarPuntos() {

        lblPuntos.setText(
                "Puntos: "
                        + jugador.getPuntos()
        );
    }
    
    private void guardar() {

        int[] vector = obtenerVector();

        if(vector == null) {
            return;
        }

        EstadoOrdenamiento estado =
                new EstadoOrdenamiento(
                        vector,
                        combo.getSelectedItem().toString()
                );

        Persistencia.guardar(estado);

        JOptionPane.showMessageDialog(this, "Guardado");
    }

    private void cargar() {

        EstadoOrdenamiento estado = Persistencia.cargar();

        if(estado == null) {
            return;
        }

        String texto = "";

        int[] datos = estado.getDatos();

        for(int i = 0; i < datos.length; i++) {

            texto += datos[i];

            if(i < datos.length - 1) {
                texto += ",";
            }
        }

        txtNumeros.setText(texto);

        combo.setSelectedItem(estado.getAlgoritmo());

        panelGrafico.setDatos(datos);
    }
}
