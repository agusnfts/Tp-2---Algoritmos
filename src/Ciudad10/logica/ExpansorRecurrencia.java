package logica;

import java.util.ArrayList;
import java.util.List;

/**
 * Genera la expansion del arbol de recurrencia nivel por nivel
 * Se utiliza para visualizar como se descompone el problema en subproblemas.
 */
public class ExpansorRecurrencia {

    /**
     * Expande la recurrencia hasta el numero de niveles indicado
     * Pre: recurrencia != null, niveles > 0
     * Post: Lista de nodos con cantidad y tamaño en cada nivel
     */
    public List<NodoExpansion> expandir(Recurrencia recurrencia, int niveles) {
        List<NodoExpansion> lista = new ArrayList<>();
        int nivel = 0;

        while (nivel < niveles) {
            int cantidad = (int) Math.pow(recurrencia.getA(), nivel);
            String tamanio;
            if (nivel == 0) {
                tamanio = "n";                 
            } else {
                tamanio = "n/" + (int) Math.pow(recurrencia.getB(), nivel);
            }

            lista.add(new NodoExpansion(nivel, cantidad, tamanio));
            nivel++;
        }
        return lista;
    }
}