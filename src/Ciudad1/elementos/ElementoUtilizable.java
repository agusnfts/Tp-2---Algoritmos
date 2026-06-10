package elementos;

import Personaje.Jugador;

/**
 * Representa un elemento que se guarda en la mochila 
 * para usarse después.
 */
public abstract class ElementoUtilizable extends Elemento {

    public ElementoUtilizable() {
    }

    @Override
    public abstract void usar(Jugador jugador);
}