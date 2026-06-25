package Ciudad10.logica;

/**
 * Representa un nodo en la expansion del arbol de recurrencia.
 */
public class NodoExpansion {

    private final int nivel;
    private final int cantidadProblemas;
    private final String tamanio;

    /**
     * PRE:
     * nivel >= 0
     * cantidadProblemas > 0
     * POST: crea un nodo de la expansión del árbol.
     */
    public NodoExpansion(int nivel, int cantidadProblemas, String tamanio) {
        this.nivel = nivel;
        this.cantidadProblemas = cantidadProblemas;
        this.tamanio = tamanio;
    }

    public int getNivel() { return nivel; }
    public int getCantidadProblemas() { return cantidadProblemas; }
    public String getTamanio() { return tamanio; }
}