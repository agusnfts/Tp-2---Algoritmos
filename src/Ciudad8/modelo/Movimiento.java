package Ciudad8.modelo;

public class Movimiento {

    private int origen;
    private int destino;

    public Movimiento(int origen, int destino) {
        this.origen = origen;
        this.destino = destino;
    }

    public int getOrigen() {
        return origen;
    }

    public int getDestino() {
        return destino;
    }
}
