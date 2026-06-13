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

        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));

        JTextArea instrucciones = new JTextArea("""
                Forma esperada:
                T(n)=aT(n/b)+n
                T(n)=aT(n/b)+n^k
                Donde:
                a > 0
                b > 1

                Escriba su recurrencia debajo en la casilla
                Descubre los 3 casos para ganar
                """);
        instrucciones.setEditable(false);
        centro.add(instrucciones);

        campoRecurrencia = new JTextField();
        centro.add(campoRecurrencia);

        botonAnalizar = new JButton("Analizar");
        centro.add(botonAnalizar);

        resultado = new JTextArea();
        resultado.setEditable(false);
        JScrollPane scrollResultado = new JScrollPane(resultado);
        scrollResultado.setPreferredSize(new Dimension(650, 250));
        centro.add(scrollResultado);

        panelArbol = new PanelArbolRecurrencia();
        JScrollPane scrollArbol = new JScrollPane(panelArbol);
        scrollArbol.setPreferredSize(new Dimension(650, 250));
        centro.add(scrollArbol);

        add(centro, BorderLayout.CENTER);
        botonAnalizar.addActionListener(e -> analizar());
    }

    /**
     * Procesa la recurrencia ingresada por el usuario
     * Valida, analiza con el teorema maestro, actualiza progreso y dibuja el arbol.
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

        StringBuilder texto = new StringBuilder();
        texto.append("Recurrencia válida\n");
        texto.append("a = ").append(r.getA())
             .append(", b = ").append(r.getB())
             .append(", k = ").append(r.getK())
             .append("\n\n");
        texto.append(analizador.analizar(r));
        texto.append("\n\n=== Expansión ===\n");
        texto.append("Patrón detectado:\n");
        texto.append("Nivel i -> ").append(r.getA())
             .append("^i problemas de tamaño n/").append(r.getB()).append("^i\n\n");
        texto.append("=== Progreso ===\n").append(condicion.estado()).append("\n");

        for (NodoExpansion nodo : lista) {
            texto.append("Nivel ").append(nodo.getNivel())
                 .append(" -> ").append(nodo.getCantidadProblemas())
                 .append(" problema(s) -> tamaño ").append(nodo.getTamanio())
                 .append("\n");
        }

        resultado.setText(texto.toString());

        panelArbol.setRecurrencia(r.getA(), r.getB(), r.getK());
        panelArbol.setNodos(lista);
        revalidate();
        panelArbol.repaint();

        //Se activa al encontrar los 3 casos, y devuelve al mapa(mapa WIP)
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
}