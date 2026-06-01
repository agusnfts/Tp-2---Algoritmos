package elementos;

import Personaje.Jugador;

/**
 * Elemento que aumenta temporalmente
 * la visión del jugador.
 */
public class AntorchaPerdida extends ElementoUtilizable {

    // CONSTANTES

    /**
     * POST:
     * crea una Antorcha Perdida.
     */
    public AntorchaPerdida() {

        super(
                "Antorcha Perdida",
                "Aumenta la visión temporalmente."
        );
    }

    /**
     * PRE:
     * jugador != null
     *
     * POST:
     * aumenta la visión del jugador.
     */
    @Override
    public void usar(Jugador jugador) {

        jugador.mejorarVision();

        jugador.getPartida().setMensaje(
            "La antorcha ya está iluminando tu camino"
        );
    }
}
