package Ciudad1.partida;

import Ciudad1.Personaje.Jugador;
import Ciudad1.Personaje.Mochila;
import Ciudad1.tablero.Casillero;
import Ciudad1.tablero.GeneradorTablero;
import Ciudad1.tablero.Tablero;
import Ciudad1.tablero.TipoCasillero;
import Ciudad1.tablero.Cofre;
import Ciudad1.elementos.Elemento;
import Ciudad1.elementos.ElementoUtilizable;
import Ciudad1.elementos.Trampa;
import Ciudad1.elementos.AmuletoMistico;
import Ciudad1.elementos.AntorchaPerdida;
import Ciudad1.elementos.MapaRasgado;

/**
 * Representa una partida en curso.
 */
public class Partida {

    // ATRIBUTOS

    private Jugador jugador;
    private Tablero tablero;
    private String mensaje;
    private GeneradorTablero generador;
    private int turnos;
    private Partida partida;

    /**
     * POST:
     * crea una nueva partida.
     */
    public Partida() {

        this.jugador = new Jugador();
        
        this.jugador.setPartida(this);
        
        this.tablero = new Tablero();
        
        this.jugador.setPosicion(1, 1, 0);
        
        this.generador = new GeneradorTablero();
        
        this.generador.generar(this.tablero);

        this.turnos = 0;
        
        this.mensaje = "";
    }
    
    public Partida getPartida() {
        return partida;
    }
    
    
    //METODOS
    
    /**
     * POST:
     * abre el cofre de la posición actual
     * del jugador.
     */
    public void abrirCofre() {

        Casillero casillero;
        Cofre cofre;
        Elemento contenido;
        
        casillero = tablero.getCasillero(
                	jugador.getPosX(),
                	jugador.getPosY(),
                	jugador.getPosZ()
                	
        );
        
        if (casillero.getTipo() != TipoCasillero.COFRE) {
            setMensaje("No hay cofre aquí");
            return;
        }

        cofre = casillero.getCofre();

        if (cofre == null || !cofre.tieneContenido()) {
            setMensaje("El cofre está vacío");
            return;
        }

        contenido = cofre.getContenido();

        if (casillero.getTipo() == TipoCasillero.COFRE
                && casillero.tieneCofre()) {

            cofre = casillero.getCofre();

            if (!cofre.estaAbierto() && cofre.tieneContenido()) {

                contenido = cofre.getContenido();

                if ( contenido instanceof ElementoUtilizable ) {

                    this.jugador.agregarElemento(contenido);
                    
                    if (
                            contenido
                            instanceof AntorchaPerdida
                    )
                    {
                        this.jugador.mejorarVision();
                    }
                    setMensaje("Encontraste un objeto.");
                }
                

                if (contenido instanceof Trampa) {

                    contenido.usar(
                            this.jugador
                    );
                    this.setMensaje(
                            "Has activado una trampa."
                    );
                }
                

                cofre.abrir();

                cofre.vaciar();

            }
        }
    }
    
    public void setMensaje(
            String mensaje
    ) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {

        if (this.mensaje == null) {

            return "";
        }

        return this.mensaje;
    }

    // ==================
    // GETTERS
    // ==================

    public Jugador getJugador() {

        return this.jugador;
    }

    public Tablero getTablero() {

        return this.tablero;
    }

    public int getTurnos() {

        return this.turnos;
    }

    // ==================
    // COMPORTAMIENTO
    // ==================

    /**
     * POST:
     * avanza un turno, y si posee un efecto, se le agregara.
     */
    public void avanzarTurno() {

        this.turnos++;

        this.jugador.actualizarEfectos();
    }
    
    /**
     * PRE:
     * desplazamientos válidos.
     *
     * POST:
     * mueve al jugador si la posición es válida
     * y no existe una pared.
     */
    public boolean moverJugador(int dx, int dy, int dz) {

        int nx = jugador.getPosX() + dx;
        int ny = jugador.getPosY() + dy;
        int nz = jugador.getPosZ() + dz;

        if (!tablero.posicionValida(nx, ny, nz)) return false;

        Casillero destino = tablero.getCasillero(nx, ny, nz);

        if (destino.getTipo() == TipoCasillero.PARED) 
        	return false;

        jugador.mover(dx, dy, dz);
        avanzarTurno();

        return true;
    }
    
