package Ciudad10.ui;

import Ciudad10.bitmap.Bitmap;
import Ciudad10.logica.NodoExpansion;
import javax.swing.*;
import java.awt.*;
import java.util.List;


//Panel que dibuja visualmente el arbol de expansion de la recurrencia.
public class PanelArbolRecurrencia extends JPanel {

    private Bitmap bitmap;
    private JLabel lblImagen;
    private int nivelVisible;
    private List<NodoExpansion> nodos;
    private int a;
    private int b;
    private int k;
    

    /**
     * POST: crea el panel gráfico e inicializa el bitmap
     * donde se dibujará el árbol de recurrencia.
     */
    public PanelArbolRecurrencia() {
        setLayout(new BorderLayout());

        bitmap = new Bitmap(1600, 450);
        bitmap.rellenar(Color.WHITE);

        lblImagen = new JLabel(new ImageIcon(bitmap.getImage()));
        add(lblImagen, BorderLayout.CENTER);
    }

    /**
     * PRE: a > 0, b > 1.
     * POST: guarda los parámetros de la recurrencia
     * para mostrar información resumida del patrón.
     */
    public void setRecurrencia(int a, int b, int k) {
        this.a = a;
        this.b = b;
        this.k = k;
    }

    /**
     * PRE: nodos != null.
     * POST: almacena los nodos generados para la expansión
     * y redibuja el árbol completo.
     */
    public void setNodos(List<NodoExpansion> nodos) {
        this.nodos = nodos;
        dibujarArbol();
        lblImagen.repaint();
    }
    
    /**
     * PRE: nivel >= 0.
     * POST: muestra únicamente los niveles del árbol
     * hasta el nivel indicado.
     */
    public void mostrarHastaNivel(int nivel) {
        this.nivelVisible = nivel;
        dibujarArbol();
        lblImagen.repaint();
    }

    /**
     * PRE: nodos puede ser null.
     * POST: dibuja la expansión del árbol de recurrencia
     * hasta el nivel visible actual. Si la cantidad de nodos
     * supera el límite establecido, muestra únicamente
     * una descripción del patrón de crecimiento.
     */
    private void dibujarArbol() {
    	if (nodos == null) {
            return;
        }
    	bitmap.rellenar(Color.WHITE);
        int anchoBitmap = bitmap.getWidth();
        int y = 50;

        for (NodoExpansion nodo : nodos) {
        	if (nodo.getNivel() > nivelVisible) {
                continue;
            }
            int cantidad = nodo.getCantidadProblemas();

            if (cantidad > 28) {
                // Mensaje cuando hay demasiados nodos (para no saturar el grafico)
            	bitmap.drawText("No se muestran mas nodos para mantener el grafico legible",
            			anchoBitmap / 2 - 140,y,
            	        new Font("Arial", Font.PLAIN, 18),
            	        Color.BLACK,null);
                return;
            }

            int separacion = anchoBitmap / (cantidad + 1);
            // Cantidad de hijos que genera cada nodo del nivel anterior.
            int factorRamificacion = (nodo.getNivel() > 0) 
                    ? cantidad / nodos.get(nodo.getNivel() - 1).getCantidadProblemas() 
                    : 1;

            for (int i = 0; i < cantidad; i++) {
                int x = separacion * (i + 1);

                bitmap.drawCircle(x, y, 15, Color.BLACK);
                bitmap.drawText(nodo.getTamanio(), x - 8, y + 5,
                        new Font("Arial", Font.PLAIN, 12), 
                        Color.BLACK, null);

                if (nodo.getNivel() > 0) {
                    int cantidadPadres = nodos.get(nodo.getNivel() - 1).getCantidadProblemas();
                    int separacionPadres = anchoBitmap / (cantidadPadres + 1);
                    // Determina qué nodo del nivel anterior es el padre del nodo actual.
                    int padre = i / factorRamificacion;
                    int xPadre = separacionPadres * (padre + 1);

                    bitmap.drawLine(xPadre, y - 50, x, y - 20, Color.BLACK);
                }
            }
            y += 80;
        }
    }
}