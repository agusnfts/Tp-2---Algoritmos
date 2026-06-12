package Ciudad3;

import java.util.Random;

public class MatricesEjemplo {

    private static final int[][][] BANCO_DE_LABERINTOS = {
        

        {
            {1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1}
        },

        {
            {1, 1, 1, 0, 0},
            {0, 0, 1, 1, 0},
            {0, 0, 0, 1, 1},
            {0, 0, 0, 0, 1},
            {0, 0, 0, 0, 1}
        },
        
        {
            {1, 1, 1, 1, 1, 0, 0, 0},
            {0, 0, 0, 0, 1, 1, 1, 0},
            {0, 1, 1, 1, 1, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 1, 0},
            {0, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 1}
        },

        {
            {1, 1, 0, 1, 1, 1, 1, 0},
            {0, 1, 0, 1, 0, 0, 1, 0},
            {0, 1, 1, 1, 0, 0, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 1}
        },

        {
            {1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 1, 0, 1},
            {1, 0, 0, 0, 0, 1, 0, 1},
            {1, 1, 1, 1, 0, 1, 0, 1},
            {0, 0, 0, 1, 0, 1, 0, 0},
            {0, 0, 0, 1, 1, 1, 1, 1} 
        },

        {
            {1, 0, 0, 0, 0},
            {1, 1, 1, 0, 0},
            {0, 0, 1, 0, 0},
            {0, 0, 1, 1, 1},
            {0, 0, 0, 0, 1},
            {0, 1, 1, 1, 1},
            {0, 1, 0, 0, 0},
            {0, 1, 1, 1, 1}
        },

        {
            {1, 1, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 1}
        },

        {
            {1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 1, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 0, 1, 0, 1, 0, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {0, 1, 0, 0, 0, 1, 0},
            {1, 1, 1, 1, 0, 1, 1}
        },

        {
            {1, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 1, 0, 0, 1},
            {1, 1, 1, 0, 1, 1, 0, 1},
            {0, 0, 1, 0, 0, 1, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 1}
        },

        {
            {1, 1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 1},
            {0, 1, 1, 1, 1, 1},
            {0, 1, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 1}
        },

        {
            {1, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 0, 0, 0, 0, 0},
            {0, 1, 0, 1, 1, 1, 1, 0},
            {0, 1, 0, 1, 0, 0, 1, 0},
            {1, 1, 0, 1, 0, 0, 1, 0},
            {1, 0, 1, 1, 0, 1, 1, 0},
            {1, 1, 1, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 1, 1, 1}
        }
    };

    public static int[][] obtenerLaberintoAleatorio() {
        Random rand = new Random();
        int indiceAleatorio = rand.nextInt(BANCO_DE_LABERINTOS.length);
        
        int[][] original = BANCO_DE_LABERINTOS[indiceAleatorio];
        int filas = original.length;
        int columnas = original[0].length;
        
        int[][] copia = new int[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                copia[i][j] = original[i][j];
            }
        }
        return copia;
    }

    public static int[] getDimensionesMaximas() {
        int maxF = 0;
        int maxC = 0;
        for (int[][] m : BANCO_DE_LABERINTOS) {
            if (m.length > maxF) maxF = m.length;
            if (m[0].length > maxC) maxC = m[0].length;
        }
        return new int[]{maxF, maxC}; 
    }
}
