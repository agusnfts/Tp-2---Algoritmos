package Ciudad3;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import Ciudad3.BitmapViewerConMenu.MenuAction;

/**
 * Clase principal que resuelve un laberinto utilizando el algoritmo de Backtracking.
 * Proporciona una visualización gráfica en tiempo real simulando un hackeo de sistemas.
 */
public class SalidaLaberinto {

    //Configuracion de dimensioes
    static int FILAS;
    static int COLUMNAS;
    
    //Configuracion visual y tiempos
    static final int TAMANO_CELDA = 150;
    static final int PAUSA_MS = 500;

    //Paleta de colores (Temática Hacker)
    static final Color COLOR_FONDO_CIRCUITO = new Color(15, 30, 20);
    static final Color COLOR_RAM_BASE = new Color(25, 80, 45);
    static final Color COLOR_RAM_CHIP = new Color(10, 40, 20);
    static final Color COLOR_PISTAS = new Color(60, 180, 90);
    static final Color COLOR_ESTELA = new Color(150, 150, 0);
    
    static final int GROSOR_ESTELA = 30;
    static final int RADIO_PELOTA = 35;
    
    //Márgenes para mantener el laberinto centrado en la pantalla
    static final int MARGEN_X = 200;
    static final int MARGEN_Y = 100;

    //Indica si se está ejecutando el laberinto
    static boolean hackeoEnProgreso = false;

    public static void main(String[] args) {
        //Matriz base Hardcodeada: 1 representa un camino transitable, 0 representa una pared
        int[][] laberinto = {
            {1, 0, 0, 0, 1, 0},
            {1, 1, 0, 1, 1, 1},
            {1, 0, 1, 1, 0, 1},
            {1, 1, 1, 0, 0, 1}
        };

        //Calcula la dimension de la matriz
        FILAS = laberinto.length;
        COLUMNAS = laberinto[0].length;
        
        //Inicializamos el lienzo (Bitmap) con un tamaño que envuelva todo el laberinto más los márgenes
        Bitmap bmp = new Bitmap((COLUMNAS * TAMANO_CELDA) + (MARGEN_X * 2), (FILAS * TAMANO_CELDA) + (MARGEN_Y * 2));
        
        dibujarMenuPrincipal(bmp);

        //Configuracion de las acciones de los botones inferiores
        List<MenuAction> acciones = new ArrayList<>();
        
        acciones.add(new MenuAction("Empezar Hackeo", () -> {
            
            //Si el proceso ya se está ejecutando, ignoramos el clic para evitar superposiciones
            if (hackeoEnProgreso) {
                return;
            }
            hackeoEnProgreso = true;

            //Delegamos la resolución a un hilo (Thread) secundario para no congelar la interfaz gráfica
            new Thread(() -> {
                //Se crea una matriz limpia en cada intento para rastrear los pasos dados
                int[][] solucion = new int[FILAS][COLUMNAS];
                
                prepararTablero(bmp, laberinto);
                
                //Inicia la recursión desde el punto de origen, y depende si encuentra salida o no
                //brinda el cartel final de Victoria o Derrota
                if (resolverLaberinto(laberinto, 0, 0, 0, 0, solucion, bmp)) {
                    dibujarCartelFinal(bmp, true);
                } else {
                    dibujarCartelFinal(bmp, false);
                }
                
                //Liberamos el botón para permitir futuros intentos sin necesidad de reiniciar la app
                hackeoEnProgreso = false;
                
            }).start();
        }));

        //Cierra la Máquina Virtual de Java
        acciones.add(new MenuAction("Salir", () -> {
            System.exit(0);
        }));

        BitmapViewerConMenu.showBitmapsWithMenu(acciones, bmp);
    }

    /**
     * Dibuja la pantalla de bienvenida con estética cibernética
     */
    public static void dibujarMenuPrincipal(Bitmap bmp) {
        bmp.rellenar(Color.BLACK);
        Font fTitulo = new Font("Courier New", Font.BOLD, 50);
        Font fSub = new Font("Courier New", Font.BOLD, 25);
        
        //Líneas de decoración
        for(int i = 0; i < bmp.getHeight(); i += 10) {
            bmp.drawLine(0, i, 50, i, new Color(0, 50, 0));
        }

        bmp.drawText("CIUDAD N°3:", (bmp.getWidth()/2) - 150, 200, fSub, Color.CYAN, Color.BLACK);
        bmp.drawText("LABERINTO HACKER", (bmp.getWidth()/2) - 250, 300, fTitulo, Color.GREEN, Color.BLACK);
        bmp.drawText("> PRESIONE EL BOTON PARA INICIAR INTRUSION", (bmp.getWidth()/2) - 280, 450, fSub, Color.WHITE, Color.BLACK);
    }

