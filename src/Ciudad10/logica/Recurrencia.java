package Ciudad10.logica;

/**
 * Modelo simple que representa una recurrencia T(n) = aT(n/b) + n^k
 */
public class Recurrencia {

    private int a;
    private int b;
    private int k;

    public Recurrencia(int a, int b, int k) {
        this.a = a;
        this.b = b;
        this.k = k;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public int getK() {
        return k;
    }
}