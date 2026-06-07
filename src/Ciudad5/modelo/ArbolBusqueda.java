package Ciudad5.modelo;

public class ArbolBusqueda {

    private NodoABB raiz;

    private int operaciones;

    public void insertar(
            String palabra,
            int linea,
            int posicion
    ) {

        raiz = insertarRec(
                raiz,
                palabra,
                linea,
                posicion
        );
    }

    private NodoABB insertarRec(
            NodoABB actual,
            String palabra,
            int linea,
            int posicion
    ) {

        if(actual == null) {

            return new NodoABB(
                    palabra,
                    linea,
                    posicion
            );
        }

        if(palabra.compareTo(
                actual.palabra) < 0) {

            actual.izquierda =
                    insertarRec(
                            actual.izquierda,
                            palabra,
                            linea,
                            posicion
                    );

        } else {

            actual.derecha =
                    insertarRec(
                            actual.derecha,
                            palabra,
                            linea,
                            posicion
                    );
        }

        return actual;
    }

    public ResultadoBusqueda buscar(
            String palabra
    ) {

        operaciones = 0;

        long inicio = System.nanoTime();

        NodoABB resultado =
                buscarRec(raiz, palabra);

        long fin = System.nanoTime();

        if(resultado == null) {

            return null;
        }

        return new ResultadoBusqueda(
                resultado.linea,
                resultado.posicion,
                fin - inicio,
                operaciones
        );
    }

    private NodoABB buscarRec(
            NodoABB actual,
            String palabra
    ) {

        operaciones++;

        if(actual == null) {

            return null;
        }

        if(actual.palabra.equals(
                palabra)) {

            return actual;
        }

        if(palabra.compareTo(
                actual.palabra) < 0) {

            return buscarRec(
                    actual.izquierda,
                    palabra
            );
        }

        return buscarRec(
                actual.derecha,
                palabra
        );
    }
}
