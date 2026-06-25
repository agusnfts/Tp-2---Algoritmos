package Ciudad8.modelo;

import utiles.ValidacionesUtiles;

public class Movimiento {

    private int origen;
    private int destino;

    /**
     * PRE:
     * - origen ∈ [0..2]
     * - destino ∈ [0..2]
     *
     * POST:
     * - Se crea un objeto Movimiento
     * - Se almacenan los valores de origen y destino
     * - El movimiento queda listo para ser usado en la solución de Hanoi
     */
    
    public Movimiento(
            int origen,
            int destino
    ) {

        ValidacionesUtiles.validarRangoNumerico(
                origen,
                0,
                2,
                "origen"
        );

        ValidacionesUtiles.validarRangoNumerico(
                destino,
                0,
                2,
                "destino"
        );

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
