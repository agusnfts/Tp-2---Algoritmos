package principal;

import javax.swing.JFrame;

import utiles.ValidacionesUtiles;

public class VentanaPrincipal extends JFrame {

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
