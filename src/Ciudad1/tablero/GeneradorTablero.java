package tablero;

import java.util.Random;

import elementos.AmuletoMistico;
import elementos.AntorchaPerdida;
import elementos.MapaRasgado;
import elementos.Interferencia;
import elementos.PortalInestable;

//Genera el contenido del tablero
public class GeneradorTablero {
	
	private int variantePiso2;
	private int amuletoX;
	private int amuletoY;

	public void generar(Tablero tablero) {


	    Random random = new Random();

	    int variantePiso0 = random.nextInt(3);
	    int variantePiso1 = random.nextInt(3);
	    variantePiso2 = random.nextInt(3);

	    construirParedesExternas(tablero, 0);
	    construirParedesExternas(tablero, 1);
	    construirParedesExternas(tablero, 2);


	    construirPiso0(tablero, variantePiso0);
	    construirPiso1(tablero, variantePiso1);
	    construirPiso2(tablero, variantePiso2);
	    
	    
	}
	
	private void colocarAmuleto(Tablero tablero, int x, int y) {

	    amuletoX = x;
	    amuletoY = y;

	    colocarCofre(tablero, x, y, 2, new AmuletoMistico());
	}
	
	public int getVariantePiso2() {
	    return variantePiso2;
	}

    //PAREDES EXTERNAS
    private void construirParedesExternas(Tablero tablero, int z) {
        for (int i = 0; i < 15; i++) {
            setSeguro(tablero, i, 0, z, TipoCasillero.PARED);
            setSeguro(tablero, i, 14, z, TipoCasillero.PARED);
            setSeguro(tablero, 0, i, z, TipoCasillero.PARED);
            setSeguro(tablero, 14, i, z, TipoCasillero.PARED);
        }
    }

    //ESCALERAS
    private void colocarEscalera(Tablero tablero, int x, int y, int zBajo, int zAlto) {
        tablero.getCasillero(x, y, zBajo).setTipo(TipoCasillero.ESCALERA_SUBE);
        tablero.getCasillero(x, y, zAlto).setTipo(TipoCasillero.ESCALERA_BAJA);
    }

    //COFRES
    private void colocarCofre(Tablero tablero, int x, int y, int z, elementos.Elemento contenido) {
        Casillero casillero = tablero.getCasillero(x, y, z);
        casillero.setTipo(TipoCasillero.COFRE);
        casillero.setCofre(new Cofre(contenido));
    }

    /**
     * LABERINTO 
     * Cada piso y sus variantes
     * Se asigno paredes, cofres y escaleras en diferentes posiciones
     * Se elige al azar una de las 3 variantes de piso
     */
    
    private void construirPiso0(Tablero tablero, int variante) {

        switch (variante) {

        case 0:
            construirPiso0A(tablero);

            colocarEscalera(tablero, 10, 12, 0, 1);
            colocarCofre(tablero, 2, 11, 0, null);
            colocarCofre(tablero, 10, 3, 0, new Interferencia());
            colocarCofre(tablero, 7, 12, 0, new AntorchaPerdida());

            break;

        case 1:
            construirPiso0B(tablero);

            colocarEscalera(tablero, 12, 2, 0, 1);
            colocarCofre(tablero, 2, 12, 0, null);
            colocarCofre(tablero, 9, 6, 0, new Interferencia());
            colocarCofre(tablero, 11, 11, 0, new AntorchaPerdida());

            break;

        case 2:
            construirPiso0C(tablero);

            colocarEscalera(tablero, 12, 12, 0, 1);
            colocarCofre(tablero, 2, 2, 0, null);
            colocarCofre(tablero, 10, 6, 0, new Interferencia());
            colocarCofre(tablero, 8, 11, 0, new AntorchaPerdida());

            break;
        }
    }
    
    private void construirPiso1(Tablero tablero, int variante) {

        switch (variante) {

        case 0:
            construirPiso1A(tablero);

            colocarEscalera(tablero, 2, 3, 1, 2);
            colocarCofre(tablero, 10, 12, 1, null);
            colocarCofre(tablero, 11, 2, 1, new PortalInestable());
            colocarCofre(tablero, 5, 11, 1, new MapaRasgado());

            break;

        case 1:
            construirPiso1B(tablero);

            colocarEscalera(tablero, 2, 12, 1, 2);
            colocarCofre(tablero, 12, 2, 1, null);
            colocarCofre(tablero, 10, 10, 1, new PortalInestable());
            colocarCofre(tablero, 4, 7, 1, new MapaRasgado());

            break;

        case 2:
            construirPiso1C(tablero);

            colocarEscalera(tablero, 12, 6, 1, 2);
            colocarCofre(tablero, 10, 3, 1, null);
            colocarCofre(tablero, 10, 11, 1, new PortalInestable());
            colocarCofre(tablero, 1, 1, 1, new MapaRasgado());

            break;
        }
    }
    
    private void construirPiso2(Tablero tablero, int variante) {

        switch (variante) {

        case 0:
            construirPiso2A(tablero);

            colocarAmuleto(tablero, 13, 11);           
            colocarCofre(tablero, 2, 7, 2, new Interferencia());
            colocarCofre(tablero, 11, 3, 2, new PortalInestable());

            break;

        case 1:
            construirPiso2B(tablero);

            colocarAmuleto(tablero, 4, 10);
            colocarCofre(tablero, 11, 3, 2, new Interferencia());
            colocarCofre(tablero, 7, 7, 2, new PortalInestable());

            break;

        case 2:
            construirPiso2C(tablero);

            colocarAmuleto(tablero, 12, 12);
            colocarCofre(tablero, 3, 3, 2, new Interferencia());
            colocarCofre(tablero, 8, 8, 2, new PortalInestable());

            break;
        }
    }
    private void construirPiso0A(Tablero tablero) {

        rectanguloPared(tablero, 2, 2, 4, 4, 0);
        rectanguloPared(tablero, 10, 2, 12, 4, 0);
        rectanguloPared(tablero, 2, 10, 4, 12, 0);
        rectanguloPared(tablero, 10, 10, 12, 12, 0);
        lineaParedV(tablero, 7, 2, 5, 0);
        lineaParedV(tablero, 7, 9, 12, 0);
        lineaParedH(tablero, 5, 9, 7, 0);
    }
    
