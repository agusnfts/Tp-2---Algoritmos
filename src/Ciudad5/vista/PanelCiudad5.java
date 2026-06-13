package Ciudad5.vista;

import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Ciudad5.bitmap.Bitmap;
import principal.ProgresoJuego;
import utiles.ValidacionesUtiles;

public class PanelCiudad5 extends JPanel {

    private Bitmap bmp;

    private JLabel lblImagen;

    private ProgresoJuego progreso;

    public PanelCiudad5(ProgresoJuego progreso) {

        ValidacionesUtiles.esDistintoDeNull(
                progreso,
                "progreso"
        );

        this.progreso = progreso;

        setLayout(new BorderLayout());

        bmp = new Bitmap(850, 450);

        lblImagen = new JLabel(new ImageIcon(bmp.getImage()));

        add(lblImagen, BorderLayout.CENTER);
    }

    /**
     * Muestra el resultado del algoritmo de búsqueda
     */
    public void mostrarResultado(String texto) {

        ValidacionesUtiles.esDistintoDeNull(
                texto,
                "texto"
        );

        bmp.mostrarTexto(texto);

        lblImagen.repaint();

        // DESBLOQUEO CIUDAD 6
        if (progreso != null) {
            progreso.desbloquear(6);
            progreso.guardar();
        }
    }
}

