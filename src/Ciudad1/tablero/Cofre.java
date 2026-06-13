package Ciudad1.tablero;

import Ciudad1.elementos.Elemento;


//Representa un cofre del tablero.
public class Cofre {

    // ATRIBUTOS
    private Elemento contenido;
    private boolean abierto;

    //PRE:  contenido puede ser null
    //POST: crea un cofre con el contenido indicado
    public Cofre(Elemento contenido) {
        this.contenido = contenido;
        this.abierto = false;
    }

    public Elemento getContenido() {
        return contenido;
    }

    public boolean estaAbierto() {
        return abierto;
    }

    public boolean tieneContenido() {
        return contenido != null;
    }

    public void abrir() {
        this.abierto = true;
    }

    public void vaciar() {
        this.contenido = null;
    }

    @Override
    public String toString() {
        if (contenido != null) {
            return "Cofre con " + contenido.getNombre();
        } else {
            return "Cofre vacio";
        }
    }
}