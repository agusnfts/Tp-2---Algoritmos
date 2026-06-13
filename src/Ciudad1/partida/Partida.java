package Ciudad1.partida;

import javax.swing.JOptionPane;

import Ciudad1.Personaje.Jugador;
import Ciudad1.Personaje.Mochila;
import Ciudad1.tablero.Casillero;
import Ciudad1.tablero.GeneradorTablero;
import Ciudad1.tablero.Tablero;
import Ciudad1.tablero.TipoCasillero;
import principal.ProgresoJuego;
import Ciudad1.tablero.Cofre;
import Ciudad1.elementos.Elemento;
import Ciudad1.elementos.ElementoUtilizable;
import Ciudad1.elementos.Trampa;
import Ciudad1.elementos.AntorchaPerdida;

//Representa una partida en curso
public class Partida {

    private Jugador jugador;
    private Tablero tablero;
    private String mensaje;
    private GeneradorTablero generador;

    private int elementosRecolectados;
    private boolean victoria;
    private boolean partidaTerminada;
    
    private ProgresoJuego progreso;
    private Runnable accionVictoria;

    //POST: crea una nueva partida
    public Partida(ProgresoJuego progreso) {

        this.progreso = progreso;

        this.jugador = new Jugador();
        this.jugador.setPartida(this);

        this.tablero = new Tablero();
        this.jugador.setPosicion(1, 1, 0);

        this.generador = new GeneradorTablero();
        this.generador.generar(this.tablero);

        this.mensaje = "";
        this.elementosRecolectados = 0;
        this.victoria = false;
        this.partidaTerminada = false;
        
        this.mensaje = "¡Bienvenido a la Ciudad1!\nEncuentra los 3 objetos perdidos para ganar.";
        
    }


     //POST: abre el cofre de la posicion actual del jugador
    
    public void abrirCofre() {
        if (partidaTerminada) return;

        Casillero casillero = tablero.getCasillero(
                jugador.getPosX(),
                jugador.getPosY(),
                jugador.getPosZ()
        );

        if (casillero.getTipo() != TipoCasillero.COFRE) {
            setMensaje("No hay cofre aquí");
            return;
        }

        Cofre cofre = casillero.getCofre();
        
        if (cofre == null) {
            setMensaje("Este cofre está vacío");
            return;
        }

        if (cofre.estaAbierto()) {
            setMensaje("Este cofre ya fue abierto");
            return;
        }

        Elemento contenido = cofre.getContenido();

        //Cofre vacio desde el principio
        if (contenido == null) {
            cofre.abrir();
            setMensaje("El cofre estaba vacío");
            return;
        }

        //Elemento utilizable
        if (contenido instanceof ElementoUtilizable && !(contenido instanceof Trampa)) {
            jugador.agregarElemento(contenido);
            elementosRecolectados++;

            if (contenido instanceof AntorchaPerdida) {
                jugador.mejorarVision();
            }

            cofre.abrir();
            cofre.vaciar();

            if (verificarVictoria()) return;

            setMensaje("Encontraste: " + contenido.getNombre());
            return;
        }

        // Cofre Trampa
        if (contenido instanceof Trampa) {
            contenido.usar(jugador);
            setMensaje("¡Has activado una trampa!");
            cofre.abrir();
            cofre.vaciar();
            return;
        }
    }

     //PRE:  desplazamientos validos
     // POST: mueve al jugador si la posicion es valida y no existe una pared
    public boolean moverJugador(int dx, int dy, int dz) {
        if (partidaTerminada) return false;

        int nuevoX = jugador.getPosX() + dx;
        int nuevoY = jugador.getPosY() + dy;
        int nuevoZ = jugador.getPosZ() + dz;

        if (!tablero.posicionValida(nuevoX, nuevoY, nuevoZ)) return false;

        Casillero destino = tablero.getCasillero(nuevoX, nuevoY, nuevoZ);
        if (destino.getTipo() == TipoCasillero.PARED) return false;

        jugador.mover(dx, dy, dz);
        avanzarTurno();
        return true;
    }

    //POST: avanza un turno(movimiento) y actualiza efectos
    public void avanzarTurno() {
        this.jugador.actualizarEfectos();
    }

    //POST: sube un piso si el jugador esta sobre una escalera de subida
    public boolean subirPiso() {
        Casillero casilleroActual = tablero.getCasillero(
                jugador.getPosX(),
                jugador.getPosY(),
                jugador.getPosZ()
        );

        if (casilleroActual.getTipo() == TipoCasillero.ESCALERA_SUBE) {
            int nuevoZ = jugador.getPosZ() + 1;
            if (tablero.posicionValida(jugador.getPosX(), jugador.getPosY(), nuevoZ)) {
                jugador.setPosicion(jugador.getPosX(), jugador.getPosY(), nuevoZ);
                avanzarTurno();
                return true;
            }
        }
        return false;
    }

    /**
     * POST: baja un piso si el jugador esta sobre una escalera de bajada
     */
    public boolean bajarPiso() {
        Casillero casilleroActual = tablero.getCasillero(
                jugador.getPosX(),
                jugador.getPosY(),
                jugador.getPosZ()
        );

        if (casilleroActual.getTipo() == TipoCasillero.ESCALERA_BAJA) {
            int nuevoZ = jugador.getPosZ() - 1;
            if (tablero.posicionValida(jugador.getPosX(), jugador.getPosY(), nuevoZ)) {
                jugador.setPosicion(jugador.getPosX(), jugador.getPosY(), nuevoZ);
                avanzarTurno();
                return true;
            }
        }
        return false;
    }

    public void interactuar() {
        if (partidaTerminada) return;

        Casillero actual = tablero.getCasillero(
                jugador.getPosX(),
                jugador.getPosY(),
                jugador.getPosZ()
        );

        if (actual.getTipo() == TipoCasillero.ESCALERA_SUBE) {
            subirPiso();
        } else if (actual.getTipo() == TipoCasillero.ESCALERA_BAJA) {
            bajarPiso();
        } else if (actual.getTipo() == TipoCasillero.COFRE) {
            abrirCofre();
        }
    }

    public void usarElemento(int index) {
    	 if (partidaTerminada) {
    	        return;
    	    }
        Mochila mochila = jugador.getMochila();
        if (index < 0 || index >= mochila.cantidadElementos()) {
            setMensaje("No llevas nada ahi");
            return;
        }
        Elemento elemento = mochila.getElemento(index);
        elemento.usar(jugador);
    }

    public GeneradorTablero getGenerador() {
        return generador;
    }
    
    public Jugador getJugador() {
        return jugador;
    }

    public Tablero getTablero() {
        return tablero;
    }
    

    public int getElementosRecolectados() {
        return elementosRecolectados;
    }

    public String getMensaje() {
        return (mensaje == null) ? "" : mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setAccionVictoria(Runnable accionVictoria) {
        this.accionVictoria = accionVictoria;
    }

    // VICTORIA
    private boolean verificarVictoria() {

        if (elementosRecolectados >= 3 && !victoria) {

            victoria = true;
            partidaTerminada = true;

            Object[] opciones = {"Salir al mapa"};

            progreso.desbloquear(2);
            progreso.guardar();
            
            JOptionPane.showMessageDialog(
                    null,
                    "¡Ciudad 1 completada!\nLa Ciudad 2 fue desbloqueada."
            );

            if (accionVictoria != null) {
                accionVictoria.run();
            }

            return true;
        }

        return false;
    }
}
    
