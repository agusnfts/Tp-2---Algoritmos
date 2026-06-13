package principal;
import java.awt.GridLayout;
import javax.swing.*;

import utiles.ValidacionesUtiles;
public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {

        setTitle("Menu Principal");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        JButton iniciar = new JButton("Iniciar nueva partida");
        JButton cargar = new JButton("Cargar partida");
        JButton borrar = new JButton("Borrar partida guardada");

        add(iniciar);
        add(cargar);
        add(borrar);

        // =========================
        // NUEVA PARTIDA
        // =========================
        iniciar.addActionListener(e -> {

            String nombre =
                    JOptionPane.showInputDialog(
                            this,
                            "Nombre del jugador:"
                    );

            if (nombre == null) {
                return;
            }

            nombre = nombre.trim();

            ValidacionesUtiles.validarLongitudDeTexto(
                    nombre,
                    1,
                    null,
                    "nombre del jugador"
            );

            ProgresoJuego progreso =
                    new ProgresoJuego();

            progreso.setNombreJugador(
                    nombre
            );

            progreso.guardar();

            abrirMapa(progreso);
        });

        // =========================
        // CARGAR PARTIDA
        // =========================
        cargar.addActionListener(e -> {

            String[] partidas =
                    ProgresoJuego.listarPartidas();

            if (partidas.length == 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "No hay partidas guardadas"
                );

                return;
            }

            String seleccion =
                    (String) JOptionPane.showInputDialog(
                            this,
                            "Elegí una partida:",
                            "Cargar partida",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            partidas,
                            partidas[0]
                    );

            if (seleccion == null) {
                return;
            }

            String nombre =
                    seleccion.replace(
                            ".dat",
                            ""
                    );

            ProgresoJuego progreso =
                    ProgresoJuego.cargar(
                            nombre
                    );

            ValidacionesUtiles.esDistintoDeNull(
                    progreso,
                    "progreso"
            );

            abrirMapa(progreso);
        });

        // =========================
        // BORRAR PARTIDA
        // =========================
        borrar.addActionListener(e -> {

            String[] partidas =
                    ProgresoJuego.listarPartidas();

            if (partidas.length == 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "No hay partidas para borrar"
                );

                return;
            }

            String seleccion =
                    (String) JOptionPane.showInputDialog(
                            this,
                            "Elegí partida a borrar:",
                            "Borrar partida",
                            JOptionPane.WARNING_MESSAGE,
                            null,
                            partidas,
                            partidas[0]
                    );

            if (seleccion == null) {
                return;
            }

            ProgresoJuego.borrar(
                    seleccion.replace(
                            ".dat",
                            ""
                    )
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Partida borrada"
            );
        });
    }

    private void abrirMapa(
            ProgresoJuego progreso
    ) {

        ValidacionesUtiles.esDistintoDeNull(
                progreso,
                "progreso"
        );

        JFrame frame =
                new JFrame("Mapa");

        frame.setSize(1000, 700);

        frame.setLocationRelativeTo(null);

        frame.setDefaultCloseOperation(
                EXIT_ON_CLOSE
        );

        frame.setContentPane(
                new PanelMapa(progreso)
        );

        frame.setVisible(true);

        this.dispose();
    }
}
