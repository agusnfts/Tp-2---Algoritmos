package Ciudad1.tablero;

import java.util.Vector;


//Mapa tridimensional usando vectores

public class Tablero {

    private static final int ANCHO = 15;
    private static final int ALTO = 15;
    private static final int PISOS = 3;

    private Vector<Vector<Vector<Casillero>>> mapa;

    
    //POST: crea un tablero vacio usando vectores
    public Tablero() {
        mapa = new Vector<>();

        for (int z = 0; z < PISOS; z++) {
            Vector<Vector<Casillero>> piso = new Vector<>();
            for (int y = 0; y < ALTO; y++) {
                Vector<Casillero> fila = new Vector<>();
                for (int x = 0; x < ANCHO; x++) {
                    fila.add(new Casillero());
                }
                piso.add(fila);
            }
            mapa.add(piso);
        }
    }

    //PRE: posición valida
    //POST: devuelve el casillero en la posicion indicada
    public Casillero getCasillero(int x, int y, int z) {
        if (posicionValida(x, y, z)) {
            return mapa.get(z).get(y).get(x);
        }
        return null;
    }

    //POST: indica si la posicion existe en el tablero
    public boolean posicionValida(int x, int y, int z) {
        return x >= 0 && x < ANCHO &&
               y >= 0 && y < ALTO &&
               z >= 0 && z < PISOS;
    }

    //POST: devuelve el ancho del tablero
    public int getAncho() { return ANCHO; }
    //POST: devuelve el ancho del tablero
    public int getAlto() { return ALTO; }
    //POST: devuelve la cantidad de pisos
    public int getPisos() { return PISOS; }
}