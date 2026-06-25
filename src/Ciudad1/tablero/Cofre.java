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

  //POST: devuelve el elemento contenido en el cofre, o null si esta vacio
    public Elemento getContenido() {
        return contenido;
    }
    
  //POST: devuelve true si el cofre ya fue abierto
    public boolean estaAbierto() {
        return abierto;
    }

  //POST: devuelve true si el cofre contiene algun elemento
    public boolean tieneContenido() {
        return contenido != null;
    }

  //POST: marca el cofre como abierto
    public void abrir() {
        this.abierto = true;
    }

  //POST: elimina el contenido del cofre
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