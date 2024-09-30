package Caminos;
/*
    N: Cantidad de nodos (escenarios)
    M: Cantidad de aristas (bidireccionales)
    - u: Escenario de origen
    - v: Escenario de destino
    - w: Costo de usar el camino
    F: Valor de la entrada a Grafopalooza
    K: Cantidad de personas entrando a Grafopalooza

    Se puede llegar a todos los escenarios desde todos los otros escenarios
    Todas las personas inicialmente entran al escenario 1 -> Usar Prim

    Input:
    N M F
    N enteros que determinan la capacidad de cada escenario
    M líneas con tres enteros que representan el camino entre dos escenarios y el costo de usarlo
    K

    Output: K valores que representan el costo mínimo para cada persona

    Ejemplo 1:
    7 6 9 -> N, M, F
    1 1 1 1 1 1 1 -> Capacidad de cada escenario
    1 4 1 -> Escenario 1 a Escenario 4 con costo 1
    4 6 7 -> Escenario 4 a Escenario 6 con costo 7
    4 7 10 -> Escenario 4 a Escenario 7 con costo 10
    4 5 4 -> Escenario 4 a Escenario 5 con costo 4
    6 2 3 -> Escenario 6 a Escenario 2 con costo 3
    7 3 8 -> Escenario 7 a Escenario 3 con costo 8
    8 -> K
    Salida: 9 10 14 17 20 20 28 -1

    Suponiendo que son 8 personas:
    - Primera persona se queda en el nodo 1 y paga 9
    - Segunda persona va de 1 a 4 y paga (9 + 1 = 10)
    - Tercera persona va de 1 a 4 a 5 y paga (9 + 1 + 4 = 14)
    - Cuarta persona va de 1 a 4 a 6 y paga (9 + 1 + 7 = 17)
    - Quinta persona va de 1 a 4 a 6 a 2 y paga (9 + 1 + 7 + 3 = 20)
    - Sexta persona va de 1 a 4 a 7 y paga (9 + 1 + 10 = 20)
    - Séptima perona va de 1 a 4 a 7 a 3 y paga (9 + 1 + 10 + 8 = 28)
    - Octava persona queda en -1 ya que todos los escenarios están llenos
*/

import java.util.*;

public class grafopalooza {

    static class Edge { // Arista
        int destino;
        int costo;

        Edge(int destino, int costo) {
            this.destino = destino;
            this.costo = costo;
        }
    }

    public static List<Integer> calcularCostos(int N, int M, int F, int[] capacidades, List<int[]> caminos, int K) {
        List<List<Edge>> grafo = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            grafo.add(new ArrayList<>());
        }

        // Agregar las conexiones al grafo
        for (int[] camino : caminos) {
            int u = camino[0] - 1;
            int v = camino[1] - 1;
            int w = camino[2];
            grafo.get(u).add(new Edge(v, w));
            grafo.get(v).add(new Edge(u, w)); // bidireccional
        }

        // Usar Dijkstra para encontrar el costo mínimo desde el nodo 1 (escenario 1)
        int[] costosMinimos = new int[N];
        Arrays.fill(costosMinimos, Integer.MAX_VALUE);
        costosMinimos[0] = 0; // Costo desde el nodo 1 es 0 (sin incluir F)

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.offer(new int[]{0, 0}); // {nodo, costo}

        while (!pq.isEmpty()) {
            int[] actual = pq.poll();
            int nodoActual = actual[0];
            int costoActual = actual[1];

            for (Edge vecino : grafo.get(nodoActual)) {
                int nuevoCosto = costoActual + vecino.costo;
                if (nuevoCosto < costosMinimos[vecino.destino]) {
                    costosMinimos[vecino.destino] = nuevoCosto;
                    pq.offer(new int[]{vecino.destino, nuevoCosto});
                }
            }
        }

        // Lista de costos finales
        List<Integer> costosFinales = new ArrayList<>();
        int[] personasEnEscenario = new int[N];
        PriorityQueue<int[]> capacidadDisponible = new PriorityQueue<>(Comparator.comparingInt(a -> costosMinimos[a[0]]));

        // Inicialmente, todas las capacidades se pueden usar
        for (int i = 0; i < N; i++) {
            if (capacidades[i] > 0) {
                capacidadDisponible.offer(new int[]{i, capacidades[i]});
            }
        }

        // Procesar cada persona
        for (int i = 0; i < K; i++) {
            if (capacidadDisponible.isEmpty()) {
                costosFinales.add(-1); // No hay más espacio
            } else {
                int[] mejorEscenario = capacidadDisponible.poll(); // Nodo con menor costo
                int nodo = mejorEscenario[0];
                int capacidadRestante = mejorEscenario[1];

                // Cálculo del costo total para esta persona
                int costoPersona = F + costosMinimos[nodo]; // Costo de entrada + costo mínimo para llegar al nodo
                costosFinales.add(costoPersona);

                // Actualizar la capacidad restante del escenario
                capacidadRestante--;
                if (capacidadRestante > 0) {
                    capacidadDisponible.offer(new int[]{nodo, capacidadRestante});
                }
            }
        }

        return costosFinales;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int F = sc.nextInt();
        int[] capacidades = new int[N];
        for (int i = 0; i < N; i++) {
            capacidades[i] = sc.nextInt();
        }
        List<int[]> caminos = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int w = sc.nextInt();
            caminos.add(new int[]{u, v, w});
        }
        int K = sc.nextInt();
        sc.close();

        List<Integer> costos = calcularCostos(N, M, F, capacidades, caminos, K);
        
        costos.sort((a, b) -> {
            if (a == -1) return 1;
            if (b == -1) return -1;
            return Integer.compare(a, b);
        });
        
        // Imprimir los costos ordenados
        for (int costo : costos) {
            System.out.print(costo + " ");
        }
    }
}


