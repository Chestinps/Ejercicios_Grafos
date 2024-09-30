package MST;
/*
    Un pase te sirve para entrar a un portal y salir por otro
    Cada portal tiene un conjunto de pases que son requeridos para poder usarlo
    Los pases no se desgastan, por lo que pueden ser usados una cantidad cualquiera de veces
    un pase i te cuesta c_i

    Objetivo: Calcular el costo más mínimo de un conjunto de pases que permita disfrutar todas las atracciones

    N: Cantidad de atracciones
    M: Cantidad de portales
    K: Cantidad de pases
    Conexiones bidireccionales

    La entrada al parque de atracciones está en una de las zonas de atracciones. 
    Por los requerimientos del problema, no importa cuál. -> Kruskal

    Input:
    N M K
    M líneas con tres enteros que representan el camino entre dos atracciones y el costo de usarlo
    - u: Atracción de origen
    - v: Atracción de destino
    - l: Cantidad de pases requeridos para usar el portal
        - se mencionan los identificadores de los pases utilizados

    por ejemplo si l es 2, se van a entregar los identificadores de dos pases, los cuales tendrán un costo asociado, por lo que el costo total del camino será la suma de los costos de los pases

    Output: El costo mínimo de pases que se debe comprar, de modo de poder entrar a todas las actividades

    Ejemplo:
    3 3 4 -> 3 atracciones, 3 portales, 4 pases
    1 2 5 10 -> K enteros que representan el costo de los pases (ordenados de menor a mayor)

*/


import java.util.*;

// Clase para manejar la unión de conjuntos disjuntos
public class locura_de_pases {
    int[] parent, rank;

    public locura_de_pases(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
    }

    // Encuentra la raíz del conjunto al que pertenece el nodo u
    public int find(int u) {
        if (parent[u] != u) {
            parent[u] = find(parent[u]); // Compresión de ruta
        }
        return parent[u];
    }

    // Une dos conjuntos, los que contienen u y v
    public boolean union(int u, int v) {
        int rootU = find(u);
        int rootV = find(v);
        if (rootU != rootV) {
            // Unión por rango
            if (rank[rootU] > rank[rootV]) {
                parent[rootV] = rootU;
            } else if (rank[rootU] < rank[rootV]) {
                parent[rootU] = rootV;
            } else {
                parent[rootV] = rootU;
                rank[rootU]++;
            }
            return true; // Se realizó la unión
        }
        return false; // Ya están en el mismo conjunto
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int M = sc.nextInt();
        int K = sc.nextInt();

        int[] passCost = new int[K];
        for (int i = 0; i < K; i++) {
            passCost[i] = sc.nextInt();
        }

        List<int[]> portales = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            int l = sc.nextInt();

            int costoTotal = 0;
            // Sumar el costo de los pases requeridos
            for (int j = 0; j < l; j++) {
                int paseID = sc.nextInt() - 1;
                costoTotal += passCost[paseID];
            }

            // Añadir el portal a la lista (u, v, costo total)
            portales.add(new int[]{u, v, costoTotal});
        }
        sc.close();

        // Ordenar los portales por costo ascendente (para Kruskal)
        portales.sort(Comparator.comparingInt(a -> a[2]));

        locura_de_pases conjunto = new locura_de_pases(N);
        int minCost = 0;
        int edgesUsed = 0;

        for (int[] portal : portales) {
            int u = portal[0];
            int v = portal[1];
            int cost = portal[2];

            // Si unir u y v no forma un ciclo, se añade el portal al MST
            if (conjunto.union(u, v)) {
                minCost += cost;
                edgesUsed++;
            }

            if (edgesUsed == N - 1) {
                break;
            }
        }

        // Si no se han recorridos todas las atracciones, el MST no es posible
        if (edgesUsed != N - 1) {
            System.out.println(1);
        } else {
            System.out.println(minCost);
        }
    }
}



