package Ciudad3;

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

/**
 * Panel de transición para la Ciudad 3.
 * Sirve como menú intermedio para lanzar el minijuego del laberinto hacker
 * y permite al usuario regresar al mapa principal.
 */
public class PanelCiudad3 extends JPanel {

    private static final long serialVersionUID = 1L;

    public PanelCiudad3() {
        setBackground(new Color(15, 30, 20));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        
        // Título del Panel
        gbc.gridy = 0;
        JLabel lblTitulo = new JLabel("--- CIUDAD 3: TERMINAL SECRETA ---");
        lblTitulo.setFont(new Font("Courier New", Font.BOLD, 30));
        lblTitulo.setForeground(Color.GREEN);
        add(lblTitulo, gbc);

        // Botón para arrancar el laberinto
        gbc.gridy = 1;
        JButton btnArrancar = new JButton("INICIAR PROTOCOLO: LABERINTO HACKER");
        btnArrancar.setFont(new Font("Courier New", Font.BOLD, 22));
        btnArrancar.setBackground(Color.BLACK);
        btnArrancar.setForeground(Color.CYAN);
        btnArrancar.setFocusPainted(false);
        
        btnArrancar.addActionListener(e -> {
            SalidaLaberinto.main(new String[0]);
        });
        add(btnArrancar, gbc);

        // Botón para volver al mapa
        gbc.gridy = 2;
        JButton btnVolver = new JButton("DESCONECTAR Y VOLVER AL MAPA");
        btnVolver.setFont(new Font("Courier New", Font.BOLD, 22));
        btnVolver.setBackground(Color.BLACK);
        btnVolver.setForeground(Color.RED);
        btnVolver.setFocusPainted(false);
        
        btnVolver.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (frame != null) {
                frame.setContentPane(new PanelMapa());
                frame.revalidate();
                frame.repaint();
            }
        });
        add(btnVolver, gbc);
    }
}