package Ciudad1.tablero;

import Ciudad1.elementos.*;

public class GeneradorTablero {

    public void generar(Tablero t) {

        limpiarMapa(t);

        construirDungeon(t);

        colocarContenido(t);
    }

    // =========================
    // BASE LIMPIA
    // =========================

    private void limpiarMapa(Tablero t) {

        for (int z = 0; z < t.getPisos(); z++) {
            for (int y = 0; y < t.getAlto(); y++) {
                for (int x = 0; x < t.getAncho(); x++) {
                    if (t.posicionValida(x, y, z)) {
                        t.getCasillero(x, y, z).setTipo(TipoCasillero.VACIO);
                        t.getCasillero(x, y, z).setCofre(null);
                    }
                }
            }
        }
    }

    // =========================
    // HELPERS SEGUROS
    // =========================

    private void pared(Tablero t, int x, int y, int z) {
        if (!t.posicionValida(x, y, z)) return;
        t.getCasillero(x, y, z).setTipo(TipoCasillero.PARED);
    }

    private void vacio(Tablero t, int x, int y, int z) {
        if (!t.posicionValida(x, y, z)) return;
        t.getCasillero(x, y, z).setTipo(TipoCasillero.VACIO);
    }

    private void cofre(Tablero t, int x, int y, int z, Elemento e) {
        if (!t.posicionValida(x, y, z)) return;
        vacio(t, x, y, z);
        t.getCasillero(x, y, z).setTipo(TipoCasillero.COFRE);
        t.getCasillero(x, y, z).setCofre(new Cofre(e));
    }

    private void escalera(Tablero t, int x, int y, int z) {

        if (!t.posicionValida(x, y, z)) return;
        if (!t.posicionValida(x, y, z + 1)) return;

        vacio(t, x, y, z);
        t.getCasillero(x, y, z).setTipo(TipoCasillero.ESCALERA_SUBE);

        vacio(t, x, y, z + 1);
        t.getCasillero(x, y, z + 1).setTipo(TipoCasillero.ESCALERA_BAJA);
    }

    // =========================
    // DUNGEON SIMPLE PERO FUNCIONAL
    // =========================

    private void construirDungeon(Tablero t) {

        // =========================
        // PISO 0 (inicio lineal)
        // =========================

        for (int x = 1; x < 14; x++) {
            pared(t, x, 1, 0);
            pared(t, x, 13, 0);
        }

        for (int y = 1; y < 14; y++) {
            pared(t, 1, y, 0);
            pared(t, 13, y, 0);
        }

        // camino obligatorio
        for (int x = 2; x <= 12; x++) {
            vacio(t, x, 2, 0);
        }

        escalera(t, 12, 2, 0);

        // =========================
        // PISO 1 (bifurcación)
        // =========================

        for (int x = 1; x < 14; x++) {
            pared(t, x, 1, 1);
            pared(t, x, 13, 1);
        }

        for (int y = 1; y < 14; y++) {
            pared(t, 1, y, 1);
            pared(t, 13, y, 1);
        }

        // centro bloqueado
        pared(t, 7, 7, 1);
        pared(t, 7, 6, 1);
        pared(t, 7, 8, 1);

        // rama izquierda
        vacio(t, 3, 3, 1);
        vacio(t, 4, 3, 1);
        vacio(t, 5, 3, 1);

        // rama derecha
        vacio(t, 11, 3, 1);
        vacio(t, 10, 3, 1);

        escalera(t, 11, 11, 1);

        // =========================
        // PISO 2 (objetivo protegido)
        // =========================

        for (int x = 1; x < 14; x++) {
            pared(t, x, 1, 2);
            pared(t, x, 13, 2);
        }

        for (int y = 1; y < 14; y++) {
            pared(t, 1, y, 2);
            pared(t, 13, y, 2);
        }

        // anillo interno
        for (int i = 4; i <= 10; i++) {
            pared(t, i, 4, 2);
            pared(t, i, 10, 2);
            pared(t, 4, i, 2);
            pared(t, 10, i, 2);
        }

        vacio(t, 7, 4, 2);
    }

    // =========================
    // CONTENIDO (SEGURO)
    // =========================

    private void colocarContenido(Tablero t) {

        cofre(t, 5, 5, 0, new AntorchaPerdida());
        cofre(t, 8, 4, 0, null);
        cofre(t, 10, 10, 0, new Interferencia());

        cofre(t, 7, 7, 1, new MapaRasgado());
        cofre(t, 9, 4, 1, new PortalInestable());
        cofre(t, 12, 8, 1, null);

        cofre(t, 7, 7, 2, new AmuletoMistico());
        cofre(t, 4, 4, 2, null);
        cofre(t, 10, 5, 2, new Interferencia());
        cofre(t, 5, 11, 2, new PortalInestable());
    }
}