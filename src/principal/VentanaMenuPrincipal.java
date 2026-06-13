package principal;
import javax.swing.*;

import utiles.ValidacionesUtiles;

import java.awt.*;
public class VentanaMenuPrincipal extends JFrame {

    private ProgresoJuego progreso;

    public VentanaMenuPrincipal() {

        setTitle("Al-Quest - Menú");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 10, 10));

        JButton btnIniciar =
                new JButton("Iniciar Partida");

        JButton btnCargar =
                new JButton("Cargar Partida");

        JButton btnBorrar =
                new JButton("Borrar Partida");

        JButton btnSalir =
                new JButton("Salir");

        add(btnIniciar);
        add(btnCargar);
        add(btnBorrar);
        add(btnSalir);

        btnIniciar.addActionListener(
                e -> iniciarNuevaPartida()
        );

        btnCargar.addActionListener(
                e -> cargarPartida()
        );

        btnBorrar.addActionListener(
                e -> borrarPartida()
        );

        btnSalir.addActionListener(
                e -> System.exit(0)
        );
    }

    // =========================
    // NUEVA PARTIDA
    // =========================
    private void iniciarNuevaPartida() {

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

        progreso =
                new ProgresoJuego();

        progreso.setNombreJugador(
                nombre
        );

        progreso.guardar();

        abrirMapa();
    }

    // =========================
    // CARGAR PARTIDA
    // =========================
    private void cargarPartida() {

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

        progreso =
                ProgresoJuego.cargar(
                        nombre
                );

        ValidacionesUtiles.esDistintoDeNull(
                progreso,
                "progreso"
        );

        abrirMapa();
    }

    // =========================
    // BORRAR PARTIDA
    // =========================
    private void borrarPartida() {

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

        String nombre =
                seleccion.replace(
                        ".dat",
                        ""
                );

        ProgresoJuego.borrar(
                nombre
        );

        JOptionPane.showMessageDialog(
                this,
                "Partida borrada correctamente"
        );
    }

    // =========================
    // ABRIR MAPA
    // =========================
    private void abrirMapa() {

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

        PanelMapa mapa =
                new PanelMapa(
                        progreso
                );

        mapa.setFrame(
                frame
        );

        frame.setContentPane(
                mapa
        );

        frame.setVisible(true);

        this.dispose();
    }
}