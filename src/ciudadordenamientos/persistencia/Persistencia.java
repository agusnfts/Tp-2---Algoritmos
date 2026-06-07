package ciudadordenamientos.persistencia;

import java.io.ObjectOutputStream;

import ciudadordenamientos.modelo.EstadoOrdenamiento;

import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;

public class Persistencia {

    public static void guardar(EstadoOrdenamiento estado) {

        try {

            ObjectOutputStream out =
                    new ObjectOutputStream(
                            new FileOutputStream("partida.dat")
                    );

            out.writeObject(estado);

            out.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static EstadoOrdenamiento cargar() {

        try {

            ObjectInputStream in =
                    new ObjectInputStream(
                            new FileInputStream("partida.dat")
                    );

            EstadoOrdenamiento estado =
                    (EstadoOrdenamiento) in.readObject();

            in.close();

            return estado;

        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
