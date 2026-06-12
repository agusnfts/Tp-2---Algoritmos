package Ciudad8.vista;

import javax.swing.JFrame;

import Ciudad8.modelo.Hanoi;
import Ciudad8.modelo.Movimiento;
import principal.ProgresoJuego;

import java.util.List;
import java.util.Stack;

import javax.swing.JFrame;

public class VentanaCiudad8 extends JFrame {

    public VentanaCiudad8(ProgresoJuego progreso) {

        super(
                "Ciudad 8 - Torres de Hanoi"
        );

        add(
                new PanelCiudad8(progreso)
        );

        setSize(
                1300,
                900
        );

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );
    }
}
