package Ciudad1.elementos;

import Ciudad1.Personaje.Jugador;

/**
 * Te permite saber la 
 * casilla en la que te encuentras
 */

public class AmuletoMistico extends ElementoUtilizable {
	/**
     * POST:
     * crea una AmuletoMistico.
     */
    public AmuletoMistico() {
        super("Amuleto Místico", "Revela tu posición actual");
    }
    /**
     * PRE:
     * jugador != null
     *
     * POST:
     * info
     */
    
    @Override
    public void usar(Jugador jugador) {

        jugador.getPartida().setMensaje(
            "Estás en (" +
            jugador.getPosX() + ", " +
            jugador.getPosY() + ", " +
            jugador.getPosZ() + ")"
        );
    }
}
