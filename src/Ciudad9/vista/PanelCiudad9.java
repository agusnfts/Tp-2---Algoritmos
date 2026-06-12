package Ciudad9.vista;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Ciudad9.bitmap.BitmapCiudad9;
import Ciudad9.modelo.Enemigo;
import Ciudad9.modelo.JugadorCombate;

public class PanelCiudad9 extends JPanel {

    private BitmapCiudad9 bmp;
    private JLabel lblImagen;

    public PanelCiudad9() {
        setLayout(new BorderLayout());
        bmp = new BitmapCiudad9();
        lblImagen = new JLabel(new ImageIcon(bmp.getImage()));
        add(lblImagen, BorderLayout.CENTER);
    }

    public void actualizar(JugadorCombate jugador, List<Enemigo> enemigos) {
        bmp.dibujarEstadoCombate(jugador, enemigos);
        lblImagen.setIcon(new ImageIcon(bmp.getImage()));
        revalidate();
        repaint();
    }

    public void mostrarVictoria() {
        bmp.dibujarVictoria();
        lblImagen.setIcon(new ImageIcon(bmp.getImage()));
        revalidate();
        repaint();
    }

    public void mostrarDerrota() {
        bmp.dibujarDerrota();
        lblImagen.setIcon(new ImageIcon(bmp.getImage()));
        revalidate();
        repaint();
    }
}