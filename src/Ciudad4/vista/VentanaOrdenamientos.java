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

public class VentanaOrdenamientos extends JFrame {

    private JTextField txtNumeros;

    private JComboBox<String> combo;

    private JButton btnOrdenar;
    private JButton btnSalir;

    private PanelCiudad4 panel;

    private ProgresoJuego progreso;

    private boolean usoBubbleSort = false;
    private boolean usoQuickSort = false;

    public VentanaOrdenamientos(ProgresoJuego progreso) {

        this.progreso = progreso;

        setTitle("Ciudad 4 - Ordenamientos");

        setSize(900, 700);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        inicializar();
    }



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

        btnOrdenar.addActionListener(
                e -> ordenar()
        );

        btnSalir.addActionListener(
                e -> dispose()
        );
    }

    private void ordenar() {

        try {

            String[] partes =
                    txtNumeros
                            .getText()
                            .split(",");

            int[] datos =
                    new int[partes.length];

            for(int i = 0;
                i < partes.length;
                i++) {

                datos[i] =
                        Integer.parseInt(
                                partes[i]
                                        .trim()
                        );
            }

            Queue<int[]> pasos;

            String algoritmo =
                    combo
                            .getSelectedItem()
                            .toString();

            if(algoritmo.equals(
                    "BubbleSort")) {

                usoBubbleSort = true;

                pasos =
                        BubbleSort.ordenar(
                                datos
                        );

            } else {

                usoQuickSort = true;

                pasos =
                        QuickSort.ordenar(
                                datos
                        );
            }

            verificarVictoria();

            animar(pasos);

        } catch(Exception ex) {

            ex.printStackTrace();
        }
    }

    /**
     * Verifica si el jugador ya utilizó ambos algoritmos.
     * Si lo hizo, se desbloquea la Ciudad 5.
     */
    private void verificarVictoria() {

        System.out.println("Bubble: " + usoBubbleSort);
        System.out.println("Quick: " + usoQuickSort);

        if(progreso != null) {
            System.out.println("Ciudad 5 desbloqueada: "
                    + progreso.estaDesbloqueada(5));
        }

        if(usoBubbleSort && usoQuickSort) {

            if(progreso != null
                    && !progreso.estaDesbloqueada(5)) {

                System.out.println("[CIUDAD 4] COMPLETADA");

                progreso.desbloquear(5);

                progreso.guardar();

                JOptionPane.showMessageDialog(
                        this,
                        "¡Felicitaciones!\n"
                        + "Utilizaste BubbleSort y QuickSort.\n"
                        + "La Ciudad 5 ha sido desbloqueada."
                );
            }
        }
    }

    private void animar(
            Queue<int[]> pasos
    ) {

        Timer timer =
                new Timer(
                        700,
                        null
                );

        timer.addActionListener(e -> {

            if(!pasos.isEmpty()) {

                int[] estado =
                        pasos.poll();

                panel.mostrarDatos(
                        estado
                );

            } else {

                timer.stop();
            }
        });

        timer.start();
    }
}
