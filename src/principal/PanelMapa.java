package principal;

import javax.swing.JPanel;

import javax.swing.SwingUtilities;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import Ciudad1.partida.Partida;
import Ciudad1.ui.PanelCiudad1;
import Ciudad1.ui.VentanaCiudad1;
import Ciudad10.ui.VentanaCiudad10;
import Ciudad2.PanelCiudad2;
import Ciudad3.PanelCiudad3;
import Ciudad8.vista.PanelCiudad8;
import Ciudad9.vista.PanelCiudad9;
import Ciudad9.vista.VentanaCiudad9;
import utiles.ValidacionesUtiles;
import Ciudad4.vista.PanelCiudad4;
import Ciudad4.vista.VentanaOrdenamientos;
import Ciudad5.vista.VentanaBusquedas;
import Ciudad6.PanelCiudad6;
import Ciudad7.PanelCiudad7;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelMapa extends JPanel {

    private Image mapa;
    private JFrame frame;
    private ProgresoJuego progreso;

    /**
     * PRE: progreso != null
     *
     * POST:
     * - Se inicializa el panel del mapa
     * - Se almacena la referencia al progreso del jugador
     * - Se carga la imagen del mapa
     * - Se registran los eventos del mouse
     */
    
    public PanelMapa(ProgresoJuego progreso) {

        ValidacionesUtiles.esDistintoDeNull(
                progreso,
                "progreso"
        );

        this.progreso = progreso;

        mapa = new ImageIcon(
                "imagenes/mapa.png"
        ).getImage();

        ValidacionesUtiles.esDistintoDeNull(
                mapa,
                "mapa"
        );

        eventos();
    }

    /**
     * PRE: g != null
     *
     * POST: Se dibuja la imagen del mapa ocupando todo el panel
     */
    
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        ValidacionesUtiles.esDistintoDeNull(
                g,
                "graphics"
        );

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
     * PRE: Ninguna
     *
     * POST:
     * - Se registra un MouseListener sobre el panel
     * - Al hacer clic se obtienen las coordenadas seleccionadas
     * - Se verifica si el clic corresponde a alguna ciudad
     */
    
    private void eventos() {

        addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            MouseEvent e
                    ) {

                        ValidacionesUtiles.esDistintoDeNull(
                                e,
                                "evento"
                        );

                        int x = e.getX();
                        int y = e.getY();

                        System.out.println(
                                "X: " + x + " Y: " + y
                        );

                        verificarCiudad(
                                x,
                                y
                        );
                    }
                }
        );
    }

    /**
     * PRE:
     * - panel != null
     * - frame != null
     *
     * POST:
     * - El panel recibido pasa a ser el contenido principal de la ventana
     * - La interfaz se actualiza visualmente
     * - El nuevo panel recibe el foco
     */
    
    public void cambiarPanel(JPanel panel) {

        ValidacionesUtiles.esDistintoDeNull(panel, "panel");
        ValidacionesUtiles.esDistintoDeNull(frame, "frame");

        frame.setContentPane(panel);

        frame.revalidate();
        frame.repaint();

        panel.setFocusable(true);
        panel.requestFocusInWindow();
    }

    /**
     * PRE: frame != null
     *
     * POST: Se almacena la referencia a la ventana principal
     */
    
    public void setFrame(
            JFrame frame
    ) {

        ValidacionesUtiles.esDistintoDeNull(
                frame,
                "frame"
        );

        this.frame = frame;
    }

    /**
     * PRE: x e y representan coordenadas válidas del mapa
     *
     * POST:
     * - Se determina si las coordenadas pertenecen a alguna ciudad
     * - Si la ciudad está desbloqueada, se abre la ventana o panel correspondiente
     * - Si la ciudad está bloqueada, no se realiza ninguna acción
     * - Puede producir cambios de panel o apertura de nuevas ventanas
     */

    
    private void verificarCiudad(
            int x,
            int y
    ) {

        // CIUDAD 1
    	if (x >= 54 && x <= 252
    	        && y >= 393 && y <= 597) {

    	    System.out.println("[MAPA] Ciudad 1");

    	    Partida ciudad1 = new Partida(progreso);

    	    PanelCiudad1 panelCiudad1 = new PanelCiudad1(ciudad1);

    	    panelCiudad1.setAccionSalir(
    	            () -> cambiarPanel(PanelMapa.this)
    	    );

    	    cambiarPanel(panelCiudad1);
    	}

        // CIUDAD 2
        if (x >= 270 && x <= 455
                && y >= 415 && y <= 550) {

            System.out.println(
                    "[MAPA] Ciudad 2"
            );

            if (!progreso.estaDesbloqueada(2)) {
                return;
            }

            PanelCiudad2 ciudad2 =
                    new PanelCiudad2(
                            progreso
                    );

            ciudad2.setAccionSalir(
                    () -> cambiarPanel(
                            PanelMapa.this
                    )
            );

            cambiarPanel(
                    ciudad2
            );
        }

        // CIUDAD 3
        if (x >= 160 && x <= 329
                && y >= 255 && y <= 400) {

            System.out.println(
                    "[MAPA] Ciudad 3"
            );

            if (!progreso.estaDesbloqueada(3)) {
                return;
            }

            cambiarPanel(
                    new PanelCiudad3(
                            progreso
                    )
            );
        }

        // CIUDAD 4
        if (x >= 461 && x <= 645
                && y >= 445 && y <= 580) {

            System.out.println(
                    "[MAPA] Ciudad 4"
            );

            if (!progreso.estaDesbloqueada(4)) {
                return;
            }

            VentanaOrdenamientos v =
                    new VentanaOrdenamientos(
                            progreso
                    );

            v.setVisible(true);
        }

        // CIUDAD 5
        if (x >= 375 && x <= 621
                && y >= 123 && y <= 301) {

            System.out.println(
                    "[MAPA] Ciudad 5"
            );

            if (!progreso.estaDesbloqueada(5)) {
                return;
            }

            VentanaBusquedas v =
                    new VentanaBusquedas(
                            progreso
                    );

            v.setVisible(true);
        }

        // CIUDAD 6
        if (x >= 523 && x <= 700
                && y >= 298 && y <= 415) {

            System.out.println(
                    "[MAPA] Ciudad 6"
            );

            if (!progreso.estaDesbloqueada(6)) {
                return;
            }

            PanelCiudad6 ciudad6 = new PanelCiudad6(progreso);

            ciudad6.setAccionSalir(
                    () -> cambiarPanel(PanelMapa.this)
            );

            ciudad6.setAccionSiguiente(
                    () -> cambiarPanel(PanelMapa.this)
            );

            cambiarPanel(ciudad6);
        }

        // CIUDAD 7
        if (x >= 719 && x <= 872
                && y >= 262 && y <= 408) {

            System.out.println(
                    "[MAPA] Ciudad 7"
            );

            if (!progreso.estaDesbloqueada(7)) {
                return;
            }

            cambiarPanel(
                    new PanelCiudad7(
                            progreso
                    )
            );
        }

        // CIUDAD 8
        if (x >= 698 && x <= 870
                && y >= 95 && y <= 240) {

            System.out.println(
                    "[MAPA] Ciudad 8"
            );

            if (!progreso.estaDesbloqueada(8)) {
                return;
            }

            PanelCiudad8 ciudad8 =
                    new PanelCiudad8(
                            progreso
                    );

            ciudad8.setAccionSalir(
                    () -> cambiarPanel(PanelMapa.this)
            );

            cambiarPanel(
                    ciudad8
            );
        }

        // CIUDAD 9
        
        if(x>=746 && x<=939 && y>= 420 && y<= 584) {
        	
        	System.out.println(
                    "[MAPA] Ciudad 9"
            );
        	
        	
        	 if (!progreso.estaDesbloqueada(9)) {
                 return;
             }

        	 VentanaCiudad9 ventana = new VentanaCiudad9(progreso);


        	 ventana.setVisible(true);
        	
        }
        
        
        // CIUDAD 10
        if (x >= 873 && x <= 935
                && y >= 77 && y <= 290) {

            System.out.println(
                    "[MAPA] Ciudad 10"
            );

            if (!progreso.estaDesbloqueada(10)) {
                return;
            }

            VentanaCiudad10 ventana =
                    new VentanaCiudad10(
                            progreso
                    );

            ventana.setVisible(true);
        }
    }
}