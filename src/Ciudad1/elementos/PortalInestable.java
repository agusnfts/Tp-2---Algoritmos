package elementos;

import Personaje.Jugador;

/**
 * Trampa que teletransporta al jugador.
 */
public class PortalInestable extends Trampa {

    public PortalInestable() {

        super(
                "Portal Inestable",
                "Una anomalía espacial."
        );
    }

    @Override
    public void usar(
            Jugador jugador
    ) {

        jugador.setPosicion(1,1,0);

        System.out.println(
                "Haz sido teletransportado al inicio."
        );
    }
}