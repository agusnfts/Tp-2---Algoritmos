package Ciudad7.grafos;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Ciudad7.bitmap.Bitmap;

public class GrafoToBitmap {
    
    private static final int VERTEX_RADIUS = 25; 
    private static final int PADDING = 100; 
    private static final Color VERTEX_COLOR = Color.BLUE;
    private static final Color EDGE_COLOR = Color.GRAY;
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 14);

    private final int V; 
    private final int[][] matrizAdyacencia; 
    private final Bitmap mapaBits;
    private final Map<Integer, Point2D.Double> posicionesNodo;
    private final List<String> listadoNombres; 

    private List<Integer> rutaVerde = null;
    private int[][] caudalRojo = null;

    public GrafoToBitmap(int[][] matrizAdyacencia, List<String> listadoNombres, int ancho, int alto) {
        this.V = matrizAdyacencia.length;
        this.matrizAdyacencia = matrizAdyacencia;
        this.listadoNombres = listadoNombres;
        this.mapaBits = new Bitmap(ancho, alto);
        this.mapaBits.rellenar(Color.BLACK); 
        this.posicionesNodo = new HashMap<>();
        calcularPosicionesCirculares(ancho, alto);
    }

    public void setRutaVerde(List<Integer> ruta) { this.rutaVerde = ruta; }
    public void setCaudalRojo(int[][] flujo) { this.caudalRojo = flujo; }

    private void calcularPosicionesCirculares(int ancho, int alto) {
        int centroX = ancho / 2;
        int centroY = alto / 2;
        int radioAnillo = Math.min(centroX, centroY) - PADDING;
        
        for (int i = 0; i < V; i++) {
            double anguloNodo = 2 * Math.PI * i / V;
            posicionesNodo.put(i, new Point2D.Double(
                (int) (centroX + radioAnillo * Math.cos(anguloNodo)),
                (int) (centroY + radioAnillo * Math.sin(anguloNodo))
            ));
        }
    }

    public Bitmap drawGraph() {
        dibujarAristas();
        dibujarNodos();
        return mapaBits;
    }

    private void dibujarAristas() {
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (matrizAdyacencia[i][j] >= 1 && matrizAdyacencia[i][j] != 99999) { 
                    Point2D.Double origen = posicionesNodo.get(i);
                    Point2D.Double destino = posicionesNodo.get(j);
                    
                    Color colorActual = EDGE_COLOR;
                    if (caudalRojo != null && caudalRojo[i][j] > 0) {
                        colorActual = Color.RED;
                    } else if (rutaVerde != null && esRutaVerde(i, j)) {
                        colorActual = Color.GREEN;
                    }

                    if (i == j) {
                        dibujarBucle((int)origen.getX(), (int)origen.getY(), matrizAdyacencia[i][j], colorActual);
                    } else {
                        boolean conexionDoble = (matrizAdyacencia[i][j] == matrizAdyacencia[j][i]);
                        if (conexionDoble) {
                            if (i < j) { 
                                double angle = Math.atan2(destino.getY() - origen.getY(), destino.getX() - origen.getX());
                                
                                int startX = (int) (origen.getX() + VERTEX_RADIUS * Math.cos(angle));
                                int startY = (int) (origen.getY() + VERTEX_RADIUS * Math.sin(angle));
                                int destX = (int) (destino.getX() - VERTEX_RADIUS * Math.cos(angle));
                                int destY = (int) (destino.getY() - VERTEX_RADIUS * Math.sin(angle));

                                mapaBits.drawLine(startX, startY, destX, destY, colorActual);
                                
                                int medioX = (int) ((origen.getX() + destino.getX()) / 2 - 5);
                                int medioY = (int) ((origen.getY() + destino.getY()) / 2 - 5);
                                mapaBits.drawText(String.valueOf(matrizAdyacencia[i][j]), medioX, medioY, LABEL_FONT, Color.YELLOW, Color.BLACK);
                            }
                        } else {
                            dibujarVector((int) origen.getX(), (int) origen.getY(), (int) destino.getX(), (int) destino.getY(), colorActual);
                            int textoX = (int) (origen.getX() + (destino.getX() - origen.getX()) * 0.7 - 5);
                            int textoY = (int) (origen.getY() + (destino.getY() - origen.getY()) * 0.7 - 5);
                            mapaBits.drawText(String.valueOf(matrizAdyacencia[i][j]), textoX, textoY, LABEL_FONT, Color.YELLOW, Color.BLACK);
                        }
                    }
                }
            }
        }
    }

    private boolean esRutaVerde(int inicio, int fin) {
        if (rutaVerde == null) return false;
        for (int i = 0; i < rutaVerde.size() - 1; i++) {
            if (rutaVerde.get(i) == inicio && rutaVerde.get(i+1) == fin) return true;
        }
        return false;
    }

    private void dibujarVector(int x1, int y1, int x2, int y2, Color colorFlecha) {
        double direccion = Math.atan2(y2 - y1, x2 - x1);
        int bordeDestinoX = (int) (x2 - (VERTEX_RADIUS + 2) * Math.cos(direccion));
        int bordeDestinoY = (int) (y2 - (VERTEX_RADIUS + 2) * Math.sin(direccion));
        int bordeOrigenX = (int) (x1 + VERTEX_RADIUS * Math.cos(direccion));
        int bordeOrigenY = (int) (y1 + VERTEX_RADIUS * Math.sin(direccion));

        mapaBits.drawLine(bordeOrigenX, bordeOrigenY, bordeDestinoX, bordeDestinoY, colorFlecha);

        int magnitudPunta = 15;
        double apertura = Math.PI / 6;
        int puntaArribaX = (int) (bordeDestinoX - magnitudPunta * Math.cos(direccion - apertura));
        int puntaArribaY = (int) (bordeDestinoY - magnitudPunta * Math.sin(direccion - apertura));
        int puntaAbajoX = (int) (bordeDestinoX - magnitudPunta * Math.cos(direccion + apertura));
        int puntaAbajoY = (int) (bordeDestinoY - magnitudPunta * Math.sin(direccion + apertura));

        mapaBits.drawLine(bordeDestinoX, bordeDestinoY, puntaArribaX, puntaArribaY, colorFlecha);
        mapaBits.drawLine(bordeDestinoX, bordeDestinoY, puntaAbajoX, puntaAbajoY, colorFlecha);
    }

    private void dibujarBucle(int coordX, int coordY, int pesoMasa, Color colorBucle) {
        int centroPantallaX = mapaBits.getWidth() / 2;
        int centroPantallaY = mapaBits.getHeight() / 2;
        double anguloEscape = Math.atan2(coordY - centroPantallaY, coordX - centroPantallaX);
        
        int expansionX = 30; 
        int expansionY = 15;
        double focoX = coordX + (VERTEX_RADIUS + expansionX - 10) * Math.cos(anguloEscape);
        double focoY = coordY + (VERTEX_RADIUS + expansionX - 10) * Math.sin(anguloEscape);

        int trazoPrevioX = -1; 
        int trazoPrevioY = -1; 
        int fragmentos = 50; 
        
        for (int i = 0; i <= fragmentos; i++) {
            double paso = 2 * Math.PI * i / fragmentos;
            double ejeX = expansionX * Math.cos(paso); 
            double ejeY = expansionY * Math.sin(paso);
            int marcaX = (int) (focoX + ejeX * Math.cos(anguloEscape) - ejeY * Math.sin(anguloEscape));
            int marcaY = (int) (focoY + ejeX * Math.sin(anguloEscape) + ejeY * Math.cos(anguloEscape));
            double distanciaAlCentro = Math.hypot(marcaX - coordX, marcaY - coordY);
            
            if (distanciaAlCentro >= VERTEX_RADIUS) {
                if (trazoPrevioX != -1) {
                    mapaBits.drawLine(trazoPrevioX, trazoPrevioY, marcaX, marcaY, colorBucle);
                }
                trazoPrevioX = marcaX; 
                trazoPrevioY = marcaY;
            } else {
                trazoPrevioX = -1; 
                trazoPrevioY = -1;
            }
        }
        
        int posTextoX = (int) (focoX + (expansionX + 10) * Math.cos(anguloEscape)) - 5;
        int posTextoY = (int) (focoY + (expansionX + 10) * Math.sin(anguloEscape)) + 5;
        mapaBits.drawText(String.valueOf(pesoMasa), posTextoX, posTextoY, LABEL_FONT, Color.YELLOW, Color.BLACK);
    }

    private void dibujarNodos() {
        for (int i = 0; i < V; i++) {
            Point2D.Double posicion = posicionesNodo.get(i);
            int posX = (int) posicion.getX(); 
            int posY = (int) posicion.getY();
            
            Color tonoCirculo = VERTEX_COLOR;
            if (rutaVerde != null && rutaVerde.contains(i)) {
                tonoCirculo = Color.GREEN;
            }

            mapaBits.drawCircle(posX, posY, VERTEX_RADIUS, tonoCirculo);
            String rotulo = (listadoNombres != null && i < listadoNombres.size()) ? listadoNombres.get(i) : String.valueOf(i);
            mapaBits.drawText(rotulo, posX - 12, posY + 5, LABEL_FONT, TEXT_COLOR, new Color(0,0,0,0)); 
        }
    }
}