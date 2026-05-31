package ciudadbusquedas.vista;

import ciudadbusquedas.modelo.ArbolBusqueda;
import ciudadbusquedas.modelo.BusquedaLineal;
import ciudadbusquedas.modelo.ResultadoBusqueda;

import ciudadbusquedas.util.LectorArchivo;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.io.File;

public class VentanaBusquedas extends JFrame {

    private static final long
            serialVersionUID = 1L;

    // COMPONENTES

    private JTextField txtPalabra;

    private JButton btnCargarArchivo;

    private JButton btnBuscar;

    private JTextArea areaResultados;

    // ESTRUCTURAS

    private ArbolBusqueda arbol;

    private BusquedaLineal lista;

    public VentanaBusquedas() {

        arbol = new ArbolBusqueda();

        lista = new BusquedaLineal();

        setTitle("Ciudad Busquedas");

        setSize(900, 600);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        inicializar();
    }

    private void inicializar() {

        setLayout(new BorderLayout());

        // PANEL SUPERIOR

        JPanel superior = new JPanel();

        superior.setLayout(
                new GridLayout(2, 1)
        );

        JPanel fila1 = new JPanel();

        btnCargarArchivo =
                new JButton(
                        "Cargar TXT"
                );

        fila1.add(btnCargarArchivo);

        superior.add(fila1);

        JPanel fila2 = new JPanel();

        fila2.add(
                new JLabel("Palabra:")
        );

        txtPalabra = new JTextField(20);

        fila2.add(txtPalabra);

        btnBuscar =
                new JButton("Buscar");

        fila2.add(btnBuscar);

        superior.add(fila2);

        add(superior, BorderLayout.NORTH);

        // AREA RESULTADOS

        areaResultados = new JTextArea();

        areaResultados.setEditable(false);

        JScrollPane scroll = new JScrollPane(areaResultados);

        add(scroll, BorderLayout.CENTER);

        eventos();
    }

    private void eventos() {

        btnCargarArchivo.addActionListener(e -> cargarArchivo());

        btnBuscar.addActionListener( e -> buscar());
    }

    private void cargarArchivo() {

        JFileChooser chooser =new JFileChooser();

        int resultado = chooser.showOpenDialog(this);

        if(resultado== JFileChooser.APPROVE_OPTION) {

            File archivo =chooser.getSelectedFile();

            // REINICIAR ESTRUCTURAS

            arbol = new ArbolBusqueda();

            lista = new BusquedaLineal();

            // CARGAR

            LectorArchivo.cargarArchivo(
                    archivo.getAbsolutePath(),
                    arbol,
                    lista
            );

            JOptionPane.showMessageDialog( this,"Archivo cargado correctamente");
        }
    }

    private void buscar() {

        String palabra =
                txtPalabra.getText()
                        .trim()
                        .toLowerCase();

        if(palabra.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ingrese una palabra"
            );

            return;
        }

        // BUSQUEDA LINEAL

        ResultadoBusqueda resultadoLineal =
                lista.buscar(palabra);

        // BUSQUEDA ARBOL

        ResultadoBusqueda resultadoArbol =
                arbol.buscar(palabra);

        mostrarResultados(
                palabra,
                resultadoLineal,
                resultadoArbol
        );
    }

    private void mostrarResultados(
            String palabra,
            ResultadoBusqueda lineal,
            ResultadoBusqueda arbolRes
    ) {

        areaResultados.setText("");

        areaResultados.append(
                "PALABRA BUSCADA: "
                        + palabra
                        + "\n\n"
        );

        // RESULTADO LINEAL

        areaResultados.append(
                "===== BUSQUEDA LINEAL =====\n"
        );

        if(lineal != null) {

            areaResultados.append(
                    "Linea: "
                            + lineal.getLinea()
                            + "\n"
            );

            areaResultados.append(
                    "Posicion: "
                            + lineal.getPosicion()
                            + "\n"
            );

            areaResultados.append(
                    "Tiempo: "
                            + lineal.getTiempo()
                            + " ns\n"
            );

            areaResultados.append(
                    "Operaciones: "
                            + lineal.getOperaciones()
                            + "\n\n"
            );

        } else {

            areaResultados.append(
                    "Palabra no encontrada\n\n"
            );
        }

        // RESULTADO ARBOL

        areaResultados.append(
                "===== ARBOL BINARIO =====\n"
        );

        if(arbolRes != null) {

            areaResultados.append(
                    "Linea: "
                            + arbolRes.getLinea()
                            + "\n"
            );

            areaResultados.append(
                    "Posicion: "
                            + arbolRes.getPosicion()
                            + "\n"
            );

            areaResultados.append(
                    "Tiempo: "
                            + arbolRes.getTiempo()
                            + " ns\n"
            );

            areaResultados.append(
                    "Operaciones: "
                            + arbolRes.getOperaciones()
                            + "\n\n"
            );

        } else {

            areaResultados.append(
                    "Palabra no encontrada\n\n"
            );
        }

        compararResultados(
                lineal,
                arbolRes
        );
    }

    private void compararResultados(
            ResultadoBusqueda lineal,
            ResultadoBusqueda arbolRes
    ) {

        if(lineal == null
                || arbolRes == null) {

            return;
        }

        areaResultados.append(
                "===== COMPARACION =====\n"
        );

        if(arbolRes.getTiempo()
                < lineal.getTiempo()) {

            areaResultados.append(
                    "El ABB fue mas rapido\n"
            );

        } else {

            areaResultados.append(
                    "La busqueda lineal fue mas rapida\n"
            );
        }

        if(arbolRes.getOperaciones()
                < lineal.getOperaciones()) {

            areaResultados.append(
                    "El ABB realizo menos operaciones\n"
            );

        } else {

            areaResultados.append(
                    "La busqueda lineal realizo menos operaciones\n"
            );
        }
    }
}