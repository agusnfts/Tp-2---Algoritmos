package principal;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Ciudad2.PanelCiudad2;
import Ciudad3.PanelCiudad3;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import java.awt.Graphics;
import java.awt.Image;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ciudadordenamientos.vista
        .VentanaOrdenamientos;

import ciudadbusquedas.vista
        .VentanaBusquedas;

/**
 * Panel que muestra el mapa principal del juego.
 * Permite acceder a las distintas ciudades mediante clics sobre regiones del mapa.
 */
public class PanelMapa extends JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * Imagen utilizada como mapa de fondo.
     */
    private Image mapa;

    /**
     * pre: existe el archivo "imagenes/mapa.png".
     * post: se crea el panel y se carga la imagen del mapa.
     *       Se registran los eventos de mouse necesarios para detectar clics.
     */
    public PanelMapa() {

        mapa = new ImageIcon(
                "imagenes/mapa.png"
        ).getImage();

        eventos();
    }

    /**
     * pre: g != null.
     * post: se dibuja el mapa ocupando todo el tamaño actual del panel.
     */
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.drawImage(
                mapa,
                0,
                0,
                getWidth(),
                getHeight(),
                this
        );
    }

    /**
     * pre: ninguna.
     * post: se registra un MouseListener que detecta los clics
     *       realizados sobre el mapa.
     */
    private void eventos() {

        addMouseListener(
            new MouseAdapter() {

                /**
                 * pre: e != null.
                 * post: se obtienen las coordenadas del clic y se verifica
                 *       si corresponden a alguna ciudad del mapa.
                 */
                @Override
                public void mouseClicked(MouseEvent e) {

                    int x = e.getX();
                    int y = e.getY();

                    System.out.println(
                            "X: " + x +
                            " Y: " + y
                    );

                    verificarCiudad(x, y);
                }
            }
        );
    }

    /**
     * pre: x e y representan una posición válida dentro del panel.
     * post:
     * - si las coordenadas pertenecen a la Ciudad 2, se muestra PanelCiudad2.
     * - si las coordenadas pertenecen a la Ciudad 3, se muestra PanelCiudad3.
     * - si las coordenadas pertenecen a la Ciudad 4, se abre la ventana de ordenamientos.
     * - si las coordenadas pertenecen a la Ciudad 5, se abre la ventana de búsquedas.
     * - en cualquier otro caso no se realiza ninguna acción.
     */
    private void verificarCiudad(int x, int y) {

        // CIUDAD 4
        if(x >= 461 && x <= 645
                && y >= 445 && y <= 580) {

            VentanaOrdenamientos v =
                    new VentanaOrdenamientos();

            v.setVisible(true);
        }

        // CIUDAD 5
        if(x >= 375 && x <=621
                && y >= 123 && y <= 301) {

            VentanaBusquedas v =
                    new VentanaBusquedas();

            v.setVisible(true);
        }

        // CIUDAD 2
        if(x >= 270 && x <= 455
                && y >= 415 && y <= 550) {

            JFrame frame =
                (JFrame) SwingUtilities.getWindowAncestor(this);

            frame.setContentPane(new PanelCiudad2());

            frame.revalidate();
            frame.repaint();
        }

        // CIUDAD 3
        if(x >= 160 && x <= 329
                && y >= 255 && y <= 400) {

            JFrame frame =
                (JFrame) SwingUtilities.getWindowAncestor(this);

            frame.setContentPane(
                new PanelCiudad3()
            );

            frame.revalidate();
            frame.repaint();
        }
    }
}