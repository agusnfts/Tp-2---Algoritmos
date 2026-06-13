package Ciudad5.util;

import java.io.BufferedReader;
import java.io.FileReader;

import Ciudad5.modelo.ArbolBusqueda;
import Ciudad5.modelo.BusquedaLineal;
import utiles.ValidacionesUtiles;

public class LectorArchivo {

    public static void cargarArchivo(
            String ruta,
            ArbolBusqueda arbol,
            BusquedaLineal lista
    ) {

        ValidacionesUtiles.esDistintoDeNull(
                ruta,
                "ruta"
        );

        ValidacionesUtiles.esDistintoDeNull(
                arbol,
                "arbol"
        );

        ValidacionesUtiles.esDistintoDeNull(
                lista,
                "lista"
        );

        try (BufferedReader br =
                     new BufferedReader(
                             new FileReader(ruta)
                     )) {

            String linea;

            int nroLinea = 1;

            while ((linea = br.readLine()) != null) {

                String[] palabras =
                        linea.split(" ");

                for (int i = 0;
                     i < palabras.length;
                     i++) {

                    String palabra =
                            palabras[i]
                                    .toLowerCase();

                    arbol.insertar(
                            palabra,
                            nroLinea,
                            i + 1
                    );

                    lista.agregar(
                            palabra,
                            nroLinea,
                            i + 1
                    );
                }

                nroLinea++;
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error al cargar el archivo",
                    e
            );
        }
    }
}
