package Ciudad9.vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Ciudad9.modelo.Accion;
import Ciudad9.modelo.Combate;
import Ciudad9.modelo.Enemigo;
import Ciudad9.modelo.TipoAccion;

public class VentanaCiudad9 extends JFrame {

    private Combate combate;
    private PanelCiudad9 panel;

    private JButton botonAtaque;
    private JButton botonDefensa;
    private JButton botonHabilidad;
    private JButton botonTurno;
    private JButton botonSalir;

    public VentanaCiudad9() {
        setTitle("Ciudad 9 - Combate");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        inicializar();
    }

    private void inicializar() {
        combate = new Combate("Jugador");
        combate.agregarEnemigo(new Enemigo("Goblin", 50, 10));
        combate.agregarEnemigo(new Enemigo("Orco", 80, 15));
        combate.agregarEnemigo(new Enemigo("Brujo", 40, 20));
        combate.iniciarCombate();

        panel = new PanelCiudad9();
        add(panel, BorderLayout.CENTER);

        JPanel botones = new JPanel(new FlowLayout());
        botonAtaque = new JButton("Ataque");
        botonDefensa = new JButton("Defensa");
        botonHabilidad = new JButton("Habilidad");
        botonTurno = new JButton("Procesar Turno");
        botonSalir = new JButton("Salir");

        botones.add(botonAtaque);
        botones.add(botonDefensa);
        botones.add(botonHabilidad);
        botones.add(botonTurno);
        botones.add(botonSalir);
        add(botones, BorderLayout.SOUTH);

        eventos();
        actualizarVista();
    }

    private void eventos() {
        botonAtaque.addActionListener(e -> combate.apilarAccion(new Accion(TipoAccion.ATAQUE)));
        botonDefensa.addActionListener(e -> combate.apilarAccion(new Accion(TipoAccion.DEFENSA)));
        botonHabilidad.addActionListener(e -> combate.apilarAccion(new Accion(TipoAccion.HABILIDAD)));
        botonTurno.addActionListener(e -> procesarTurno());
        botonSalir.addActionListener(e -> dispose());
    }

    private void procesarTurno() {
        combate.procesarTurno();
        actualizarVista();

        if (combate.combateTerminado()) {
            if (combate.jugadorGano()) {
                panel.mostrarVictoria();
                JOptionPane.showMessageDialog(this, "¡Ganaste el combate!");
            } else {
                panel.mostrarDerrota();
                JOptionPane.showMessageDialog(this, "Has sido derrotado.");
            }
            botonAtaque.setEnabled(false);
            botonDefensa.setEnabled(false);
            botonHabilidad.setEnabled(false);
            botonTurno.setEnabled(false);
        }
    }

    private void actualizarVista() {
        panel.actualizar(combate.getJugador(), combate.getEnemigos());
    }
}