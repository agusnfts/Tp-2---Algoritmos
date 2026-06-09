package Ciudad1.tablero;

/**
 * Representa el mapa tridimensional.
 */
public class Tablero {

    // CONSTANTES

    private static final int ANCHO = 15;

    private static final int ALTO = 15;

    private static final int PISOS = 3;

    // ATRIBUTOS

    private Casillero[][][] mapa;

    // ==================
    // CONSTRUCTOR
    // ==================

    /**
     * POST:
     * crea un tablero vacío.
     */
    public Tablero() {

        int x;
        int y;
        int z;

        this.mapa =
                new Casillero
                        [ANCHO]
                        [ALTO]
                        [PISOS];

        for (x = 0; x < ANCHO; x++) {

            for (y = 0; y < ALTO; y++) {

                for (z = 0; z < PISOS; z++) {

                    this.mapa[x][y][z] =
                            new Casillero();
                }
            }
        }
    }
    
    

    // ==================
    // GETTERS
    // ==================

    /**
     * PRE:
     * coordenadas válidas.
     *
     * POST:
     * devuelve el casillero.
     */
    public Casillero getCasillero(
            int x,
            int y,
            int z
    ) {

        return this.mapa[x][y][z];
    }

    public int getAncho() {

        return ANCHO;
    }

    public int getAlto() {

        return ALTO;
    }

    public int getPisos() {

        return PISOS;
    }

    // ==================
    // CONSULTAS
    // ==================

    /**
     * POST:
     * indica si la posición existe.
     */
    public boolean posicionValida(
            int x,
            int y,
            int z
    ) {

        return x >= 0
                && x < ANCHO
                && y >= 0
                && y < ALTO
                && z >= 0
                && z < PISOS;
    }
}