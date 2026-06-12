package Ciudad1.Personaje;

import Ciudad1.elementos.Elemento;
import Ciudad1.partida.Partida;


//Representa al jugador de la partida

public class Jugador {

    private static final int POSICION_INICIAL = 0;
    private static final int VISION_INICIAL = 2;

    private int posX;
    private int posY;
    private int posZ;
    private int visionActual;
    private int visionMaxima;
    private int turnosInterferencia;
    private Mochila mochila;
    private Partida partida;


     //POST: crea un jugador en la posicion inicial con vision inicial y mochila vacia

    public Jugador() {
        this.posX = POSICION_INICIAL;
        this.posY = POSICION_INICIAL;
        this.posZ = POSICION_INICIAL;
        this.visionActual = VISION_INICIAL;
        this.visionMaxima = VISION_INICIAL;
        this.mochila = new Mochila();
        this.turnosInterferencia = 0;
    }


    public void mover(int dx, int dy, int dz) {
        this.posX += dx;
        this.posY += dy;
        this.posZ += dz;
    }

    public void mejorarVision() {
        this.visionMaxima = 3;
        this.visionActual = 3;
    }

    public void aplicarInterferencia(int turnos) {
        this.visionActual = 1;
        this.turnosInterferencia = turnos;
    }

    public void agregarElemento(Elemento elemento) {
        this.mochila.agregarElemento(elemento);
    }

    public void actualizarEfectos() {
        if (turnosInterferencia > 0) {
            turnosInterferencia--;
            if (turnosInterferencia == 0) {
                visionActual = visionMaxima;
            }
        }
    }


    public int getPosX() { return posX; }
    public int getPosY() { return posY; }
    public int getPosZ() { return posZ; }
    public int getVision() { return visionActual; }
    public Mochila getMochila() { return mochila; }
    public Partida getPartida() { return partida; }


    public void setPosicion(int x, int y, int z) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    @Override
    public String toString() {
        return "Jugador [" + posX + "," + posY + "," + posZ + "]";
    }
}