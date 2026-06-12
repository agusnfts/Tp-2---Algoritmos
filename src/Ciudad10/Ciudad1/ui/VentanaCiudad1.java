package Ciudad1.ui;

import javax.swing.JFrame;
import Ciudad1.partida.Partida;

//Ventana principal de la Ciudad 1.
public class VentanaCiudad1 extends JFrame {

    public VentanaCiudad1() {
        Partida partida = new Partida();
        PanelCiudad1 panel = new PanelCiudad1(partida);

        add(panel);
        
        setTitle("Ciudad 1 - Recolección en Matriz");
        setSize(850, 650);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        panel.requestFocus();
    }
}