package Personaje;

import java.util.ArrayList;
import elementos.Elemento;

/**
 * Administra los elementos obtenidos.
 */
public class Mochila {

    // CONSTANTES

    private static final int MAX_ELEMENTOS = 3;
	
    // ATRIBUTOS

    private ArrayList<Elemento> elementos;

    /**
     * POST:
     * crea una mochila vacía.
     */
    public Mochila() {

        this.elementos =
                new ArrayList<Elemento>();
    }
    

    // METODOS DE COMPORTAMIENTO

    /**
     * PRE:
     * elemento != null
     *
     * POST:
     * si hay lugar en la mochila, agrega el elemento.
     */
    public void agregarElemento(Elemento elemento) {

        if (this.cantidadElementos() < MAX_ELEMENTOS) {

            if (!contieneElemento(elemento.getClass())) {

                this.elementos.add(elemento);
            }
        }
    }
    
    public boolean posicionValida(
            int posicion
    ) {

        return posicion >= 0
                &&
                posicion < elementos.size();
    }
    
    /**
     * POST:
     * devuelve la cantidad de elementos.
     */
    public int cantidadElementos() {
        return this.elementos.size();
    }

    // ==================
    // GETTERS
    // ==================

    /**
     * PRE:
     * posicion válida.
     *
     * POST:
     * devuelve el elemento.
     */
    public Elemento getElemento(int posicion) {

        if (posicion >= 0 && posicion < elementos.size()) {
            return elementos.get(posicion);
        }

        return null;
    }
    
    public String getNombreElemento(int posicion) {

        Elemento e = getElemento(posicion);

        if (e == null) {
            return "---";
        }

        return e.getNombre();
    }
    
    /**
     * POST:
     * devuelve una copia de los elementos.
     */
    public ArrayList<Elemento> getElementos() {
        return new ArrayList<Elemento>(this.elementos);
    }
    
    public boolean contieneElemento(Class<?> tipo) {

        int i = 0;

        while (i < elementos.size()) {

            if (tipo.isInstance(elementos.get(i))) {
                return true;
            }

            i++;
        }

        return false;
    }

 // GETTERS COMPLEJOS

    /**
     * POST:
     * devuelve true si la mochila está llena.
     */
    public boolean estaLlena() {

        return this.cantidadElementos() >= MAX_ELEMENTOS;
    }
    
    

    // ==================
    // TO STRING
    // ==================

    @Override
    public String toString() {
        return "Mochila: " + this.cantidadElementos() + " elementos";
    }
}
