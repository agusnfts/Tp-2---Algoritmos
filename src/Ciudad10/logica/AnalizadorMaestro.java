package logica;

//Clase que implementa el teorema maestro para analizar recurrencias.
//Determina el caso (1, 2 o 3) y devuelve la complejidad asintotica.
public class AnalizadorMaestro {

	/**
     * Analiza una recurrencia y devuelve un reporte detallado.
     * Pre: r != null
     * Post: Devuelve string con caso detectado y complejidad Θ
     */
    public String analizar(Recurrencia recurrencia) {
        double logBA = Math.log(recurrencia.getA()) / Math.log(recurrencia.getB());

        double diferencia = Math.abs(logBA - recurrencia.getK());

        if (diferencia < 0.0001) {
            return "Caso 2\n\n"
                    + "log_b(a) = "
                    + String.format("%.2f", logBA)
                    + "\n\n"
                    + "T(n) = Θ(n^"
                    + recurrencia.getK()
                    + " log n)";
        }

        if (recurrencia.getK() < logBA) {
            return "Caso 1\n\n"
                    + "log_b(a) = "
                    + String.format("%.2f", logBA)
                    + "\n\n"
                    + "T(n) = Θ(n^"
                    + String.format("%.2f", logBA)
                    + ")";
        }

        return "Caso 3\n\n"
                + "log_b(a) = "
                + String.format("%.2f", logBA)
                + "\n\n"
                + "T(n) = Θ(n^"
                + recurrencia.getK()
                + ")";
    }

    /**
     * Devuelve solo el numero de caso segun el teorema maestro.
     * Pre: r != null
     * Post: Retorna 1, 2 o 3
     */
    public int obtenerCaso(Recurrencia recurrencia) {
        double logaritmo = Math.log(recurrencia.getA()) / Math.log(recurrencia.getB());

        double diferencia = Math.abs(logaritmo - recurrencia.getK());

        if (diferencia < 0.0001) {
            return 2;
        }
        if (recurrencia.getK() < logaritmo) {
            return 1;
        }
        return 3;
    }
}