package ciudadbusquedas.modelo;

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
