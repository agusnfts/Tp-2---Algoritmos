package Ciudad3.bitmap;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


/* Saco librerias que ya estaban de antes porque ya no se usa:
-import javax.swing.Timer;
-import javax.swing.JScrollPane;
-import javax.swing.JButton;
-import java.awt.GridLayout;
*/

import utiles.ValidacionesUtiles;

public class BitmapViewerConMenu {

    private JFrame frame;
    private JPanel panel;
    private JPanel menuPanel;
    private List<Bitmap> bitmaps;

    // Nueva lista de acciones del menú
    private List<MenuAction> menuActions;

    /**
     * Constructor privado
     */
    private BitmapViewerConMenu(List<Bitmap> bitmaps, List<MenuAction> menuActions) {
        ValidacionesUtiles.esDistintoDeNull(bitmaps, "bitmaps");
        ValidacionesUtiles.validarMayorACero(bitmaps.size(), "bitmaps");

        this.bitmaps = bitmaps;
        this.menuActions = menuActions != null ? menuActions : List.of();

        SwingUtilities.invokeLater(this::createAndShowGUI);
    }

    /**
     * Muestra bitmaps sin menú
     */
    public static void showBitmaps(Bitmap... bitmaps) {
        new BitmapViewerConMenu(Arrays.asList(bitmaps), List.of());
    }

    /**
     * Muestra bitmaps + menú con acciones
     */
    public static void showBitmapsWithMenu(List<MenuAction> actions, Bitmap... bitmaps) {
        new BitmapViewerConMenu(Arrays.asList(bitmaps), actions);
    }

    /**
     * Crea la UI (Anteriormente, en el material que el profesor nos muestra, esta
     * funcion nos mostraba la UI con barras laterales y horizontales en un fomrato de 1000x800
     * Modifico esto por si en la ciudad 3, el laberinto, si es muy grande se usan barras laterales y horizontales
     * pero si el laberinto es pequeño y cabe en la ventana del programa no agurega las barras laterales y horizontales.
     */
    private void createAndShowGUI() {
        frame = new JFrame("Visualizador de Bitmaps");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        panel = new JPanel(new java.awt.GridBagLayout());

        for (Bitmap bmp : bitmaps) {
            JLabel label = new JLabel(new ImageIcon(bmp.getImage()));
            panel.add(label);
        }

        //Se envuelve el panel centrado en un JScrollPane
        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(panel);
        scrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder()); //Quita el borde por defecto
        
        //Calculamos el tamaño del monitor para no excederlo (%90 de ancho y alto)
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int maxWidth = (int) (screenSize.width * 0.9); 
        int maxHeight = (int) (screenSize.height * 0.9);
        
        java.awt.Dimension panelSize = panel.getPreferredSize();
        
        //Le decimos al scroll que tome el tamaño de la imagen, 
        //SALVO que la imagen sea más grande que la pantalla, en cuyo caso se frena en el máximo
        scrollPane.setPreferredSize(new java.awt.Dimension(
            Math.min(panelSize.width + 50, maxWidth), 
            Math.min(panelSize.height + 50, maxHeight)
        ));

        //Agregamos el scrollPane en lugar del panel directamente
        frame.add(scrollPane, BorderLayout.CENTER);

        //-----PANEL DE BOTONES-------------
        menuPanel = new JPanel(new FlowLayout());

        for (MenuAction action : menuActions) {
            javax.swing.JButton btn = new javax.swing.JButton(action.getLabel());
            btn.addActionListener(e -> action.getRunnable().run());
            menuPanel.add(btn);
        }

        frame.add(menuPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Se refresca automáticamente cada 500 ms
        new javax.swing.Timer(500, e -> refreshImages()).start();
    }

    /**
     * Actualiza las imágenes
     */
    private void refreshImages() {
        Component[] comps = panel.getComponents();
        for (int i = 0; i < comps.length; i++) {
            if (comps[i] instanceof JLabel lbl) {
                lbl.setIcon(new ImageIcon(bitmaps.get(i).getImage()));
            }
        }
    }

    // -----------------------------------------------------
    // Clase para representar una acción de menú
    // -----------------------------------------------------
    public static class MenuAction {
        private final String label;
        private final Runnable runnable;

        public MenuAction(String label, Runnable runnable) {
            this.label = label;
            this.runnable = runnable;
        }

        public String getLabel() {
            return label;
        }

        public Runnable getRunnable() {
            return runnable;
        }
    }
}
