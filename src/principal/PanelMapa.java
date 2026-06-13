package principal;

import Ciudad1.ui.VentanaCiudad1;
import Ciudad10.ui.VentanaCiudad10;
import Ciudad2.PanelCiudad2;
import Ciudad3.PanelCiudad3;
import Ciudad4.vista.VentanaOrdenamientos;
import Ciudad5.vista.VentanaBusquedas;
import Ciudad6.PanelCiudad6;
import Ciudad7.PanelCiudad7;
import Ciudad8.vista.PanelCiudad8;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PanelMapa extends JPanel {

    private Image mapa;
    private JFrame frame;
    private ProgresoJuego progreso;

    public PanelMapa(ProgresoJuego progreso) {

        this.progreso = progreso;

        mapa = new ImageIcon("imagenes/mapa.png").getImage();

        eventos();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(mapa, 0, 0, getWidth(), getHeight(), this);
    }

    private void eventos() {

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                int x = e.getX();
                int y = e.getY();

                System.out.println("X: " + x + " Y: " + y);

                verificarCiudad(x, y);
            }
        });
    }

    public void cambiarPanel(JPanel panel) {
        if (frame == null) {
            System.out.println("ERROR: frame no inicializado en PanelMapa");
            return;
        }

        frame.setContentPane(panel);
        frame.revalidate();
        frame.repaint();
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
 
    
    private void verificarCiudad(int x, int y) {

        // CIUDAD 1
    	if (x >= 54 && x <= 252 && y >= 393 && y <= 597) {

    	    System.out.println("[MAPA] Ciudad 1");

    	    VentanaCiudad1 ventana =
    	            new VentanaCiudad1(progreso);

    	    ventana.setVisible(true);

    	    return;
    	}
        // CIUDAD 2
        if (x >= 270 && x <= 455 && y >= 415 && y <= 550) {

            System.out.println("[MAPA] Ciudad 2");

            if (!progreso.estaDesbloqueada(2)) return;

            PanelCiudad2 ciudad2 = new PanelCiudad2(progreso);

            ciudad2.setAccionSalir(() -> cambiarPanel(PanelMapa.this));
            // o cambiarPanel(panelMapa) si tenés referencia

            cambiarPanel(ciudad2);
        }

        // CIUDAD 3
        if (x >= 160 && x <= 329
                && y >= 255 && y <= 400) {

            System.out.println("[MAPA] Ciudad 3");
            System.out.println("[MAPA] Desbloqueada: "
                    + progreso.estaDesbloqueada(3));

            if (!progreso.estaDesbloqueada(3)) {
                return;
            }

            cambiarPanel(new PanelCiudad3(progreso));
        }
        
        // CIUDAD 4
        if(x >= 461 && x <= 645
                && y >= 445 && y <= 580) {

            System.out.println("[MAPA] Ciudad 4");

            if (!progreso.estaDesbloqueada(4)) {
                return;
            }

            VentanaOrdenamientos v =
            		new VentanaOrdenamientos(progreso);

            v.setVisible(true);
        }
        
        // CIUDAD 5
  
        if(x >= 375 && x <=621
                && y >= 123 && y <= 301) {

        	System.out.println("[MAPA] Ciudad 5");

            if (!progreso.estaDesbloqueada(5)) {
                return;
            }
        	
            VentanaBusquedas v =
                    new VentanaBusquedas(progreso);

            v.setVisible(true);
        }
        
        // CIUDAD 6
        if(x>= 523 && x<=700 && y>=298 && y<=415) {
            
            System.out.println("[MAPA] Ciudad 6");
            System.out.println("[MAPA] Desbloqueada: "
                    + progreso.estaDesbloqueada(6));

            if (!progreso.estaDesbloqueada(6)) {
                return;
            }

            PanelCiudad6 ciudad6 = new PanelCiudad6(progreso);
            
            ciudad6.setAccionSiguiente(() -> cambiarPanel(PanelMapa.this)); 
            
            cambiarPanel(ciudad6);
        }
                
        // CIUDAD 7
        
        if(x>= 719 && x<= 872 && y>=262 && y<= 408) {
        	
        	System.out.println("[MAPA] Ciudad 7");
            System.out.println("[MAPA] Desbloqueada: "
                    + progreso.estaDesbloqueada(7));

            if (!progreso.estaDesbloqueada(7)) {
                return;
            }

            cambiarPanel(new PanelCiudad7(progreso));
        	
        	
        }
        
        // CIUDAD 8
        
        if(x>= 698 && x<=911 && y>= 109 && y<= 244) {
        	
        	System.out.println("[MAPA] Ciudad 8");
            System.out.println("[MAPA] Desbloqueada: "
                    + progreso.estaDesbloqueada(8));

            if (!progreso.estaDesbloqueada(8)) {
                return;
            }

            cambiarPanel(new PanelCiudad8(progreso));
        	
        	
        }
        
        // CIUDAD 10
        
        if(x>= 849 && x<= 966 && y<=94 && y>=16) {
     	   
        	System.out.println("[MAPA] Ciudad 10");
        	System.out.println("[MAPA] Desbloqueada: "
        	            + progreso.estaDesbloqueada(10));

        	if (!progreso.estaDesbloqueada(10)) {
        	     return;
        	}

        	VentanaCiudad10 ventana = new VentanaCiudad10(progreso);

        	ventana.setVisible(true);
     	   
        }
        
    }
}