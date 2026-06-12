package Ciudad8.modelo;

import java.util.ArrayList;
import java.util.List;

import utiles.ValidacionesUtiles;
public class Hanoi {

    private List<Movimiento> movimientos;

    public Hanoi() {
        movimientos = new ArrayList<>();
    }

    /**
     * PRE:
     * - cantidadDiscos > 0
     *
     * POST:
     * - Se genera la solución completa del problema de las Torres de Hanoi
     * - Se limpia la lista de movimientos anteriores
     * - Se almacenan todos los movimientos necesarios para resolver el problema
     * - Se devuelve la lista de movimientos en orden correcto
     */
    public List<Movimiento> resolver(int cantidadDiscos) {

        ValidacionesUtiles.validarMayorACero(cantidadDiscos, "cantidadDiscos");

        movimientos.clear();

        mover(cantidadDiscos, 0, 2, 1);

        return movimientos;
    }

    /**
     * PRE:
     * - discos > 0
     * - origen ∈ [0..2]
     * - destino ∈ [0..2]
     * - auxiliar ∈ [0..2]
     *
     * POST:
     * - Resuelve recursivamente el problema de Hanoi
     * - Agrega los movimientos necesarios a la lista "movimientos"
     * - Mantiene la correcta secuencia de traslados de discos
     */
    private void mover(
            int discos,
            int origen,
            int destino,
            int auxiliar
    ) {

        ValidacionesUtiles.validarMayorACero(discos, "discos");

        ValidacionesUtiles.validarRangoNumerico(origen, 0, 2, "origen");
        ValidacionesUtiles.validarRangoNumerico(destino, 0, 2, "destino");
        ValidacionesUtiles.validarRangoNumerico(auxiliar, 0, 2, "auxiliar");

        if (discos == 1) {

            movimientos.add(new Movimiento(origen, destino));
            return;
        }

        mover(discos - 1, origen, auxiliar, destino);

        movimientos.add(new Movimiento(origen, destino));

        mover(discos - 1, auxiliar, destino, origen);
    }
}
