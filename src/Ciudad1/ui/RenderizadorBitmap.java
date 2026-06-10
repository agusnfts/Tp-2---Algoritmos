package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import sprites.Sprites;
import Personaje.Jugador;
import Personaje.Mochila;
import bitmap.Bitmap;
import tablero.Casillero;
import tablero.TipoCasillero;
import partida.Partida;
import tablero.Tablero;

public class RenderizadorBitmap {

    private static final int TAM_CASILLA = 35;
    private static final int OFFSET_X = 40;
    private static final int OFFSET_Y = 40;
    private static final int SLOT_SIZE = 70;
    private static final int ITEM_SIZE = 32;

    private final Sprites sprites;

    public RenderizadorBitmap() {
        this.sprites = new Sprites();
    }

    public Bitmap renderizar(Partida partida) {
        Bitmap bitmap = new Bitmap(850, 650);
        bitmap.rellenar(Color.BLACK);

        Tablero tablero = partida.getTablero();
        Jugador jugador = partida.getJugador();

        dibujarMapa(bitmap, tablero, jugador);
        dibujarHUD(bitmap, partida);

        return bitmap;
    }

    private void dibujarMapa(Bitmap bitmap, Tablero tablero, Jugador jugador) {
        int piso = jugador.getPosZ();

        for (int y = 0; y < tablero.getAlto(); y++) {
            for (int x = 0; x < tablero.getAncho(); x++) {
                Casillero casillero = tablero.getCasillero(x, y, piso);

                int posX = OFFSET_X + x * TAM_CASILLA;
                int posY = OFFSET_Y + y * TAM_CASILLA;

                int dx = Math.abs(x - jugador.getPosX());
                int dy = Math.abs(y - jugador.getPosY());
                int vision = jugador.getVision();

                if (dx > vision || dy > vision) {
                    bitmap.drawRectangle(posX, posY, TAM_CASILLA, TAM_CASILLA, Color.BLACK);
                    continue;
                }

                dibujarImagen(bitmap, sprites.suelo, posX, posY);

                if (casillero.getTipo() == TipoCasillero.PARED) {
                    dibujarImagen(bitmap, sprites.pared, posX, posY);
                }

                if (casillero.getTipo() == TipoCasillero.COFRE) {
                    int cx = posX + (TAM_CASILLA - 28) / 2;
                    int cy = posY + (TAM_CASILLA - 28) / 2;
                    dibujarImagen(bitmap, sprites.cofre, cx, cy);
                }

                if (casillero.getTipo() == TipoCasillero.ESCALERA_SUBE) {
                    dibujarImagen(bitmap, sprites.escaleraSubir, posX, posY);
                }

                if (casillero.getTipo() == TipoCasillero.ESCALERA_BAJA) {
                    dibujarImagen(bitmap, sprites.escaleraBajar, posX, posY);
                }
            }
        }

        int jugadorX = OFFSET_X + jugador.getPosX() * TAM_CASILLA + (TAM_CASILLA - 28) / 2;
        int jugadorY = OFFSET_Y + jugador.getPosY() * TAM_CASILLA + (TAM_CASILLA - 38) / 2;
        dibujarImagen(bitmap, sprites.jugador, jugadorX, jugadorY);
    }

    private void dibujarImagen(Bitmap bitmap, BufferedImage imagen, int x, int y) {
        if (imagen == null) return;

        Bitmap temp = new Bitmap(imagen.getWidth(), imagen.getHeight());
        
        Graphics2D g = temp.getImage().createGraphics();
        g.drawImage(imagen, 0, 0, null);
        g.dispose();

        bitmap.pasteBitmap(temp, x, y);
    }

    private void dibujarHUD(Bitmap bitmap, Partida partida) {
        Jugador jugador = partida.getJugador();
        Mochila mochila = jugador.getMochila();

        bitmap.drawText("Objetivo: Encontrar ",
                620, 40, new Font("Arial", Font.BOLD, 18), Color.WHITE, Color.BLACK);
        
        bitmap.drawText("  3 objetos perdidos ",
                620, 70, new Font("Arial", Font.BOLD, 18), Color.WHITE, Color.BLACK);

        bitmap.drawText("Objetos: " + partida.getElementosRecolectados() + "/3",
        		620,110,new Font("Arial", Font.BOLD, 18),Color.WHITE,Color.BLACK);


        bitmap.drawText("Inventario", 620, 150,
                new Font("Arial", Font.BOLD, 20), Color.YELLOW, Color.BLACK);

        bitmap.drawText(partida.getMensaje(), 20, 600,
                new Font("Arial", Font.BOLD, 16), Color.YELLOW, Color.BLACK);

        dibujarSlot(bitmap, 620, 180, mochila, 0, "1");
        dibujarSlot(bitmap, 620, 290, mochila, 1, "2");
        dibujarSlot(bitmap, 620, 400, mochila, 2, "3");
    }

    private void dibujarSlot(Bitmap bitmap, int x, int y, Mochila mochila, int posicion, String tecla) {
        String nombre = mochila.getNombreElemento(posicion);

        dibujarImagen(bitmap, sprites.slotVacio, x, y);

        if (!nombre.equals("---")) {
            BufferedImage item = getImagenItem(nombre);
            if (item != null) {
                int itemX = x + (SLOT_SIZE - ITEM_SIZE) / 2;
                int itemY = y + (SLOT_SIZE - ITEM_SIZE) / 2;
                dibujarImagen(bitmap, item, itemX, itemY);
            }
        }

        bitmap.drawText(tecla + " - " + nombre,x,y + SLOT_SIZE + 20,new Font("Arial", Font.PLAIN, 13),Color.WHITE,Color.BLACK);
    }

    private BufferedImage getImagenItem(String nombre) {
        if (nombre.contains("Antorcha")) return sprites.antorcha;
        if (nombre.contains("Mapa")) return sprites.mapaRasgado;
        if (nombre.contains("Amuleto")) return sprites.amuletoMistico;
        return null;
    }
}