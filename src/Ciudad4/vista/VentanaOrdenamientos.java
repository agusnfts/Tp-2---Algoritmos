package Ciudad4.vista;
import java.awt.BorderLayout;
import java.util.Queue;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import Ciudad4.modelo.BubbleSort;
import Ciudad4.modelo.QuickSort;
import principal.ProgresoJuego;
import utiles.ValidacionesUtiles;

public class VentanaOrdenamientos extends JFrame {

    private JTextField txtNumeros;

    private JComboBox<String> combo;

    private JButton btnOrdenar;
    private JButton btnSalir;

    private PanelCiudad4 panel;

    private ProgresoJuego progreso;

    private boolean usoBubbleSort = false;
    private boolean usoQuickSort = false;

    /**
     * PRE: progreso != null.
     * POST: crea la ventana principal de la Ciudad 4 e inicializa sus componentes.
     */
    
    public VentanaOrdenamientos(ProgresoJuego progreso) {

        ValidacionesUtiles.esDistintoDeNull(
                progreso,
                "progreso"
        );

        this.progreso = progreso;

        setTitle("Ciudad 4 - Ordenamientos");

        setSize(900, 700);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        inicializar();
    }

    /**
     * POST: inicializa y configura todos los componentes gráficos
     *       y eventos de la ventana.
     */
    
    private void inicializar() {

        JPanel superior =
                new JPanel();

        txtNumeros =
                new JTextField(30);

        combo =
                new JComboBox<>();

        combo.addItem("BubbleSort");
        combo.addItem("QuickSort");

        btnOrdenar =
                new JButton("Ordenar");

        btnSalir =
                new JButton("Salir");

        superior.add(txtNumeros);
        superior.add(combo);
        superior.add(btnOrdenar);
        superior.add(btnSalir);

        add(
                superior,
                BorderLayout.NORTH
        );

        panel =
                new PanelCiudad4(progreso);

        add(
                panel,
                BorderLayout.CENTER
        );
        
        panel.mostrarInstrucciones();
        

        btnOrdenar.addActionListener(
                e -> ordenar()
        );

        btnSalir.addActionListener(
                e -> dispose()
        );
    }

    /**
     * PRE: el campo de texto debe contener una lista válida de números
     *      enteros separados por comas.
     * POST: obtiene los datos ingresados, ejecuta el algoritmo seleccionado,
     *       verifica la condición de victoria e inicia la animación del
     *       proceso de ordenamiento.
     */
    
    private void ordenar() {

        try {

            ValidacionesUtiles.validarLongitudDeTexto(
                    txtNumeros.getText(),
                    1,
                    null,
                    "numeros"
            );

            String[] partes =
                    txtNumeros
                            .getText()
                            .split(",");

            ValidacionesUtiles.validarMayorACero(
                    partes.length,
                    "cantidad de numeros"
            );

            int[] datos =
                    new int[partes.length];

            for (int i = 0; i < partes.length; i++) {

                ValidacionesUtiles.validarLongitudDeTexto(
                        partes[i].trim(),
                        1,
                        null,
                        "numero"
                );

                datos[i] =
                        Integer.parseInt(
                                partes[i].trim()
                        );
            }

            Queue<int[]> pasos;

            String algoritmo =
                    (String) combo.getSelectedItem();

            ValidacionesUtiles.esDistintoDeNull(
                    algoritmo,
                    "algoritmo"
            );

            if (algoritmo.equals("BubbleSort")) {

                usoBubbleSort = true;

                pasos =
                        BubbleSort.ordenar(datos);

            } else {

                usoQuickSort = true;

                pasos =
                        QuickSort.ordenar(datos);
            }

            animar(pasos);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debes ingresar únicamente números enteros separados por comas.\n"
                    + "Ejemplo: 1,5,8,7",
                    "Vector inválido",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * PRE: los indicadores de uso de algoritmos fueron actualizados.
     * POST: si el jugador utilizó BubbleSort y QuickSort, desbloquea
     *       la Ciudad 5 y guarda el progreso.
     */
    private void verificarVictoria() {

        System.out.println("Bubble: " + usoBubbleSort);
        System.out.println("Quick: " + usoQuickSort);

        if (progreso != null) {
            System.out.println(
                    "Ciudad 5 desbloqueada: "
                            + progreso.estaDesbloqueada(5)
            );
        }

        if (usoBubbleSort && usoQuickSort
                && !progreso.estaDesbloqueada(5)) {
        	
        	hacerPreguntas();

        }
    }
    
    /**
     * PRE: pasos != null.
     * POST: reproduce gráficamente cada estado almacenado en la cola
     *       hasta finalizar la animación del algoritmo.
     */

    private void animar(
            Queue<int[]> pasos
    ) {

        Timer timer =
                new Timer(
                        700,
                        null
                );

        timer.addActionListener(e -> {

            if (!pasos.isEmpty()) {

                int[] estado =
                        pasos.poll();

                panel.mostrarDatos(
                        estado
                );

            } else {

                timer.stop();

                verificarVictoria();
            }
        });

        timer.start();
    }
    
    /**
     * PRE:
     * - progreso != null.
     * - El usuario utilizó BubbleSort y QuickSort.
     * - La Ciudad 5 aún no se encuentra desbloqueada.
     *
     * POST:
     * - Se muestran dos preguntas de opción múltiple sobre
     *   BubbleSort y QuickSort.
     * - Si ambas respuestas son correctas:
     *      * se desbloquea la Ciudad 5,
     *      * se guarda el progreso,
     *      * se muestra un mensaje de felicitación.
     * - Si alguna respuesta es incorrecta:
     *      * la Ciudad 5 permanece bloqueada,
     *      * se informa al usuario que debe volver a intentarlo.
     */
    
    private void hacerPreguntas() {

        String[] opciones1 = {
                "O(n)",
                "O(n²)",
                "O(log n)"
        };

        int respuesta1 =
                JOptionPane.showOptionDialog(
                        this,
                        "¿Cuál es la complejidad promedio de BubbleSort?",
                        "Pregunta 1",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        opciones1,
                        opciones1[0]
                );

        String[] opciones2 = {
                "Siempre usa el primer elemento",
                "Divide el arreglo usando un pivote",
                "Compara elementos adyacentes"
        };

        int respuesta2 =
                JOptionPane.showOptionDialog(
                        this,
                        "¿Cómo funciona QuickSort?",
                        "Pregunta 2",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        opciones2,
                        opciones2[0]
                );

        boolean correcta1 = (respuesta1 == 1);
        boolean correcta2 = (respuesta2 == 1);

        if (correcta1 && correcta2) {

            progreso.desbloquear(5);
            progreso.guardar();

            JOptionPane.showMessageDialog(
                    this,
                    "¡Felicitaciones!\n"
                    + "Respondiste correctamente.\n"
                    + "La Ciudad 5 ha sido desbloqueada."
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Alguna respuesta fue incorrecta.\n"
                    + "Vuelve a intentarlo.",
                    "Error",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }
    
}

