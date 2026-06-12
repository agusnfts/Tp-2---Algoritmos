package Ciudad2;

import java.util.ArrayList;
import java.util.List;
import utiles.ValidacionesUtiles;

public class BacktrackingReinas {

	private TableroReinas tablero;
	private List<int[]> pasos;
	private boolean solucionEncontrada;

	/**
	 * pre: tablero != null.
	 * post: inicializa el solver con el tablero recibido y sin pasos registrados.
	 */
	public BacktrackingReinas(TableroReinas tablero) {
		ValidacionesUtiles.esDistintoDeNull(tablero, "tablero");
		this.tablero = tablero;
		this.pasos = new ArrayList<>();
		this.solucionEncontrada = false;
	}

	/**
	 * pre: 0 <= filaInicial < cantidad de reinas. La reina inicial ya está colocada
	 *      en el tablero.
	 * post: coloca las reinas restantes en las filas libres usando backtracking,
	 *       guardando un paso por cada colocación y cada retroceso. Devuelve true si
	 *       encontró una solución.
	 */
	public boolean resolver(int filaInicial) {
		ValidacionesUtiles.validarRangoNumerico(filaInicial, 0, tablero.getCantidadReinas() - 1, "fila inicial");
		solucionEncontrada = false;
		pasos.clear();

		guardarPaso();

		int n = tablero.getCantidadReinas();

		List<Integer> filasLibres = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			if (i != filaInicial) {
				filasLibres.add(i);
			}
		}

		solucionEncontrada = resolverDesde(filasLibres, 0, 1, n);

		return solucionEncontrada;
	}

	/**
	 * pre: filasLibres != null.
	 * post: intenta colocar las reinas restantes a partir de la fila libre indicada.
	 *       Por cada columna segura coloca la reina y sigue en forma recursiva; si no
	 *       llega a una solución, retrocede. Devuelve true si logra colocarlas todas.
	 */
	private boolean resolverDesde(List<Integer> filasLibres, int indiceFilas, int reinasColocadas, int totalReinas) {
		if (reinasColocadas == totalReinas) {
			return true;
		}

		if (indiceFilas >= filasLibres.size()) {
			return false;
		}

		int fila = filasLibres.get(indiceFilas);

		for (int columna = 0; columna < totalReinas; columna++) {
			if (tablero.esPosicionSegura(fila, columna)) {
				tablero.colocarReina(fila, columna);
				guardarPaso();

				if (resolverDesde(filasLibres, indiceFilas + 1, reinasColocadas + 1, totalReinas)) {
					return true;
				}

				tablero.quitarReina(fila);
				guardarPaso();
			}
		}

		return resolverDesde(filasLibres, indiceFilas + 1, reinasColocadas, totalReinas);
	}

	/**
	 * pre: -
	 * post: agrega a la lista de pasos una copia del estado actual del tablero.
	 */
	private void guardarPaso() {
		pasos.add(tablero.getPosiciones());
	}

	/**
	 * pre: -
	 * post: devuelve la lista de pasos registrados durante la resolución.
	 */
	public List<int[]> getPasos() {
		return pasos;
	}

	/**
	 * pre: -
	 * post: devuelve true si la última llamada a resolver encontró solución.
	 */
	public boolean isSolucionEncontrada() {
		return solucionEncontrada;
	}

	/**
	 * pre: -
	 * post: devuelve el tablero con la solución final.
	 */
	public TableroReinas getTablero() {
		return tablero;
	}

	/**
	 * pre: -
	 * post: devuelve cuántos pasos se registraron durante la resolución.
	 */
	public int getCantidadPasos() {
		return pasos.size();
	}
}