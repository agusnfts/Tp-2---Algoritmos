package Ciudad5.util;

import java.io.BufferedReader;
import java.io.FileReader;

import Ciudad5.modelo.ArbolBusqueda;
import Ciudad5.modelo.BusquedaLineal;

public class LectorArchivo {

    public static void cargarArchivo(
            String ruta,
            ArbolBusqueda arbol,
            BusquedaLineal lista
    ) {

        try {

            BufferedReader br =
                    new BufferedReader(
                            new FileReader(ruta)
                    );

            String linea;

            int nroLinea = 1;

            while((linea = br.readLine())
                    != null) {

                String[] palabras =
                        linea.split(" ");

                for(int i = 0;
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

            br.close();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }
}