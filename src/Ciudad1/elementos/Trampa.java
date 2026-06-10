package elementos;

import Personaje.Jugador;

/**
 * Trampa que se activa inmediatamente al abrir un cofre.
 */
public abstract class Trampa extends Elemento {

	/**
     * POST: crea una trampa.
     */
    public Trampa() {
    }

    @Override
    public abstract void usar(Jugador jugador);
}