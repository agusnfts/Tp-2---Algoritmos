package Ciudad1.ui;
import Ciudad1.partida.Partida;
import javax.swing.JFrame;

public class VentanaCiudad1 extends JFrame {

    public VentanaCiudad1() {

        Partida partida;

        PanelCiudad1 panel;

        partida =
                new Partida();

        panel =
                new PanelCiudad1(
                        partida
                );

        add(
                panel
        );

        setTitle(
                "Ciudad 1"
        );

        setSize(
                850,
                650
        );

        setResizable(
                false
        );

        setLocationRelativeTo(
                null
        );

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setVisible(
                true
        );
        
        panel.requestFocus();
    }
}