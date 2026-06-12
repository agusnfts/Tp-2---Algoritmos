package Ciudad9.bitmap;

import Ciudad9.modelo.Enemigo;
import Ciudad9.modelo.JugadorCombate;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.List;

public class BitmapCiudad9 {

    private static final int ANCHO = 800;
    private static final int ALTO = 600;

    private static final Font FUENTE_TITULO = new Font("Arial",Font.BOLD,22);

    private static final Font FUENTE_NORMAL =new Font("Arial",Font.PLAIN,16);

    private static final Color COLOR_FONDO=new Color(30,30,30);

    private static final Color COLOR_JUGADOR = new Color(100,200,100);

    private static final Color COLOR_ENEMIGO=new Color(200,80,80);

    private static final Color COLOR_TEXTO =
            Color.WHITE;

    private Bitmap bmp;

    public BitmapCiudad9() {
        bmp = new Bitmap(ANCHO, ALTO);
    }

    public void dibujarEstadoCombate(
            JugadorCombate jugador,
            List<Enemigo> enemigos
    ) {

        bmp.rellenar(COLOR_FONDO);

        bmp.drawText("=== CIUDAD 9 - BATALLA ===",250,40,FUENTE_TITULO, COLOR_TEXTO, COLOR_FONDO);

        bmp.drawText("JUGADOR: "+ jugador.getNombre(), 50, 100,FUENTE_NORMAL,COLOR_JUGADOR, COLOR_FONDO);

        bmp.drawText("Vida: " + jugador.getVida(),50,130,FUENTE_NORMAL,COLOR_JUGADOR,COLOR_FONDO);

        bmp.drawLine(50,160,750, 160, Color.GRAY);

        bmp.drawText("ENEMIGO ACTUAL:", 50,  200, FUENTE_NORMAL, COLOR_ENEMIGO, COLOR_FONDO);

        if (!enemigos.isEmpty()) {
            Enemigo enemigo = enemigos.get(0);

            bmp.drawText(enemigo.getNombre(),50,240, FUENTE_NORMAL, COLOR_ENEMIGO,COLOR_FONDO);
            bmp.drawText("Vida: " + enemigo.getVida(), 50, 270,FUENTE_NORMAL,COLOR_ENEMIGO,COLOR_FONDO);

        } else {
            bmp.drawText("No quedan enemigos",50,240,FUENTE_NORMAL,COLOR_TEXTO,COLOR_FONDO);
        }
    }

    public void dibujarVictoria() {
        bmp.rellenar(COLOR_FONDO);
        bmp.drawText("¡VICTORIA!",300,280,FUENTE_TITULO,Color.YELLOW,COLOR_FONDO);
    }

    public void dibujarDerrota() {

        bmp.rellenar(COLOR_FONDO);

        bmp.drawText(
                "DERROTA",
                320,
                280,
                FUENTE_TITULO,
                Color.RED,
                COLOR_FONDO
        );
    }

    public BufferedImage getImage() {

        return bmp.getImage();
    }
}