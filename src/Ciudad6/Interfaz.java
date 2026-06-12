package Ciudad6;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import principal.ProgresoJuego;

public class Interfaz {

	/**
	 * pre: -
	 * post: abre una ventana con la Ciudad 6 (Hashing) usando una tabla de 7
	 *       buckets (primo). Se gana al llenar 5 de los 7 buckets con 2 palabras
	 *       o más cada uno. Sirve para probarla de forma independiente del juego.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame ventana = new JFrame("Al-Quest - Ciudad 6: Hashing");
			ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			ProgresoJuego progreso = new ProgresoJuego();
			//progreso.setNombreJugador("prueba");
			PanelCiudad6 panel = new PanelCiudad6(7, 5, 2, progreso);
			panel.setAccionSiguiente(() -> System.exit(0));
			ventana.add(panel);
			ventana.setSize(960, 820);
			ventana.setLocationRelativeTo(null);
			ventana.setVisible(true);
		});
	}
}