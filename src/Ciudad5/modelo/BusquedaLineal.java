package Ciudad5.modelo;

import java.util.ArrayList;

import utiles.ValidacionesUtiles;

import java.util.ArrayList;

public class BusquedaLineal {

    private ArrayList<String> palabras;
    private ArrayList<Integer> lineas;
    private ArrayList<Integer> posiciones;

    public BusquedaLineal() {

        palabras = new ArrayList<>();
        lineas = new ArrayList<>();
        posiciones = new ArrayList<>();
    }

    /**
     * PRE: palabra != null linea > 0 posicion > 0
     *
     * POST:
     * - Se agrega la palabra al final de la lista "palabras"
     * - Se agrega la línea correspondiente en "lineas"
     * - Se agrega la posición correspondiente en "posiciones"
     * - Las tres listas quedan sincronizadas por índice
     */
    public void agregar(
            String palabra,
            int linea,
            int posicion
    ) {

        ValidacionesUtiles.esDistintoDeNull(palabra, "palabra");
        ValidacionesUtiles.validarMayorACero(linea, "linea");
        ValidacionesUtiles.validarMayorACero(posicion, "posicion");

        palabras.add(palabra);
        lineas.add(linea);
        posiciones.add(posicion);
    }

    /**
     * PRE:palabra != null
     *
     *  POST: Si la palabra existe:
     *      retorna un ResultadoBusqueda con:
     *          * línea donde aparece
     *          * posición donde aparece
     *          * tiempo de ejecución de la búsqueda
     *          * cantidad de operaciones realizadas
     *
     * - Si la palabra NO existe:
     *      retorna null
     *
     * - No modifica las estructuras internas
     */
    public ResultadoBusqueda buscar(String palabra) {

        ValidacionesUtiles.esDistintoDeNull(palabra, "palabra");

        int operaciones = 0;
        long inicio = System.nanoTime();

        for (int i = 0; i < palabras.size(); i++) {

            operaciones++;

            if (palabras.get(i).equals(palabra)) {

                long fin = System.nanoTime();

                return new ResultadoBusqueda(
                        lineas.get(i),
                        posiciones.get(i),
                        fin - inicio,
                        operaciones
                );
            }
        }

        return null;
    }
}

