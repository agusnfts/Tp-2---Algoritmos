package Ciudad1.ui;

import javax.swing.JFrame;
import Ciudad1.partida.Partida;
import principal.ProgresoJuego;

//Ventana principal de la Ciudad 1.
public class VentanaCiudad1 extends JFrame {

	private ProgresoJuego progreso;
	
	public VentanaCiudad1(ProgresoJuego progreso) {
	    this.progreso = progreso;
	    Partida partida = new Partida(progreso);
	    partida.setAccionVictoria(() -> dispose());
        PanelCiudad1 panel = new PanelCiudad1(partida);

        add(panel);
        
        setTitle("Ciudad 1 - Recolección en Matriz");
        setSize(850, 650);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        panel.requestFocus();
    }
}