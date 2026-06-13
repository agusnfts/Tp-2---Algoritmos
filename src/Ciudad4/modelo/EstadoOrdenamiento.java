package Ciudad4.modelo;

import java.io.Serializable;

/**
 * Representa el estado de una ejecución de ordenamiento.
 * Almacena los datos y el nombre del algoritmo utilizado.
 */

public class EstadoOrdenamiento implements Serializable {

    private int[] datos;
    private String algoritmo;

    /**
     * pre: datos != null.
     *      algoritmo != null.
     *
     * post: se crea un EstadoOrdenamiento que contiene
     *       los datos y el nombre del algoritmo indicados.
     */
    
    public EstadoOrdenamiento(int[] datos, String algoritmo) {

        if (datos == null) {
            throw new IllegalArgumentException(
                    "datos no puede ser null");
        }

        if (algoritmo == null) {
            throw new IllegalArgumentException(
                    "algoritmo no puede ser null");
        }

        this.datos = datos;
        this.algoritmo = algoritmo;
    }

    public int[] getDatos() {
        return datos;
    }

    public String getAlgoritmo() {
        return algoritmo;
    }
}
