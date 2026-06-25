package Ciudad5.vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Ciudad5.util.LectorArchivo;
import principal.ProgresoJuego;
import utiles.ValidacionesUtiles;
import Ciudad5.modelo.ArbolBusqueda;
import Ciudad5.modelo.BusquedaLineal;
import Ciudad5.modelo.ResultadoBusqueda;

public class VentanaBusquedas extends JFrame {

    private JTextField txtPalabra;
    private JButton btnCargarArchivo;
    private JButton btnBuscar;
    private JButton btnSalir;

    private PanelCiudad5 panel;

    private ArbolBusqueda arbol;
    private BusquedaLineal lista;

    private ProgresoJuego progreso;

    private boolean archivoCargado = false;
    private int cantidadBusquedas = 0;

    /**
     * PRE:
     * - progreso != null
     *
     * POST:
     * - Se inicializa la ventana principal
     * - Se crean las estructuras de búsqueda (ABB y lista lineal)
     * - Se configura tamaño, título y comportamiento de cierre
     * - Se inicializa la interfaz gráfica
     */
    public VentanaBusquedas(ProgresoJuego progreso) {

        ValidacionesUtiles.esDistintoDeNull(progreso, "progreso");

        this.progreso = progreso;

        arbol = new ArbolBusqueda();
        lista = new BusquedaLineal();

        setTitle("Ciudad Busquedas");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        inicializar();
    }

    /**
     * PRE:
     * - No requiere condiciones externas
     *
     * POST:
     * - Se construye la interfaz gráfica de la ventana
     * - Se crean y organizan los componentes visuales
     * - Se asocian los eventos a los botones
     */
    private void inicializar() {

        setLayout(new BorderLayout());

        JPanel superior = new JPanel();
        superior.setLayout(new GridLayout(2, 1));

        JPanel fila1 = new JPanel();

        btnCargarArchivo = new JButton("Cargar TXT");
        fila1.add(btnCargarArchivo);

        superior.add(fila1);

        JPanel fila2 = new JPanel();

        fila2.add(new JLabel("Palabra:"));

        txtPalabra = new JTextField(20);
        fila2.add(txtPalabra);

        btnBuscar = new JButton("Buscar");
        fila2.add(btnBuscar);

        btnSalir = new JButton("Salir");
        fila2.add(btnSalir);

        superior.add(fila2);

        add(superior, BorderLayout.NORTH);

        panel = new PanelCiudad5(progreso);
        
        panel.mostrarInstrucciones();
        
        add(panel, BorderLayout.CENTER);

        eventos();
    }

    /**
     * PRE:
     * - Componentes inicializados
     *
     * POST:
     * - Se asignan los eventos a los botones
     */
    private void eventos() {

        btnCargarArchivo.addActionListener(e -> cargarArchivo());
        btnBuscar.addActionListener(e -> buscar());
        btnSalir.addActionListener(e -> dispose());
    }

    /**
     * PRE:
     * - Usuario selecciona un archivo válido
     *
     * POST:
     * - Se carga el archivo en memoria
     * - Se reinician las estructuras (ABB y lista lineal)
     * - Se insertan todas las palabras en ambas estructuras
     * - Se marca archivoCargado = true
     */
    private void cargarArchivo() {

        JFileChooser chooser = new JFileChooser();

        int resultado = chooser.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {

            File archivo = chooser.getSelectedFile();

            ValidacionesUtiles.esDistintoDeNull(archivo, "archivo");

            arbol = new ArbolBusqueda();
            lista = new BusquedaLineal();

            LectorArchivo.cargarArchivo(
                    archivo.getAbsolutePath(),
                    arbol,
                    lista
            );

            archivoCargado = true;

            JOptionPane.showMessageDialog(this, "Archivo cargado correctamente");
        }
    }

    /**
     * PRE:
     * - palabra ingresada != null y longitud >= 1
     * - archivo previamente cargado
     *
     * POST:
     * - Realiza búsqueda en ABB y en lista lineal
     * - Obtiene resultados de ambas estructuras
     * - Muestra comparación de rendimiento en pantalla
     * - Incrementa contador de búsquedas
     * - Verifica condición de victoria
     */
    private void buscar() {

        String palabra = txtPalabra.getText().trim().toLowerCase();

        ValidacionesUtiles.validarLongitudDeTexto(palabra, 1, null, "palabra");

        ResultadoBusqueda resultadoLineal = lista.buscar(palabra);
        ResultadoBusqueda resultadoArbol = arbol.buscar(palabra);

        mostrarResultados(palabra, resultadoLineal, resultadoArbol);

        cantidadBusquedas++;

        verificarVictoria();
    }

