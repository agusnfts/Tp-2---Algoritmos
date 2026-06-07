package principal;

public class Jugador {

    private String nombre;
    private int puntos;

    public Jugador(String nombre) {

        this.nombre = nombre;
        this.puntos = 0;
    }

    public void sumarPuntos(int puntos) {

        this.puntos += puntos;
    }

    public int getPuntos() {
        return puntos;
    }
}
