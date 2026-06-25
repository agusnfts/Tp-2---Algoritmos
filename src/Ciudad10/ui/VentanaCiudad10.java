package Ciudad10.ui;

import Ciudad10.logica.AnalizadorMaestro;
import Ciudad10.logica.CondicionVictoria;
import Ciudad10.logica.ParserRecurrencia;
import Ciudad10.logica.Recurrencia;
import principal.PanelMapa;
import principal.ProgresoJuego;
import Ciudad10.logica.ExpansorRecurrencia;
import Ciudad10.logica.NodoExpansion;
import java.util.List;
import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal de la Ciudad10
 * Permite ingresar recurrencias, analizarlas con el teorema maestro y visualizar el arbol.
 */
public class VentanaCiudad10 extends JFrame {

    private JTextField campoRecurrencia;
    private JTextArea resultado;
    private JButton botonAnalizar;
    private PanelArbolRecurrencia panelArbol;
    private CondicionVictoria condicion;
    private ProgresoJuego progreso;
    private JButton botonSalir;
    private JSlider sliderPasos;
    private Runnable accionSalir;

    /**
     * PRE: progreso != null.
     * POST: crea la interfaz grafica de la Ciudad 10.
     */
    public VentanaCiudad10(ProgresoJuego progreso) {
    	this.progreso = progreso;
        this.condicion = new CondicionVictoria();

        setTitle("Ciudad 10 - Complejidad Algoritmica");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Complejidad Algoritmica", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(titulo, BorderLayout.NORTH);

        JPanel centro = 
        		new JPanel();
        centro.setLayout(
        		new BoxLayout(centro, BoxLayout.Y_AXIS)
        		);

        JTextArea instrucciones = new JTextArea("""
        		Forma: T(n)=aT(n/b)+n o T(n)=aT(n/b)+n^k
        		a > 0, b > 1
        		Descubre los 3 casos para ganar
        		""");
        instrucciones.setEditable(false);
        centro.add(instrucciones);
        
        instrucciones.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 60)
        );

        instrucciones.setPreferredSize(
                new Dimension(650, 60)
        );

        campoRecurrencia = new JTextField();
        campoRecurrencia.setMaximumSize(
                new Dimension(400, 50)
        );
        centro.add(campoRecurrencia);

        botonAnalizar = new JButton("Analizar");
        centro.add(botonAnalizar);

        botonSalir = new JButton("Salir al mapa");
        centro.add(botonSalir);
        
        resultado = new JTextArea();
        resultado.setEditable(false);
        JScrollPane scrollResultado = new JScrollPane(resultado);
        scrollResultado.setPreferredSize(new Dimension(650, 120));
        centro.add(scrollResultado);

        panelArbol = new PanelArbolRecurrencia();
        JScrollPane scrollArbol = new JScrollPane(panelArbol);
        scrollArbol.setPreferredSize(new Dimension(650, 250));
        centro.add(scrollArbol);
        
        sliderPasos = new JSlider();
        sliderPasos.setMinimum(0);
        sliderPasos.setMaximum(0);
        centro.add(sliderPasos);
        
     // Permite visualizar progresivamente la expansión del arbol.
        sliderPasos.addChangeListener(e -> {
            int paso = sliderPasos.getValue();
            panelArbol.mostrarHastaNivel(paso);
        });

        add(centro, BorderLayout.CENTER);
        botonAnalizar.addActionListener(e -> analizar());
        botonSalir.addActionListener(e -> salirAlMapa());
    }

    /**
     * PRE: el usuario ingresó una recurrencia.
     * POST: analiza la recurrencia, actualiza el progreso,
     * muestra el resultado obtenido y dibuja el arbol de expansión.
     */
    private void analizar() {
        AnalizadorMaestro analizador = new AnalizadorMaestro();
        ParserRecurrencia parser = new ParserRecurrencia();
        Recurrencia r = parser.parsear(campoRecurrencia.getText());

        if (r == null) {
            JOptionPane.showMessageDialog(this, "No es una recurrencia válida.\nIntente nuevamente.");
            return;
        }

        int caso = analizador.obtenerCaso(r);
        if (!condicion.casoCompleto(caso)) {
        	condicion.completarCaso(caso);
            JOptionPane.showMessageDialog(this, "¡Descubriste el Caso " + caso + "!");
        }

        if (r.getA() <= 0 || r.getB() <= 1) {
            JOptionPane.showMessageDialog(this, "a debe ser > 0 y b debe ser > 1.");
            return;
        }

        ExpansorRecurrencia expansor = new ExpansorRecurrencia();
        List<NodoExpansion> lista = expansor.expandir(r, 4);
        
     //Permite recorrer los niveles generados.
        sliderPasos.setMaximum(lista.size() - 1);
        sliderPasos.setValue(lista.size() - 1);
        

        StringBuilder texto = new StringBuilder();
        texto.append("Recurrencia válida\n");
        texto.append("a = ").append(r.getA())
             .append(", b = ").append(r.getB())
             .append(", k = ").append(r.getK())
             .append("\n");
        texto.append(analizador.analizar(r));
        texto.append("\n\n=== Expansión ===\n");
        texto.append("Patrón detectado:\n");
        texto.append("Nivel i -> ").append(r.getA())
             .append("^i problemas de tamaño n/").append(r.getB()).append("^i\n\n");
        texto.append("=== Progreso ===\n").append(condicion.estado()).append("\n");


        resultado.setText(texto.toString());

        panelArbol.setRecurrencia(r.getA(), r.getB(), r.getK());
        panelArbol.setNodos(lista);
        revalidate();
        panelArbol.repaint();

        //Se activa al encontrar los 3 casos, y devuelve al mapa
        if (condicion.ciudadCompletada()) {

            JOptionPane.showMessageDialog(
                    this,
                    "¡Has completado los tres casos de la Ciudad 10!\n"
                    + "GANASTE EL VIDEOJUEGO!! Gracias por jugar."
            );

            if (progreso != null) {

                progreso.guardar();
            }

            dispose();
        }
    }
    
    /**
     * PRE: accion != null.
     * POST: establece la acción a ejecutar al salir al mapa.
     */
    public void setAccionSalir(Runnable accion) {
        this.accionSalir = accion;
    }

    /**
     * POST: ejecuta la acción de salida si fue definida y cierra la ventana.
     */
    private void salirAlMapa() {

        if (accionSalir != null) {
            accionSalir.run();
        }

        dispose();
    }
    
}