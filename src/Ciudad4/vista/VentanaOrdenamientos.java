package Ciudad4.vista;
import java.awt.BorderLayout;
import java.util.Queue;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import Ciudad4.modelo.BubbleSort;
import Ciudad4.modelo.QuickSort;

public class VentanaOrdenamientos extends JFrame {

    private JTextField txtNumeros;

    private JComboBox<String> combo;

    private JButton btnOrdenar;
    private JButton btnSalir;

    private PanelCiudad4 panel;

    public VentanaOrdenamientos() {

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
                new PanelCiudad4();

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

                pasos =
                        BubbleSort.ordenar(
                                datos
                        );

            } else {

                pasos =
                        QuickSort.ordenar(
                                datos
                        );
            }

            animar(pasos);

        } catch(Exception ex) {

            ex.printStackTrace();
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
