package Ciudad3;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import Ciudad3.bitmap.Bitmap;
import Ciudad3.bitmap.BitmapViewerConMenu;
import Ciudad3.bitmap.BitmapViewerConMenu.MenuAction;
import principal.ProgresoJuego;


/**
 * Clase principal que resuelve un laberinto utilizando el algoritmo de Backtracking.
 * Proporciona una visualización gráfica simulando un hackeo de sistemas.
 */
public class SalidaLaberinto {

    //Inicializacion dimensiones, visual y tiempos, paleta de colores y margenes

    static int FILAS;
    static int COLUMNAS;
    
    static final int TAMANO_CELDA = 150;
    static final int PAUSA_MS = 500;

    static final Color COLOR_FONDO_CIRCUITO = new Color(15, 30, 20);
    static final Color COLOR_RAM_BASE = new Color(25, 80, 45);
    static final Color COLOR_RAM_CHIP = new Color(10, 40, 20);
    static final Color COLOR_PISTAS = new Color(60, 180, 90);
    static final Color COLOR_ESTELA = new Color(150, 150, 0);
    static final int GROSOR_ESTELA = 30;
    static final int RADIO_PELOTA = 35;
    
    static int MARGEN_X = 200;
    static int MARGEN_Y = 100;


    //Variables que se usaran para saber si está en progreso el laberinto
    
    static boolean hackeoEnProgreso = false;
    static boolean abortarHackeo = false;
    static boolean ciudad3Completada = false;
    
    private static ProgresoJuego progreso;

    /**
     * Recibe el progreso actual del juego para poder
     * desbloquear la siguiente ciudad al completar el laberinto.
     */
    public static void setProgreso(ProgresoJuego p) {
        progreso = p;

        // silencia la victoria para que no le vuelva a salir el cartel
        if (progreso != null && progreso.estaDesbloqueada(8)) {
            ciudad3Completada = true;
        }
    }
    
