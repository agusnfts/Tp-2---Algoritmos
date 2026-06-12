package Ciudad4.modelo;

import java.util.Queue;

import utiles.ValidacionesUtiles;

import java.util.LinkedList;

public class BubbleSort {

    /**
     * pre: vector != null.
     * post: devuelve una cola con los estados generados durante la ejecución
     *       del algoritmo Bubble Sort sobre el vector recibido.
     *       El vector queda ordenado en forma creciente.
     */
    public static Queue<int[]> ordenar(int[] vector) {

        ValidacionesUtiles.esDistintoDeNull(
                vector,
                "vector"
        );

        Queue<int[]> pasos = new LinkedList<>();

        pasos.add(vector.clone());

        for (int i = 0; i < vector.length - 1; i++) {

            for (int j = 0; j < vector.length - i - 1; j++) {

                if (vector[j] > vector[j + 1]) {

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

