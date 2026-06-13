package Ciudad4.vista;
import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Ciudad4.bitmap.Bitmap;
import principal.ProgresoJuego;
import utiles.ValidacionesUtiles;

public class PanelCiudad4 extends JPanel {

    private Bitmap bmp;

    private JLabel lblImagen;

    private ProgresoJuego progreso;

    public PanelCiudad4(ProgresoJuego progreso) {

        ValidacionesUtiles.esDistintoDeNull(
                progreso,
                "progreso"
        );

        this.progreso = progreso;

        setLayout(new BorderLayout());

        bmp = new Bitmap(800, 500);

        lblImagen = new JLabel(new ImageIcon(bmp.getImage()));

        add(lblImagen, BorderLayout.CENTER);
    }

    // Constructor por compatibilidad
    public PanelCiudad4() {
        this(new ProgresoJuego());
    }

    /**
     * Se llama cuando el algoritmo genera datos
     */
    public void mostrarDatos(int[] datos) {

        ValidacionesUtiles.esDistintoDeNull(
                datos,
                "datos"
        );

        bmp.dibujarBarras(datos);

        lblImagen.repaint();

        // DESBLOQUEO CIUDAD 5 (cuando ya se usa el algoritmo)
        if (progreso != null) {
            progreso.desbloquear(5);
            progreso.guardar();
        }
    }
}


