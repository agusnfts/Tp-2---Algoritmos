package Ciudad7.grafos;

import java.util.Arrays;
import java.util.Stack;

public class AlgoritmoFordFulkerson {
    
    private static boolean dfs(int[][] residualGraph, int source, int sink, int[] parent) {
        int numVertices = residualGraph.length;
        boolean[] visited = new boolean[numVertices];
        Arrays.fill(visited, false);

        Stack<Integer> stack = new Stack<>();
        stack.push(source);
        visited[source] = true;

        while (!stack.isEmpty()) {
            int u = stack.pop();

            for (int v = 0; v < numVertices; v++) {
                if (!visited[v] && residualGraph[u][v] > 0) {
                    parent[v] = u; 
                    if (v == sink) {
                        return true; 
                    }
                    stack.push(v);
                    visited[v] = true;
                }
            }
        }
        return false;
    }

    public static int fordFulkerson(int[][] graph, int source, int sink) {
        int numVertices = graph.length;

        int[][] residualGraph = new int[numVertices][numVertices];
        for (int u = 0; u < numVertices; u++) {
            for (int v = 0; v < numVertices; v++) {
                residualGraph[u][v] = graph[u][v];
            }
        }

        int[] parent = new int[numVertices];
        int maxFlow = 0; 

        while (dfs(residualGraph, source, sink, parent)) {
            int pathFlow = Integer.MAX_VALUE;
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);
            }

            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                residualGraph[u][v] -= pathFlow; 
                residualGraph[v][u] += pathFlow; 
            }

            maxFlow += pathFlow;
        }

        return maxFlow;
    }

    public static int[][] obtenerCaudalesUsados(int[][] graph, int source, int sink) {
        int numVertices = graph.length;
        int[][] residualGraph = new int[numVertices][numVertices];
        
        for (int u = 0; u < numVertices; u++) {
            for (int v = 0; v < numVertices; v++) {
                residualGraph[u][v] = graph[u][v];
            }
        }
        int[] parent = new int[numVertices];

        while (dfs(residualGraph, source, sink, parent)) {
            int pathFlow = Integer.MAX_VALUE;
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);
            }
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                residualGraph[u][v] -= pathFlow; 
                residualGraph[v][u] += pathFlow; 
            }
        }

        int[][] caudalesReales = new int[numVertices][numVertices];
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (graph[i][j] > 0 && residualGraph[i][j] < graph[i][j]) {
                    caudalesReales[i][j] = graph[i][j] - residualGraph[i][j];
                }
            }
        }
        
        return caudalesReales;
    }
}