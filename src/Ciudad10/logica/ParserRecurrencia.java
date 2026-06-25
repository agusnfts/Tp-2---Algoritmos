package Ciudad10.logica;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ParserRecurrencia {

    /**
     * Intenta parsear un texto a una recurrencia.
     * Pre: texto no nulo
     * Post: Retorna objeto recurrencia o null si no es valido
     */
    public Recurrencia parsear(String texto) {
        Recurrencia recurrencia = null;
        texto = texto.replace(" ", "");
        
        // Patrón regex que captura a, b y k (k es opcional)
        Pattern patron = Pattern.compile("T\\(n\\)=(\\d+)T\\(n/(\\d+)\\)\\+n(?:\\^(\\d+))?");
        Matcher matcher = patron.matcher(texto);

        if (matcher.matches()) {
            int a = Integer.parseInt(matcher.group(1));
            int b = Integer.parseInt(matcher.group(2));
            // Si no hay exponente (^k), se asume k = 1
            int k;
            if (matcher.group(3) == null) {
                k = 1;                    
            } else {
                k = Integer.parseInt(matcher.group(3));
            }

            recurrencia = new Recurrencia(a, b, k);
        }
        return recurrencia;
    }
}