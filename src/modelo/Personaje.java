package modelo;

public class Personaje {

    private String nombre;
    private int vida;

    public Personaje(String nombre, int vida) {
        this.nombre = nombre;
        this.vida = vida;
    }

    public void recibirDanio(int danio) {
        vida -= danio;

        if(vida < 0) {
            vida = 0;
        }
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

    //agrego metodo

    public void curar(int cantidad) {
    vida += cantidad;
}
}