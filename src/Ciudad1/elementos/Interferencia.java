package elementos;

import Personaje.Jugador;

/**
 * Reduce la vision del jugador por 20 pasos.
 */
public class Interferencia extends Trampa {

    public Interferencia() {
        this.nombre = "Interferencia";
        this.descripcion = "Una energia extraña nubla tu vision";
    }

    @Override
    public void usar(Jugador jugador) {
        jugador.aplicarInterferencia(20);
    }
}