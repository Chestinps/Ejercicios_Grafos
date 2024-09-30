package Caminos;
/*  Listo
    Se sabe que el sistema del transantiago funciona de tal forma que cada vez que se 
    va a tomar una micro el costo de dicha micro es igual a la diferencia que 
    existe entre el costo de la micro actual y el total pagado durante todo el recorrido, 
    si la resta es un numero negativo, significa que no se paga nada en ese recorrido.
    - Mapa con N nodos
    - Determinar la cantidad de dinero que debe llevar para ir desde el primer nodo del 
    mapa (etiquetado con 1) al ultimo nodo del mapa (etiquetado con N)
    - Cada arista tiene un costo asociado
    - Las aristas son bidireccionales

    Input:
    V: Cantidad de nodos
    E: Cantidad de aristas
    E líneas con tres enteros que representan el nodo de inicio, el nodo de fin y el costo de la arista

    Output: Cantidad de dinero que debe llevar para ir desde el primer nodo al ultimo nodo
    En el caso de que no se pueda llegar al ultimo nodo, retornar NO PATH EXISTS

    Ejemplo 1:
    Entrada:
    4 4
    1 2 20
    1 3 5
    2 4 30
    3 4 40
    Salida: 30 (Para ir de 1 a 2 paga 20, para ir de 2 a 4 paga 10, debido a que ya pago 20 y como el pasaje de 2 a 4 cuesta 30, entonces paga 30-20=10)

*/

import java.util.*;

class el_transantiasco {

    static class Node implements Comparable<Node> {
        int vertex, cost;

        public Node(int vertex, int cost) {
            this.vertex = vertex;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.cost, other.cost);
        }
    }

    public static int dijkstra(int V, List<List<Node>> graph) {
        // Inicializamos la cantidad máxima de dinero a una cantidad infinita
        int[] minCost = new int[V + 1];
        Arrays.fill(minCost, Integer.MAX_VALUE);

        // El costo inicial es 0 en el nodo de partida (nodo 1)
        minCost[1] = 0;

        // Cola de prioridad para seleccionar el nodo con el menor costo acumulado
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(1, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int u = current.vertex;
            int currentCost = current.cost;

            // Si ya hemos alcanzado el último nodo (nodo N), devolvemos el costo mínimo
            if (u == V) {
                return currentCost;
            }

            // Exploramos los vecinos del nodo actual
            for (Node neighbor : graph.get(u)) {
                int v = neighbor.vertex;
                int travelCost = neighbor.cost;

                // Calculamos el costo adicional basado en el sistema de pago descrito
                int additionalCost = Math.max(0, travelCost - currentCost);
                int newCost = currentCost + additionalCost;

                // Si encontramos una ruta más barata hacia el vecino, la actualizamos
                if (newCost < minCost[v]) {
                    minCost[v] = newCost;
                    pq.add(new Node(v, newCost));
                }
            }
        }

        // Si no hay un camino hacia el nodo N, retornamos que no existe camino
        return -1;  // NO PATH EXISTS
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Leemos la cantidad de nodos (V) y la cantidad de aristas (E)
        int V = sc.nextInt();
        int E = sc.nextInt();

        // Creamos la representación del grafo usando listas de adyacencia
        List<List<Node>> graph = new ArrayList<>();
        for (int i = 0; i <= V; i++) {
            graph.add(new ArrayList<>());
        }

        // Leemos las aristas
        for (int i = 0; i < E; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int cost = sc.nextInt();
            graph.get(u).add(new Node(v, cost));
            graph.get(v).add(new Node(u, cost));  // Como es bidireccional, se añade en ambos sentidos
        }

        // Calculamos el costo mínimo usando Dijkstra
        int result = dijkstra(V, graph);

        // Imprimimos el resultado
        if (result == -1) {
            System.out.println("NO PATH EXISTS");
        } else {
            System.out.println(result);
        }

        sc.close();
    }
}
