package Ciudad1.elementos;

import Ciudad1.Personaje.Jugador;

//Te permite saber la casilla en la que te encuentras.
public class AmuletoMistico extends ElementoUtilizable {
	
	//POST: crea un Amuleto.
    public AmuletoMistico() {
        this.nombre = "Amuleto Mistico";
        this.descripcion = "Revela tu posicion actual";
    }
    
    /**
     * PRE:  jugador != null
     * POST: muestra la posicion actual del jugador.
     */
    @Override
    public void usar(Jugador jugador) {
        int piso = jugador.getPosZ() + 1;
        jugador.getPartida().setMensaje(
            "Estas en (" + jugador.getPosX() + ", " 
            + jugador.getPosY() + ", " + piso + ")"
        );
    }
}
