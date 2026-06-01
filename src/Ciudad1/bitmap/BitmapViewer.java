package bitmap;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import utiles.ValidacionesUtiles;

public class BitmapViewer {
//INTERFACES ----------------------------------------------------------------------------------------------
//ENUMERADOS ----------------------------------------------------------------------------------------------
//CONSTANTES ----------------------------------------------------------------------------------------------
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
	
    private JFrame frame;
    private JPanel panel;
    private List<Bitmap> bitmaps;
    
//ATRIBUTOS TRANSITORIOS ----------------------------------------------------------------------------------
//CONSTRUCTORES -------------------------------------------------------------------------------------------
    
    /**
     * Genera un visualizador de bitmaps con los bitmaps adjuntos
     * @param bitmaps
     */
    private BitmapViewer(List<Bitmap> bitmaps) {
    	ValidacionesUtiles.esDistintoDeNull(bitmaps, "bitmaps");
    	ValidacionesUtiles.validarMayorACero(bitmaps.size(), "bitmaps");
        this.bitmaps = bitmaps;
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }
    
//METODOS ABSTRACTOS --------------------------------------------------------------------------------------
//METODOS HEREDADOS (CLASE)--------------------------------------------------------------------------------
//METODOS HEREDADOS (INTERFACE)----------------------------------------------------------------------------
//METODOS DE CLASE ----------------------------------------------------------------------------------------
    
    /**
     * Inicia el visualizador de bitmaps
     * @param bitmaps
     */
    public static void showBitmaps(Bitmap... bitmaps) {    	
        new BitmapViewer(Arrays.asList(bitmaps));
    }
    
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
    
    /**
     * Genera una grafica con los bitmaps
     * Luego los refresca cada 0.5 segundo
     */
    private void createAndShowGUI() {
        frame = new JFrame("Visualizador de Bitmaps");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel(new GridLayout(0, 3, 10, 10));

        for (Bitmap bmp : bitmaps) {
            JLabel label = new JLabel(new ImageIcon(bmp.getImage()));
            panel.add(label);
        }

        frame.add(new JScrollPane(panel));
        frame.setSize(1000, 800);
        frame.setVisible(true);

        // Refresca automáticamente cada 500 ms
        new Timer(500, e -> refreshImages()).start();
    }
    
    /**
     * Refresca las imagenes
     */
    private void refreshImages() {
        Component[] comps = panel.getComponents();
        for (int i = 0; i < comps.length; i++) {
            if (comps[i] instanceof JLabel lbl) {
                lbl.setIcon(new ImageIcon(bitmaps.get(i).getImage()));
            }
        }
    }
    
//METODOS DE CONSULTA DE ESTADO ---------------------------------------------------------------------------	
//GETTERS REDEFINIDOS -------------------------------------------------------------------------------------
//GETTERS INICIALIZADOS -----------------------------------------------------------------------------------
//GETTERS COMPLEJOS ---------------------------------------------------------------------------------------
//GETTERS SIMPLES -----------------------------------------------------------------------------------------
//SETTERS COMPLEJOS----------------------------------------------------------------------------------------	
//SETTERS SIMPLES -----------------------------------------------------------------------------------------
}