    /**
     * PRE:
     * - palabra != null
     *
     * POST:
     * - Genera un texto con los resultados de ambas búsquedas
     * - Muestra tiempos, posiciones y operaciones
     * - Compara rendimiento entre ABB y búsqueda lineal
     * - Envía el resultado al panel gráfico
     */
    private void mostrarResultados(
            String palabra,
            ResultadoBusqueda lineal,
            ResultadoBusqueda arbolRes
    ) {

        ValidacionesUtiles.esDistintoDeNull(palabra, "palabra");

        String texto = "";

        texto += "PALABRA BUSCADA: " + palabra + "\n\n";

        texto += "===== BUSQUEDA LINEAL =====\n";

        if (lineal != null) {

            texto += "Linea: " + lineal.getLinea() + "\n";
            texto += "Posicion: " + lineal.getPosicion() + "\n";
            texto += "Tiempo: " + lineal.getTiempo() + " ns\n";
            texto += "Operaciones: " + lineal.getOperaciones() + "\n\n";

        } else {
            texto += "Palabra no encontrada\n\n";
        }

        texto += "===== ARBOL BINARIO =====\n";

        if (arbolRes != null) {

            texto += "Linea: " + arbolRes.getLinea() + "\n";
            texto += "Posicion: " + arbolRes.getPosicion() + "\n";
            texto += "Tiempo: " + arbolRes.getTiempo() + " ns\n";
            texto += "Operaciones: " + arbolRes.getOperaciones() + "\n\n";

        } else {
            texto += "Palabra no encontrada\n\n";
        }

        if (lineal != null && arbolRes != null) {

            texto += "===== COMPARACION =====\n";

            if (arbolRes.getTiempo() < lineal.getTiempo()) {
                texto += "El ABB fue mas rapido\n";
            } else {
                texto += "La busqueda lineal fue mas rapida\n";
            }

            if (arbolRes.getOperaciones() < lineal.getOperaciones()) {
                texto += "El ABB realizo menos operaciones\n";
            } else {
                texto += "La busqueda lineal realizo menos operaciones\n";
            }
        }

        panel.mostrarResultado(texto);
    }

    /**
     * PRE:
     * - cantidadBusquedas >= 0
     * - archivoCargado definido
     *
     * POST:
     * - Si archivo fue cargado y se realizaron al menos 3 búsquedas:
     *      * se desbloquea la Ciudad 6 si cumplis los objetivos
     *      * se guarda el progreso
     *      * se muestra mensaje de victoria (una sola vez)
     */
    private void verificarVictoria() {

        ValidacionesUtiles.validarMayorOIgualACero(
                cantidadBusquedas,
                "cantidadBusquedas"
        );

        if (archivoCargado && cantidadBusquedas >= 3) {

            if (progreso != null && !progreso.estaDesbloqueada(6)) {

            	hacerPreguntas();
            }
        }
    }
    
    /**
     * PRE:
     * - progreso != null.
     * - La Ciudad 5 aún no debe estar desbloqueada.
     * - El usuario completó los objetivos previos de la ciudad
     *   (cargar un archivo y realizar al menos tres búsquedas).
     *
     * POST:
     * - Se muestran dos preguntas de opción múltiple sobre
     *   búsqueda lineal y árboles binarios de búsqueda.
     * - Si ambas respuestas son correctas:
     *      * se desbloquea la Ciudad 6,
     *      * se guarda el progreso,
     *      * se muestra un mensaje de felicitación.
     * - Si alguna respuesta es incorrecta:
     *      * la Ciudad 6 permanece bloqueada,
     *      * se muestra un mensaje indicando que debe volver a intentarlo.
     */
    
    
    private void hacerPreguntas() {

        String[] opciones1 = {
                "O(log n)",
                "O(n)",
                "O(n²)"
        };

        int respuesta1 =
                JOptionPane.showOptionDialog(
                        this,
                        "¿Cuál es la complejidad de una búsqueda lineal?",
                        "Pregunta 1",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        opciones1,
                        opciones1[0]
                );

        String[] opciones2 = {
                "Se recorren todos los elementos",
                "Usa una estructura jerárquica de nodos",
                "Ordena automáticamente los datos"
        };

        int respuesta2 =
                JOptionPane.showOptionDialog(
                        this,
                        "¿Qué característica tiene un Árbol Binario de Búsqueda?",
                        "Pregunta 2",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        opciones2,
                        opciones2[0]
                );

        boolean correcta1 = (respuesta1 == 1);
        boolean correcta2 = (respuesta2 == 1);

        if (correcta1 && correcta2) {

            progreso.desbloquear(6);
            progreso.guardar();

            JOptionPane.showMessageDialog(
                    this,
                    "¡Felicitaciones!\n"
                    + "Respondiste correctamente.\n"
                    + "La Ciudad 6 ha sido desbloqueada."
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Alguna respuesta fue incorrecta.\n"
                    + "Debes volver a intentarlo.",
                    "Error",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }
    
}
