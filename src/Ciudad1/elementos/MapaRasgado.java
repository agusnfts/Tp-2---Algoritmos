package elementos;

import Personaje.Jugador;
import partida.Partida;
import tablero.GeneradorTablero;

/**
 * Revela información sobre el mapa.
 */
public class MapaRasgado extends ElementoUtilizable {

	/**
     * POST: crea un Mapa Rasgado.
     */
    public MapaRasgado() {
        this.nombre = "Mapa Rasgado";
        this.descripcion = "Revela la ubicación de un objeto importante";
    }

    /**
     * PRE:  jugador != null
     * POST: revela una pista leve de la ubicacion del cofre util.
     */
    @Override
    public void usar(Jugador jugador) {

        Partida partida = jugador.getPartida();

        GeneradorTablero generador = partida.getGenerador();

        String pista;

        switch (generador.getVariantePiso2()) {

        case 0:
            pista = "Muestra la ubicación de un cofre al sureste pero ¿Qué piso?.";
            break;

        case 1:
            pista = "Muestra la ubicación de un cofre en el centro pero ¿Qué piso?.";
            break;

        default:
            pista = "Muestra la ubicación de un cofre al sureste pero ¿Qué piso?.";
            break;
        }

        partida.setMensaje(pista);
    }
}