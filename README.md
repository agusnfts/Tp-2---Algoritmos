## TP 2 - Algoritmos y estructuras de datos

# Ciudad 2 - Problema de las N Reinas
 
Implementación del problema de las N Reinas con backtracking recursivo.
 
## Archivos
 
**`TableroReinas.java`**
Representa el tablero N x N. Almacena las posiciones de las reinas en un vector y expone métodos para colocar, quitar y validar posiciones.
 
**`BacktrackingReinas.java`**
Resuelve el problema usando backtracking recursivo. El jugador fija una posición inicial y el algoritmo coloca las reinas restantes en cualquier fila libre del tablero. Guarda un snapshot del tablero en cada paso para poder mostrar el proceso.
 
**`Interfaz.java`**
Toma los snapshots generados por el solver y los convierte en imágenes BMP. Dibuja el tablero con colores de ajedrez, reinas doradas para colocaciones y rojas para retrocesos.
 
**`PanelCiudad2.java`**
Panel Swing principal. El jugador elige la cantidad de reinas y la posición inicial, presiona Resolver y puede navegar el paso a paso con un slider y botones.


# Ciudad 3 - Laberinto

    Resuelve un laberinto utilizando el algoritmo de Backtracking (Búsqueda en Profundidad / DFS). A medida que el algoritmo evalúa los distintos caminos, el programa muestra el progreso en tiempo real a través de una interfaz visual.

## Archivos

**`SalidaLaberinto.java`**
    Es el núcleo del programa. Contiene el método `main` que arranca la aplicación, la matriz que define el laberinto, y el algoritmo recursivo de backtracking (`resolverLaberinto`). Además, acá se encuentra toda la lógica de diseño y renderizado.

**`Bitmap.java`**
    Funciona como un lienzo en memoria y provee todos los métodos primitivos para dibujar formas geométricas (líneas, rectángulos, círculos, textos) píxel por píxel, así como guardar y cargar imágenes.

**`BitmapViewerConMenu.java`**
    Maneja la Interfaz Gráfica de Usuario (GUI) principal. Construye una ventana adaptable inteligente (con barras de desplazamiento automáticas si el laberinto es muy grande) e incluye un panel inferior para inyectar botones interactivos (como "Empezar Hackeo" y "Salir").

**`BitmapViewer.java`**
    Es una versión más simple del visualizador gráfico que solo muestra imágenes estáticas en cuadrícula y las actualiza.