    /**
     * Limpia la pantalla y dibuja la estructura estática del laberinto (paredes y suelo)
     */
    public static void prepararTablero(Bitmap bmp, int[][] laberinto) {
        bmp.rellenar(new Color(30, 30, 30));
        
        for (int fila = 0; fila < FILAS; fila++) {
            for (int col = 0; col < COLUMNAS; col++) {
                if (laberinto[fila][col] == 1) {
                    llenarCuadrado(bmp, (col * TAMANO_CELDA) + MARGEN_X, (fila * TAMANO_CELDA) + MARGEN_Y, TAMANO_CELDA, TAMANO_CELDA, COLOR_FONDO_CIRCUITO);
                } else {
                    dibujarParedHacker(bmp, fila, col);
                }
            }
        }
        
        //Zócalos metálicos para destacar el inicio y final
        dibujarCasillaEspecial(bmp, 0, 0, true, false);
        dibujarCasillaEspecial(bmp, FILAS - 1, COLUMNAS - 1, false, false);
    }

    /**
     * Muestra un cartel central al concluir el algoritmo indicando victoria o derrota.
     */
    public static void dibujarCartelFinal(Bitmap bmp, boolean exito) {
        int centroX = bmp.getWidth() / 2;
        int centroY = bmp.getHeight() / 2;
        int anchoCartel = 700;
        int altoCartel = 400;

        // Fondo semi-transparente y borde del cartel
        llenarCuadrado(bmp, centroX - (anchoCartel/2), centroY - (altoCartel/2), anchoCartel, altoCartel, new Color(0,0,0, 200));
        bmp.drawRectangle(centroX - (anchoCartel/2), centroY - (altoCartel/2), anchoCartel, altoCartel, exito ? Color.GREEN : Color.RED);

        if (exito) {

            //Diseño de victoria

            int skX = centroX;
            int skY = centroY - 50;
            
            //Diseño de craneo (al estilo bandera pirata)
            int grosorHueso = 12;
            int longitudHueso = 50;
            Color colorHueso = Color.GREEN;
            
            for(int i = -grosorHueso/2; i <= grosorHueso/2; i++) {
                bmp.drawLine(skX - longitudHueso, skY - longitudHueso + i, skX + longitudHueso, skY + longitudHueso + i, colorHueso);
                bmp.drawLine(skX + longitudHueso, skY - longitudHueso + i, skX - longitudHueso, skY + longitudHueso + i, colorHueso);
            }
            llenarCirculo(bmp, skX - longitudHueso, skY - longitudHueso, 12, colorHueso);
            llenarCirculo(bmp, skX + longitudHueso, skY + longitudHueso, 12, colorHueso);
            llenarCirculo(bmp, skX + longitudHueso, skY - longitudHueso, 12, colorHueso);
            llenarCirculo(bmp, skX - longitudHueso, skY + longitudHueso, 12, colorHueso);
            
            llenarCirculo(bmp, skX, skY, 40, Color.GREEN); 
            llenarCuadrado(bmp, skX - 20, skY + 20, 8, 30, Color.GREEN); 
            llenarCuadrado(bmp, skX - 3, skY + 20, 8, 30, Color.GREEN);
            llenarCuadrado(bmp, skX + 15, skY + 20, 8, 30, Color.GREEN);
            llenarCirculo(bmp, skX - 15, skY, 10, Color.BLACK); 
            llenarCirculo(bmp, skX + 15, skY, 10, Color.BLACK); 
            
            bmp.drawText("SISTEMAS HACKEADOS CON EXITO", centroX - 280, centroY + 100, new Font("Courier New", Font.BOLD, 30), Color.GREEN, Color.BLACK);
        } else {

            //Diseño de derrota

            bmp.drawText("ANTIVIRUS ACTIVADO", centroX - 200, centroY - 20, new Font("Courier New", Font.BOLD, 35), Color.RED, Color.BLACK);
            bmp.drawText("SISTEMAS FALLANDO....", centroX - 220, centroY + 40, new Font("Courier New", Font.BOLD, 30), Color.RED, Color.BLACK);
        }
    }

