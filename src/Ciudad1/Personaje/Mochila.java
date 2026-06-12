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

    public Elemento getElemento(int posicion) {
        if (posicionValida(posicion)) {
            return elementos.get(posicion);
        }
        return null;
    }

    public String getNombreElemento(int posicion) {
        Elemento e = getElemento(posicion);
        return (e == null) ? "---" : e.getNombre();
    }

    public ArrayList<Elemento> getElementos() {
        return new ArrayList<>(elementos);
    }

    public boolean contieneElemento(Class<?> tipo) {
        for (Elemento e : elementos) {
            if (tipo.isInstance(e)) {
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
