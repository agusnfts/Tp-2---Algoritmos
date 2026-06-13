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

//Panel principal que maneja la entrada de teclado y muestra el juego.

public class PanelCiudad1 extends JPanel implements KeyListener {

    private JLabel imagen;
    private Partida partida;
    private RenderizadorBitmap renderizador;

    public PanelCiudad1(Partida partida) {
        this.partida = partida;
        this.renderizador = new RenderizadorBitmap();
        this.imagen = new JLabel();
        
        setBackground(Color.BLACK);

        add(imagen);
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();

        actualizar();
    }

    public void actualizar() {
        Bitmap bitmap = renderizador.renderizar(partida);
        imagen.setIcon(new ImageIcon(bitmap.getImage()));
        
        revalidate();
        repaint();
        requestFocus();
    }

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
        //Interaccion
        else if (tecla == KeyEvent.VK_E) {
            partida.interactuar();
        } 
        //Usar elementos
        else if (tecla == KeyEvent.VK_1) {
            partida.usarElemento(0);
        } else if (tecla == KeyEvent.VK_2) {
            partida.usarElemento(1);
        } else if (tecla == KeyEvent.VK_3) {
            partida.usarElemento(2);
        }
        
        actualizar();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}