    /**
     * Algoritmo de Backtracking principal. Busca el camino explorando recursivamente las 4 direcciones.
     */
    public static boolean resolverLaberinto(int[][] laberinto, int x, int y, int prevX, int prevY, int[][] solucion, Bitmap bmp) {
        
        //CASO BASE: Si llegamos a la esquina inferior derecha, terminamos la búsqueda.
        if (x == FILAS - 1 && y == COLUMNAS - 1 && laberinto[x][y] == 1) {
            solucion[x][y] = 1;
            repintarLaberinto(bmp, laberinto, solucion, x, y);
            return true;
        }

        //Se evalúa si es válido moverse a la casilla actual
        if (esSeguro(laberinto, x, y, solucion)) {
            
            // Marcamos el paso en la matriz de recorrido
            solucion[x][y] = 1;
            repintarLaberinto(bmp, laberinto, solucion, x, y);

            // Búsqueda en profundidad (DFS) en las 4 direcciones cardinales
            if (resolverLaberinto(laberinto, x + 1, y, x, y, solucion, bmp)) return true; // Abajo
            if (resolverLaberinto(laberinto, x, y + 1, x, y, solucion, bmp)) return true; // Derecha
            if (resolverLaberinto(laberinto, x - 1, y, x, y, solucion, bmp)) return true; // Arriba
            if (resolverLaberinto(laberinto, x, y - 1, x, y, solucion, bmp)) return true; // Izquierda

            // Si ningún camino sirvió, hacemos BACKTRACKING: desmarcamos el paso y retrocedemos
            solucion[x][y] = 0;
            repintarLaberinto(bmp, laberinto, solucion, prevX, prevY);
            return false;
        }
        
        return false;
    }

    /**
     * Valida que las coordenadas estén dentro del mapa, que no sea una pared y que no haya pasado por ahí.
     */
    public static boolean esSeguro(int[][] laberinto, int x, int y, int[][] solucion) {
        return (x >= 0 && x < FILAS && y >= 0 && y < COLUMNAS && laberinto[x][y] == 1 && solucion[x][y] == 0);
    }

    /**
     * Redibuja la escena completa (caminos y estelas) en base al estado actual de la matriz solucion.
     * Esto evita fallos visuales y asegura que las conexiones entre celdas se vean perfectas.
     */
    public static void repintarLaberinto(Bitmap bmp, int[][] laberinto, int[][] solucion, int pelotaX, int pelotaY) {
        
        //1.Se limpian todos los pasillos transitables
        for (int fila = 0; fila < FILAS; fila++) {
            for (int col = 0; col < COLUMNAS; col++) {
                if (laberinto[fila][col] == 1) {
                    llenarCuadrado(bmp, (col * TAMANO_CELDA) + MARGEN_X, (fila * TAMANO_CELDA) + MARGEN_Y, TAMANO_CELDA, TAMANO_CELDA, COLOR_FONDO_CIRCUITO);
                }
            }
        }

        //2.Repinta las zonas seguras de inicio y fin
        dibujarCasillaEspecial(bmp, 0, 0, true, false); 
        boolean exito = (pelotaX == FILAS-1 && pelotaY == COLUMNAS-1);
        dibujarCasillaEspecial(bmp, FILAS-1, COLUMNAS-1, false, exito); 

        //3.Dibuja la estela conectando las casillas confirmadas en la matriz solucion
        for (int fila = 0; fila < FILAS; fila++) {
            for (int col = 0; col < COLUMNAS; col++) {
                if (solucion[fila][col] == 1) {
                    int cx = (col * TAMANO_CELDA) + MARGEN_X + (TAMANO_CELDA / 2);
                    int cy = (fila * TAMANO_CELDA) + MARGEN_Y + (TAMANO_CELDA / 2);

                    //Nodo central de la estela
                    llenarCuadrado(bmp, cx - GROSOR_ESTELA/2, cy - GROSOR_ESTELA/2, GROSOR_ESTELA, GROSOR_ESTELA, COLOR_ESTELA);

                    //Conexión hacia la derecha
                    if (col + 1 < COLUMNAS && solucion[fila][col+1] == 1) {
                        llenarCuadrado(bmp, cx, cy - GROSOR_ESTELA/2, TAMANO_CELDA, GROSOR_ESTELA, COLOR_ESTELA);
                    }
                    //Conexión hacia abajo
                    if (fila + 1 < FILAS && solucion[fila+1][col] == 1) {
                        llenarCuadrado(bmp, cx - GROSOR_ESTELA/2, cy, GROSOR_ESTELA, TAMANO_CELDA, COLOR_ESTELA);
                    }
                }
            }
        }

        //4.Posiciona la entidad activa (pelotita amarilla) en su cuadrante actual
        if (pelotaX >= 0 && pelotaY >= 0) {
            int cxActual = (pelotaY * TAMANO_CELDA) + MARGEN_X + (TAMANO_CELDA / 2);
            int cyActual = (pelotaX * TAMANO_CELDA) + MARGEN_Y + (TAMANO_CELDA / 2);
            llenarCirculo(bmp, cxActual, cyActual, RADIO_PELOTA, Color.YELLOW);
        }

        //Pausa para que se pueda apreciar el flujo del algoritmo visualmente
        try { Thread.sleep(PAUSA_MS); } catch (InterruptedException e) {}
    }

