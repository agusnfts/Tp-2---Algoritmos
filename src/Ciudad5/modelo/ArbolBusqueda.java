package Ciudad5.modelo;

import utiles.ValidacionesUtiles;

public class ArbolBusqueda {

    private NodoABB raiz;

    private int operaciones;

    /**
     * PRE: palabra != null, linea > 0 y posicion > 0.
     * POST: inserta la palabra en el árbol binario de búsqueda junto
     *       con la línea y posición indicadas.
     */
    public void insertar(
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

        raiz = insertarRec(
                raiz,
                palabra,
                linea,
                posicion
        );
    }

    /**
     * PRE: palabra != null.
     * POST: inserta recursivamente la palabra en la posición
     *       correspondiente del árbol y devuelve la raíz actualizada
     *       del subárbol.
     */
    private NodoABB insertarRec(
            NodoABB actual,
            String palabra,
            int linea,
            int posicion
    ) {

        ValidacionesUtiles.esDistintoDeNull(
                palabra,
                "palabra"
        );

        if (actual == null) {

            return new NodoABB(
                    palabra,
                    linea,
                    posicion
            );
        }

        if (palabra.compareTo(
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

    /**
     * PRE: palabra != null.
     * POST: busca la palabra en el árbol. Si la encuentra,
     *       devuelve un ResultadoBusqueda con la línea,
     *       posición, tiempo empleado y cantidad de operaciones.
     *       Si no la encuentra, devuelve null.
     */
    public ResultadoBusqueda buscar(
            String palabra
    ) {

        ValidacionesUtiles.esDistintoDeNull(
                palabra,
                "palabra"
        );

        operaciones = 0;

        long inicio = System.nanoTime();

        NodoABB resultado =
                buscarRec(
                        raiz,
                        palabra
                );

        long fin = System.nanoTime();

        if (resultado == null) {

            return null;
        }

        return new ResultadoBusqueda(
                resultado.linea,
                resultado.posicion,
                fin - inicio,
                operaciones
        );
    }

    /**
     * PRE: palabra != null.
     * POST: busca recursivamente la palabra dentro del árbol,
     *       incrementando el contador de operaciones y devolviendo
     *       el nodo encontrado o null si no existe.
     */
    private NodoABB buscarRec(
            NodoABB actual,
            String palabra
    ) {

        ValidacionesUtiles.esDistintoDeNull(
                palabra,
                "palabra"
        );

        operaciones++;

        if (actual == null) {

            return null;
        }

        if (actual.palabra.equals(
                palabra
        )) {

            return actual;
        }

        if (palabra.compareTo(
                actual.palabra
        ) < 0) {

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

