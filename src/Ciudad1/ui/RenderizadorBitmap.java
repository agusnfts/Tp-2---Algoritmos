package ui;

import java.awt.Color;
import java.awt.Font;
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

    public Bitmap renderizar(
            Partida partida
    ) {

        Bitmap bmp;

        Tablero tablero;

        Jugador jugador;

        bmp =
                new Bitmap(
                        850,
                        650
                );

        bmp.rellenar(
                Color.BLACK
        );

        tablero =
                partida.getTablero();

        jugador =
                partida.getJugador();

        dibujarMapa(
                bmp,
                tablero,
                jugador
        );

        dibujarHUD(
                bmp,
                partida
        );

        return bmp;
    }

    private void dibujarMapa(
            Bitmap bmp,
            Tablero tablero,
            Jugador jugador
    )
    {

        int x;
        int y;

        int piso;

        Casillero casillero;

        piso =
                jugador.getPosZ();

        y = 0;

        while (
                y < tablero.getAlto()
        ) {

            x = 0;

            while (
                    x < tablero.getAncho()
            ) {

                casillero =
                        tablero.getCasillero(
                                x,
                                y,
                                piso
                        );
                int dx = Math.abs(x - jugador.getPosX());
                int dy = Math.abs(y - jugador.getPosY());

                int vision = jugador.getVision();

                if (dx > vision || dy > vision) {

                    bmp.drawRectangle(
                        OFFSET_X + x * TAM_CASILLA,
                        OFFSET_Y + y * TAM_CASILLA,
                        TAM_CASILLA,
                        TAM_CASILLA,
                        Color.BLACK
                    );

                    x++;
                    continue;
                }

                bmp.drawRectangle(
                        OFFSET_X + x * TAM_CASILLA,
                        OFFSET_Y + y * TAM_CASILLA,
                        TAM_CASILLA,
                        TAM_CASILLA,
                        Color.WHITE
                );
                // PARED

                if (
                        casillero.getTipo()
                        ==
                        TipoCasillero.PARED
                ) {

                    int i;

                    i = 0;

                    while (
                            i < TAM_CASILLA
                    ) {

                    	bmp.drawLine(
                    	        OFFSET_X + x * TAM_CASILLA,
                    	        OFFSET_Y + y * TAM_CASILLA + i,

                    	        OFFSET_X + x * TAM_CASILLA + TAM_CASILLA,

                    	        OFFSET_Y + y * TAM_CASILLA + i,

                    	        Color.DARK_GRAY
                    	);

                        i++;
                    }
                }

                // COFRE

                if (
                        casillero.getTipo()
                        ==
                        TipoCasillero.COFRE
                ) {

                	bmp.drawCircle(
                	        OFFSET_X
                	        +
                	        x * TAM_CASILLA
                	        +
                	        TAM_CASILLA / 2,

                	        OFFSET_Y
                	        +
                	        y * TAM_CASILLA
                	        +
                	        TAM_CASILLA / 2,

                	        8,

                	        Color.YELLOW
                	);
                }

                // ESCALERA SUBE

                if (
                        casillero.getTipo()
                        ==
                        TipoCasillero.ESCALERA_SUBE
                ) {

                    bmp.drawText(
                            "UP",

                            OFFSET_X + x * TAM_CASILLA + 12,

                            OFFSET_Y + y * TAM_CASILLA + 25,

                            new Font(
                                    "Arial",
                                    Font.BOLD,
                                    16
                            ),

                            Color.GREEN,

                            null
                    );
                }

                // ESCALERA BAJA

                if (
                        casillero.getTipo()
                        ==
                        TipoCasillero.ESCALERA_BAJA
                ) {

                    bmp.drawText(
                            "DN",

                            OFFSET_X + x * TAM_CASILLA + 12,

                            OFFSET_Y + y * TAM_CASILLA + 25,

                            new Font(
                                    "Arial",
                                    Font.BOLD,
                                    16
                            ),

                            Color.CYAN,

                            null
                    );
                }

                x++;
            }

            y++;
        }

        // JUGADOR

        bmp.drawCircle(
                40 + jugador.getPosX() * TAM_CASILLA + TAM_CASILLA / 2,
                40 + jugador.getPosY() * TAM_CASILLA + TAM_CASILLA / 2,
                10,
                Color.RED
        );
    }
    private void dibujarHUD(
            Bitmap bmp,
            Partida partida
    ) {

        Jugador jugador;

        Mochila mochila;

        jugador =
                partida.getJugador();

        mochila =
                jugador.getMochila();

        bmp.drawText(
                "Piso: "
                +
                (jugador.getPosZ() + 1),

                620,
                40,

                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                ),

                Color.WHITE,

                Color.BLACK
        );

        bmp.drawText(
                "Turnos: "
                +
                partida.getTurnos(),

                620,
                80,

                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                ),

                Color.WHITE,

                Color.BLACK
        );

        bmp.drawText(
                "Inventario",

                620,
                150,

                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                ),

                Color.YELLOW,

                Color.BLACK
        );
        
        bmp.drawText(
                "Vision: "
                +
                jugador.getVision(),

                620,
                120,

                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                ),

                Color.WHITE,

                Color.BLACK
        );
        
        bmp.drawText(
                partida.getMensaje(),

                20,
                600,

                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                ),

                Color.YELLOW,

                Color.BLACK
        );

        dibujarSlot(bmp, 620, 180, mochila, 0, "1");
        dibujarSlot(bmp, 620, 260, mochila, 1, "2");
        dibujarSlot(bmp, 620, 340, mochila, 2, "3");
    }

    private void dibujarSlot(Bitmap bmp, int x, int y, Mochila mochila, int posicion, String tecla) {

        bmp.drawRectangle(x, y, 120, 60, Color.WHITE);

        String texto = mochila.getNombreElemento(posicion);

        bmp.drawText(
            tecla + " - " + texto,
            x + 10,
            y + 35,
            new Font("Arial", Font.PLAIN, 14),
            Color.WHITE,
            Color.BLACK
        );
    }
}