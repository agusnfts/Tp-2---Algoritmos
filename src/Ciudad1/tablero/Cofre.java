package Ciudad1.tablero;

import Ciudad1.elementos.Elemento;

/**
 * Representa un cofre del tablero.
 */
public class Cofre {

    // ATRIBUTOS

    private Elemento contenido;

    private boolean abierto;

    /**
     * PRE:
     * contenido != null
     *
     * POST:
     * crea un cofre con contenido.
     */
    public Cofre(
            Elemento contenido
    ) {

        this.contenido = contenido;

        this.abierto = false;
    }

    // ==================
    // GETTERS
    // ==================

    public Elemento getContenido() {

        return this.contenido;
    }

    // ==================
    // CONSULTAS
    // ==================

    public boolean estaAbierto() {

        return this.abierto;
    }

    public boolean tieneContenido() {

        return this.contenido != null;
    }

    // ==================
    // COMPORTAMIENTO
    // ==================

    public void abrir() {

        this.abierto = true;
    }

    public void vaciar() {

        this.contenido = null;
    }

    // ==================
    // TO STRING
    // ==================

    @Override
    public String toString() {

        String texto;

        if (this.contenido != null) {

            texto =
                    "Cofre con "
                    + this.contenido.getNombre();

        } else {

            texto = "Cofre vacio";
        }

        return texto;
    }
}
