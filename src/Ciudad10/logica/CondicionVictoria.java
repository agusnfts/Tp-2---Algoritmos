package Ciudad10.logica;

/**
 * Clase que lleva el progreso del estudiante en los 3 casos del teorema maestro.
 * Permite saber cuándo se completa la ciudad/minijuego.
 */
public class CondicionVictoria {

    private boolean caso1;
    private boolean caso2;
    private boolean caso3;

    public CondicionVictoria() {
        this.caso1 = false;
        this.caso2 = false;
        this.caso3 = false;
    }

    /**
     * Marca un caso como completado.
     * Pre: caso es 1, 2 o 3
     */
    public void completarCaso(int caso) {
        if (caso == 1) this.caso1 = true;
        else if (caso == 2) this.caso2 = true;
        else if (caso == 3) this.caso3 = true;
    }

    /**
     * Verifica si un caso ya fue completado.
     * Pre: caso es 1, 2 o 3
     */
    public boolean casoCompleto(int caso) {
        if (caso == 1) return caso1;
        if (caso == 2) return caso2;
        return caso3;
    }

    /**
     * Indica si los 3 casos fueron completados.
     */
    public boolean ciudadCompletada() {
        return caso1 && caso2 && caso3;
    }

    /**
     * Devuelve el estado actual de progreso.
     */
    public String estado() {

        String texto;
        texto = "";
        if (caso1) {
            texto = texto + "Caso 1: ✓";
        } else {
            texto = texto + "Caso 1: ✗";
        }

        texto = texto + "\n";
        
        if (caso2) {
            texto = texto + "Caso 2: ✓";
        } else {
            texto = texto + "Caso 2: ✗";
        }

        texto = texto + "\n";

        if (caso3) {
            texto = texto + "Caso 3: ✓";
        } else {
            texto = texto + "Caso 3: ✗";
        }
        
        texto = texto + "\n";
        return texto;
    }
}
