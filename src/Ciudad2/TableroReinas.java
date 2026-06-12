package Ciudad2;

import utiles.ValidacionesUtiles;

public class TableroReinas {

	public static final int VACIO = -1;

	private int cantidadReinas;
	private int[] posiciones;

	/**
	 * pre: cantidadReinas > 0.
	 * post: crea un tablero N x N vacío, donde N es la cantidad de reinas.
	 */
	public TableroReinas(int cantidadReinas) {
		ValidacionesUtiles.validarMayorACero(cantidadReinas, "cantidad de reinas");
		this.cantidadReinas = cantidadReinas;
		this.posiciones = new int[cantidadReinas];
		limpiarTablero();
	}

	/**
	 * pre: otro != null.
	 * post: crea un tablero nuevo con el mismo estado que el tablero recibido.
	 */
	public TableroReinas(TableroReinas otro) {
		ValidacionesUtiles.esDistintoDeNull(otro, "tablero a copiar");
		this.cantidadReinas = otro.cantidadReinas;
		this.posiciones = new int[cantidadReinas];
		for (int i = 0; i < cantidadReinas; i++) {
			this.posiciones[i] = otro.posiciones[i];
		}
	}

	/**
	 * pre: -
	 * post: deja todas las filas del tablero sin reina (en VACIO).
	 */
	public void limpiarTablero() {
		for (int i = 0; i < cantidadReinas; i++) {
			posiciones[i] = VACIO;
		}
	}

	/**
	 * pre: 0 <= fila < cantidadReinas. 0 <= columna < cantidadReinas.
	 * post: coloca una reina en la fila y columna indicadas.
	 */
	public void colocarReina(int fila, int columna) {
		ValidacionesUtiles.validarRangoNumerico(fila, 0, cantidadReinas - 1, "fila");
		ValidacionesUtiles.validarRangoNumerico(columna, 0, cantidadReinas - 1, "columna");
		posiciones[fila] = columna;
	}

	/**
	 * pre: 0 <= fila < cantidadReinas.
	 * post: quita la reina de la fila indicada.
	 */
	public void quitarReina(int fila) {
		ValidacionesUtiles.validarRangoNumerico(fila, 0, cantidadReinas - 1, "fila");
		posiciones[fila] = VACIO;
	}

	/**
	 * pre: 0 <= fila < cantidadReinas.
	 * post: devuelve true si hay una reina en la celda (fila, columna).
	 */
	public boolean hayReina(int fila, int columna) {
		return posiciones[fila] == columna;
	}

	/**
	 * pre: 0 <= fila < cantidadReinas. 0 <= columna < cantidadReinas.
	 * post: devuelve true si colocar una reina en (fila, columna) no es atacada por
	 *       ninguna reina ya colocada, ni por columna ni por diagonal.
	 */
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

	/**
	 * pre: 0 <= fila < cantidadReinas.
	 * post: devuelve la columna de la reina en esa fila, o VACIO si no hay ninguna.
	 */
	public int getColumnaReina(int fila) {
		return posiciones[fila];
	}

	/**
	 * pre: -
	 * post: devuelve la cantidad de reinas, que es también el tamaño N del tablero.
	 */
	public int getCantidadReinas() {
		return cantidadReinas;
	}

	/**
	 * pre: -
	 * post: devuelve una copia del vector de posiciones (fila -> columna).
	 */
	public int[] getPosiciones() {
		int[] copia = new int[cantidadReinas];
		for (int i = 0; i < cantidadReinas; i++) {
			copia[i] = posiciones[i];
		}
		return copia;
	}

	/**
	 * pre: -
	 * post: devuelve una representación en texto del tablero (Q = reina, . = vacío).
	 */
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