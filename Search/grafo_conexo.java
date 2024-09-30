package Search;
// N: cantidad de nodos
// K: cantidad de aristas
// X: nodo a desacoplar

// Retornar si el grafico es conexo o no si se desacopla el nodo X

// Ejemplo de entrada
// 3
// 3
// 1 2
// 2 1
// 1 0
// 1
// Salida: No es Conexo

import java.util.*;

public class grafo_conexo {
    
    public static void DFS(int nodo, boolean[] visitados, List<List<Integer>> grafo) {
        visitados[nodo] = true;
        for (int vecino : grafo.get(nodo)) {
            if (!visitados[vecino]) {
                DFS(vecino, visitados, grafo);
            }
        }
    }

    public static boolean esConexo(int N, List<List<Integer>> grafo, int X) {
        // Desacoplar el nodo X
        for (int i = 0; i < grafo.size(); i++) {
            grafo.get(i).remove(Integer.valueOf(X));
        }

        // Realizar un recorrido DFS desde un nodo que no sea el nodo X
        boolean[] visitados = new boolean[N + 1];
        int startNode = 1;
        if (startNode == X) {
            startNode = (startNode == N) ? 1 : startNode + 1;
        }
        DFS(startNode, visitados, grafo);

        // Verificar si todos los nodos fueron visitados
        for (int i = 1; i <= N; i++) {
            if (!visitados[i]) {
                return false;
            }
        }

        return true;
    }
    
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Leer el número de nodos y aristas
        int N = sc.nextInt(); // Número de nodos
        int K = sc.nextInt(); // Número de aristas
        
        // Crear una lista de adyacencia para representar el grafo
        List<List<Integer>> grafo = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            grafo.add(new ArrayList<>());
        }

        // Leer las conexiones entre los nodos
        for (int i = 0; i < K; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            grafo.get(a).add(b);
            //grafo.get(b).add(a); // Es un grafo no dirigido
        }

        // Leer el nodo a desacoplar
        int X = sc.nextInt();

        // Verificar si el grafo es conexo después de desacoplar el nodo X
        if (esConexo(N, grafo, X)) {
            System.out.println("Es conexo");
        } else {
            System.out.println("No es conexo");
        }
        
        sc.close();
    }
}