    private void construirPiso0B(Tablero tablero) {

        rectanguloPared(tablero, 3, 3, 5, 5, 0);
        rectanguloPared(tablero, 9, 3, 11, 5, 0);
        rectanguloPared(tablero, 3, 9, 5, 11, 0);
        rectanguloPared(tablero, 9, 9, 11, 11, 0);
        lineaParedH(tablero, 5, 9, 7, 0);
        lineaParedV(tablero, 7, 5, 9, 0);
    }
    
    private void construirPiso0C(Tablero tablero) {

        lineaParedV(tablero, 4, 2, 12, 0);
        lineaParedH(tablero, 4, 10, 4, 0);
        lineaParedH(tablero, 4, 10, 10, 0);
        rectanguloPared(tablero, 2, 4, 2, 12, 0);
        rectanguloPared(tablero, 11, 1, 13, 2, 0);
        rectanguloPared(tablero, 12, 5, 12, 11, 0);
        rectanguloPared(tablero, 6, 2, 8, 3, 0);
        rectanguloPared(tablero, 6, 11, 8, 12, 0);
        rectanguloPared(tablero, 6, 6, 8, 8, 0);
    }
    
    private void construirPiso1A(Tablero tablero) {

        rectanguloPared(tablero, 2, 2, 5, 5, 1);
        rectanguloPared(tablero, 9, 2, 12, 5, 1);
        rectanguloPared(tablero, 2, 9, 5, 12, 1);
        rectanguloPared(tablero, 9, 9, 12, 12, 1);
        lineaParedV(tablero, 7, 2, 12, 1);
    }
    
    private void construirPiso1B(Tablero tablero) {

        lineaParedV(tablero, 5, 2, 12, 1);
        lineaParedV(tablero, 9, 2, 12, 1);
        lineaParedH(tablero, 5, 9, 5, 1);
        lineaParedH(tablero, 5, 9, 9, 1);
        rectanguloPared(tablero, 6, 6, 8, 8, 1);
    }
    
    private void construirPiso1C(Tablero tablero) {

        rectanguloPared(tablero, 2, 2, 4, 4, 1);
        rectanguloPared(tablero, 10, 2, 12, 4, 1);
        rectanguloPared(tablero, 2, 10, 4, 12, 1);
        rectanguloPared(tablero, 10, 10, 12, 12, 1);
        lineaParedH(tablero, 5, 9, 7, 1);
        lineaParedV(tablero, 7, 5, 9, 1);
    }
    
    private void construirPiso2A(Tablero tablero) {

        rectanguloPared(tablero, 3, 3, 5, 5, 2);
        rectanguloPared(tablero, 9, 3, 11, 5, 2);
        rectanguloPared(tablero, 3, 9, 5, 11, 2);
        rectanguloPared(tablero, 9, 9, 11, 11, 2);
        lineaParedH(tablero, 5, 9, 7, 2);
    }
    
    private void construirPiso2B(Tablero tablero) {

        lineaParedV(tablero, 4, 2, 12, 2);
        lineaParedV(tablero, 10, 2, 12, 2);
        rectanguloPared(tablero, 6, 4, 8, 6, 2);
        rectanguloPared(tablero, 6, 8, 8, 10, 2);
    }
    
    private void construirPiso2C(Tablero tablero) {

        rectanguloPared(tablero, 2, 2, 12, 3, 2);
        rectanguloPared(tablero, 2, 11, 12, 12, 2);
        lineaParedV(tablero, 6, 4, 10, 2);
        lineaParedV(tablero, 8, 4, 10, 2);
    }
    
    public int getAmuletoX() {
        return amuletoX;
    }

    public int getAmuletoY() {
        return amuletoY;
    }

    private void setSeguro(Tablero tablero, int x, int y, int z, TipoCasillero tipo) {
        if (!tablero.posicionValida(x, y, z)) return;
        Casillero casillero = tablero.getCasillero(x, y, z);
        if (casillero.getTipo() == TipoCasillero.ESCALERA_SUBE ||
            casillero.getTipo() == TipoCasillero.ESCALERA_BAJA ||
            casillero.getTipo() == TipoCasillero.COFRE) {
            return;
        }
        casillero.setTipo(tipo);
    }
    
    private void lineaParedH(Tablero tablero, int x1, int x2, int y, int z) {
        for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
            setSeguro(tablero, x, y, z, TipoCasillero.PARED);
        }
    }

    private void lineaParedV(Tablero tablero, int x, int y1, int y2, int z) {
        for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
            setSeguro(tablero, x, y, z, TipoCasillero.PARED);
        }
    }

    private void rectanguloPared(Tablero tablero, int x1, int y1, int x2, int y2, int z) {
        for (int y = y1; y <= y2; y++) {
            for (int x = x1; x <= x2; x++) {
                setSeguro(tablero, x, y, z, TipoCasillero.PARED);
            }
        }
    }
}