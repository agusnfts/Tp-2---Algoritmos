package principal;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProgresoJuego implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombreJugador;
    private boolean[] ciudades;

    public ProgresoJuego() {

        ciudades = new boolean[11];

        ciudades[1] = true; // Ciudad 1 y 2 desbloqueada por defecto
        ciudades[2]=true;
    }

    public void desbloquear(int ciudad) {

        if (ciudad >= 1 && ciudad < ciudades.length) {
            ciudades[ciudad] = true;
        }
    }

    public boolean estaDesbloqueada(int ciudad) {

        if (ciudad < 1 || ciudad >= ciudades.length) {
            return false;
        }

        return ciudades[ciudad];
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    // ==========================
    // GUARDAR
    // ==========================

    public void guardar() {

        try {

            File carpeta = new File("partidas");

            if (!carpeta.exists()) {
                carpeta.mkdir();
            }

            ObjectOutputStream out =
                    new ObjectOutputStream(
                            new FileOutputStream(
                                    "partidas/" + nombreJugador + ".dat"
                            )
                    );

            out.writeObject(this);
            out.close();

            System.out.println("[SAVE] Partida guardada: " + nombreJugador);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // ==========================
    // CARGAR
    // ==========================

    public static ProgresoJuego cargar(String nombre) {

    	
        try {

            ObjectInputStream in =
                    new ObjectInputStream(
                            new FileInputStream(
                                    "partidas/" + nombre + ".dat"
                            )
                    );

            ProgresoJuego progreso =
                    (ProgresoJuego) in.readObject();

            in.close();

            System.out.println("[LOAD] Partida cargada: " + nombre);

            return progreso;

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }

    // ==========================
    // LISTAR PARTIDAS
    // ==========================

    public static String[] listarPartidas() {

        File carpeta = new File("partidas");

        if (!carpeta.exists()) {
            return new String[0];
        }

        File[] archivos =
                carpeta.listFiles((dir, name) ->
                        name.endsWith(".dat"));

        if (archivos == null) {
            return new String[0];
        }

        String[] nombres =
                new String[archivos.length];

        for (int i = 0; i < archivos.length; i++) {

            nombres[i] =
                    archivos[i]
                            .getName()
                            .replace(".dat", "");
        }

        return nombres;
    }

    // ==========================
    // BORRAR
    // ==========================

    public static void borrar(String nombre) {

        File archivo =
                new File("partidas/" + nombre + ".dat");

        if (archivo.exists()) {

            archivo.delete();

            System.out.println("[DELETE] Partida borrada: " + nombre);
        }
    }
}