    /**
     * Dibuja los zócalos de hardware con luces LED para indicar el punto de entrada o salida.
     */
    public static void dibujarCasillaEspecial(Bitmap bmp, int fila, int col, boolean esInicio, boolean exitoFinal) {
        int x = (col * TAMANO_CELDA) + MARGEN_X;
        int y = (fila * TAMANO_CELDA) + MARGEN_Y;
        int grosor = 15;
        Color colorMetal = new Color(110, 110, 110); 

        //Fondo oscuro y placa interna del puerto de conexión
        Color colorFondoPuerto = new Color(30, 40, 35); 
        llenarCuadrado(bmp, x + grosor, y + grosor, TAMANO_CELDA - (grosor * 2), TAMANO_CELDA - (grosor * 2), colorFondoPuerto);

        Color colorPlaca = new Color(60, 70, 65);
        int margenPlaca = 35;
        llenarCuadrado(bmp, x + margenPlaca, y + margenPlaca, TAMANO_CELDA - (margenPlaca * 2), TAMANO_CELDA - (margenPlaca * 2), colorPlaca);
        
        //Ranuras cruzadas decorativas
        bmp.drawLine(x + (TAMANO_CELDA / 2), y + grosor, x + (TAMANO_CELDA / 2), y + TAMANO_CELDA - grosor, new Color(20, 20, 20));
        bmp.drawLine(x + grosor, y + (TAMANO_CELDA / 2), x + TAMANO_CELDA - grosor, y + (TAMANO_CELDA / 2), new Color(20, 20, 20));

        //Marco de acero perimetral
        llenarCuadrado(bmp, x, y, TAMANO_CELDA, grosor, colorMetal); // Borde superior
        llenarCuadrado(bmp, x, y + TAMANO_CELDA - grosor, TAMANO_CELDA, grosor, colorMetal); // Borde inferior
        llenarCuadrado(bmp, x, y, grosor, TAMANO_CELDA, colorMetal); // Borde izquierdo
        llenarCuadrado(bmp, x + TAMANO_CELDA - grosor, y, grosor, TAMANO_CELDA, colorMetal); // Borde derecho

        //Indicador LED (Verde para inicio o éxito, Rojo para pendiente o error)
        Color colorLuz = (esInicio || exitoFinal) ? new Color(50, 255, 50) : Color.RED;
        llenarCirculo(bmp, x + (grosor / 2), y + (grosor / 2), 6, colorLuz); 
    }

    /**
     * Dibuja un obstáculo simulando una memoria RAM de computadora
     */
    public static void dibujarParedHacker(Bitmap bmp, int fila, int col) {
        int x = (col * TAMANO_CELDA) + MARGEN_X;
        int y = (fila * TAMANO_CELDA) + MARGEN_Y;
        
        llenarCuadrado(bmp, x, y, TAMANO_CELDA, TAMANO_CELDA, COLOR_RAM_BASE);
        
        // Genera las rayas metálicas saliendo de los bordes
        for(int i = 25; i < TAMANO_CELDA; i += 30) {
            bmp.drawLine(x + i, y, x + i, y + 30, COLOR_PISTAS); 
            bmp.drawLine(x + i, y + TAMANO_CELDA - 30, x + i, y + TAMANO_CELDA, COLOR_PISTAS); 
            bmp.drawLine(x, y + i, x + 30, y + i, COLOR_PISTAS); 
            bmp.drawLine(x + TAMANO_CELDA - 30, y + i, x + TAMANO_CELDA, y + i, COLOR_PISTAS); 
        }
        
        //Núcleo o chip central de la RAM
        llenarCuadrado(bmp, x + 35, y + 35, TAMANO_CELDA - 70, TAMANO_CELDA - 70, COLOR_RAM_CHIP);
    }

    //---------FUNCIONES PRIMITIVAS DE DIBUJO-------

    /**
     * Dibuja un cuadrado sólido trazando líneas paralelas horizontales
     */
    public static void llenarCuadrado(Bitmap bmp, int x, int y, int ancho, int alto, Color color) {
        for (int i = 0; i < alto; i++) bmp.drawLine(x, y + i, x + ancho, y + i, color);
    }
    
    /**
     * Dibuja un círculo sólido trazando circunferencias concéntricas hacia el centro
     */
   public static void llenarCirculo(Bitmap bmp, int centerX, int centerY, int radius, Color color) {
        for (int r = radius; r > 0; r--) bmp.drawCircle(centerX, centerY, r, color);
    }
}