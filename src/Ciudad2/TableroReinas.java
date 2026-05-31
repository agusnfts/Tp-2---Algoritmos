package Ciudad2;

/*
 * Representa el tablero del problema N reinas.
 * El tablero es N x N donde N es la cantidad de reinas.
 * Usamos un vector donde el indice es la fila y el valor es la columna de la reina.
 * Si el valor es -1, no hay reina en esa fila.
 *
 * Atributos:
 * - VACIO: constante que indica que no hay reina en una celda
 * - cantidadReinas: cuantas reinas hay que colocar (y tamaño del tablero N x N)
 * - posiciones: vector donde posiciones[fila] = columna de la reina
 */
public class TableroReinas {

    public static final int VACIO = -1;

    private int cantidadReinas;
    private int[] posiciones;

    // Inicializamos el tablero vacio N x N
    public TableroReinas(int cantidadReinas) {
        this.cantidadReinas = cantidadReinas;
        this.posiciones = new int[cantidadReinas];
        limpiarTablero();
    }

    // Constructor de copia, creamos un tablero con el mismo estado que otro
    public TableroReinas(TableroReinas otro) {
        this.cantidadReinas = otro.cantidadReinas;
        this.posiciones = new int[cantidadReinas];
        for (int i = 0; i < cantidadReinas; i++) {
            this.posiciones[i] = otro.posiciones[i];
        }
    }

    // Aqui ponemos todas las filas en VACIO
    public void limpiarTablero() {
        for (int i = 0; i < cantidadReinas; i++) {
            posiciones[i] = VACIO;
        }
    }

    // Colocamos una reina en la fila y columna indicadas
    public void colocarReina(int fila, int columna) {
        posiciones[fila] = columna;
    }

    // Quitamos la reina de la fila indicada
    public void quitarReina(int fila) {
        posiciones[fila] = VACIO;
    }

    // Devuelve true si hay una reina en esa celda
    public boolean hayReina(int fila, int columna) {
        return posiciones[fila] == columna;
    }

    // Verificamos si colocar una reina en (fila, columna) es seguro
    // Revisamos que ninguna reina anterior ataque por columna o diagonal
    public boolean esPosicionSegura(int fila, int columna) {
        for (int filaAnterior = 0; filaAnterior < cantidadReinas; filaAnterior++) {
            int colAnterior = posiciones[filaAnterior];
            if (filaAnterior == fila || colAnterior == VACIO) {
                continue;
            }
            if (colAnterior == columna) {
                return false;
            }
            int distanciaFila = Math.abs(fila - filaAnterior);
            int distanciaColumna = Math.abs(columna - colAnterior);
            if (distanciaFila == distanciaColumna) {
                return false;
            }
        }
        return true;
    }

    // Devuelve la columna de la reina en esa fila, o VACIO si no hay ninguna
    public int getColumnaReina(int fila) {
        return posiciones[fila];
    }

    // Devuelve la cantidad de reinas y el tamaño del tablero
    public int getCantidadReinas() {
        return cantidadReinas;
    }

    // Devuelve una copia del vector de posiciones
    public int[] getPosiciones() {
        int[] copia = new int[cantidadReinas];
        for (int i = 0; i < cantidadReinas; i++) {
            copia[i] = posiciones[i];
        }
        return copia;
    }

    // Representamos el tablero como texto para debug, Q = reina, . = vacio
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int fila = 0; fila < cantidadReinas; fila++) {
            for (int col = 0; col < cantidadReinas; col++) {
                if (posiciones[fila] == col) {
                    sb.append("Q ");
                } else {
                    sb.append(". ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}