package Ciudad8.modelo;

import java.util.ArrayList;
import java.util.List;

import utiles.ValidacionesUtiles;
public class Hanoi {

    private List<Movimiento> movimientos;

    public Hanoi() {

        movimientos = new ArrayList<>();
    }

    public List<Movimiento> resolver(int cantidadDiscos) {

        ValidacionesUtiles.validarMayorACero(
                cantidadDiscos,
                "cantidadDiscos"
        );

        movimientos.clear();

        mover(
                cantidadDiscos,
                0,
                2,
                1
        );

        return movimientos;
    }

    private void mover(
            int discos,
            int origen,
            int destino,
            int auxiliar
    ) {

        ValidacionesUtiles.validarMayorACero(
                discos,
                "discos"
        );

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

        ValidacionesUtiles.validarRangoNumerico(
                auxiliar,
                0,
                2,
                "auxiliar"
        );

        if (discos == 1) {

            movimientos.add(
                    new Movimiento(
                            origen,
                            destino
                    )
            );

            return;
        }

        mover(
                discos - 1,
                origen,
                auxiliar,
                destino
        );

        movimientos.add(
                new Movimiento(
                        origen,
                        destino
                )
        );

        mover(
                discos - 1,
                auxiliar,
                destino,
                origen
        );
    }
}
