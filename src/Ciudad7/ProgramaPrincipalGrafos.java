package Ciudad7;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


import Ciudad7.grafos.Grafo;
import Ciudad7.grafos.Vertice;
import principal.ProgresoJuego;
import Ciudad7.grafos.Arista;
import Ciudad7.grafos.GrafoToBitmap;
import Ciudad7.bitmap.Bitmap;
import Ciudad7.bitmap.BitmapViewerConMenu;
import Ciudad7.bitmap.BitmapViewerConMenu.MenuAction;
import Ciudad7.grafos.AlgoritmoFordFulkerson;

public class ProgramaPrincipalGrafos {

    // Instancia global del TDA Grafo
    private static Grafo<String, Integer> ciudad7 = new Grafo<>();
    
    // Vincula el valor del nodo (String) con un índice numérico secuencial (0, 1, ..., n-1)
    private static List<String> mapeoNodos = new ArrayList<>();
    
    // Dimensiones absolutas del viewport. Fijan la resolución para garantizar consistencia 
    // en la proyección trigonométrica de coordenadas en GrafoToBitmap.
    private static final int ANCHO = 1200;
    private static final int ALTO = 600;

    // Buffer gráfico principal que almacena el frame actual a renderizar.
    private static Bitmap lienzoPrincipal = new Bitmap(ANCHO, ALTO);

    // Booleanos para ver si se ejecuto el camino minimo o maximo.
    private static boolean usoCaminoMinimo = false;
    private static boolean usoCaminoMaximo = false;
    private static boolean ciudad7Completada = false;
    
    private static ProgresoJuego progreso;

    
    public static void main(String[] args) {
        
        // Limpieza inicial del buffer de video
        lienzoPrincipal.rellenar(Color.BLACK);

        // Acciones que inyectan el comportamiento en los botones de Swing
        List<MenuAction> acciones = new ArrayList<>();

        // >=== BOTÓN 1: AGREGAR NODO ===<
        acciones.add(new MenuAction("Agregar Nodo", () -> {
            String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre del nuevo nodo:");
            if (nombre != null && !nombre.trim().isEmpty()) {
                // Se valida unicidad para evitar colisiones en la tabla hash interna del TDA
                if (!ciudad7.existeVertice(nombre)) {
                    ciudad7.agregarVertice(nombre);
                    actualizarLienzo();
                } else {
                    JOptionPane.showMessageDialog(null, "Ese nodo ya existe.");
                }
            }
        }));

        
        
        // >=== BOTÓN 2: CONECTAR NODOS ===<
        acciones.add(new MenuAction("Conectar Nodos (Arista)", () -> {
            if (mapeoNodos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Primero debés agregar nodos al grafo.");
                return;
            }

            // Construcción del layout de captura de propiedades de la arista
            JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 10, 10));

            JComboBox<String> cbInicio = new JComboBox<>(mapeoNodos.toArray(new String[0]));
            JComboBox<String> cbFinal = new JComboBox<>(mapeoNodos.toArray(new String[0]));
            JComboBox<String> cbDireccion = new JComboBox<>(new String[]{"Sin dirección", "Con dirección"});
            JComboBox<String> cbSentido = new JComboBox<>(new String[]{"Inicio -> Final", "Final -> Inicio"});
            JTextField txtMasa = new JTextField();

            // Bloquea el selector de sentido si se modela un grafo no dirigido
            cbSentido.setEnabled(false);
            cbDireccion.addActionListener(e -> {
                cbSentido.setEnabled(cbDireccion.getSelectedIndex() == 1);
            });

            panelFormulario.add(new JLabel("Nodo de Inicio:"));
            panelFormulario.add(cbInicio);
            panelFormulario.add(new JLabel("Nodo Final:"));
            panelFormulario.add(cbFinal);
            panelFormulario.add(new JLabel("Tipo de Conexión:"));
            panelFormulario.add(cbDireccion);
            panelFormulario.add(new JLabel("Sentido del Flujo:"));
            panelFormulario.add(cbSentido);
            panelFormulario.add(new JLabel("Masa (Capacidad/Peso):"));
            panelFormulario.add(txtMasa);

            int clickBoton = JOptionPane.showConfirmDialog(null, panelFormulario, 
                    "Configurar Nueva Arista", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (clickBoton == JOptionPane.OK_OPTION) {
                String nodoIn = (String) cbInicio.getSelectedItem();
                String nodoFin = (String) cbFinal.getSelectedItem();
                boolean conDireccion = cbDireccion.getSelectedIndex() == 1;
                String sentidoFlujo = (String) cbSentido.getSelectedItem();
                
                try {
                    int peso = Integer.parseInt(txtMasa.getText().trim());
                    
                    // Validación preventiva, evita la inserción de aristas mayores que 0
                    if (peso < 0) {
                        JOptionPane.showMessageDialog(null, "La masa debe ser mayor a 0.");
                        return;
                    }

                    if (!conDireccion) {
                        // Inserción bidireccional simétrica
                        ciudad7.agregarArista(nodoIn, nodoFin, peso);
                        if (!nodoIn.equals(nodoFin)) { 
                            ciudad7.agregarArista(nodoFin, nodoIn, peso);
                        }
                    } else {
                        // Inserción estricta de vector dirigido según la selección del usuario
                        if (sentidoFlujo.equals("Inicio -> Final")) {
                            ciudad7.agregarArista(nodoIn, nodoFin, peso);
                        } else {
                            ciudad7.agregarArista(nodoFin, nodoIn, peso);
                        }
                    }
                    actualizarLienzo();

                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Error: La masa debe ser un número entero válido.");
                }
            }
        }));

