package Ciudad5.modelo;

import java.util.ArrayList;

import utiles.ValidacionesUtiles;

public class BusquedaLineal {

    private ArrayList<String> palabras;

    private ArrayList<Integer> lineas;

    private ArrayList<Integer> posiciones;

    public BusquedaLineal() {

        palabras = new ArrayList<>();

        lineas = new ArrayList<>();

        posiciones = new ArrayList<>();
    }

    public void agregar(
            String palabra,
            int linea,
            int posicion
    ) {

        ValidacionesUtiles.esDistintoDeNull(
                palabra,
                "palabra"
        );

        ValidacionesUtiles.validarMayorACero(
                linea,
                "linea"
        );

        ValidacionesUtiles.validarMayorACero(
                posicion,
                "posicion"
        );

        palabras.add(palabra);

        lineas.add(linea);

        posiciones.add(posicion);
    }

    public ResultadoBusqueda buscar(
            String palabra
    ) {

        ValidacionesUtiles.esDistintoDeNull(
                palabra,
                "palabra"
        );

        int operaciones = 0;

        long inicio = System.nanoTime();

        for (int i = 0;
             i < palabras.size();
             i++) {

            operaciones++;

            if (palabras.get(i)
                    .equals(palabra)) {

                long fin =
                        System.nanoTime();

                return new ResultadoBusqueda(
                        lineas.get(i),
                        posiciones.get(i),
                        fin - inicio,
                        operaciones
                );
            }
        }

        return null;
    }
}

