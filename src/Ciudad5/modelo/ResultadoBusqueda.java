package Ciudad5.modelo;

import utiles.ValidacionesUtiles;

public class ResultadoBusqueda {

    private int linea;

    private int posicion;

    private long tiempo;

    private int operaciones;

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

