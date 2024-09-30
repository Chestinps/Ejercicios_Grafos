package Search;

import java.util.*;

class DFS {
    private LinkedList<Integer>[] adjList;
    private int vertices;

    DFS(int vertices) {
        this.vertices = vertices;
        adjList = new LinkedList[vertices];
        for (int i = 0; i < vertices; i++) {
            adjList[i] = new LinkedList<>();
        }
    }

    // Método para agregar aristas
    public void addEdge(int source, int dest) {
        adjList[source].add(dest);
    }

    // DFS Recursivo
    public void DFS(int startVertex) {
        boolean[] visited = new boolean[vertices];
        dfsRecursive(startVertex, visited);
    }

    private void dfsRecursive(int vertex, boolean[] visited) {
        visited[vertex] = true;
        System.out.print(vertex + " ");

        for (int adj : adjList[vertex]) {
            if (!visited[adj]) {
                dfsRecursive(adj, visited);
            }
        }
    }

    public static void main(String[] args) {
        DFS g = new DFS(6);

        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 4);
        g.addEdge(2, 5);

        System.out.println("Recorrido DFS comenzando desde el nodo 0:");
        g.DFS(0);
    }
}

