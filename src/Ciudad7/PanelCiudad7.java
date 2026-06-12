package Ciudad7;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import principal.PanelMapa;
import principal.ProgresoJuego;

/**
 * Panel de integración para la Ciudad 7.
 * Actúa como menú intermedio dentro del JFrame principal del juego.
 * Permite instanciar el gestor de grafos independiente o retornar al flujo de navegación base.
 */
public class PanelCiudad7 extends JPanel {

    private static final long serialVersionUID = 1L;

    private ProgresoJuego progreso;

    public PanelCiudad7(ProgresoJuego progreso) {

        this.progreso = progreso;

        setLayout(new GridBagLayout());
        setBackground(new Color(40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //Título
        JLabel titulo = new JLabel("Menú - Ciudad 7 (Grafos)", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        gbc.gridy = 0;
        add(titulo, gbc);

        //Botón de Ejecución del Gestor
        JButton btnIniciar = new JButton("Iniciar Programa (Gestor de Redes)");
        btnIniciar.setFont(new Font("Arial", Font.PLAIN, 16));
        btnIniciar.setFocusPainted(false);

        btnIniciar.addActionListener(e -> {

            ProgramaPrincipalGrafos.setProgreso(progreso);

            ProgramaPrincipalGrafos.main(new String[0]);
        });

        gbc.gridy = 1;
        add(btnIniciar, gbc);

        // Botón de Retorno
        JButton btnVolver = new JButton("Volver al Mapa Principal");
        btnVolver.setFont(new Font("Arial", Font.PLAIN, 16));
        btnVolver.setFocusPainted(false);

        btnVolver.addActionListener(e -> {

            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

            if (frame != null) {

                frame.getContentPane().removeAll();
                frame.setContentPane(new PanelMapa(progreso));

                frame.revalidate();
                frame.repaint();
                frame.requestFocusInWindow();
            }
        });

        gbc.gridy = 2;
        add(btnVolver, gbc);
    }

    //  constructor por compatibilidad
    public PanelCiudad7() {
        this(new ProgresoJuego());
    }
}
