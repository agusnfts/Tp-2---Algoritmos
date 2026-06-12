package Ciudad3;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import principal.ProgresoJuego;

public class PanelCiudad3 extends JPanel {

    private List<Bitmap> bitmaps;
    private JPanel menuPanel;
    private JLabel lblImagen;
    
    private ProgresoJuego progreso;

    public PanelCiudad3(ProgresoJuego progreso) {

        setLayout(new BorderLayout());

        Bitmap bmp = new Bitmap(1300, 900);

        SalidaLaberinto.dibujarMenuPrincipal(bmp);

        bitmaps = new ArrayList<>();
        bitmaps.add(bmp);

        JPanel panelImagenes =
                new JPanel(new GridBagLayout());

        lblImagen =new JLabel(new ImageIcon(bmp.getImage()));

        panelImagenes.add(lblImagen);

        JScrollPane scroll = new JScrollPane(panelImagenes);

        add(scroll, BorderLayout.CENTER);

        menuPanel = new JPanel(new FlowLayout());

        JButton btnHackeo = new JButton("Empezar Hackeo");

        btnHackeo.addActionListener(e -> {

            new Thread(() -> {

                try {

                    int[][] laberinto = {
                        {1, 0, 0, 0, 1, 0},
                        {1, 1, 0, 1, 1, 1},
                        {1, 0, 1, 1, 0, 1},
                        {1, 1, 1, 0, 0, 1}
                    };

                    SalidaLaberinto.FILAS = laberinto.length;

                    SalidaLaberinto.COLUMNAS = laberinto[0].length;

                    int[][] solucion =new int[SalidaLaberinto.FILAS][SalidaLaberinto.COLUMNAS];

                    System.out.println("Preparando tablero...");

                    SalidaLaberinto.prepararTablero( bmp,laberinto);

                    System.out.println("Resolviendo...");

                    boolean exito =SalidaLaberinto.resolverLaberinto(laberinto, 0, 0,0,0,solucion,bmp);

                    SalidaLaberinto.dibujarCartelFinal( bmp,exito);

                    System.out.println("Finalizado");

                } catch (Exception ex) {

                    ex.printStackTrace();

                }

            }).start();

        });

        JButton btnSalir =
                new JButton("Salir");

        btnSalir.addActionListener(e -> System.exit(0));

        menuPanel.add(btnHackeo);
        menuPanel.add(btnSalir);

        add(menuPanel, BorderLayout.SOUTH);

        Timer timer = new Timer( 100,  e -> lblImagen.setIcon(new ImageIcon( bmp.getImage()) ) );

        timer.start();
    }
}
