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

    /**
     * PRE:
     * - progreso != null
     *
     * POST:
     * - Se inicializa el panel gráfico
     * - Se configura el layout del panel
     * - Se crea el Bitmap para dibujar la imagen
     * - Se crea el JLabel con la imagen del Bitmap
     * - Se agrega el JLabel al centro del panel
     * - Se guarda la referencia al objeto ProgresoJuego
     */
    
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
     * PRE:
     * - texto != null
     *
     * POST:
     * - Se muestra el texto en el Bitmap
     * - Se actualiza el componente gráfico (repaint del JLabel)
     * - Se desbloquea la ciudad 6 en el progreso del juego
     * - Se guarda el estado del progreso
     */
    public void mostrarResultado(String texto) {

        ValidacionesUtiles.esDistintoDeNull(
                texto,
                "texto"
        );

        bmp.mostrarTexto(texto);

        lblImagen.repaint();

        if (progreso != null) {
            progreso.desbloquear(6);
            progreso.guardar();
        }
    }
}

