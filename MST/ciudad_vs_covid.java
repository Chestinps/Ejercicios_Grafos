package MST;
/* 
    Se busca eliminar todas las calles antiguas y construir calles nuevas de tal forma que exista un camino 
    entre cualquier par de puntos de interés en la ciudad, naturalmente, al mínimo costo posible, 
    pero adicionalmente, minimizando el riesgo de aglomeraciones.

    pN: Número de puntos de interés en la ciudad
    pM: Número de calles que se pueden construir, conectando dos puntos x e y con un costo c
    pAristas: Lista de listas de 3 elementos, donde cada lista representa una calle con los puntos x, y y c

    Los puntos de interés están ordenados de forma decresciente con respecto a su riesgo de generar aglomeraciones.

    El objetivo es priorizar la opción que minimice la cantidad de calles incidentes a los puntos de interés de mayor 
    riesgo (con identificador más pequeño).

    Para una solución se debe:
    - Seleccionar calles a construir
    - Considerar la cantidad de calles incidentes a cada uno de los puntos de interés
    - La secuencia de estos valores, ordenados de forma creciente con respecto al identificador del punto de interés respectivo, es la "secuencia de grados" de la solución. 

    Entre todas las soluciones posibles de costo mínimo, debe encontrar la opción con la secuencia de grados lexicográficamente mínima

    Input:
    T -> Número de casos de prueba
    pN pM -> Número de puntos de interés y número de calles que se pueden construir
    M líneas con tres enteros que representan la calle entre dos puntos de interés y el costo de construirla

    Output:
    Para cada caso de prueba
    - El costo mínimo de un conjunto de calles de conectan todos los puntos de interés.
    - La cantidad de calles incidentes a cada uno de los puntos de interés en la mejor solución.

    Ejemplo:
    3 -> T
    5 7 -> pN pM del caso 1
    2 5 7
    1 4 3
    2 3 5
    1 3 2
    1 5 4
    3 4 6
    1 2 1
    4 6 -> pN pM del caso 2
    1 2 1000000000
    2 3 1000000000
    3 4 1000000000
    4 1 1000000000
    1 1 1
    2 3 1000000000
    6 7 -> pN pM del caso 3
    1 2 1
    2 3 1
    3 1 1
    4 5 2
    5 6 2
    6 4 2
    1 4 100000

    Salida:
    10 -> Costo mínimo del caso 1
    4 1 1 1 1 -> Secuencia de grados del caso 1
    3000000000 -> Costo mínimo del caso 2
    1 1 2 2 -> Secuencia de grados del caso 2
    100006 -> Costo mínimo del caso 3
    2 1 2 2 1 2 -> Secuencia de grados del caso 3
*/

import java.util.*;

public class ciudad_vs_covid {

    static class Edge implements Comparable<Edge> {
        int u, v, cost;

        Edge(int u, int v, int cost) {
            this.u = u;
            this.v = v;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge other) {
            return this.cost - other.cost;
        }
    }

    static int find(int[] parent, int u) {
        if (parent[u] == u) return u;
        return parent[u] = find(parent, parent[u]);
    }

    static void union(int[] parent, int[] rank, int u, int v) {
        int rootU = find(parent, u);
        int rootV = find(parent, v);
        if (rootU != rootV) {
            if (rank[rootU] > rank[rootV]) {
                parent[rootV] = rootU;
            } else if (rank[rootU] < rank[rootV]) {
                parent[rootU] = rootV;
            } else {
                parent[rootV] = rootU;
                rank[rootU]++;
            }
        }
    }

    public static long calcularCosto(int pN, int pM, List<List<Integer>> pAristas) {
        List<Edge> edges = new ArrayList<>();
        for (List<Integer> arista : pAristas) {
            int u = arista.get(0) - 1;  // Convertimos a 0-indexed
            int v = arista.get(1) - 1;  // Convertimos a 0-indexed
            int cost = arista.get(2);
            edges.add(new Edge(u, v, cost));
        }

        // Ordenar las aristas por costo (Kruskal)
        Collections.sort(edges);

        // Algoritmo de Kruskal
        int[] parent = new int[pN];
        int[] rank = new int[pN];
        Arrays.fill(rank, 0);
        for (int i = 0; i < pN; i++) parent[i] = i;

        long totalCost = 0;
        List<Edge> mstEdges = new ArrayList<>();

        for (Edge edge : edges) {
            if (find(parent, edge.u) != find(parent, edge.v)) {
                totalCost += edge.cost;
                mstEdges.add(edge);
                union(parent, rank, edge.u, edge.v);
            }
        }

        // Guardamos las aristas seleccionadas para calcular los grados
        return totalCost;
    }

    public static List<Integer> calcularGrados(int pN, int pM, List<List<Integer>> pAristas) {
        // Inicializamos la lista de aristas
        List<Edge> edges = new ArrayList<>();
        for (List<Integer> arista : pAristas) {
            int u = arista.get(0) - 1;
            int v = arista.get(1) - 1;
            int cost = arista.get(2);
            edges.add(new Edge(u, v, cost));
        }

        // Ordenamos por costo
        Collections.sort(edges);

        // Algoritmo de Kruskal
        int[] parent = new int[pN];
        int[] rank = new int[pN];
        int[] grados = new int[pN]; // Contar los grados de cada nodo
        Arrays.fill(rank, 0);
        Arrays.fill(grados, 0);

        for (int i = 0; i < pN; i++) parent[i] = i;

        List<Edge> mstEdges = new ArrayList<>();
        for (Edge edge : edges) {
            if (find(parent, edge.u) != find(parent, edge.v)) {
                mstEdges.add(edge);
                union(parent, rank, edge.u, edge.v);

                // Actualizamos el grado de los nodos
                grados[edge.u]++;
                grados[edge.v]++;
            }
        }

        // Convertimos los grados en una lista y la ordenamos lexicográficamente
        List<Integer> gradoOrdenado = new ArrayList<>();
        for (int grado : grados) {
            gradoOrdenado.add(grado);
        }

        return gradoOrdenado;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();  // Número de casos de prueba

        for (int t = 0; t < T; t++) {
            int pN = sc.nextInt();  // Número de puntos de interés
            int pM = sc.nextInt();  // Número de calles disponibles
            List<List<Integer>> pAristas = new ArrayList<>();

            for (int i = 0; i < pM; i++) {
                List<Integer> arista = new ArrayList<>();
                arista.add(sc.nextInt());  // Punto de interés u
                arista.add(sc.nextInt());  // Punto de interés v
                arista.add(sc.nextInt());  // Costo de la calle
                pAristas.add(arista);
            }

            long costoMinimo = calcularCosto(pN, pM, pAristas);
            List<Integer> secuenciaDeGrados = calcularGrados(pN, pM, pAristas);

            // Imprimir el costo mínimo y la secuencia de grados
            System.out.println(costoMinimo);
            for (int grado : secuenciaDeGrados) {
                System.out.print(grado + " ");
            }
            System.out.println();
        }
        sc.close();
    }
}
