package Ciudad9.modelo;

public class Enemigo extends Personaje {

    private int danio;

    public Enemigo(String nombre, int vida, int danio) {
        super(nombre, vida);
        this.danio = danio;
    }

    public int getDanio() {
        return danio;
    }
}