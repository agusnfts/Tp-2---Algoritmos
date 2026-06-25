package Ciudad10.logica;

/**
 * Representa una recurrencia de la forma:
 * T(n) = aT(n/b) + n^k
 */
public class Recurrencia {

    private int a;
    private int b;
    private int k;

    /**
     * PRE: a > 0, b > 1.
     * POST: crea una recurrencia con los parámetros indicados.
     */
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