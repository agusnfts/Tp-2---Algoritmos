package Ciudad6;

import java.util.Queue;
import java.util.LinkedList;

import utiles.ValidacionesUtiles;

public class TablaHash {

	private LinkedList<String>[] tabla;
	private int tamanio;
	private int cantidad;

	/**
	 * pre: tamanio > 1.
	 * post: crea una tabla hash vacía con 'tamanio' buckets, cada uno
	 *       inicializado como una lista vacía. Conviene que sea un número primo.
	 */
	public TablaHash(int tamanio) {
		ValidacionesUtiles.validarMayorAUno(tamanio, "tamanio de la tabla");
		this.tamanio = tamanio;
		this.cantidad = 0;
		this.tabla = (LinkedList<String>[]) new LinkedList[tamanio];
		for (int i = 0; i < tamanio; i++) {
			this.tabla[i] = new LinkedList<String>();
		}
	}

	/**
	 * pre: palabra != null.
	 * post: devuelve la suma de los valores ASCII de todos los caracteres de palabra.
	 */
	public int sumaAscii(String palabra) {
		int suma = 0;
		for (int i = 0; i < palabra.length(); i++) {
			suma = suma + (int) palabra.charAt(i);
		}
		return suma;
	}

	/**
	 * pre: palabra != null. tamanio > 0.
	 * post: devuelve el índice del bucket aplicando el método del módulo sobre
	 *       la suma ASCII (suma % tamanio).
	 */
	public int funcionHash(String palabra) {
		return sumaAscii(palabra) % tamanio;
	}

	/**
	 * pre: palabra != null y no vacía.
	 * post: si la palabra no estaba ya en la tabla, la agrega al final de la cadena
	 *       de su bucket; si ya existía, no la agrega (la clave es única). Devuelve
	 *       una cola con los pasos que describen la operación en el orden en que
	 *       ocurren (cálculo del índice, revisión de duplicados, colisión/inserción).
	 */
	public Queue<PasoHash> insertar(String palabra) {
		ValidacionesUtiles.validarLongitudDeTexto(palabra, 1, null, "palabra");

		Queue<PasoHash> pasos = new LinkedList<>();
		int suma = sumaAscii(palabra);
		int indice = suma % tamanio;

		pasos.add(new PasoHash("Suma ASCII de \"" + palabra + "\" = " + suma,
				indice, -1, PasoHash.Tipo.CALCULO));
		pasos.add(new PasoHash("Indice = " + suma + " % " + tamanio + " = " + indice,
				indice, -1, PasoHash.Tipo.CALCULO));

		boolean huboColision = !tabla[indice].isEmpty();

		LinkedList<String> cadena = tabla[indice];
		for (int i = 0; i < cadena.size(); i++) {
			String actual = cadena.get(i);
			if (actual.equals(palabra)) {
				pasos.add(new PasoHash("La palabra \"" + palabra
						+ "\" ya existe en la posición " + i
						+ ". No se inserta (la clave es única).",
						indice, i, PasoHash.Tipo.YA_EXISTE));
				return pasos;
			}
			pasos.add(new PasoHash("Comparo con \"" + actual
					+ "\": es distinta, sigo revisando duplicados.",
					indice, i, PasoHash.Tipo.COMPARACION));
		}

		cadena.addLast(palabra);
		cantidad++;
		int posicion = cadena.size() - 1;

		if (huboColision) {
			pasos.add(new PasoHash("El bucket " + indice
					+ " ya tenía elementos: COLISION. Se encadena al final.",
					indice, posicion, PasoHash.Tipo.COLISION));
		} else {
			pasos.add(new PasoHash("El bucket " + indice
					+ " estaba vacío. Se inserta directo.",
					indice, posicion, PasoHash.Tipo.INSERTADO));
		}
		return pasos;
	}

	/**
	 * pre: palabra != null y no vacía.
	 * post: devuelve una cola con los pasos de la búsqueda recorriendo la cadena
	 *       del bucket correspondiente, comparando con equals hasta encontrar la
	 *       palabra o agotar la cadena. No modifica la tabla.
	 */
	public Queue<PasoHash> buscar(String palabra) {
		ValidacionesUtiles.validarLongitudDeTexto(palabra, 1, null, "palabra");

		Queue<PasoHash> pasos = new LinkedList<>();
		int suma = sumaAscii(palabra);
		int indice = suma % tamanio;

		pasos.add(new PasoHash("Suma ASCII = " + suma + "  ->  indice = " + indice,
				indice, -1, PasoHash.Tipo.CALCULO));

		LinkedList<String> cadena = tabla[indice];
		for (int i = 0; i < cadena.size(); i++) {
			String actual = cadena.get(i);
			if (actual.equals(palabra)) {
				pasos.add(new PasoHash("Comparo con \"" + actual
						+ "\": coincide. ENCONTRADA en la posición " + i + ".",
						indice, i, PasoHash.Tipo.ENCONTRADO));
				return pasos;
			}
			pasos.add(new PasoHash("Comparo con \"" + actual
					+ "\": no coincide, sigo en la cadena.",
					indice, i, PasoHash.Tipo.COMPARACION));
		}
		pasos.add(new PasoHash("Final de la cadena: la palabra NO está en la tabla.",
				indice, -1, PasoHash.Tipo.NO_ENCONTRADO));
		return pasos;
	}

	/**
	 * pre: palabra != null y no vacía.
	 * post: ejecuta la búsqueda real (sin pasos ni pausas) midiendo cuántas
	 *       comparaciones hace y el tiempo promedio que tarda. Repite la búsqueda
	 *       muchas veces y promedia, porque una sola es tan rápida que el reloj
	 *       casi no la registra. No modifica la tabla.
	 */
	public Medicion medir(String palabra) {
		ValidacionesUtiles.validarLongitudDeTexto(palabra, 1, null, "palabra");

		int indice = funcionHash(palabra);
		LinkedList<String> cadena = tabla[indice];

		int comparaciones = 0;
		boolean encontrada = false;
		int repeticiones = 10000;

		long inicio = System.nanoTime();
		for (int r = 0; r < repeticiones; r++) {
			int comp = 0;
			boolean enc = false;
			for (int i = 0; i < cadena.size(); i++) {
				comp++;
				if (cadena.get(i).equals(palabra)) {
					enc = true;
					break;
				}
			}
			comparaciones = comp;
			encontrada = enc;
		}
		long nanos = (System.nanoTime() - inicio) / repeticiones;

		return new Medicion(comparaciones, nanos, encontrada);
	}

	/**
	 * pre: min >= 0.
	 * post: devuelve cuántos buckets de la tabla tienen al menos 'min' elementos.
	 */
	public int cantidadBucketsConAlMenos(int min) {
		int cuenta = 0;
		for (int i = 0; i < tamanio; i++) {
			if (tabla[i].size() >= min) {
				cuenta++;
			}
		}
		return cuenta;
	}

	/**
	 * pre: -
	 * post: devuelve la cantidad de palabras distintas guardadas en la tabla.
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * pre: -
	 * post: devuelve la cantidad de buckets de la tabla.
	 */
	public int getTamanio() {
		return tamanio;
	}

	/**
	 * pre: 0 <= indice < tamanio.
	 * post: devuelve la lista (cadena) de elementos del bucket indicado.
	 */
	public LinkedList<String> getBucket(int indice) {
		return tabla[indice];
	}
}