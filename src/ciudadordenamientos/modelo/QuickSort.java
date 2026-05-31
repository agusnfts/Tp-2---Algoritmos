package ciudadordenamientos.modelo;

import java.util.Queue;
import java.util.LinkedList;

public class QuickSort {

    private static Queue<int[]> pasos;

    /**
     * pre: vector != null.
     * post: ordena el vector de menor a mayor utilizando QuickSort.
     *       Devuelve una cola con los estados intermedios generados
     *       durante el proceso de ordenamiento.
     *       El vector recibido queda ordenado.
     */
    public static Queue<int[]> ordenar(int[] vector) {

        if (vector == null) {
            throw new IllegalArgumentException(
                    "El vector no puede ser null");
        }

        pasos = new LinkedList<>();

        pasos.add(vector.clone());

        quickSort(vector, 0, vector.length - 1);

        return pasos;
    }

    private static void quickSort(int[] v, int inicio, int fin) {

        if (inicio < fin) {

            int pivote = particion(v, inicio, fin);

            quickSort(v, inicio, pivote - 1);
            quickSort(v, pivote + 1, fin);
        }
    }

    private static int particion(int[] v, int inicio, int fin) {

        int pivote = v[fin];
        int i = inicio - 1;

        for (int j = inicio; j < fin; j++) {

            if (v[j] < pivote) {

                i++;

                int aux = v[i];
                v[i] = v[j];
                v[j] = aux;

                pasos.add(v.clone());
            }
        }

        int aux = v[i + 1];
        v[i + 1] = v[fin];
        v[fin] = aux;

        pasos.add(v.clone());

        return i + 1;
    }
}
