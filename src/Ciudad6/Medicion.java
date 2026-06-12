package Ciudad6;

public class Medicion {
 
	private int comparaciones;
	private long nanos;
	private boolean encontrada;
 
	/**
	 * pre: comparaciones >= 0. nanos >= 0.
	 * post: crea una medición de una búsqueda con la cantidad de comparaciones
	 *       hechas, el tiempo promedio en nanosegundos y si la palabra se encontró.
	 */
	public Medicion(int comparaciones, long nanos, boolean encontrada) {
		this.comparaciones = comparaciones;
		this.nanos = nanos;
		this.encontrada = encontrada;
	}
 
	public int getComparaciones() {
		return comparaciones;
	}
 
	public long getNanos() {
		return nanos;
	}
 
	public boolean fueEncontrada() {
		return encontrada;
	}
}
 