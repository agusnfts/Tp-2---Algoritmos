package Ciudad5.modelo;

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

        this.palabra = palabra;

        this.linea = linea;

        this.posicion = posicion;
    }
}