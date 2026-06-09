package Ciudad1.elementos;

import Ciudad1.Personaje.Jugador;




/**
 * Revela información sobre el mapa.
 */
public class MapaRasgado extends ElementoUtilizable {


    /**
     * POST:
     * crea un mapa rasgado.
     */
 
    public MapaRasgado() {

        super(
                "Mapa Rasgado",
                "Revela la ubicacion de un objeto importante."
        );

    }

    /**
     * PRE:
     * jugador != null
     *
     * POST:
     * revela una ubicación.
     */
    @Override
    public void usar(Jugador jugador) {

        jugador.getPartida().setMensaje(
            "Lees (7,7,3)"
        );
    }
}

