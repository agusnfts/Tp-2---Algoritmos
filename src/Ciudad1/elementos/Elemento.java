package Ciudad1.elementos;

import Ciudad1.Personaje.Jugador;

/**
 * Representa un elemento base del juego.
 */
public abstract class Elemento {

    protected String nombre;
    protected String descripcion;

    public Elemento() {
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public abstract void usar(Jugador jugador);

    @Override
    public String toString() {
        return nombre;
    }
}