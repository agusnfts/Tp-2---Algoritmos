package Ciudad1.ui;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import Ciudad1.bitmap.Bitmap;
import Ciudad1.partida.Partida;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import Ciudad1.bitmap.Bitmap;
import Ciudad1.partida.Partida;

//Panel principal que maneja la entrada de teclado y muestra el juego.
public class PanelCiudad1 extends JPanel implements KeyListener {

    private JLabel imagen;
    private JButton botonSalir;
    private Partida partida;
    private RenderizadorBitmap renderizador;
    private Runnable accionSalir;

    // PRE: partida != null
    // POST: crea el panel principal de la Ciudad 1 y configura sus componentes.
    public PanelCiudad1(Partida partida) {

        this.partida = partida;
        this.renderizador = new RenderizadorBitmap();

        setBackground(Color.BLACK);
        setFocusable(true);

        this.imagen = new JLabel();
        imagen.setFocusable(false);

        this.botonSalir = new JButton("Salir al mapa");
        botonSalir.setFocusable(false);

        botonSalir.addActionListener(e -> {
            if (accionSalir != null) {
                accionSalir.run();
            }
        });

        add(imagen);
        add(botonSalir);

        addKeyListener(this);

        actualizar();
    }

    // PRE: accionSalir != null
    // POST: establece la acción a ejecutar al pulsar el botón de salida.
    public void setAccionSalir(Runnable accionSalir) {
        this.accionSalir = accionSalir;
    }

    // POST: actualiza la imagen mostrada con el estado actual de la partida.
    public void actualizar() {

        Bitmap bitmap = renderizador.renderizar(partida);

        imagen.setIcon(new ImageIcon(bitmap.getImage()));

        revalidate();
        repaint();
        requestFocusInWindow();
    }

    // PRE: evento != null
    // POST: procesa la tecla presionada y actualiza la partida.
    @Override
    public void keyPressed(KeyEvent evento) {

        int tecla = evento.getKeyCode();

        // Movimiento
        if (tecla == KeyEvent.VK_W) {
            partida.moverJugador(0, -1, 0);

        } else if (tecla == KeyEvent.VK_S) {
            partida.moverJugador(0, 1, 0);

        } else if (tecla == KeyEvent.VK_A) {
            partida.moverJugador(-1, 0, 0);

        } else if (tecla == KeyEvent.VK_D) {
            partida.moverJugador(1, 0, 0);

        }

        // Interaccion
        else if (tecla == KeyEvent.VK_E) {
            partida.interactuar();

        }

        // Usar elementos
        else if (tecla == KeyEvent.VK_1) {
            partida.usarElemento(0);

        } else if (tecla == KeyEvent.VK_2) {
            partida.usarElemento(1);

        } else if (tecla == KeyEvent.VK_3) {
            partida.usarElemento(2);
        }

        actualizar();
    }

    // POST: no realiza ninguna acción al liberar una tecla.
    @Override
    public void keyReleased(KeyEvent event) {
    }

    // POST: no realiza ninguna acción al escribir una tecla.
    @Override
    public void keyTyped(KeyEvent event) {
    }
}