        // >=== BOTÓN 3: ELIMINAR ARISTA ===<
        acciones.add(new MenuAction("Eliminar Arista", () -> {
            if (mapeoNodos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay nodos en el grafo.");
                return;
            }

            // Escaneo O(V): Determina si existe conectividad en la red antes de instanciar el menú
            boolean existenAristas = false;
            for (Vertice<String, Integer> v : ciudad7.getVertices()) {
                if (!v.getAdyacencias().isEmpty()) {
                    existenAristas = true; 
                    break;
                }
            }

            if (!existenAristas) {
                JOptionPane.showMessageDialog(null, "No hay ninguna conexión (arista) creada en el grafo para eliminar.");
                return;
            }

            JPanel panelEliminarArista = new JPanel(new GridLayout(2, 2, 10, 10));
            JComboBox<String> cbInicio = new JComboBox<>(mapeoNodos.toArray(new String[0]));
            JComboBox<String> cbFinal = new JComboBox<>(mapeoNodos.toArray(new String[0]));
            
            panelEliminarArista.add(new JLabel("Nodo de Inicio:"));
            panelEliminarArista.add(cbInicio);
            panelEliminarArista.add(new JLabel("Nodo Final:"));
            panelEliminarArista.add(cbFinal);

            int clickEliminar = JOptionPane.showConfirmDialog(null, panelEliminarArista, 
                    "Destruir Conexión", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (clickEliminar == JOptionPane.OK_OPTION) {
                String inicio = (String) cbInicio.getSelectedItem();
                String fin = (String) cbFinal.getSelectedItem();

                // Validación O(E): Confirma la existencia física de la arista en la lista de adyacencia
                boolean aristaEncontrada = false;
                for (Arista<String, Integer> arista : ciudad7.getAdyacentes(inicio)) {
                    if (arista.getDestino().getValor().equals(fin)) {
                        aristaEncontrada = true;
                        break;
                    }
                }
                
                // Fallback para grafos dirigidos: Revisa el vector inverso
                if (!aristaEncontrada) {
                    for (Arista<String, Integer> arista : ciudad7.getAdyacentes(fin)) {
                        if (arista.getDestino().getValor().equals(inicio)) {
                            aristaEncontrada = true;
                            break;
                        }
                    }
                }

                if (!aristaEncontrada) {
                    JOptionPane.showMessageDialog(null, "No existe ninguna arista conectando a " + inicio + " con " + fin + ".");
                    return;
                }

                // La eliminacion se ejecuta en ambas direcciones para purgar referenciación cruzada
                ciudad7.eliminarArista(inicio, fin);
                if (!inicio.equals(fin)) {
                    ciudad7.eliminarArista(fin, inicio);
                }
                
                actualizarLienzo();
            }
        }));

        // >=== BOTÓN 4: ELIMINAR NODO ===<
        acciones.add(new MenuAction("Eliminar Nodo", () -> {
            if (mapeoNodos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay nodos para destruir.");
                return;
            }

            JPanel panelEliminarNodo = new JPanel(new GridLayout(1, 2, 10, 10));
            JComboBox<String> cbNodo = new JComboBox<>(mapeoNodos.toArray(new String[0]));
            
            panelEliminarNodo.add(new JLabel("Seleccione el nodo:"));
            panelEliminarNodo.add(cbNodo);

            int clickDestruir = JOptionPane.showConfirmDialog(null, panelEliminarNodo, 
                    "Destruir Nodo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

            if (clickDestruir == JOptionPane.OK_OPTION) {
                String nodoDestruir = (String) cbNodo.getSelectedItem();
                
                // Delega al TDA la recolección de basura interna y purga de aristas huérfanas
                ciudad7.eliminarVertice(nodoDestruir);
                actualizarLienzo();
            }
        }));

        // >=== BOTÓN 5: FLUJO MÁXIMO (Ford-Fulkerson) ===<
        acciones.add(new MenuAction("Calcular Flujo Máximo", () -> {
            int totalNodos = ciudad7.getVertices().size();
            if (totalNodos < 2) {
                JOptionPane.showMessageDialog(null, "Se necesitan al menos 2 nodos.");
                return;
            }

            JPanel panelFlujo = new JPanel(new GridLayout(2, 2, 10, 10));
            JComboBox<String> cbFuente = new JComboBox<>(mapeoNodos.toArray(new String[0]));
            JComboBox<String> cbSumidero = new JComboBox<>(mapeoNodos.toArray(new String[0]));
            
            panelFlujo.add(new JLabel("Nodo Fuente (Inicio):"));
            panelFlujo.add(cbFuente);
            panelFlujo.add(new JLabel("Nodo Sumidero (Final):"));
            panelFlujo.add(cbSumidero);

            int clickFlujo = JOptionPane.showConfirmDialog(null, panelFlujo, 
                    "Calcular Flujo Máximo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (clickFlujo == JOptionPane.OK_OPTION) {
                String nodoOrigen = (String) cbFuente.getSelectedItem();
                String nodoDestino = (String) cbSumidero.getSelectedItem();

                // Regla algorítmica: La fuente debe ser estrictamente distinto del Sumidero
                if (nodoOrigen.equals(nodoDestino)) {
                    JOptionPane.showMessageDialog(null, "La fuente y el sumidero no pueden ser el mismo nodo.");
                    return;
                }

                usoCaminoMaximo = true;
                
                verificarVictoria();
                
                int indiceFuente = mapeoNodos.indexOf(nodoOrigen);
                int indiceSumidero = mapeoNodos.indexOf(nodoDestino);

                // Instancia la matriz para llenarlo con los recorridos reales que usa al algoritmo y poder reomarcarlo visualmente
                int[][] mtzCapacidades = generarMatrizBase(0);
                int flujoCalculado = AlgoritmoFordFulkerson.fordFulkerson(mtzCapacidades, indiceFuente, indiceSumidero);
                int[][] caudalesUsados = AlgoritmoFordFulkerson.obtenerCaudalesUsados(mtzCapacidades, indiceFuente, indiceSumidero);
                int[][] mtzLienzo = generarMatrizBase(0);
                GrafoToBitmap dibujadorGrafico = new GrafoToBitmap(mtzLienzo, mapeoNodos, ANCHO, ALTO);
                
                dibujadorGrafico.setCaudalRojo(caudalesUsados); 
                lienzoPrincipal.pasteBitmap(dibujadorGrafico.drawGraph(), 0, 0);

                JOptionPane.showMessageDialog(null, "El Flujo Máximo alcanzado es: " + flujoCalculado + 
                                                    "\n\nLas conexiones de la red activa están resaltadas de color ROJO.");
                
                actualizarLienzo(); //Repintado final
            }
        }));

        // >=== BOTÓN 6: CAMINO MÍNIMO (Dijkstra) ===<
        acciones.add(new MenuAction("Calcular Caminos Mínimos", () -> {
            int cantNodos = ciudad7.getVertices().size();
            if (cantNodos < 2) {
                JOptionPane.showMessageDialog(null, "Faltan nodos en el grafo.");
                return;
            }
         
         

            JPanel panelCamino = new JPanel(new GridLayout(2, 2, 10, 10));
            JComboBox<String> cbOrigen = new JComboBox<>(mapeoNodos.toArray(new String[0]));
            JComboBox<String> cbDestino = new JComboBox<>(mapeoNodos.toArray(new String[0]));
            
            panelCamino.add(new JLabel("Nodo de Origen:"));
            panelCamino.add(cbOrigen);
            panelCamino.add(new JLabel("Nodo de Destino:"));
            panelCamino.add(cbDestino);

            int clickCamino = JOptionPane.showConfirmDialog(null, panelCamino, 
                    "Calcular Ruta Más Corta", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (clickCamino == JOptionPane.OK_OPTION) {
                String puntoInicio = (String) cbOrigen.getSelectedItem();
                String puntoFinal = (String) cbDestino.getSelectedItem();

                // Condición de corte temprana
                if (puntoInicio.equals(puntoFinal)) {
                    JOptionPane.showMessageDialog(null, "Ya te encontrás en ese nodo. Distancia: 0");
                    return;
                }

                try {
                    // Ejecuta el algoritmo de camino mínimo interno devolviendo el path-tracking de nodos
                    List<String> listadoRuta = ciudad7.dijkstra(puntoInicio, puntoFinal);
                    
                    if (listadoRuta.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No existe ninguna ruta posible entre estos dos nodos.");
                        return;
                    }

                    
                    // MARCAR OBJETIVO CUMPLIDO
                    usoCaminoMinimo = true;

                    // VERIFICAR SI GANÓ LA CIUDAD
                    verificarVictoria();
                    
                    List<Integer> listadoIndices = new java.util.ArrayList<>();
                    for (String nodo : listadoRuta) {
                        listadoIndices.add(mapeoNodos.indexOf(nodo));
                    }

                    int[][] mtzLienzo = generarMatrizBase(0);
                    GrafoToBitmap motorDibujo = new GrafoToBitmap(mtzLienzo, mapeoNodos, ANCHO, ALTO);
                    motorDibujo.setRutaVerde(listadoIndices); 
                    lienzoPrincipal.pasteBitmap(motorDibujo.drawGraph(), 0, 0);
                    
                    JOptionPane.showMessageDialog(null, "¡Ruta óptima calculada!\n\nOrden del recorrido: " + listadoRuta.toString() + 
                                                        "\n\nEl camino exacto está trazado en color VERDE.");
                    
                    actualizarLienzo(); 

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Fallo algorítmico: " + e.getMessage());
                }
            }
        }));
        
        // >=== BOTÓN 7: SALIR ===<
        acciones.add(new MenuAction("Salir", () -> {

            int opcion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Desea salir de Ciudad 7?",
                    "Salir",
                    JOptionPane.YES_NO_OPTION
            );

            if (opcion == JOptionPane.YES_OPTION) {

                // Cierra SOLO el visor del grafo
                BitmapViewerConMenu.closeViewer();

                // Volver al menú principal (sin matar todo el sistema)
                SwingUtilities.invokeLater(() -> {
                    JFrame frame = new JFrame("Menú Principal");
                    frame.setContentPane(new PanelCiudad7());
                    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    frame.setVisible(true);
                });
            }
        }));
        
        // Inicializa el bucle de eventos de Swing y delega la ejecución del Timer al visor
        BitmapViewerConMenu.showBitmapsWithMenu(acciones, lienzoPrincipal);
     
        
    }
    
    private static void verificarVictoria() {

        if(ciudad7Completada) {
            return;
        }

        if(ciudad7.getVertices().size() >= 5
                && usoCaminoMinimo
                && usoCaminoMaximo) {

            ciudad7Completada = true;

            System.out.println("[CIUDAD 7] COMPLETADA");

            JOptionPane.showMessageDialog(
                    null,
                    "¡Ciudad 7 completada!\n"
                    + "Creaste una red de al menos 5 nodos,\n"
                    + "calculaste el camino mínimo y el flujo máximo.\n"
                    + "La Ciudad 8 ha sido desbloqueada."
            );

            if(progreso != null) {
                progreso.desbloquear(8);
                progreso.guardar();
            }
        }
    }

    /**
     * Motor principal de sincronización gráfica.
     * Lee el estado de la estructura de datos dinámica y reconstruye el espacio de dibujo desde cero
     * mediante la técnica de "Clear & Redraw" para evitar artefactos visuales.
     */
    private static void actualizarLienzo() {
        int n = ciudad7.getVertices().size();
        
        // Prevención de desbordamiento: si la red queda vacía, aborta y oscurece el lienzo
        if (n == 0) {
            mapeoNodos.clear();
            lienzoPrincipal.rellenar(Color.BLACK);
            return;
        }

        // Se reasignan los índices de la matriz para ignorar de manera transparente los nodos previamente eliminados
        mapeoNodos.clear();
        for (Vertice<String, Integer> v : ciudad7.getVertices()) {
            mapeoNodos.add(v.getValor());
        }

        int[][] matrizDibujo = generarMatrizBase(0);

        GrafoToBitmap dibujador = new GrafoToBitmap(matrizDibujo, mapeoNodos, ANCHO, ALTO);
        Bitmap nuevoDibujo = dibujador.drawGraph();

        lienzoPrincipal.pasteBitmap(nuevoDibujo, 0, 0);
    }
    
    
    public static void setProgreso(ProgresoJuego p) {
        progreso = p;
    }
    
  
    

    /**
     * Transforma la Lista de Adyacencia flexible del TDA a una Matriz de Adyacencia clásica 
     * consumible por el motor de renderizado y por los algoritmos estáticos.
     * * @param valorVacio Representa el null pointer para las rutas (0 gráficamente, o constante INFINITO algorítmicamente)
     */
    private static int[][] generarMatrizBase(int valorVacio) {
        int n = ciudad7.getVertices().size();
        int[][] matriz = new int[n][n];

        // Rellenado O(N^2)
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matriz[i][j] = valorVacio;
            }
        }

        // Volcado de adyacencias iterativo O(V + E)
        for (int i = 0; i < n; i++) {
            String nombreOrigen = mapeoNodos.get(i);
            for (Arista<String, Integer> arista : ciudad7.getAdyacentes(nombreOrigen)) {
                String nombreDestino = arista.getDestino().getValor();
                int j = mapeoNodos.indexOf(nombreDestino);
                if (j != -1) {
                    matriz[i][j] = arista.getPeso();
                }
            }
        }
        return matriz;
    }
}