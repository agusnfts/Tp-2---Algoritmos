package Ciudad4.vista;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Ciudad4.bitmap.Bitmap;

public class PanelCiudad4 extends JPanel {

    private Bitmap bmp;

    private JLabel lblImagen;

    public PanelCiudad4() {

        setLayout(
                new BorderLayout()
        );

        bmp =
                new Bitmap(
                        800,
                        500
                );

        lblImagen =
                new JLabel(
                        new ImageIcon(
                                bmp.getImage()
                        )
                );

        add(
                lblImagen,
                BorderLayout.CENTER
        );
    }

    public void mostrarDatos(
            int[] datos
    ) {

        bmp.dibujarBarras(
                datos
        );

        lblImagen.repaint();
    }
}
