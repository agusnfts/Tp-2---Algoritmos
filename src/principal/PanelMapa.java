package principal;

import javax.swing.JPanel;

import javax.swing.SwingUtilities;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

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

    public void cambiarPanel(
            JPanel panel
    ) {

        ValidacionesUtiles.esDistintoDeNull(
                panel,
                "panel"
        );

        ValidacionesUtiles.esDistintoDeNull(
                frame,
                "frame"
        );

        frame.setContentPane(
                panel
        );

        frame.revalidate();

        frame.repaint();
    }

    public void setFrame(
            JFrame frame
    ) {

        ValidacionesUtiles.esDistintoDeNull(
                frame,
                "frame"
        );

        this.frame = frame;
    }

    private void verificarCiudad(
            int x,
            int y
    ) {

        // CIUDAD 1
        if (x >= 54 && x <= 252
                && y >= 393 && y <= 597) {

            System.out.println(
                    "[MAPA] Ciudad 1"
            );

            return;
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

            cambiarPanel(
                    new PanelCiudad6(
                            progreso
                    )
            );
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
        if (x >= 698 && x <= 911
                && y >= 109 && y <= 244) {

            System.out.println(
                    "[MAPA] Ciudad 8"
            );

            if (!progreso.estaDesbloqueada(8)) {
                return;
            }

            cambiarPanel(
                    new PanelCiudad8(
                            progreso
                    )
            );
        }

        // CIUDAD 9
        
        if(x>=729 && x<=917 && y>= 406 && y<= 586) {
        	
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
        if (x >= 849 && x <= 966
                && y >= 16 && y <= 94) {

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