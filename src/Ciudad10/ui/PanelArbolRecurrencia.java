package Ciudad10.ui;

import Ciudad10.bitmap.Bitmap;
import Ciudad10.logica.NodoExpansion;
import javax.swing.*;
import java.awt.*;
import java.util.List;


//Panel que dibuja visualmente el arbol de expansion de la recurrencia.
public class PanelArbolRecurrencia extends JPanel {

    private Bitmap bmp;
    private JLabel lblImagen;
    private int a;
    private int b;
    private int k;

    public PanelArbolRecurrencia() {
        setLayout(new BorderLayout());

        bmp = new Bitmap(1600, 500);
        bmp.rellenar(Color.WHITE);

        lblImagen = new JLabel(new ImageIcon(bmp.getImage()));
        add(lblImagen, BorderLayout.CENTER);
    }

    public void setRecurrencia(int a, int b, int k) {
        this.a = a;
        this.b = b;
        this.k = k;
    }

    /**
     * Actualiza el dibujo del arbol con la nueva lista de nodos.
     */
    public void setNodos(List<NodoExpansion> nodos) {
        dibujarArbol(nodos);
        lblImagen.repaint();
    }

    /**
     * Dibuja el arbol de recurrencia
     * Si hay demasiados nodos, muestra solo el patron general.
     */
    private void dibujarArbol(List<NodoExpansion> nodos) {
        bmp.rellenar(Color.WHITE);
        int anchoBitmap = bmp.getWidth();
        int y = 50;

        for (NodoExpansion nodo : nodos) {
            int cantidad = nodo.getCantidadProblemas();

            if (cantidad > 28) {
                // Mensaje cuando hay demasiados nodos (para no saturar el grafico)
                bmp.drawText("No se muestran mas nodos para mantener el grafico legible",
                        anchoBitmap / 2 - 180, y, new Font("Arial", Font.PLAIN, 18), Color.BLACK, null);
                bmp.drawText("Patron detectado:", anchoBitmap / 2 - 150, y + 40,
                        new Font("Arial", Font.PLAIN, 18), Color.BLACK, null);
                bmp.drawText("Nivel i -> " + a + "^i problemas", anchoBitmap / 2 - 150, y + 70,
                        new Font("Arial", Font.PLAIN, 18), Color.BLACK, null);
                bmp.drawText("Tamano -> n/(" + b + "^i)", anchoBitmap / 2 - 150, y + 100,
                        new Font("Arial", Font.PLAIN, 18), Color.BLACK, null);
                bmp.drawText("Costo por nodo -> (n/(" + b + "^i))^" + k, anchoBitmap / 2 - 150, y + 130,
                        new Font("Arial", Font.PLAIN, 18), Color.BLACK, null);
                return;
            }

            int separacion = anchoBitmap / (cantidad + 1);
            int factorRamificacion = (nodo.getNivel() > 0) 
                    ? cantidad / nodos.get(nodo.getNivel() - 1).getCantidadProblemas() 
                    : 1;

            for (int i = 0; i < cantidad; i++) {
                int x = separacion * (i + 1);

                bmp.drawCircle(x, y, 15, Color.BLACK);
                bmp.drawText(nodo.getTamanio(), x - 8, y + 5,
                        new Font("Arial", Font.PLAIN, 12), Color.BLACK, null);

                if (nodo.getNivel() > 0) {
                    int cantidadPadres = nodos.get(nodo.getNivel() - 1).getCantidadProblemas();
                    int separacionPadres = anchoBitmap / (cantidadPadres + 1);
                    int padre = i / factorRamificacion;
                    int xPadre = separacionPadres * (padre + 1);

                    bmp.drawLine(xPadre, y - 50, x, y - 20, Color.BLACK);
                }
            }
            y += 80;
        }
    }
}