    /**
     * POST:
     * sube un piso si el jugador está
     * sobre una escalera de subida.
     */
    public boolean subirPiso() {

        boolean pudoSubir;

        Casillero casilleroActual;

        pudoSubir = false;

        casilleroActual =
                this.tablero.getCasillero(
                        this.jugador.getPosX(),
                        this.jugador.getPosY(),
                        this.jugador.getPosZ()
                );

        if (
                casilleroActual.getTipo()
                ==
                TipoCasillero.ESCALERA_SUBE
        ) {

            if (
                    this.tablero.posicionValida(
                            this.jugador.getPosX(),
                            this.jugador.getPosY(),
                            this.jugador.getPosZ() + 1
                    )
            ) {

                this.jugador.setPosicion(
                        this.jugador.getPosX(),
                        this.jugador.getPosY(),
                        this.jugador.getPosZ() + 1
                );

                this.avanzarTurno();

                pudoSubir = true;
            }
        }

        return pudoSubir;
    }
    
    /**
     * POST:
     * baja un piso si el jugador está
     * sobre una escalera de bajada.
     */
    public boolean bajarPiso() {

        boolean pudoBajar;

        Casillero casilleroActual;

        pudoBajar = false;

        casilleroActual =
                this.tablero.getCasillero(
                        this.jugador.getPosX(),
                        this.jugador.getPosY(),
                        this.jugador.getPosZ()
                );

        if (
                casilleroActual.getTipo()
                ==
                TipoCasillero.ESCALERA_BAJA
        ) {

            if (
                    this.tablero.posicionValida(
                            this.jugador.getPosX(),
                            this.jugador.getPosY(),
                            this.jugador.getPosZ() - 1
                    )
            ) {

                this.jugador.setPosicion(
                        this.jugador.getPosX(),
                        this.jugador.getPosY(),
                        this.jugador.getPosZ() - 1
                );

                this.avanzarTurno();

                pudoBajar = true;
            }
        }

        return pudoBajar;
    }
    
    public void interactuar() {

        Casillero actual;

        actual =
                this.tablero.getCasillero(
                        this.jugador.getPosX(),
                        this.jugador.getPosY(),
                        this.jugador.getPosZ()
                );

        if (actual.getTipo() == TipoCasillero.ESCALERA_SUBE) {

            this.subirPiso();
        }

        else if (actual.getTipo() == TipoCasillero.ESCALERA_BAJA) {

            this.bajarPiso();
        }

        else if (actual.getTipo() == TipoCasillero.COFRE) {

            this.abrirCofre();
        }
    }
    
    public void usarElemento(int index) {

        Mochila mochila = jugador.getMochila();

        if (index < 0 || index >= mochila.cantidadElementos()) {
            setMensaje("No llevas nada ahí");
            return;
        }

        Elemento e = mochila.getElemento(index);
        e.usar(jugador);
    }

    public void usarMapa() {

        System.out.println(
                obtenerPistaObjetoFaltante()
        );
    }
    

    public String obtenerPistaObjetoFaltante() {

        return
                "Objeto util detectado en X=7 Y=7 Piso=2";
    }

    
    //VICTORY ROYALE
    
    /**
     * POST:
     * devuelve true si el jugador
     * posee los tres elementos.
     */
    public boolean gano() {

        boolean tieneAntorcha = false;
        boolean tieneAmuleto = false;
        boolean tieneMapa = false;

        int i = 0;

        while (i < jugador.getMochila().cantidadElementos()) {

            Elemento element = jugador.getMochila().getElemento(i);

            if (element instanceof AntorchaPerdida) {
                tieneAntorcha = true;
            }

            if (element instanceof AmuletoMistico) {
                tieneAmuleto = true;
            }

            if (element instanceof MapaRasgado) {
                tieneMapa = true;
            }

            i++;
        }

        return tieneAntorcha && tieneAmuleto && tieneMapa;
    }

    public boolean verificarVictoria() {
        return gano();
    }
    
}