package Ciudad5.modelo;

import utiles.ValidacionesUtiles;

public class NodoABB {

    String palabra;

    int linea;

    int posicion;

    NodoABB izquierda;

    NodoABB derecha;

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
