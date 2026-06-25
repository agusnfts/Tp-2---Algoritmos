package principal;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import utiles.ValidacionesUtiles;

public class ProgresoJuego implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombreJugador;
    private boolean[] ciudades;

    /**
     * PRE: Ninguna
     *
     * POST:
     * - Se crea un nuevo progreso de juego
     * - Se inicializa el arreglo de ciudades
     * - La Ciudad 1 queda desbloqueada
     * - Las demás ciudades quedan bloqueadas
     */
    
    public ProgresoJuego() {

        ciudades = new boolean[11];

        ciudades[1] = true;
    }

    /**
     * PRE: ciudad ∈ [1..10]
     *
     * POST: La ciudad indicada queda desbloqueada
     */
    
    public void desbloquear(int ciudad) {

        ValidacionesUtiles.validarRangoNumerico(
                ciudad,
                1,
                ciudades.length - 1,
                "ciudad"
        );

        ciudades[ciudad] = true;
    }

    /**
     * PRE: Ninguna
     *
     * POST:
     * - Devuelve true si la ciudad está desbloqueada
     * - Devuelve false si la ciudad está bloqueada o el número es inválido
     * - No modifica el estado del objeto
     */
    
    public boolean estaDesbloqueada(int ciudad) {

        if (ciudad < 1
                || ciudad >= ciudades.length) {

            return false;
        }

        return ciudades[ciudad];
    }

    /**
     * PRE:
     * - nombreJugador != null
     * - longitud(nombreJugador) >= 1
     *
     * POST:
     * - Se actualiza el nombre del jugador
     */
    
    public void setNombreJugador(
            String nombreJugador
    ) {

        ValidacionesUtiles.validarLongitudDeTexto(
                nombreJugador,
                1,
                null,
                "nombre del jugador"
        );

        this.nombreJugador = nombreJugador;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    /**
     * PRE:
     * - nombreJugador != null
     * - longitud(nombreJugador) >= 1
     *
     * POST:
     * - Se crea la carpeta de partidas si no existe
     * - Se serializa el objeto actual en un archivo .dat
     * - El progreso queda almacenado persistentemente
     * - Si ocurre un error se lanza RuntimeException
     */
    
    public void guardar() {

        ValidacionesUtiles.validarLongitudDeTexto(
                nombreJugador,
                1,
                null,
                "nombre del jugador"
        );

        File carpeta =
                new File("partidas");

        if (!carpeta.exists()) {
            carpeta.mkdir();
        }

        try {

            ObjectOutputStream out =
                    new ObjectOutputStream(
                            new FileOutputStream(
                                    "partidas/"
                                            + nombreJugador
                                            + ".dat"
                            )
                    );

            out.writeObject(this);

            out.close();

            System.out.println(
                    "[SAVE] Partida guardada: "
                            + nombreJugador
            );

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

    /**
     * PRE:
     * - nombre != null
     * - longitud(nombre) >= 1
     * - Debe existir una partida guardada con ese nombre
     *
     * POST:
     * - Se carga y devuelve el progreso almacenado
     * - No modifica archivos existentes
     * - Si ocurre un error se lanza RuntimeException
     */
    
    public static ProgresoJuego cargar(
            String nombre
    ) {

        ValidacionesUtiles.validarLongitudDeTexto(
                nombre,
                1,
                null,
                "nombre"
        );

        try {

            ObjectInputStream in =
                    new ObjectInputStream(
                            new FileInputStream(
                                    "partidas/"
                                            + nombre
                                            + ".dat"
                            )
                    );

            ProgresoJuego progreso =
                    (ProgresoJuego) in.readObject();

            in.close();

            System.out.println(
                    "[LOAD] Partida cargada: "
                            + nombre
            );

            return progreso;

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

    /**
     * PRE: Ninguna
     *
     * POST:
     * - Devuelve un arreglo con los nombres de todas las partidas guardadas
     * - Si no existen partidas devuelve un arreglo vacío
     * - No modifica archivos ni el estado del sistema
     */

    public static String[] listarPartidas() {

        File carpeta =
                new File("partidas");

        if (!carpeta.exists()) {
            return new String[0];
        }

        File[] archivos =
                carpeta.listFiles(
                        (dir, name) ->
                                name.endsWith(".dat")
                );

        if (archivos == null) {
            return new String[0];
        }

        String[] nombres =
                new String[archivos.length];

        for (int i = 0;
             i < archivos.length;
             i++) {

            nombres[i] =
                    archivos[i]
                            .getName()
                            .replace(
                                    ".dat",
                                    ""
                            );
        }

        return nombres;
    }

    /**
     * PRE:
     * - nombre != null
     * - longitud(nombre) >= 1
     *
     * POST:
     * - Si existe la partida indicada, el archivo correspondiente es eliminado
     * - Si no existe, no se realiza ninguna acción
     * - No modifica otras partidas almacenadas
     */

    public static void borrar(
            String nombre
    ) {

        ValidacionesUtiles.validarLongitudDeTexto(
                nombre,
                1,
                null,
                "nombre"
        );

        File archivo =
                new File(
                        "partidas/"
                                + nombre
                                + ".dat"
                );

        if (archivo.exists()) {

            archivo.delete();

            System.out.println(
                    "[DELETE] Partida borrada: "
                            + nombre
            );
        }
    }
}