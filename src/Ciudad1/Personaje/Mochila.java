package Ciudad1.Personaje;

import java.util.ArrayList;
import Ciudad1.elementos.Elemento;


//Administra los elementos obtenidos por el jugador
public class Mochila {

    private static final int MAX_ELEMENTOS = 3;

    private ArrayList<Elemento> elementos;

    //POST: crea una mochila vacía.
    public Mochila() {
        this.elementos = new ArrayList<>();
    }


    //PRE:  elemento != null
    //POST: agrega el elemento a la mochila si hay espacio y no está duplicado.

    public void agregarElemento(Elemento elemento) {
        if (elemento == null) return;
        if (cantidadElementos() < MAX_ELEMENTOS && !contieneElemento(elemento.getClass())) {
            this.elementos.add(elemento);
        }
    }
    
    public boolean posicionValida(int posicion) {
        return posicion >= 0 && posicion < elementos.size();
    }

    public int cantidadElementos() {
        return elementos.size();
    }
    
    //PRE: posición válida
    //POST: devuelve el elemento ubicado en esa posición o null
    public Elemento getElemento(int posicion) {
        if (posicionValida(posicion)) {
            return elementos.get(posicion);
        }
        return null;
    }
    
    //POST: devuelve el nombre del elemento o "---" si no existe
    public String getNombreElemento(int posicion) {
        Elemento elemento = getElemento(posicion);
        return (elemento == null) ? "---" : elemento.getNombre();
    }

    //POST: devuelve una copia de la lista de elementos
    public ArrayList<Elemento> getElementos() {
        return new ArrayList<>(elementos);
    }

    //POST: devuelve true si existe un elemento del tipo indicado
    public boolean contieneElemento(Class<?> tipo) {
        for (Elemento elemento : elementos) {
            if (tipo.isInstance(elemento)) {
                return true;
            }
        }
        return false;
    }

    public boolean estaLlena() {
        return cantidadElementos() >= MAX_ELEMENTOS;
    }

    @Override
    public String toString() {
        return "Mochila: " + cantidadElementos() + " elementos";
    }
}
