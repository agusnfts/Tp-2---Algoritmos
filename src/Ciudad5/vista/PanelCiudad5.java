package Ciudad5.vista;


import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Ciudad5.bitmap.Bitmap;

public class PanelCiudad5 extends JPanel {

    private Bitmap bmp;

    private JLabel lblImagen;

    public PanelCiudad5() {

        setLayout(
                new BorderLayout()
        );

        bmp =
                new Bitmap(
                        850,
                        450
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

    public void mostrarResultado(
            String texto
    ) {

        bmp.mostrarTexto(
                texto
        );

        lblImagen.repaint();
    }
}