    public static void main(String[] args) {
        
        //Calcula la dimension de la matriz
        int[] limitesMaximos = MatricesEjemplo.getDimensionesMaximas();
        int maxFilas = limitesMaximos[0];
        int maxColumnas = limitesMaximos[1];
        
        //Inicializa Bitmap con un tamaño que cubre todo el laberinto más los márgenes base
        Bitmap bmp = new Bitmap((maxColumnas * TAMANO_CELDA) + (200 * 2), (maxFilas * TAMANO_CELDA) + (100 * 2));
        
        dibujarMenuPrincipal(bmp);

        /**
         * BOTONES
         */
        List<MenuAction> acciones = new ArrayList<>();
        
        //Empezar hackeo:
        //--------------
        acciones.add(new MenuAction("Empezar Hackeo", () -> {
            
            //Si ya se está ejecutando, ignora el click
            if (hackeoEnProgreso) {
                return;
            }
            hackeoEnProgreso = true;
            abortarHackeo = false;

            //Resolucion de laberinto a un Thread (para no congelar interfaz)
            new Thread(() -> {
                
                //Obtiene laberinto aleatorio y recalcula margenes para dibujar el laberinto en el centro
                int[][] laberintoRandom = MatricesEjemplo.obtenerLaberintoAleatorio();
                FILAS = laberintoRandom.length;
                COLUMNAS = laberintoRandom[0].length;
                
                MARGEN_X = (bmp.getWidth() - (COLUMNAS * TAMANO_CELDA)) / 2;
                MARGEN_Y = (bmp.getHeight() - (FILAS * TAMANO_CELDA)) / 2;

                /**
                 * Empieza el rastreo
                 */
                int[][] solucion = new int[FILAS][COLUMNAS];
                prepararTablero(bmp, laberintoRandom);
                
                //Inicia recursión en el origen, y depende si encuentra salida o no
                //sale el cartel final de Victoria, Derrota o ejecucion detenida
                if (resolverLaberinto(laberintoRandom, 0, 0, 0, 0, solucion, bmp)) {

                    //Si se termina con exito, se desbloquea la siguiente ciudad
                    if (!abortarHackeo) {

                        dibujarCartelFinal(bmp, true);



                        if (progreso != null && !ciudad3Completada) {
                            progreso.desbloquear(4);
                            progreso.guardar();

                            ciudad3Completada=true;

                            System.out.println("[SAVE] Ciudad 4 desbloqueada");

                            javax.swing.SwingUtilities.invokeLater(() -> {
                                javax.swing.JOptionPane.showMessageDialog(null, 
                                    "¡Sistemas hackeados con éxito!\nHas completado esta ciudad y si lo deseas puedes avanzar a la siguiente.", 
                                    "¡Ciudad Completada!", 
                                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
                            });
                        }
                    }

                } else {

                    // Verifica si termina por no haber salida o si se apretó abortar
                    if (abortarHackeo) {
                        dibujarCartelAbortado(bmp);
                    } else {
                        dibujarCartelFinal(bmp, false);
                    }
                }
                
                //Prepara para jugar denuevo
                hackeoEnProgreso = false;
                
            }).start();
        }));

        //Abortar ejecución actual:
        //------------------------
        acciones.add(new MenuAction("Abortar", () -> {
            if (hackeoEnProgreso) {
                abortarHackeo = true;
            }
        }));

        //Salir:
        //-----
        //Cierra ventana del laberinto y recarga el mapa principal
        acciones.add(new MenuAction("Salir", () -> {
            //Recprre las ventanas abiertas de la aplicacion y si encuentra la nuestra, la cierra
            for (java.awt.Window window : java.awt.Window.getWindows()) {
                if (window instanceof javax.swing.JFrame) {
                    javax.swing.JFrame frameWindow = (javax.swing.JFrame) window;
                    
                    if ("Visualizador de Bitmaps".equals(frameWindow.getTitle())) {
                        frameWindow.dispose(); 
                    } else if (frameWindow.isVisible()) {
                        // Si hay una ventana visible diferente y existe progreso,
                        // cargar panel del mapa en ese frame
                        if (progreso != null) {
                            principal.PanelMapa nuevoMapa = new principal.PanelMapa(progreso);
                            nuevoMapa.setFrame(frameWindow);
                            
                            frameWindow.setContentPane(nuevoMapa);
                            frameWindow.revalidate();
                            frameWindow.repaint();
                        }
                    }
                }
            }
        }));

        BitmapViewerConMenu.showBitmapsWithMenu(acciones, bmp);
    }

    /**
     * Dibuja la pantalla de bienvenida (decoracion)
     */
    public static void dibujarMenuPrincipal(Bitmap bmp) {
        bmp.rellenar(Color.BLACK);
        Font fTitulo = new Font("Courier New", Font.BOLD, 50);
        Font fSub = new Font("Courier New", Font.BOLD, 25);
        
        for(int i = 0; i < bmp.getHeight(); i += 10) {
            bmp.drawLine(0, i, 50, i, new Color(0, 50, 0));
        }

        bmp.drawText("CIUDAD N°3:", (bmp.getWidth()/2) - 150, 200, fSub, Color.CYAN, Color.BLACK);
        bmp.drawText("LABERINTO HACKER", (bmp.getWidth()/2) - 250, 300, fTitulo, Color.GREEN, Color.BLACK);
        bmp.drawText("> PRESIONE EL BOTON PARA INICIAR INTRUSION", (bmp.getWidth()/2) - 280, 450, fSub, Color.WHITE, Color.BLACK);
    }

    /**
     * Limpia la pantalla y dibuja la estructura del laberinto
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
        
        //Destacar inicio y final
        dibujarCasillaEspecial(bmp, 0, 0, true, false);
        dibujarCasillaEspecial(bmp, FILAS - 1, COLUMNAS - 1, false, false);
    }

    /**
     * Muestra un cartel central al concluir el algoritmo indicando victoria o derrota
     */
    public static void dibujarCartelFinal(Bitmap bmp, boolean exito) {
        int centroX = bmp.getWidth() / 2;
        int centroY = bmp.getHeight() / 2;
        int anchoCartel = 700;
        int altoCartel = 400;

        llenarCuadrado(bmp, centroX - (anchoCartel/2), centroY - (altoCartel/2), anchoCartel, altoCartel, new Color(0,0,0, 200));
        bmp.drawRectangle(centroX - (anchoCartel/2), centroY - (altoCartel/2), anchoCartel, altoCartel, exito ? Color.GREEN : Color.RED);

        if (exito) {

            //Diseño de craneo tipo bandera pirata
            int skX = centroX;
            int skY = centroY - 50;
            
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

            //Diseño de un escudo antivirus
            int escudoX = centroX;
            int escudoY = centroY - 145;
            
            llenarCuadrado(bmp, escudoX - 45, escudoY, 90, 45, Color.RED);
            llenarCirculo(bmp, escudoX, escudoY + 45, 45, Color.RED);
            
            llenarCuadrado(bmp, escudoX - 35, escudoY + 10, 70, 35, Color.BLACK);
            llenarCirculo(bmp, escudoX, escudoY + 45, 35, Color.BLACK);
            
            int cruzX = escudoX;
            int cruzY = escudoY + 35;
            int grosorCruz = 10;
            int largoCruz = 18;
            
            for(int i = -grosorCruz/2; i <= grosorCruz/2; i++) {
                bmp.drawLine(cruzX - largoCruz, cruzY - largoCruz + i, cruzX + largoCruz, cruzY + largoCruz + i, Color.RED);
                bmp.drawLine(cruzX + largoCruz, cruzY - largoCruz + i, cruzX - largoCruz, cruzY + largoCruz + i, Color.RED);
            }

            bmp.drawText("ANTIVIRUS ACTIVADO", centroX - 200, centroY - 20, new Font("Courier New", Font.BOLD, 35), Color.RED, Color.BLACK);
            bmp.drawText("SISTEMAS FALLANDO....", centroX - 220, centroY + 40, new Font("Courier New", Font.BOLD, 30), Color.RED, Color.BLACK);
        }
    }

    /**
     * Muestra el cartel cuando se apreta el boton de abortar
     */
    public static void dibujarCartelAbortado(Bitmap bmp) {
        int centroX = bmp.getWidth() / 2;
        int centroY = bmp.getHeight() / 2;
        int anchoCartel = 700;
        int altoCartel = 400;

        llenarCuadrado(bmp, centroX - (anchoCartel/2), centroY - (altoCartel/2), anchoCartel, altoCartel, new Color(0,0,0, 200));
        bmp.drawRectangle(centroX - (anchoCartel/2), centroY - (altoCartel/2), anchoCartel, altoCartel, Color.YELLOW);

        // Icono de advertencia
        llenarCirculo(bmp, centroX, centroY - 40, 40, Color.YELLOW);
        llenarCuadrado(bmp, centroX - 8, centroY - 60, 16, 25, Color.BLACK);
        llenarCuadrado(bmp, centroX - 8, centroY - 25, 16, 10, Color.BLACK);

        bmp.drawText("ABORTANDO SISTEMAS", centroX - 220, centroY + 80, new Font("Courier New", Font.BOLD, 35), Color.YELLOW, Color.BLACK);
    }

    /**
     * Algoritmo de Backtracking principal. Busca el camino explorando recursivamente las 4 direcciones
     */
    public static boolean resolverLaberinto(int[][] laberinto, int x, int y, int prevX, int prevY, int[][] solucion, Bitmap bmp) {
        
        //Logica de cuando se apreta abortar
        if (abortarHackeo) return false;

        //CASO BASE: Si llega al final, termina la búsqueda
        if (x == FILAS - 1 && y == COLUMNAS - 1 && laberinto[x][y] == 1) {
            solucion[x][y] = 1;
            repintarLaberinto(bmp, laberinto, solucion, x, y);
            return true;
        }

        //Se evalúa si es válido moverse a la casilla actual
        if (esSeguro(laberinto, x, y, solucion)) {
            
            // Marca el paso en la matriz de recorrido

            solucion[x][y] = 1;
            repintarLaberinto(bmp, laberinto, solucion, x, y);

            // Búsqueda en profundidad (evalua tambien abortarHackeo)
            if (!abortarHackeo && resolverLaberinto(laberinto, x + 1, y, x, y, solucion, bmp)) return true; // abajo
            if (!abortarHackeo && resolverLaberinto(laberinto, x, y + 1, x, y, solucion, bmp)) return true; // derecha
            if (!abortarHackeo && resolverLaberinto(laberinto, x - 1, y, x, y, solucion, bmp)) return true; // arriba
            if (!abortarHackeo && resolverLaberinto(laberinto, x, y - 1, x, y, solucion, bmp)) return true; // izquierda

            // Si ningún camino sirvió y NO fue abortado manualemente, hace BACKTRACKING
            if (!abortarHackeo) {
                solucion[x][y] = 0;
                repintarLaberinto(bmp, laberinto, solucion, prevX, prevY);
            }
            return false;
        }
        
        return false;
    }

    /**
     * Valida que las coordenadas estén dentro del mapa, que no sea una pared y que no haya pasado por ahi
     */
    public static boolean esSeguro(int[][] laberinto, int x, int y, int[][] solucion) {
        return (x >= 0 && x < FILAS && y >= 0 && y < COLUMNAS && laberinto[x][y] == 1 && solucion[x][y] == 0);
    }

    /**
     * Redibuja los cuadrantes en base al estado actual de la matriz solucion
     */
    public static void repintarLaberinto(Bitmap bmp, int[][] laberinto, int[][] solucion, int pelotaX, int pelotaY) {
        
        //Limpia todos los pasillos transitables
        for (int fila = 0; fila < FILAS; fila++) {
            for (int col = 0; col < COLUMNAS; col++) {
                if (laberinto[fila][col] == 1) {
                    llenarCuadrado(bmp, (col * TAMANO_CELDA) + MARGEN_X, (fila * TAMANO_CELDA) + MARGEN_Y, TAMANO_CELDA, TAMANO_CELDA, COLOR_FONDO_CIRCUITO);
                }
            }
        }

        //Repinta zonas de inicio y fin
        dibujarCasillaEspecial(bmp, 0, 0, true, false); 
        boolean exito = (pelotaX == FILAS-1 && pelotaY == COLUMNAS-1);
        dibujarCasillaEspecial(bmp, FILAS-1, COLUMNAS-1, false, exito); 

        //Dibuja la estela conectando las casillas por las que ya pasó
        for (int fila = 0; fila < FILAS; fila++) {
            for (int col = 0; col < COLUMNAS; col++) {
                if (solucion[fila][col] == 1) {
                    int cx = (col * TAMANO_CELDA) + MARGEN_X + (TAMANO_CELDA / 2);
                    int cy = (fila * TAMANO_CELDA) + MARGEN_Y + (TAMANO_CELDA / 2);

                    //Nodo central de la estela y conexiones (derecha y abajo)
                    llenarCuadrado(bmp, cx - GROSOR_ESTELA/2, cy - GROSOR_ESTELA/2, GROSOR_ESTELA, GROSOR_ESTELA, COLOR_ESTELA);

                    if (col + 1 < COLUMNAS && solucion[fila][col+1] == 1) {
                        llenarCuadrado(bmp, cx, cy - GROSOR_ESTELA/2, TAMANO_CELDA, GROSOR_ESTELA, COLOR_ESTELA);
                    }

                    if (fila + 1 < FILAS && solucion[fila+1][col] == 1) {
                        llenarCuadrado(bmp, cx - GROSOR_ESTELA/2, cy, GROSOR_ESTELA, TAMANO_CELDA, COLOR_ESTELA);
                    }
                }
            }
        }

        //Posiciona actual representada por un circulo amarillo
        if (pelotaX >= 0 && pelotaY >= 0) {
            int cxActual = (pelotaY * TAMANO_CELDA) + MARGEN_X + (TAMANO_CELDA / 2);
            int cyActual = (pelotaX * TAMANO_CELDA) + MARGEN_Y + (TAMANO_CELDA / 2);
            llenarCirculo(bmp, cxActual, cyActual, RADIO_PELOTA, Color.YELLOW);
        }

        //Pausa para que se pueda apreciar el flujo del algoritmo visualmente
        try { Thread.sleep(PAUSA_MS); } catch (InterruptedException e) {}
    }

    /**
     * Dibuja los inicio y fin con indicador activo 
     */
    public static void dibujarCasillaEspecial(Bitmap bmp, int fila, int col, boolean esInicio, boolean exitoFinal) {
        int x = (col * TAMANO_CELDA) + MARGEN_X;
        int y = (fila * TAMANO_CELDA) + MARGEN_Y;
        int grosor = 15;
        Color colorMetal = new Color(110, 110, 110); 

        //Diseño de cuadrantes
        Color colorFondoPuerto = new Color(30, 40, 35); 
        llenarCuadrado(bmp, x + grosor, y + grosor, TAMANO_CELDA - (grosor * 2), TAMANO_CELDA - (grosor * 2), colorFondoPuerto);

        Color colorPlaca = new Color(60, 70, 65);
        int margenPlaca = 35;
        llenarCuadrado(bmp, x + margenPlaca, y + margenPlaca, TAMANO_CELDA - (margenPlaca * 2), TAMANO_CELDA - (margenPlaca * 2), colorPlaca);
        
        bmp.drawLine(x + (TAMANO_CELDA / 2), y + grosor, x + (TAMANO_CELDA / 2), y + TAMANO_CELDA - grosor, new Color(20, 20, 20));
        bmp.drawLine(x + grosor, y + (TAMANO_CELDA / 2), x + TAMANO_CELDA - grosor, y + (TAMANO_CELDA / 2), new Color(20, 20, 20));

        llenarCuadrado(bmp, x, y, TAMANO_CELDA, grosor, colorMetal); // borde superior
        llenarCuadrado(bmp, x, y + TAMANO_CELDA - grosor, TAMANO_CELDA, grosor, colorMetal); // borde inferior
        llenarCuadrado(bmp, x, y, grosor, TAMANO_CELDA, colorMetal); // borde izquierdo
        llenarCuadrado(bmp, x + TAMANO_CELDA - grosor, y, grosor, TAMANO_CELDA, colorMetal); // borde derecho

        //Indicador como una luz LED que tiene Verde para inicio o éxito y Rojo para pendiente o error
        Color colorLuz = (esInicio || exitoFinal) ? new Color(50, 255, 50) : Color.RED;
        llenarCirculo(bmp, x + (grosor / 2), y + (grosor / 2), 6, colorLuz); 
    }

    /**
     * Dibuja las paredes
     */
    public static void dibujarParedHacker(Bitmap bmp, int fila, int col) {
        int x = (col * TAMANO_CELDA) + MARGEN_X;
        int y = (fila * TAMANO_CELDA) + MARGEN_Y;
        
        llenarCuadrado(bmp, x, y, TAMANO_CELDA, TAMANO_CELDA, COLOR_RAM_BASE);
        
        for(int i = 25; i < TAMANO_CELDA; i += 30) {
            bmp.drawLine(x + i, y, x + i, y + 30, COLOR_PISTAS); 
            bmp.drawLine(x + i, y + TAMANO_CELDA - 30, x + i, y + TAMANO_CELDA, COLOR_PISTAS); 
            bmp.drawLine(x, y + i, x + 30, y + i, COLOR_PISTAS); 
            bmp.drawLine(x + TAMANO_CELDA - 30, y + i, x + TAMANO_CELDA, y + i, COLOR_PISTAS); 
        }
        
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