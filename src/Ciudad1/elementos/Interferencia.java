package elementos;

import Personaje.Jugador;

/**
 * Trampa que interfiere los equipos.
 */
public class Interferencia extends Trampa {

    public Interferencia() {

        super(
                "Interferencia",
                "Una energia extraña bloquea tus artefactos."
        );
    }

    @Override
    public void usar(
            Jugador jugador
    ) {

        jugador.aplicarInterferencia(
                5
        );

        System.out.println(
                "La vision se reduce a 1 durante 5 turnos."
        );
    }
}