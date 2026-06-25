package Ciudad8.vista;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import Ciudad8.bitmap.Bitmap;
import Ciudad8.modelo.Hanoi;
import Ciudad8.modelo.Movimiento;
import principal.ProgresoJuego;
import utiles.SistemaUtiles;
import utiles.ValidacionesUtiles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PanelCiudad8 extends JPanel {

    private Bitmap bmp;

    private Runnable accionSalir;
    
    private ProgresoJuego progreso;

    private JLabel lblImagen;

    private boolean[] completados = new boolean[9];
    
    /**
     * PRE:
     * - progreso != null
     *
     * POST:
     * - Se inicializa el panel de la Ciudad 8.
     * - Se crea el Bitmap utilizado para la representación gráfica.
     * - Se dibuja la pantalla inicial del juego.
     * - Se crea un selector para elegir la cantidad de discos
     *   (de 3 a 8).
     * - Se crean los botones de inicio y salida.
     * - Se agrega un panel inferior con los controles del usuario.
     * - Se configura el botón de inicio para ejecutar el algoritmo
     *   con la cantidad de discos seleccionada.
     * - Se configura el botón de salida para volver al mapa.
     * - Se inicia la actualización periódica de la imagen mostrada.
     */
    
    public PanelCiudad8(ProgresoJuego progreso) {

        ValidacionesUtiles.esDistintoDeNull(
                progreso,
                "progreso"
        );

        this.progreso = progreso;

        setLayout(new BorderLayout());

        bmp = new Bitmap(
                1300,
                900
        );

        dibujarMenu();

        lblImagen =
                new JLabel(
                        new ImageIcon(
                                bmp.getImage()
                        )
                );

        add(
                lblImagen,
                BorderLayout.CENTER
        );

        JPanel menuPanel =
                new JPanel();

        JComboBox<Integer> comboDiscos =
                new JComboBox<>();

        for (int i = 3; i <= 8; i++) {
            comboDiscos.addItem(i);
        }

        JButton btnIniciar =
                new JButton(
                        "Iniciar Hanoi"
                );

        btnIniciar.addActionListener(e -> {

            int discos =
                    (Integer) comboDiscos.getSelectedItem();

            iniciar(discos);
        });

        JButton btnSalir =
                new JButton(
                        "Salir"
                );

        btnSalir.addActionListener(
                e -> salirAlMapa()
        );

        menuPanel.add(
                new JLabel("Discos:")
        );

        menuPanel.add(
                comboDiscos
        );

        menuPanel.add(
                btnIniciar
        );

        menuPanel.add(
                btnSalir
        );

        add(
                menuPanel,
                BorderLayout.SOUTH
        );

        Timer timer =
                new Timer(
                        100,
                        e -> lblImagen.setIcon(
                                new ImageIcon(
                                        bmp.getImage()
                                )
                        )
                );

        timer.start();

    }

    /**
     * PRE:
     * - El Bitmap debe estar inicializado
     *
     * POST:
     * - Se limpia la pantalla
     * - Se muestra el título de la ciudad
     * - Se muestra el nombre del desafío "Torres de Hanoi"
     * - Se muestran las instrucciones iniciales del nivel
     */
    private void dibujarMenu() {

        bmp.rellenar(Color.BLACK);

        bmp.drawText(
                "CIUDAD N°8",
                500,
                150,
                new Font(
                        "Courier New",
                        Font.BOLD,
                        40
                ),
                Color.CYAN,
                Color.BLACK
        );

        bmp.drawText(
                "TORRES DE HANOI",
                400,
                250,
                new Font(
                        "Courier New",
                        Font.BOLD,
                        60
                ),
                Color.GREEN,
                Color.BLACK
        );

        bmp.drawText(
                "INSTRUCCIONES:",
                450,
                380,
                new Font("Courier New", Font.BOLD, 30),
                Color.WHITE,
                Color.BLACK
        );

        bmp.drawText(
                "- Elegí cantidad de discos (3 a 8)",
                300,
                430,
                new Font("Courier New", Font.PLAIN, 22),
                Color.LIGHT_GRAY,
                Color.BLACK
        );

        bmp.drawText(
                "- Observá la resolución automática del algoritmo",
                300,
                470,
                new Font("Courier New", Font.PLAIN, 22),
                Color.LIGHT_GRAY,
                Color.BLACK
        );

        bmp.drawText(
                "- Completá al menos 3 niveles distintos",
                300,
                510,
                new Font("Courier New", Font.PLAIN, 22),
                Color.LIGHT_GRAY,
                Color.BLACK
        );

        bmp.drawText(
                "- Respondé preguntas para desbloquear la siguiente ciudad",
                300,
                550,
                new Font("Courier New", Font.PLAIN, 22),
                Color.LIGHT_GRAY,
                Color.BLACK
        );
    }
    
    
    /**
     * PRE:
     * - discos debe ser un número entero válido
     * - 0 <= discos < completados.length
     *
     * POST:
     * - Marca como completada la configuración del nivel correspondiente
     *   a la cantidad de discos utilizada
     * - Si el valor de discos está en el rango 3 a 8,
     *   se actualiza el estado en el arreglo completados
     */
    
    private void marcarCompletado(int discos) {

        if (discos >= 3 && discos <= 8) {
            completados[discos] = true;
        }
    }
    
    /**
     * PRE:
     * - El arreglo completados debe estar inicializado
     * - Los índices válidos considerados son del 3 al 8
     *
     * POST:
     * - Recorre las configuraciones de discos del 3 al 8
     * - Cuenta cuántas configuraciones fueron completadas
     * - Devuelve true si el usuario completó al menos 3 configuraciones
     *   distintas del problema de Hanoi
     */
    
    private boolean tieneMinimoTres() {

        int count = 0;

        for (int i = 3; i <= 8; i++) {
            if (completados[i]) {
                count++;
            }
        }

        return count >= 3;
    }
    
   

    /**
     * POST: ejecuta la acción de salida si fue definida.
     */
    private void salirAlMapa() {

        if (accionSalir != null) {
            accionSalir.run();
        }
    }

    /**
     * PRE:
     * - discos != null
     * - discos > 0
     * - el valor de discos pertenece al rango permitido del juego
     *   (ej: entre 3 y 8 discos)
     * - el panel y el Bitmap deben estar correctamente inicializados
     *
     * POST:
     * - Se crea una nueva ejecución del algoritmo de Torres de Hanoi.
     * - Se valida la cantidad de discos ingresada.
     * - Se generan los movimientos necesarios para resolver el problema.
     * - Se inicializan las torres con la cantidad de discos indicada.
     * - Se actualiza la representación gráfica después de cada movimiento.
     * - Se muestra la animación completa del algoritmo.
     * - Al finalizar la ejecución se invoca finalizarCiudad().
     */

private void iniciar(int discos) {

    ValidacionesUtiles.validarMayorACero(
            discos,
            "discos"
    );

    ValidacionesUtiles.validarRangoNumerico(
            discos,
            3,
            8,
            "discos"
    );

    new Thread(() -> {

        List<List<Integer>> torres =
                crearTorres(discos);

        dibujarEstado(
                torres
        );

        Hanoi hanoi =
                new Hanoi();

        List<Movimiento> movimientos =
                hanoi.resolver(discos);

        SistemaUtiles.esperar(1000);

        for (Movimiento m : movimientos) {

            int disco =
                    torres.get(
                            m.getOrigen()
                    ).remove(
                            torres.get(
                                    m.getOrigen()
                            ).size() - 1
                    );

            torres.get(
                    m.getDestino()
            ).add(disco);

            dibujarEstado(
                    torres
            );

            SistemaUtiles.esperar(700);
        }

        SwingUtilities.invokeLater(
                () -> finalizarCiudad(discos)
        );

    }).start();
}

    /**
     * PRE: discos > 0
     *
     * POST:
     * - Se crean tres torres vacías
     * - Todos los discos se ubican inicialmente en la primera torre
     * - Se devuelve la estructura que representa el estado inicial del juego
     */
    
    private List<List<Integer>> crearTorres(
            int discos
    ) {

        ValidacionesUtiles.validarMayorACero(
                discos,
                "discos"
        );

        List<List<Integer>> torres =
                new ArrayList<>();

        torres.add(
                new ArrayList<>()
        );

        torres.add(
                new ArrayList<>()
        );

        torres.add(
                new ArrayList<>()
        );

        for (int i = discos;
             i >= 1;
             i--) {

            torres.get(0).add(i);
        }

        return torres;
    }

    /**
     * PRE: El algoritmo de Hanoi ha finalizado correctamente
     *
     * POST:
     * - Se informa al usuario que completó la ciudad
     * - Se desbloquea la Ciudad 9
     * - Se guarda el progreso actualizado
     */
    private void finalizarCiudad(int discos) {

        marcarCompletado(discos);

        mostrarProgreso();
        
        JOptionPane.showMessageDialog(
                this,
                "Completaste Hanoi con " + discos + " discos."
        );

        if (tieneMinimoTres()) {
            hacerPreguntas();
        }
    }

    /**
     * PRE:
     * - torres != null
     * - La lista debe contener las tres torres del juego
     *
     * POST:
     * - Se redibuja completamente el estado actual de las torres
     * - Se muestran los postes de Hanoi
     * - Se representan gráficamente todos los discos en sus posiciones actuales
     */
    
    private void dibujarEstado(
            List<List<Integer>> torres
    ) {

        ValidacionesUtiles.esDistintoDeNull(
                torres,
                "torres"
        );

        bmp.rellenar(Color.BLACK);

        int[] posiciones = {
                250,
                650,
                1050
        };

        for (int x : posiciones) {

            bmp.drawLine(
                    x,
                    250,
                    x,
                    700,
                    Color.WHITE
            );
        }

        bmp.drawLine(
                150,
                700,
                1150,
                700,
                Color.WHITE
        );

        for (int torre = 0;
             torre < 3;
             torre++) {

            List<Integer> discos =
                    torres.get(torre);

            ValidacionesUtiles.esDistintoDeNull(
                    discos,
                    "discos"
            );

            for (int i = 0;
                 i < discos.size();
                 i++) {

                int valor =
                        discos.get(i);

                int ancho =
                        60 + valor * 40;

                int x =
                        posiciones[torre]
                                - ancho / 2;

                int y =
                        650
                                - i * 40;

                bmp.fillRectangle(
                        x,
                        y,
                        ancho,
                        30,
                        Color.GREEN
                );
            }
        }
    }
    
    /**
     * PRE:
     * - El objeto progreso puede ser null o válido
     * - El usuario debe haber completado al menos 3 configuraciones
     *   del problema de Torres de Hanoi antes de llamar este método
     *
     * POST:
     * - Si la Ciudad 9 ya está desbloqueada, el método finaliza sin ejecutar preguntas
     * - Se muestran dos preguntas de opción múltiple relacionadas con Torres de Hanoi
     * - Se evalúan las respuestas del usuario
     * - Si ambas respuestas son correctas:
     *      * Se desbloquea la Ciudad 9
     *      * Se guarda el progreso
     *      * Se muestra un mensaje de felicitación
     * - Si alguna respuesta es incorrecta:
     *      * Se muestra un mensaje de error
     *      * No se modifica el progreso del juego
     */
    
    private void hacerPreguntas() {

    	if (progreso != null && progreso.estaDesbloqueada(9)) {
    	    return;
    	}
    	
        String[] opciones1 = {
                "Es un algoritmo iterativo sin recursión",
                "Es un problema clásico resuelto con recursión",
                "Es un algoritmo de ordenamiento"
        };

        int r1 = JOptionPane.showOptionDialog(
                this,
                "¿Qué representa el problema de Torres de Hanoi?",
                "Pregunta 1",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones1,
                opciones1[0]
        );

        String[] opciones2 = {
                "Mover todos los discos libremente",
                "Mover solo un disco por vez respetando el orden",
                "Intercambiar torres completas"
        };

        int r2 = JOptionPane.showOptionDialog(
                this,
                "¿Cuál es la regla principal del juego?",
                "Pregunta 2",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones2,
                opciones2[0]
        );

        boolean ok1 = (r1 == 1);
        boolean ok2 = (r2 == 1);

        if (ok1 && ok2) {

          

              progreso.desbloquear(9);
              progreso.guardar();

              JOptionPane.showMessageDialog(
                       this,
                        "¡Correcto!\nDesbloqueaste la siguiente ciudad."
              );
           

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Respuesta incorrecta.\nIntentá nuevamente.",
                    "Error",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }
    
    /**
     * PRE:
     * - El arreglo completados debe estar inicializado
     * - Los índices válidos considerados son del 3 al 8
     *
     * POST:
     * - Calcula cuántas configuraciones de Hanoi fueron completadas
     *   dentro del rango de discos 3 a 8
     * - Muestra un mensaje emergente con el progreso actual del jugador
     * - El progreso se expresa como cantidad completada sobre el objetivo mínimo (3)
     */
    
    private void mostrarProgreso() {

        int total = 0;

        for (int i = 3; i <= 8; i++) {
            if (completados[i]) {
                total++;
            }
        }

        String mensaje =
                "Progreso Hanoi:\n" +
                total + " / 3 configuraciones completadas";

        JOptionPane.showMessageDialog(
                this,
                mensaje
        );
    }
    
    /**
     * PRE: accion != null
     *
     * POST: Se establece la acción a ejecutar al salir al mapa
     */
    public void setAccionSalir(Runnable accion) {
        this.accionSalir = accion;
    }
    
    
}
