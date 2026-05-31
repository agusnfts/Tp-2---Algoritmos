package Ciudad2;

import java.util.ArrayList;
import java.util.List;

/*
 * Resuelve el problema de las N reinas usando backtracking recursivo.
 * El tablero es N x N donde N es la cantidad de reinas elegida.
 * La primera reina tiene posicion fija. El algoritmo coloca las restantes
 * en cualquier fila libre del tablero, tanto arriba como abajo de la inicial.
 *
 * Atributos:
 * - tablero: el tablero N x N sobre el que trabajamos
 * - pasos: lista de snapshots del tablero en cada momento, para mostrar el paso a paso en BMP
 * - solucionEncontrada: indica si se encontro una solucion
 */
public class BacktrackingReinas {

    private TableroReinas tablero;
    private List<int[]> pasos;
    private boolean solucionEncontrada;

    // Inicializamos el solver con el tablero recibido
    public BacktrackingReinas(TableroReinas tablero) {
        this.tablero = tablero;
        this.pasos = new ArrayList<>();
        this.solucionEncontrada = false;
    }

    // Arrancamos la resolucion. La posicion inicial ya esta colocada en el tablero.
    // Construimos la lista de filas libres y colocamos las reinas restantes en ellas.
    public boolean resolver(int filaInicial) {
        solucionEncontrada = false;
        pasos.clear();

        // Guardamos el estado inicial como primer paso
        guardarPaso();

        int n = tablero.getCantidadReinas();

        // Construimos la lista de filas libres (todas menos la inicial)
        List<Integer> filasLibres = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (i != filaInicial) {
                filasLibres.add(i);
            }
        }

        // Necesitamos colocar N - 1 reinas mas (la inicial ya esta)
        solucionEncontrada = resolverDesde(filasLibres, 0, 1, n);

        return solucionEncontrada;
    }

    // Aqui realizamos la recursion del backtracking
    // indiceFilas indica en que posicion de filasLibres estamos
    // reinasColocadas lleva la cuenta de cuantas reinas llevamos puestas
    private boolean resolverDesde(List<Integer> filasLibres, int indiceFilas, int reinasColocadas, int totalReinas) {
        // Caso base: ya colocamos todas las reinas
        if (reinasColocadas == totalReinas) {
            return true;
        }

        // Si nos quedamos sin filas libres sin completar, no hay solucion
        if (indiceFilas >= filasLibres.size()) {
            return false;
        }

        int fila = filasLibres.get(indiceFilas);

        // Aqui iteramos las columnas de esta fila buscando una posicion segura
        for (int columna = 0; columna < totalReinas; columna++) {
            if (tablero.esPosicionSegura(fila, columna)) {
                // Colocamos la reina y guardamos el paso
                tablero.colocarReina(fila, columna);
                guardarPaso();

                // Intentamos colocar la siguiente reina en la siguiente fila libre
                if (resolverDesde(filasLibres, indiceFilas + 1, reinasColocadas + 1, totalReinas)) {
                    return true;
                }

                // Si no funciono, quitamos la reina y probamos la siguiente columna
                tablero.quitarReina(fila);
                guardarPaso();
            }
        }

        // Ninguna columna funciono en esta fila, saltamos a la siguiente
        return resolverDesde(filasLibres, indiceFilas + 1, reinasColocadas, totalReinas);
    }

    // Guardamos una copia del estado actual del tablero en la lista de pasos
    private void guardarPaso() {
        pasos.add(tablero.getPosiciones());
    }

    // Devuelve la lista de pasos para mostrar el paso a paso en BMP
    public List<int[]> getPasos() {
        return pasos;
    }

    // Devuelve true si la ultima llamada a resolver() encontro solucion
    public boolean isSolucionEncontrada() {
        return solucionEncontrada;
    }

    // Devuelve el tablero con la solucion final
    public TableroReinas getTablero() {
        return tablero;
    }

    // Devuelve cuantos pasos se registraron durante la resolucion
    public int getCantidadPasos() {
        return pasos.size();
    }
}