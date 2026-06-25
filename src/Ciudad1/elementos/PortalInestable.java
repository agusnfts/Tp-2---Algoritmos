package Ciudad1.elementos;

import Ciudad1.Personaje.Jugador;

/**
 * Trampa que teletransporta al jugador al inicio.
 */
public class PortalInestable extends Trampa {

    public PortalInestable() {
        this.nombre = "Portal Inestable";
        this.descripcion = "TP al piso z=0, coordenadas (1,1,0)";
    }

    //POST: teletransporta al jugador a la posición inicial del mapa
    @Override
    public void usar(Jugador jugador) {
        jugador.setPosicion(1, 1, 0);
    }
}