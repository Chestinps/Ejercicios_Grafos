package MST;

import java.util.*;

class Edge implements Comparable<Edge> {
    int source, dest, weight;

    public Edge(int source, int dest, int weight) {
        this.source = source;
        this.dest = dest;
        this.weight = weight;
    }

    // Método para comparar aristas por peso
    public int compareTo(Edge compareEdge) {
        return this.weight - compareEdge.weight;
    }
}

class GraphKruskal {
    int vertices;
    List<Edge> edges = new ArrayList<>();

    GraphKruskal(int vertices) {
        this.vertices = vertices;
    }

    // Método para agregar aristas
    public void addEdge(int source, int dest, int weight) {
        edges.add(new Edge(source, dest, weight));
    }

    // Encuentra el conjunto del nodo
    int find(int[] parent, int i) {
        if (parent[i] == i)
            return i;
        return find(parent, parent[i]);
    }

    // Unión de dos subconjuntos
    void union(int[] parent, int[] rank, int x, int y) {
        int rootX = find(parent, x);
        int rootY = find(parent, y);

        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }
    }

    public void kruskalMST() {
        List<Edge> result = new ArrayList<>();
        Collections.sort(edges);

        int[] parent = new int[vertices];
        int[] rank = new int[vertices];

        for (int i = 0; i < vertices; i++) {
            parent[i] = i;
            rank[i] = 0;
        }

        int edgeCount = 0;
        int index = 0;
        while (edgeCount < vertices - 1) {
            Edge nextEdge = edges.get(index++);
            int x = find(parent, nextEdge.source);
            int y = find(parent, nextEdge.dest);

            if (x != y) {
                result.add(nextEdge);
                union(parent, rank, x, y);
                edgeCount++;
            }
        }

        System.out.println("Aristas del MST (Kruskal):");
        for (Edge edge : result) {
            System.out.println(edge.source + " -- " + edge.dest + " == " + edge.weight);
        }
    }

    public static void main(String[] args) {
        GraphKruskal g = new GraphKruskal(6);
        g.addEdge(0, 1, 4);
        g.addEdge(0, 2, 4);
        g.addEdge(1, 2, 2);
        g.addEdge(1, 3, 5);
        g.addEdge(2, 3, 5);
        g.addEdge(2, 4, 6);
        g.addEdge(3, 4, 3);
        g.addEdge(3, 5, 7);
        g.addEdge(4, 5, 8);

        g.kruskalMST();
    }
}

