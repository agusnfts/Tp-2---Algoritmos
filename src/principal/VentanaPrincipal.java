package principal;

import javax.swing.JFrame;

import utiles.ValidacionesUtiles;

public class VentanaPrincipal extends JFrame {

	/**
	 * PRE:  progreso != null
	 *
	 * POST:
	 * - Se crea la ventana principal del juego
	 * - Se configuran el título, tamaño y comportamiento de cierre
	 * - Se crea un PanelMapa utilizando el progreso recibido
	 * - El PanelMapa queda asociado a esta ventana
	 * - El mapa se establece como contenido principal de la ventana
	 * - La ventana se muestra al usuario
	 */
	
    public VentanaPrincipal(
            ProgresoJuego progreso
    ) {

        ValidacionesUtiles.esDistintoDeNull(
                progreso,
                "progreso"
        );

        setTitle("Al-Quest");

        setSize(1000, 700);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                EXIT_ON_CLOSE
        );

        PanelMapa mapa =
                new PanelMapa(
                        progreso
                );

        mapa.setFrame(this);

        setContentPane(
                mapa
        );

        setVisible(true);
    }
}
