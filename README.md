# TP 2 - Algoritmos y estructuras de datos

## Ciudad 2 - Problema de las N Reinas
 
Implementación del problema de las N Reinas con backtracking recursivo.
 
### Archivos
 
**`TableroReinas.java`**
Representa el tablero N x N. Almacena las posiciones de las reinas en un vector y expone métodos para colocar, quitar y validar posiciones.
 
**`BacktrackingReinas.java`**
Resuelve el problema usando backtracking recursivo. El jugador fija una posición inicial y el algoritmo coloca las reinas restantes en cualquier fila libre del tablero. Guarda un snapshot del tablero en cada paso para poder mostrar el proceso.
 
**`Interfaz.java`**
Toma los snapshots generados por el solver y los convierte en imágenes BMP. Dibuja el tablero con colores de ajedrez, reinas doradas para colocaciones y rojas para retrocesos.
 
**`PanelCiudad2.java`**
Panel Swing principal. El jugador elige la cantidad de reinas y la posición inicial, presiona Resolver y puede navegar el paso a paso con un slider y botones.
 
 ## Ciudad 6 - Hashing

Implementación de una tabla hash con manejo de colisiones por encadenamiento separado. El jugador inserta y busca palabras, y el sistema muestra gráficamente el paso a paso: cómo se calcula el índice, cómo se resuelven las colisiones y cómo se recorre la cadena al buscar.

El problema que demuestra es el de la **búsqueda eficiente**: en lugar de recorrer todos los elementos uno por uno (orden N), la función de hash lleva cada palabra directo a su bucket, de modo que la búsqueda promedio es casi constante (orden 1). Para hacerlo evidente, al buscar se muestran el tiempo real y la cantidad de comparaciones.

La ciudad se gana cuando se logran llenar al menos 5 de los 7 buckets con 2 palabras o más cada uno, lo que obliga al jugador a provocar y entender las colisiones.

### Archivos

**`modelo/TablaHash.java`**
La tabla hash en sí. Mantiene un arreglo de listas enlazadas (una por bucket). Calcula el índice sumando los valores ASCII de la palabra y aplicando el módulo del tamaño. Inserta evitando duplicados (clave única), busca recorriendo solo la cadena del bucket correspondiente, y puede medir el tiempo y las comparaciones de una búsqueda.

**`modelo/PasoHash.java`**
Representa un paso de la visualización: la descripción de lo que ocurre, el bucket y la posición involucrados, y el tipo de paso (cálculo, comparación, colisión, encontrado, etc.), que la vista usa para elegir el color del resaltado.

**`modelo/Medicion.java`**
Guarda el resultado de medir una búsqueda: cantidad de comparaciones, tiempo promedio en nanosegundos y si la palabra fue encontrada.

**`vista/PanelCiudad6.java`**
Panel Swing principal. Muestra la explicación inicial, permite insertar y buscar palabras, dibuja los buckets con sus cadenas y reproduce el paso a paso animado. Lleva el progreso del objetivo y, al ganar, habilita el botón para pasar a la siguiente ciudad.

**`vista/Interfaz.java`**
Ventana para probar la ciudad de forma independiente. Crea el panel con una tabla de 7 buckets y el objetivo de victoria, y define qué hace el botón de "pasar a la siguiente ciudad" (por ahora, cerrar el programa).