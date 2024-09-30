package Search;
/* 
    Dado un grafo no dirigido, determinar si es conexo o no si se desacopla un nodo X

    N: cantidad de nodos
    K: cantidad de aristas
    X: nodo a desacoplar

    Retornar si el grafico es conexo o no si se desacopla el nodo X

    Ejemplo de entrada
    3
    3
    1 2
    2 1
    1 0
    1
    Salida: No es Conexo
*/

import java.util.*;

public class grafo_conexo {
    
    // Método para realizar la búsqueda DFS
    public static void DFS(int nodo, boolean[] visitado, List<List<Integer>> grafo, int X) {
        visitado[nodo] = true;
        for (int vecino : grafo.get(nodo)) {
            if (!visitado[vecino] && vecino != X) {  // Ignoramos el nodo X
                DFS(vecino, visitado, grafo, X);
            }
        }
    }

    // Método para verificar si el grafo es conexo sin el nodo X
    public static boolean esConexo(int N, int K, List<List<Integer>> aristas, int X) {
        // Construir el grafo como lista de adyacencia
        List<List<Integer>> grafo = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            grafo.add(new ArrayList<>());
        }

        for (int i = 0; i < K; i++) {
            int u = aristas.get(i).get(0);
            int v = aristas.get(i).get(1);
            grafo.get(u).add(v);
            grafo.get(v).add(u);
        }

        // Crear un array para marcar los nodos visitados
        boolean[] visitado = new boolean[N];

        // Encontrar un nodo de inicio que no sea el nodo X
        int nodoInicio = -1;
        for (int i = 0; i < N; i++) {
            if (i != X) {
                nodoInicio = i;
                break;
            }
        }

        // Si no hay nodos restantes, el grafo no es conexo
        if (nodoInicio == -1) return false;

        // Realizar la DFS desde el nodo de inicio
        DFS(nodoInicio, visitado, grafo, X);

        // Verificar si todos los nodos, excepto X, han sido visitados
        for (int i = 0; i < N; i++) {
            if (i != X && !visitado[i]) {
                return false;
            }
        }
        return true;  // El grafo es conexo
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt(); // Número de nodos
        int K = sc.nextInt(); // Número de aristas

        // Leer las aristas
        List<List<Integer>> aristas = new ArrayList<>();
        for (int i = 0; i < K; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            List<Integer> arista = new ArrayList<>();
            arista.add(u);
            arista.add(v);
            aristas.add(arista);
        }

        // Leer el nodo X que se va a desacoplar
        int X = sc.nextInt();

        // Determinar si el grafo es conexo sin el nodo X
        if (esConexo(N, K, aristas, X)) {
            System.out.println("Es conexo");
        } else {
            System.out.println("No es conexo");
        }

        sc.close();
    }
}
