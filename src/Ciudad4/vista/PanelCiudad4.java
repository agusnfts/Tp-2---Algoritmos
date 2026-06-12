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

    /**
     * PRE: progreso != null.
     * POST: crea el panel de la Ciudad 4 e inicializa el área de visualización.
     */
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

    /**
     * POST: crea el panel utilizando una nueva instancia de ProgresoJuego.
     *       Se mantiene por compatibilidad con código existente.
     */
    public PanelCiudad4() {
        this(new ProgresoJuego());
    }

    /**
     * PRE: datos != null.
     * POST: dibuja los datos como un gráfico de barras, actualiza la vista.
     */
    public void mostrarDatos(int[] datos) {

        ValidacionesUtiles.esDistintoDeNull(
                datos,
                "datos"
        );

        bmp.dibujarBarras(datos);

        lblImagen.repaint();

      
    }
    
    
    /**
     * PRE:
     * - El Bitmap y el JLabel fueron inicializados correctamente.
     *
     * POST:
     * - Se muestra en pantalla el objetivo de la Ciudad 4.
     * - Se indican las acciones necesarias para completar la ciudad:
     *      * visualizar BubbleSort y QuickSort
     *      * responder correctamente las preguntas finales.
     * - Se actualiza la imagen mostrada en el panel.
     */
    
    public void mostrarInstrucciones() {

        String texto =
                "CIUDAD 4\n\n"
                + "OBJETIVO:\n"
                + "- Ver los dos algoritmos\n"
                + "- Responder correctamente\n"
                + "las preguntas finales";

        bmp.mostrarTexto(texto);

        lblImagen.repaint();
    }
    
}
