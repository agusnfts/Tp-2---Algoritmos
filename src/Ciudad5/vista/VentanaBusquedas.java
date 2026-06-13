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

    public VentanaBusquedas(ProgresoJuego progreso) {

        ValidacionesUtiles.esDistintoDeNull(
                progreso,
                "progreso"
        );

        this.progreso = progreso;

        arbol = new ArbolBusqueda();

        lista = new BusquedaLineal();

        setTitle("Ciudad Busquedas");

        setSize(900, 600);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        inicializar();
    }

    private void inicializar() {

        setLayout(
                new BorderLayout()
        );

        JPanel superior =
                new JPanel();

        superior.setLayout(
                new GridLayout(2, 1)
        );

        JPanel fila1 =
                new JPanel();

        btnCargarArchivo =
                new JButton(
                        "Cargar TXT"
                );

        fila1.add(
                btnCargarArchivo
        );

        superior.add(
                fila1
        );

        JPanel fila2 =
                new JPanel();

        fila2.add(
                new JLabel(
                        "Palabra:"
                )
        );

        txtPalabra =
                new JTextField(
                        20
                );

        fila2.add(
                txtPalabra
        );

        btnBuscar =
                new JButton(
                        "Buscar"
                );

        fila2.add(
                btnBuscar
        );

        btnSalir =
                new JButton(
                        "Salir"
                );

        fila2.add(
                btnSalir
        );

        superior.add(
                fila2
        );

        add(
                superior,
                BorderLayout.NORTH
        );

        panel =
                new PanelCiudad5(progreso);

        add(
                panel,
                BorderLayout.CENTER
        );

        eventos();
    }

    private void eventos() {

        btnCargarArchivo.addActionListener(
                e -> cargarArchivo()
        );

        btnBuscar.addActionListener(
                e -> buscar()
        );

        btnSalir.addActionListener(
                e -> dispose()
        );
    }

    private void cargarArchivo() {

        JFileChooser chooser =
                new JFileChooser();

        int resultado =
                chooser.showOpenDialog(
                        this
                );

        if (resultado ==
                JFileChooser.APPROVE_OPTION) {

            File archivo =
                    chooser.getSelectedFile();

            ValidacionesUtiles.esDistintoDeNull(
                    archivo,
                    "archivo"
            );

            arbol =
                    new ArbolBusqueda();

            lista =
                    new BusquedaLineal();

            LectorArchivo.cargarArchivo(
                    archivo.getAbsolutePath(),
                    arbol,
                    lista
            );

            archivoCargado = true;

            JOptionPane.showMessageDialog(
                    this,
                    "Archivo cargado correctamente"
            );
        }
    }

    private void buscar() {

        String palabra =
                txtPalabra
                        .getText()
                        .trim()
                        .toLowerCase();

        ValidacionesUtiles.validarLongitudDeTexto(
                palabra,
                1,
                null,
                "palabra"
        );

        ResultadoBusqueda resultadoLineal =
                lista.buscar(
                        palabra
                );

        ResultadoBusqueda resultadoArbol =
                arbol.buscar(
                        palabra
                );

        mostrarResultados(
                palabra,
                resultadoLineal,
                resultadoArbol
        );

        cantidadBusquedas++;

        verificarVictoria();
    }

    private void mostrarResultados(
            String palabra,
            ResultadoBusqueda lineal,
            ResultadoBusqueda arbolRes
    ) {

        ValidacionesUtiles.esDistintoDeNull(
                palabra,
                "palabra"
        );

        String texto = "";

        texto +=
                "PALABRA BUSCADA: "
                        + palabra
                        + "\n\n";

        texto +=
                "===== BUSQUEDA LINEAL =====\n";

        if (lineal != null) {

            texto +=
                    "Linea: "
                            + lineal.getLinea()
                            + "\n";

            texto +=
                    "Posicion: "
                            + lineal.getPosicion()
                            + "\n";

            texto +=
                    "Tiempo: "
                            + lineal.getTiempo()
                            + " ns\n";

            texto +=
                    "Operaciones: "
                            + lineal.getOperaciones()
                            + "\n\n";

        } else {

            texto +=
                    "Palabra no encontrada\n\n";
        }

        texto +=
                "===== ARBOL BINARIO =====\n";

        if (arbolRes != null) {

            texto +=
                    "Linea: "
                            + arbolRes.getLinea()
                            + "\n";

            texto +=
                    "Posicion: "
                            + arbolRes.getPosicion()
                            + "\n";

            texto +=
                    "Tiempo: "
                            + arbolRes.getTiempo()
                            + " ns\n";

            texto +=
                    "Operaciones: "
                            + arbolRes.getOperaciones()
                            + "\n\n";

        } else {

            texto +=
                    "Palabra no encontrada\n\n";
        }

        if (lineal != null
                && arbolRes != null) {

            texto +=
                    "===== COMPARACION =====\n";

            if (arbolRes.getTiempo()
                    < lineal.getTiempo()) {

                texto +=
                        "El ABB fue mas rapido\n";

            } else {

                texto +=
                        "La busqueda lineal fue mas rapida\n";
            }

            if (arbolRes.getOperaciones()
                    < lineal.getOperaciones()) {

                texto +=
                        "El ABB realizo menos operaciones\n";

            } else {

                texto +=
                        "La busqueda lineal realizo menos operaciones\n";
            }
        }

        panel.mostrarResultado(
                texto
        );
    }

    /**
     * Desbloquea la Ciudad 6 cuando el jugador:
     * - carga un archivo
     * - realiza al menos 3 búsquedas
     */
    private void verificarVictoria() {

        ValidacionesUtiles.validarMayorOIgualACero(
                cantidadBusquedas,
                "cantidadBusquedas"
        );

        if (archivoCargado
                && cantidadBusquedas >= 3) {

            if (progreso != null
                    && !progreso.estaDesbloqueada(6)) {

                System.out.println(
                        "[CIUDAD 5] COMPLETADA"
                );

                progreso.desbloquear(6);

                progreso.guardar();

                JOptionPane.showMessageDialog(
                        this,
                        "¡Felicitaciones!\n"
                                + "Completaste la Ciudad 5.\n"
                                + "La Ciudad 6 ha sido desbloqueada."
                );
            }
        }
    }
}
