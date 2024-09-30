package MST;

import java.util.*;

class Prim {
    private int vertices;
    private List<List<Node>> adjList;

    class Node implements Comparable<Node> {
        int vertex, weight;

        Node(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node other) {
            return this.weight - other.weight;
        }
    }

    Prim(int vertices) {
        this.vertices = vertices;
        adjList = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++) {
            adjList.add(new ArrayList<>());
        }
    }

    // Método para agregar aristas al grafo
    public void addEdge(int source, int dest, int weight) {
        adjList.get(source).add(new Node(dest, weight));
        adjList.get(dest).add(new Node(source, weight));
    }

    public void primMST() {
        boolean[] mstSet = new boolean[vertices];
        int[] key = new int[vertices];
        int[] parent = new int[vertices];

        PriorityQueue<Node> pq = new PriorityQueue<>();
        Arrays.fill(key, Integer.MAX_VALUE);
        pq.add(new Node(0, 0));
        key[0] = 0;
        parent[0] = -1;

        while (!pq.isEmpty()) {
            int u = pq.poll().vertex;
            mstSet[u] = true;

            for (Node neighbor : adjList.get(u)) {
                if (!mstSet[neighbor.vertex] && neighbor.weight < key[neighbor.vertex]) {
                    key[neighbor.vertex] = neighbor.weight;
                    pq.add(new Node(neighbor.vertex, key[neighbor.vertex]));
                    parent[neighbor.vertex] = u;
                }
            }
        }

        System.out.println("Aristas del MST (Prim):");
        for (int i = 1; i < vertices; i++) {
            System.out.println(parent[i] + " -- " + i + " == " + key[i]);
        }
    }
    
    public static void main(String[] args) {
        Prim g = new Prim(6);
        g.addEdge(0, 1, 4);
        g.addEdge(0, 2, 4);
        g.addEdge(1, 2, 2);
        g.addEdge(1, 3, 5);
        g.addEdge(2, 3, 5);
        g.addEdge(2, 4, 6);
        g.addEdge(3, 4, 3);
        g.addEdge(3, 5, 7);
        g.addEdge(4, 5, 8);

        g.primMST();
    }
}

