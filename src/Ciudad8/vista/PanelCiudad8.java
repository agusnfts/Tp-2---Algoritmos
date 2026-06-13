package Ciudad8.vista;

import Ciudad8.bitmap.Bitmap;
import Ciudad8.modelo.Hanoi;
import Ciudad8.modelo.Movimiento;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import principal.PanelMapa;
import principal.ProgresoJuego;

public class PanelCiudad8 extends JPanel {

    private Bitmap bmp;

    private ProgresoJuego progreso;
    
    private JLabel lblImagen;

    public PanelCiudad8(ProgresoJuego progreso) {

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
                e -> volverAlMapa()
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
     * pre: -
     * post: reemplaza el contenido del frame principal por el mapa del juego,
     *       conservando el progreso, en lugar de cerrar el programa.
     */
    private void volverAlMapa() {

        javax.swing.JFrame frame =
                (javax.swing.JFrame)
                javax.swing.SwingUtilities.getWindowAncestor(this);

        if(frame != null) {

            frame.getContentPane().removeAll();

            PanelMapa nuevoMapa = new PanelMapa(progreso);
            nuevoMapa.setFrame(frame);
            frame.setContentPane(nuevoMapa);

            frame.revalidate();
            frame.repaint();
            frame.requestFocusInWindow();
        }
    }

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

    private void iniciar() {

        new Thread(() -> {

            try {

                List<List<Integer>> torres =
                        crearTorres(4);

                dibujarEstado(
                        torres
                );

                Hanoi hanoi =
                        new Hanoi();

                List<Movimiento> movimientos =
                        hanoi.resolver(4);

                Thread.sleep(1000);

                for(Movimiento m : movimientos) {

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

                    Thread.sleep(700);
                }
                SwingUtilities.invokeLater(() -> finalizarCiudad());

            } catch(Exception ex) {

                ex.printStackTrace();
            }

        }).start();
    }

    private List<List<Integer>> crearTorres(
            int discos
    ) {

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

        for(int i = discos;
            i >= 1;
            i--) {

            torres.get(0).add(i);
        }

        return torres;
    }

    /**
     * pre: el algoritmo de Hanoi terminó.
     * post: marca la ciudad como completada, avisa al jugador y desbloquea y
     *       guarda el avance de la Ciudad 9.
     */
    private void finalizarCiudad() {

        JOptionPane.showMessageDialog(
                this,
                "¡Felicitaciones!\n"
                + "Completaste la Ciudad 8.\n"
                + "La Ciudad 9 ha sido desbloqueada."
        );

        if(progreso != null) {

            progreso.desbloquear(9);

            progreso.guardar();
        }
    }
    
    private void dibujarEstado(
            List<List<Integer>> torres
    ) {

        bmp.rellenar(Color.BLACK);

        int[] posiciones = {
                250,
                650,
                1050
        };

        for(int x : posiciones) {

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

        for(int torre = 0;
            torre < 3;
            torre++) {

            List<Integer> discos =
                    torres.get(torre);

            for(int i = 0;
                i < discos.size();
                i++) {

                int valor =
                        discos.get(i);

                int ancho =
                        60 +
                        valor * 40;

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