package Personaje;



import elementos.Elemento;
import partida.Partida;

/**
 * Representa al jugador de la partida.
 */
public class Jugador {

    // CONSTANTES

    private static final int POSICION_INICIAL = 0;

    private static final int VISION_INICIAL = 2;

    // ATRIBUTOS

    private int posX;

    private int posY;

    private int posZ;

    private int visionActual;

    private int visionMaxima;
    
    private int turnosInterferencia;

    private Mochila mochila;
    
    private Partida partida;
    
    
    /**
     * POST:
     * crea un jugador en la posición inicial
     * con visión inicial y mochila vacía.
     */
    
    
    public Jugador() {

        this.posX = POSICION_INICIAL;
        this.posY = POSICION_INICIAL;
        this.posZ = POSICION_INICIAL;

        this.visionActual = VISION_INICIAL;

        this.visionMaxima = VISION_INICIAL;

        this.mochila = new Mochila();
        
        this.turnosInterferencia = 0;
        
        
    }
    

    // ==================
    // METODOS DE COMPORTAMIENTO
    // ==================

    /**
     * PRE:
     * desplazamientos válidos.
     *
     * POST:
     * mueve al jugador.
     */
    public void mover(int dx, int dy, int dz) {
        this.posX += dx;
        this.posY += dy;
        this.posZ += dz;
    }
    
    public void activarAntorcha() {
        this.mejorarVision();
    }

    public void mejorarVision() {

        this.visionMaxima = 4;

        this.visionActual = 4;
    }
    
    public void aplicarInterferencia(
            int turnos
    ) {

        this.visionActual = 1;

        this.turnosInterferencia = turnos;
    }
    

    /**
     * PRE:
     * elemento != null
     *
     * POST:
     * agrega el elemento a la mochila.
     */
    public void agregarElemento(Elemento elemento) {
        this.mochila.agregarElemento(elemento);
    }

    
    /**
     * PRE:
     * posicion válida.
     *
     * POST:
     * usa el elemento indicado 
     */
    public void usarElemento(int posicion) {

        Elemento elemento;

        if (
                this.mochila.posicionValida(
                        posicion
                )
        ) {

            elemento =
                    this.mochila.getElemento(
                            posicion
                    );

            elemento.usar(
                    this
            );
        }
    }
    // ==================
    // EFECTOS
    // ==================



    public void actualizarEfectos() {

        if (
                this.turnosInterferencia > 0
        ) {

            this.turnosInterferencia--;

            if (
                    this.turnosInterferencia == 0
            ) {

                this.visionActual =
                        this.visionMaxima;
            }
        }
    }

    public boolean estaInterferido() {
        return turnosInterferencia > 0;
    }

    // ==================
    // GETTERS SIMPLES
    // ==================

    /**
     * POST:
     * devuelve la coordenada X.
     */
    public int getPosX() { return posX; }

    /**
     * POST:
     * devuelve la coordenada Y.
     */
    public int getPosY() { return posY; }

    /**
     * POST:
     * devuelve la coordenada Z.
     */
    public int getPosZ() { return posZ; }

    /**
     * POST:
     * devuelve la visión actual.
     */
    public int getVision() {

        return this.visionActual;
    }
    /**
     * POST:
     * devuelve la mochila.
     */
    public Mochila getMochila() {
        return mochila;
    }
    
    public Partida getPartida() {
        return partida;
    }
    
    // ==================
    // SETTERS SIMPLES
    // ==================

    /**
     * PRE:
     * coordenadas válidas.
     *
     * POST:
     * actualiza la posición.
     */
    public void setPosicion(int x, int y, int z) {

        this.posX = x;
        this.posY = y;
        this.posZ = z;
    }
    
    public void setPartida(Partida partida) {
        this.partida = partida;
    }
    

    // ==================
    // TO STRING
    // ==================

    @Override
    public String toString() {

        return "Jugador [" + posX + "," + posY + "," + posZ + "]";
    }
}