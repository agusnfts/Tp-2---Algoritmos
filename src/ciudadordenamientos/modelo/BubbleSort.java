package ciudadordenamientos.modelo;

import java.util.Queue;
import java.util.LinkedList;

public class BubbleSort {
	
	/**
	 * pre: vector != null.
	 * post: devuelve una cola con los estados generados durante la ejecución
	 *       del algoritmo Bubble Sort sobre el vector recibido.
	 *       El vector queda ordenado en forma creciente.
	 */

    public static Queue<int[]> ordenar(int[] vector) {
    	
    	if (vector == null) {
            throw new IllegalArgumentException(
                    "El vector no puede ser null");
        }

        Queue<int[]> pasos = new LinkedList<>();

        pasos.add(vector.clone());

        for(int i = 0; i < vector.length - 1; i++) {

            for(int j = 0; j < vector.length - i - 1; j++) {

                if(vector[j] > vector[j + 1]) {

                    int aux = vector[j];
                    vector[j] = vector[j + 1];
                    vector[j + 1] = aux;

                    pasos.add(vector.clone());
                }
            }
        }

        return pasos;
    }
}
