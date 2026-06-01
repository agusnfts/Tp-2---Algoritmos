package elementos;

import Personaje.Jugador;

/**
 * Representa un elemento utilizable por el jugador.
 */
public abstract class Elemento {

    // ATRIBUTOS

    private String nombre;

    private String descripcion;

    // ==================
    // CONSTRUCTOR
    // ==================

    /**
     * PRE:
     * nombre != null
     * descripcion != null
     *
     * POST:
     * crea un elemento.
     */
    public Elemento(
            String nombre,
            String descripcion
    ) {

        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // ==================
    // GETTERS SIMPLES
    // ==================

    /**
     * POST:
     * devuelve el nombre.
     */
    public String getNombre() {

        return this.nombre;
    }

    /**
     * POST:
     * devuelve la descripción.
     */
    public String getDescripcion() {

        return this.descripcion;
    }
    

    // ==================
    // METODO ABSTRACTO
    // ==================

    /**
     * PRE:
     * partida != null
     *
     * POST:
     * aplica el efecto del elemento.
     */
    public abstract void usar(
            Jugador jugador
    );
    // ==================
    // TO STRING
    // ==================

    @Override
    public String toString() {

        return this.nombre;
    }
}