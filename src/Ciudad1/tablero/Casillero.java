package Ciudad1.tablero;

import Ciudad1.Personaje.Jugador;
import Ciudad1.partida.Partida;

/**
 * Representa una posición del mapa.
 */
public class Casillero {

    // ATRIBUTOS

    private TipoCasillero tipo;

    private Cofre cofre;

    /**
     * POST:
     * crea un casillero vacío.
     */
    public Casillero() {

        this.tipo = TipoCasillero.VACIO;

        this.cofre = null;
    }

    // ==================
    // GETTERS
    // ==================

    public TipoCasillero getTipo() {

        return this.tipo;
    }

    public Cofre getCofre() {

        return this.cofre;
    }

    // ==================
    // SETTERS
    // ==================

    public void setTipo(
            TipoCasillero tipo
    ) {

        this.tipo = tipo;
    }

    public void setCofre(
            Cofre cofre
    ) {

        this.cofre = cofre;
    }


    // ==================
    // ESTADO
    // ==================

    public boolean tieneCofre() {
        return this.cofre != null;
    }

    // ==================
    //INTERACCIÓN
    // ==================

    /**
     * POST:
     * ejecuta la acción del casillero sobre el jugador.
     */
    public void interactuar(Jugador jugador, Partida partida) {

        if (tipo == TipoCasillero.COFRE && cofre != null) {
            partida.abrirCofre();
        }

        else if (tipo == TipoCasillero.ESCALERA_SUBE) {
            partida.subirPiso();
        }

        else if (tipo == TipoCasillero.ESCALERA_BAJA) {
            partida.bajarPiso();
        }
     
        // VACIO o PARED -> no hace nada
    }   
    
    // ==================
    // CONSULTAS
    // ==================

    public boolean esPared() {

        return this.tipo == TipoCasillero.PARED;
    }

    public boolean esEscaleraSube() {

        return this.tipo == TipoCasillero.ESCALERA_SUBE;
    }

    public boolean esEscaleraBaja() {

        return this.tipo == TipoCasillero.ESCALERA_BAJA;
    }

    public boolean estaVacio() {

        return this.tipo == TipoCasillero.VACIO;
    }
}