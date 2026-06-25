package Ciudad5.modelo;

import utiles.ValidacionesUtiles;

public class ResultadoBusqueda {

    private int linea;

    private int posicion;

    private long tiempo;

    private int operaciones;

    /**
     * PRE:
     * - linea > 0
     * - posicion > 0
     * - tiempo >= 0
     * - operaciones >= 0
     *
     * POST:
     * - Se crea un objeto ResultadoBusqueda
     * - Se almacenan los datos de la búsqueda:
     *      * línea donde se encontró la palabra
     *      * posición donde se encontró la palabra
     *      * tiempo de ejecución de la búsqueda
     *      * cantidad de operaciones realizadas
     */
    
    public ResultadoBusqueda(
            int linea,
            int posicion,
            long tiempo,
            int operaciones
    ) {

        ValidacionesUtiles.validarMayorACero(
                linea,
                "linea"
        );

        ValidacionesUtiles.validarMayorACero(
                posicion,
                "posicion"
        );

        ValidacionesUtiles.validarMayorOIgualACero(
                tiempo,
                "tiempo"
        );

        ValidacionesUtiles.validarMayorOIgualACero(
                operaciones,
                "operaciones"
        );

        this.linea = linea;
        this.posicion = posicion;
        this.tiempo = tiempo;
        this.operaciones = operaciones;
    }

    public int getLinea() {
        return linea;
    }

    public int getPosicion() {
        return posicion;
    }

    public long getTiempo() {
        return tiempo;
    }

    public int getOperaciones() {
        return operaciones;
    }
}

