package Ciudad5.modelo;

import utiles.ValidacionesUtiles;

public class NodoABB {

    String palabra;

    int linea;

    int posicion;

    NodoABB izquierda;

    NodoABB derecha;

    /**
     * PRE:
     * - palabra != null
     * - linea > 0
     * - posicion > 0
     *
     * POST:
     * - Se crea un nodo del árbol binario de búsqueda (ABB)
     * - Se inicializan los atributos palabra, linea y posicion
     * - Los punteros izquierda y derecha quedan en null
     */
    
    public NodoABB(
            String palabra,
            int linea,
            int posicion
    ) {

        ValidacionesUtiles.esDistintoDeNull(
                palabra,
                "palabra"
        );

        ValidacionesUtiles.validarMayorACero(
                linea,
                "linea"
        );

        ValidacionesUtiles.validarMayorACero(
                posicion,
                "posicion"
        );

        this.palabra = palabra;

        this.linea = linea;

        this.posicion = posicion;
    }
}
