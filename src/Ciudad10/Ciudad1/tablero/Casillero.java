package Ciudad1.tablero;

import Ciudad1.Personaje.Jugador;
import Ciudad1.partida.Partida;

//Representa una posición del mapa

public class Casillero {

    private TipoCasillero tipo;
    private Cofre cofre;

    //POST: crea un casillero vacio

    public Casillero() {
        this.tipo = TipoCasillero.VACIO;
        this.cofre = null;
    }

    public TipoCasillero getTipo() {
        return tipo;
    }

    public Cofre getCofre() {
        return cofre;
    }

    public void setTipo(TipoCasillero tipo) {
        this.tipo = tipo;
    }

    public void setCofre(Cofre cofre) {
        this.cofre = cofre;
    }


    public boolean tieneCofre() {
        return cofre != null;
    }

    public boolean esPared() {
        return tipo == TipoCasillero.PARED;
    }

    public boolean esEscaleraSube() {
        return tipo == TipoCasillero.ESCALERA_SUBE;
    }

    public boolean esEscaleraBaja() {
        return tipo == TipoCasillero.ESCALERA_BAJA;
    }

    public boolean estaVacio() {
        return tipo == TipoCasillero.VACIO;
    }


    //POST: ejecuta la accion correspondiente del casillero

    public void interactuar(Jugador jugador, Partida partida) {
        if (tipo == TipoCasillero.COFRE && cofre != null) {
            partida.abrirCofre();
        } else if (tipo == TipoCasillero.ESCALERA_SUBE) {
            partida.subirPiso();
        } else if (tipo == TipoCasillero.ESCALERA_BAJA) {
            partida.bajarPiso();
        }
    }
}