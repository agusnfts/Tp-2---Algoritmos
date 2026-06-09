package Ciudad9.modelo;

public class Personaje {

    private String nombre;
    private int vida;

    public Personaje(String nombre, int vida) {
        this.nombre = nombre;
        this.vida = vida;
    }

    public void recibirDanio(int danio) {
        vida = Math.max(0, vida - danio);
    }

    public void curar(int cantidad) {
        vida += cantidad;
    }

    public boolean estaVivo() {
        return vida > 0;
    }

    public String getNombre() {
        return nombre;
    }

    public int getVida() {
        return vida;
    }
}