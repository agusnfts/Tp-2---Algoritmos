package principal;
import javax.swing.*;

import utiles.ValidacionesUtiles;

import java.awt.*;

public class VentanaMenuPrincipal extends JFrame {

    private ProgresoJuego progreso;

    /**
     * PRE: Ninguna
     *
     * POST:
     * - Se crea la ventana principal del juego
     * - Se configuran sus propiedades visuales
     * - Se crean los botones para iniciar, cargar, borrar partidas y salir
     * - Se asocian los eventos correspondientes a cada botón
     */
    
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

    /**
     * PRE: El usuario debe ingresar un nombre válido
     *
     * POST:
     * - Se crea un nuevo objeto ProgresoJuego
     * - Se asigna el nombre del jugador
     * - Se guarda la nueva partida
     * - Se abre el mapa del juego
     * - Si el usuario cancela la operación no se realizan cambios
     */
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

    /**
     * PRE: Debe existir al menos una partida guardada
     *
     * POST:
     * - Se muestra la lista de partidas disponibles
     * - Se carga la partida seleccionada
     * - Se abre el mapa asociado al progreso cargado
     * - Si el usuario cancela la operación no se realizan cambios
     */
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

    /**
     * PRE: Debe existir al menos una partida guardada
     *
     * POST:
     * - Se muestra la lista de partidas disponibles
     * - Se elimina la partida seleccionada
     * - Se informa al usuario el resultado de la operación
     * - Si el usuario cancela la operación no se realizan cambios
     */
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

    /**
     * PRE:  progreso != null
     *
     * POST:
     * - Se crea una nueva ventana para mostrar el mapa
     * - Se inicializa un PanelMapa con el progreso actual
     * - El panel queda asociado a la ventana creada
     * - Se muestra la ventana del mapa
     * - La ventana del menú principal se cierra
     */
    private void abrirMapa() {

        ValidacionesUtiles.esDistintoDeNull(
                progreso,
                "progreso"
        );

        new VentanaPrincipal(progreso);

        this.dispose();
    }
}