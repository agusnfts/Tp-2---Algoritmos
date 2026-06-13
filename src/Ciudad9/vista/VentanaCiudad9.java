package Ciudad9.vista;

import Ciudad9.modelo.Accion;
import Ciudad9.modelo.Combate;
import Ciudad9.modelo.Enemigo;
import Ciudad9.modelo.TipoAccion;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import principal.ProgresoJuego;

public class VentanaCiudad9 extends JFrame {

    private Combate combate;
    private PanelCiudad9 panel;
    private ProgresoJuego progreso;

    private JButton botonAtaque;
    private JButton botonDefensa;
    private JButton botonHabilidad;
    private JButton botonTurno;
    private JButton botonSalir;

    public VentanaCiudad9(ProgresoJuego progreso) {

        this.progreso = progreso;
    	
        setTitle("Ciudad 9 - Combate");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        inicializar();
    }

    private void inicializar() {

        combate = new Combate("Jugador");

        combate.iniciarCombate();

        combate.agregarEnemigo(
                new Enemigo("Goblin", 50, 10)
        );

        combate.agregarEnemigo(
                new Enemigo("Orco", 80, 15)
        );

        combate.agregarEnemigo(
                new Enemigo("Brujo", 40, 20)
        );

        panel = new PanelCiudad9();

        add(
                panel,
                BorderLayout.CENTER
        );

        JPanel botones =
                new JPanel(
                        new FlowLayout()
                );

        botonAtaque =
                new JButton("Ataque");

        botonDefensa =
                new JButton("Defensa");

        botonHabilidad =
                new JButton("Habilidad");

        botonTurno =
                new JButton("Procesar Turno");

        botonSalir =
                new JButton("Salir");

        botones.add(botonAtaque);
        botones.add(botonDefensa);
        botones.add(botonHabilidad);
        botones.add(botonTurno);
        botones.add(botonSalir);

        add(
                botones,
                BorderLayout.SOUTH
        );

        eventos();

        actualizarVista();
    }

    private void eventos() {

        botonAtaque.addActionListener(e -> {
            combate.apilarAccion(
                new Accion(TipoAccion.ATAQUE)
            );

            bloquearEleccion();
        });

        botonDefensa.addActionListener(e -> {

            combate.apilarAccion(
                new Accion(TipoAccion.DEFENSA)
            );

            bloquearEleccion();
        });

        botonHabilidad.addActionListener(e -> {

            combate.apilarAccion(
                new Accion(TipoAccion.HABILIDAD)
            );

            bloquearEleccion();
        });

        botonTurno.addActionListener(e -> {

            combate.procesarTurno();

            actualizarVista();

            verificarFinCombate();

            if (!combate.combateTerminado()) {
                desbloquearEleccion();
            }
        });

        botonSalir.addActionListener(
                e -> dispose()
        );
    }

    private void bloquearEleccion() {
        botonAtaque.setEnabled(false);
        botonDefensa.setEnabled(false);
        botonHabilidad.setEnabled(false);
    }

    private void desbloquearEleccion() {
        botonAtaque.setEnabled(true);
        botonDefensa.setEnabled(true);
        botonHabilidad.setEnabled(true);
    }

    private void verificarFinCombate() {

        if (!combate.combateTerminado()) {
            return;
        }

        if (combate.jugadorGano()) {

            progreso.desbloquear(10);

            panel.mostrarVictoria();

            JOptionPane.showMessageDialog(
                    this,
                    "¡Ganaste el combate!\nCiudad 10 desbloqueada."
            );

            progreso.guardar();

        } else {

            panel.mostrarDerrota();

            JOptionPane.showMessageDialog(
                    this,
                    "Has sido derrotado."
            );
        }

        botonAtaque.setEnabled(false);
        botonDefensa.setEnabled(false);
        botonHabilidad.setEnabled(false);
        botonTurno.setEnabled(false);
    }

    private void actualizarVista() {

        panel.actualizar(
                combate.getJugador(),
                combate.getEnemigos()
        );
    }

    //main de testeo
   
}