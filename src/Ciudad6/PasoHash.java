package Ciudad6;

public class PasoHash {

	public enum Tipo {
		CALCULO, COMPARACION, COLISION, ENCONTRADO, NO_ENCONTRADO, INSERTADO, YA_EXISTE
	}

	private String descripcion;
	private int bucket;
	private int posicionEnCadena;
	private Tipo tipo;

	/**
	 * pre: descripcion != null. tipo != null. bucket >= -1.
	 * post: crea un paso de la visualización con su descripción, el bucket
	 *       involucrado, la posición dentro de la cadena (-1 si no aplica) y el
	 *       tipo de paso, que la vista usa para elegir el color del resaltado.
	 */
	public PasoHash(String descripcion, int bucket, int posicionEnCadena, Tipo tipo) {
		this.descripcion = descripcion;
		this.bucket = bucket;
		this.posicionEnCadena = posicionEnCadena;
		this.tipo = tipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public int getBucket() {
		return bucket;
	}

	public int getPosicionEnCadena() {
		return posicionEnCadena;
	}

	public Tipo getTipo() {
		return tipo;
	}
}