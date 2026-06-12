package Ciudad8.vista;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import Ciudad8.bitmap.Bitmap;
import Ciudad8.modelo.Hanoi;
import Ciudad8.modelo.Movimiento;
import principal.ProgresoJuego;
import utiles.SistemaUtiles;
import utiles.ValidacionesUtiles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PanelCiudad8 extends JPanel {

    private Bitmap bmp;

    private Runnable accionSalir;
    
    private ProgresoJuego progreso;

    private JLabel lblImagen;

    /**
     * PRE:
     * - progreso != null
     *
     * POST:
     * - Se inicializa el panel de la Ciudad 8
     * - Se crea el Bitmap utilizado para la representación gráfica
     * - Se dibuja la pantalla inicial del juego
     * - Se crean los botones de inicio y salida
     * - Se configura la actualización periódica de la imagen mostrada
     */
    
    public PanelCiudad8(ProgresoJuego progreso) {

        ValidacionesUtiles.esDistintoDeNull(
                progreso,
                "progreso"
        );

        this.progreso = progreso;

        setLayout(new BorderLayout());

        bmp = new Bitmap(
                1300,
                900
        );

        dibujarMenu();

        lblImagen =
                new JLabel(
                        new ImageIcon(
                                bmp.getImage()
                        )
                );

        add(
                lblImagen,
                BorderLayout.CENTER
        );

        JPanel menuPanel =
                new JPanel();

        JButton btnIniciar =
                new JButton(
                        "Iniciar Hanoi"
                );

        btnIniciar.addActionListener(
                e -> iniciar()
        );

        JButton btnSalir =
                new JButton(
                        "Salir"
                );

        btnSalir.addActionListener(
                e -> salirAlMapa()
        );

        menuPanel.add(btnIniciar);
        menuPanel.add(btnSalir);

        add(
                menuPanel,
                BorderLayout.SOUTH
        );

        Timer timer =
                new Timer(
                        100,
                        e -> lblImagen.setIcon(
                                new ImageIcon(
                                        bmp.getImage()
                                )
                        )
                );

        timer.start();
    }

    /**
     * PRE:
     * - El Bitmap debe estar inicializado
     *
     * POST:
     * - Se limpia la pantalla
     * - Se muestra el título de la ciudad
     * - Se muestra el nombre del desafío "Torres de Hanoi"
     */
    
    private void dibujarMenu() {

        bmp.rellenar(Color.BLACK);

        bmp.drawText(
                "CIUDAD N°8",
                500,
                150,
                new Font(
                        "Courier New",
                        Font.BOLD,
                        40
                ),
                Color.CYAN,
                Color.BLACK
        );

        bmp.drawText(
                "TORRES DE HANOI",
                400,
                250,
                new Font(
                        "Courier New",
                        Font.BOLD,
                        60
                ),
                Color.GREEN,
                Color.BLACK
        );
    }
    
    /**
     * PRE: accion != null
     *
     * POST: Se establece la acción a ejecutar al salir al mapa
     */
    public void setAccionSalir(Runnable accion) {
        this.accionSalir = accion;
    }

    /**
     * POST: ejecuta la acción de salida si fue definida.
     */
    private void salirAlMapa() {

        if (accionSalir != null) {
            accionSalir.run();
        }
    }

    /**
     * PRE: El panel debe estar correctamente inicializado
     *
     * POST:
     * - Se crea una nueva ejecución del algoritmo de Hanoi
     * - Se generan los movimientos necesarios para resolver el problema
     * - Se actualiza la representación gráfica después de cada movimiento
     * - Al finalizar se invoca el método finalizarCiudad()
     */
    
    private void iniciar() {

        new Thread(() -> {

            List<List<Integer>> torres =
                    crearTorres(4);

            dibujarEstado(
                    torres
            );

            Hanoi hanoi =
                    new Hanoi();

            List<Movimiento> movimientos =
                    hanoi.resolver(4);

            SistemaUtiles.esperar(1000);

            for (Movimiento m : movimientos) {

                int disco =
                        torres.get(
                                m.getOrigen()
                        ).remove(
                                torres.get(
                                        m.getOrigen()
                                ).size() - 1
                        );

                torres.get(
                        m.getDestino()
                ).add(disco);

                dibujarEstado(
                        torres
                );

                SistemaUtiles.esperar(700);
            }

            SwingUtilities.invokeLater(
                    () -> finalizarCiudad()
            );

        }).start();
    }

    /**
     * PRE: discos > 0
     *
     * POST:
     * - Se crean tres torres vacías
     * - Todos los discos se ubican inicialmente en la primera torre
     * - Se devuelve la estructura que representa el estado inicial del juego
     */
    
    private List<List<Integer>> crearTorres(
            int discos
    ) {

        ValidacionesUtiles.validarMayorACero(
                discos,
                "discos"
        );

        List<List<Integer>> torres =
                new ArrayList<>();

        torres.add(
                new ArrayList<>()
        );

        torres.add(
                new ArrayList<>()
        );

        torres.add(
                new ArrayList<>()
        );

        for (int i = discos;
             i >= 1;
             i--) {

            torres.get(0).add(i);
        }

        return torres;
    }

    /**
     * PRE: El algoritmo de Hanoi ha finalizado correctamente
     *
     * POST:
     * - Se informa al usuario que completó la ciudad
     * - Se desbloquea la Ciudad 9
     * - Se guarda el progreso actualizado
     */
    private void finalizarCiudad() {

        JOptionPane.showMessageDialog(
                this,
                "¡Felicitaciones!\n"
                        + "Completaste la Ciudad 8.\n"
                        + "La Ciudad 9 ha sido desbloqueada."
        );

        if (progreso != null) {

            progreso.desbloquear(9);

            progreso.guardar();
        }
    }

    /**
     * PRE:
     * - torres != null
     * - La lista debe contener las tres torres del juego
     *
     * POST:
     * - Se redibuja completamente el estado actual de las torres
     * - Se muestran los postes de Hanoi
     * - Se representan gráficamente todos los discos en sus posiciones actuales
     */
    
    private void dibujarEstado(
            List<List<Integer>> torres
    ) {

        ValidacionesUtiles.esDistintoDeNull(
                torres,
                "torres"
        );

        bmp.rellenar(Color.BLACK);

        int[] posiciones = {
                250,
                650,
                1050
        };

        for (int x : posiciones) {

            bmp.drawLine(
                    x,
                    250,
                    x,
                    700,
                    Color.WHITE
            );
        }

        bmp.drawLine(
                150,
                700,
                1150,
                700,
                Color.WHITE
        );

        for (int torre = 0;
             torre < 3;
             torre++) {

            List<Integer> discos =
                    torres.get(torre);

            ValidacionesUtiles.esDistintoDeNull(
                    discos,
                    "discos"
            );

            for (int i = 0;
                 i < discos.size();
                 i++) {

                int valor =
                        discos.get(i);

                int ancho =
                        60 + valor * 40;

                int x =
                        posiciones[torre]
                                - ancho / 2;

                int y =
                        650
                                - i * 40;

                bmp.fillRectangle(
                        x,
                        y,
                        ancho,
                        30,
                        Color.GREEN
                );
            }
        }
    }
}
