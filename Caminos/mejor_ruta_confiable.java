package Caminos;
// pN: cantidad de nodos
// pM: cantidad de arcos
// pO: nodo origen
// pD: nodo destino
// pArcos: lista de arcos (nodo origen, nodo destino, peso)

// Se debe pasar por un arco (Nodo 1 a Nodo 2) y a la vez enciar un mensaje de confirmación de vuelta (Nodo 2 a Nodo 1), si esto no es posible, el arco no es confiable

// Return: peso de la mejor ruta confiable
// Si no hay ruta confiable, retornar -1

// Input
// Linea 1: pN, pM, pO, pD
// Linea 2 a pM+1: pArcos

// Ejemplo 1
// 2 1 2 1
// 1 2 1
// Output: -1 ya que no se puede enviar el mensaje de confirmación de vuelta

// Ejemplo 2
// 2 2 2 1
// 1 2 3
// 2 1 5
// Output: 8 ya que sigue la ruta 2 -> 1 que tiene peso 5 y luego 1 -> 2 para la confirmación que tiene peso 3


import java.util.*;

class mejor_ruta_confiable {
    
    // Método para calcular la mejor ruta confiable
    public static int calcularRespuesta(int pN, int pM, int pO, int pD, List<List<Integer>> pArcos) {
        // Matriz de adyacencia para almacenar los pesos
        int[][] peso = new int[pN + 1][pN + 1];
        for (int i = 1; i <= pN; i++) {
            Arrays.fill(peso[i], Integer.MAX_VALUE); // Inicializar a infinito
        }

        // Llenar la matriz de adyacencia
        for (List<Integer> arco : pArcos) {
            int u = arco.get(0);  // nodo origen
            int v = arco.get(1);  // nodo destino
            int w = arco.get(2);  // peso del arco
            
            peso[u][v] = Math.min(peso[u][v], w); // Solo guardar el peso mínimo
            // Si deseas manejar múltiples arcos, puedes usar Math.min.
        }

        // Calcular pesos combinados y construir el grafo confiable
        Map<Integer, List<int[]>> caminoSeguro = new HashMap<>();
        for (int u = 1; u <= pN; u++) {
            caminoSeguro.put(u, new ArrayList<>());
            for (int v = 1; v <= pN; v++) {
                if (u != v && peso[u][v] != Integer.MAX_VALUE && peso[v][u] != Integer.MAX_VALUE) {
                    int combinedWeight = peso[u][v] + peso[v][u];
                    caminoSeguro.get(u).add(new int[]{v, combinedWeight});
                }
            }
        }

        // Aplicar Dijkstra en el grafo confiable
        int[] distToDest = dijkstra(caminoSeguro, pO, pN);

        // Si no hay camino desde pO a pD, retornar -1
        return distToDest[pD] == Integer.MAX_VALUE ? -1 : distToDest[pD];
    }

    // Algoritmo de Dijkstra para encontrar caminos mínimos
    private static int[] dijkstra(Map<Integer, List<int[]>> graph, int start, int n) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;
        pq.offer(new int[]{start, 0});

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int u = curr[0];
            int d = curr[1];

            if (d > dist[u]) continue;

            for (int[] neighbor : graph.get(u)) {
                int v = neighbor[0];
                int weight = neighbor[1];
                if (dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    pq.offer(new int[]{v, dist[v]});
                }
            }
        }
        return dist;
    }

    public static void main(String[] args) {
        List<List<Integer>> pArcos = Arrays.asList(
            Arrays.asList(1, 2, 3),
            Arrays.asList(2, 1, 5)
        );
        System.out.println(mejor_ruta_confiable.calcularRespuesta(2, 2, 2, 1, pArcos)); // Output: 8
    }
}


