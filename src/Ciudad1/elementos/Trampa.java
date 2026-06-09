package Ciudad1.elementos;

import Ciudad1.Personaje.Jugador;

/**
 * Representa una trampa que se activa
 * inmediatamente al abrir un cofre.
 */
public abstract class Trampa
        extends Elemento {

    /**
     * POST:
     * crea una trampa.
     */
    public Trampa(
            String nombre,
            String descripcion
    ) {

        super(
                nombre,
                descripcion
        );
    }

    @Override
    public abstract void usar(
            Jugador jugador
    );
}
