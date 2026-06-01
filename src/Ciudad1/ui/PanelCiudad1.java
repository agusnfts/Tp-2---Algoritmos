package ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import bitmap.Bitmap;
import partida.Partida;

public class PanelCiudad1
        extends JPanel
        implements KeyListener {


	private JLabel imagen;

    private Partida partida;
    

    private RenderizadorBitmap renderizador;

    public PanelCiudad1(
            Partida partida
    ) {

        this.partida = partida;

        this.renderizador =
                new RenderizadorBitmap();

        this.imagen =
                new JLabel();
   

        add(
                imagen
        );

        addKeyListener(
                this
        );

        setFocusable(
                true
        );

        requestFocusInWindow();

        actualizar();
    }

    public void actualizar() {

        Bitmap bitmap;

        bitmap =
                renderizador.renderizar(
                        partida
                );

        imagen.setIcon(
                new ImageIcon(
                        bitmap.getImage()
                )
        );
        revalidate();

        requestFocus();
        
        repaint();
    }

    @Override
    public void keyPressed(
            KeyEvent evento
    ) {

        int tecla;

        tecla =
                evento.getKeyCode();

        // W

        if (
                tecla
                ==
                KeyEvent.VK_W
        ) {

            partida.moverJugador(
                    0,
                    -1,
                    0
            );
        }

        // S

        if (
                tecla
                ==
                KeyEvent.VK_S
        ) {

            partida.moverJugador(
                    0,
                    1,
                    0
            );
        }

        // A

        if (
                tecla
                ==
                KeyEvent.VK_A
        ) {

            partida.moverJugador(
                    -1,
                    0,
                    0
            );
        }

        // D

        if (
                tecla
                ==
                KeyEvent.VK_D
        ) {

            partida.moverJugador(
                    1,
                    0,
                    0
            );
        }

        // E

        if (
                tecla
                ==
                KeyEvent.VK_E
        ) {

            partida.interactuar();
        }
        
     // 1

        if (tecla == KeyEvent.VK_1) {
            partida.usarElemento(0);
        }

        if (tecla == KeyEvent.VK_2) {
            partida.usarElemento(1);
        }

        if (tecla == KeyEvent.VK_3) {
            partida.usarElemento(2);
        }

        actualizar();
    }

    @Override
    public void keyReleased(
            KeyEvent e
    ) {

    }

    @Override
    public void keyTyped(
            KeyEvent e
    ) {

    }
}