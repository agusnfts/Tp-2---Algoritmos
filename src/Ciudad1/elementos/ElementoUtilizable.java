package elementos;

import Personaje.Jugador;

/**
 * Representa un elemento que se guarda
 * en la mochila para usarse después.
 */
public abstract class ElementoUtilizable
        extends Elemento {

    /**
     * POST:
     * crea un elemento utilizable.
     */
    public ElementoUtilizable(
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
