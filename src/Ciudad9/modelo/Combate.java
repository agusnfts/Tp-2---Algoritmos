package Ciudad9.modelo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Combate {
    private static final int VIDA_JUGADOR = 100;
    private static final int DANIO_ATAQUE = 20;
    private static final int DANIO_HABILIDAD = 40;
    private static final int CURACION_DEFENSA = 15;

    private JugadorCombate jugador;
    private List<Enemigo> enemigos;
    private Queue<Personaje> turnos;
    private Stack<Accion> acciones;

    public Combate(String nombreJugador) {
        jugador = new JugadorCombate(nombreJugador, VIDA_JUGADOR);
        enemigos = new ArrayList<>();
        turnos = new LinkedList<>();
        acciones = new Stack<>();
    }

    public void agregarEnemigo(Enemigo enemigo) {
        enemigos.add(enemigo);
        turnos.offer(enemigo);
    }

    public void iniciarCombate() {
        turnos.offer(jugador);
    }

    public void apilarAccion(Accion accion) {
        acciones.push(accion);
    }

    public void procesarTurno() {

        if (turnos.isEmpty()) {
            return;
        }

        Personaje actual = turnos.poll();

        if (actual instanceof JugadorCombate) {

            procesarTurnoJugador();

        } else {

            Enemigo enemigo = (Enemigo) actual;

            if (enemigo.estaVivo()) {
                jugador.recibirDanio(enemigo.getDanio());
            }
        }

        if (actual.estaVivo()) {
            turnos.offer(actual);
        }

        enemigos.removeIf(e -> !e.estaVivo());
    }

    /**procesa una ronda completa.juega el jugador y despues todos los enemigos. */
    public void procesarRonda() {

        int cantidadTurnos = turnos.size();

        for (int i = 0; i < cantidadTurnos; i++) {

            if (combateTerminado()) {
                break;
            }

            procesarTurno();
        }
    }

    private void procesarTurnoJugador() {

        while (!acciones.isEmpty()) {

            Accion accion = acciones.pop();

            ejecutarAccion(accion);
        }
    }

    private void ejecutarAccion(Accion accion) {

        if (accion.getTipo() == TipoAccion.ATAQUE) {

            atacarPrimerEnemigo(DANIO_ATAQUE);

        } else if (accion.getTipo() == TipoAccion.DEFENSA) {

            jugador.curar(CURACION_DEFENSA);

        } else if (accion.getTipo() == TipoAccion.HABILIDAD) {

            atacarPrimerEnemigo(DANIO_HABILIDAD);
        }
    }

    private void atacarPrimerEnemigo(int danio) {

        for (Enemigo e : enemigos) {

            if (e.estaVivo()) {

                e.recibirDanio(danio);

                return;
            }
        }
    }

    public boolean combateTerminado() {
        return !jugador.estaVivo() || enemigos.isEmpty();
    }

    public boolean jugadorGano() {
        return enemigos.isEmpty() && jugador.estaVivo();
    }

    public JugadorCombate getJugador() {
        return jugador;
    }

    public List<Enemigo> getEnemigos() {
        return enemigos;
    }
}