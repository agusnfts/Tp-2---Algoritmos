package ciudadordenamientos.vista;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

public class PanelGrafico extends JPanel {

    private int[] datos;

    public void setDatos(int[] datos) {

        this.datos = datos;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if(datos == null) {
            return;
        }

        int anchoBarra = getWidth() / datos.length;

        for(int i = 0; i < datos.length; i++) {

            int altura = datos[i] * 5;

            g.setColor(Color.BLUE);

            g.fillRect(
                    i * anchoBarra,
                    getHeight() - altura,
                    anchoBarra - 2,
                    altura
            );

            g.setColor(Color.BLACK);
            g.drawString(
                    String.valueOf(datos[i]),
                    i * anchoBarra + 5,
                    getHeight() - altura - 5
            );
        }
    }
}